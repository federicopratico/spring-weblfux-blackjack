package cat.itacademy.s04.t02.n02.blackjack.game.application.service;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.MakePlayCommand;
import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.PlayType;
import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.result.GameResult;
import cat.itacademy.s04.t02.n02.blackjack.game.application.exception.GameNotFoundException;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.in.MakePlayUseCase;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.out.GameRepository;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.out.PlayerBetReservationPort;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.entity.Game;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.service.PayoutCalculator;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.identity.GameId;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.playerreference.ResolvedPlayerReference;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;
import cat.itacademy.s04.t02.n02.blackjack.share.event.event.RoundFinishedEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.publisher.DomainEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class MakePlayService implements MakePlayUseCase {

    private final GameRepository gameRepository;
    private final PlayerBetReservationPort playerBetReservationPort;
    private final DomainEventPublisher domainEventPublisher;
    private final PayoutCalculator payoutCalculator;

    public MakePlayService(GameRepository gameRepository, PlayerBetReservationPort playerBetReservationPort, DomainEventPublisher domainEventPublisher) {
        this.gameRepository = gameRepository;
        this.playerBetReservationPort = playerBetReservationPort;
        this.domainEventPublisher = domainEventPublisher;
        this.payoutCalculator = new PayoutCalculator();
    }

    @Override
    public Mono<GameResult> execute(MakePlayCommand command) {
        return gameRepository.findById(GameId.of(command.gameId()))
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game " + command.gameId() + " not found")))
                .flatMap(game ->
                        applyPlay(command, game)
                                .then(Mono.fromSupplier(() -> continueRoundIfNeeded(game)))
                                .flatMap(roundFinished -> saveAndPublishIfNeeded(game, roundFinished))
                )
                .map(GameResult::from);
    }

    private Mono<Void> applyPlay(MakePlayCommand command, Game game) {
        if (command.playType() == PlayType.BET) {
            return placeBet(command, game);
        }

        if (command.playType() == PlayType.HIT) {
            game.hit();
            return Mono.empty();
        }

        if (command.playType() == PlayType.STAND) {
            game.stand();
            return Mono.empty();
        }

        return Mono.error(new IllegalArgumentException("Unsupported play type: " + command.playType()));
    }

    private Mono<Void> placeBet(MakePlayCommand command, Game game) {
        if (game.isRoundOver()) {
            game.clear();
        }

        PlayerId playerId = getResolvedPlayerId(game);

        return playerBetReservationPort.reserveBet(
                        game.getGameId(),
                        playerId,
                        command.betAmount()
                )
                .then(Mono.fromRunnable(() -> {
                    game.placeBet(command.betAmount());
                    game.deal();
                }));
    }

    private boolean continueRoundIfNeeded(Game game) {
        if (game.isDealerTurn()) {
            game.playDealerTurn();
        }

        if (game.isOutcomeReady()) {
            game.resolveRound();
            return true;
        }

        return false;
    }

    private Mono<Game> saveAndPublishIfNeeded(Game game, boolean roundFinished) {
        return gameRepository.save(game)
                .flatMap(savedGame -> {
                    if (!roundFinished) {
                        return Mono.just(savedGame);
                    }

                    return publishRoundFinishedEvent(savedGame)
                            .thenReturn(savedGame);
                });
    }

    private Mono<Void> publishRoundFinishedEvent(Game game) {
        return domainEventPublisher.publish(new RoundFinishedEvent(
                game.getGameId().toString(),
                getResolvedPlayerId(game).toString(),
                game.getSeat().getBet(),
                payoutCalculator.calculateProfit(game.getSeat().getBet(), game.getRoundOutcome()),
                game.getRoundOutcome().name(),
                Instant.now()
        ));
    }

    private PlayerId getResolvedPlayerId(Game game) {
        if (game.getPlayerReference() instanceof ResolvedPlayerReference(PlayerId playerId)) {
            return playerId;
        }

        throw new IllegalStateException("Player is not resolved");
    }
}


