package cat.itacademy.s04.t02.n02.blackjack.player.application.service;

import cat.itacademy.s04.t02.n02.blackjack.player.application.dto.command.ResolvePlayerCommand;
import cat.itacademy.s04.t02.n02.blackjack.player.application.port.in.ResolvePlayerUseCase;
import cat.itacademy.s04.t02.n02.blackjack.player.application.port.out.PlayerRepository;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.entity.Player;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerName;
import cat.itacademy.s04.t02.n02.blackjack.share.event.event.PlayerResolutionFailedEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.event.PlayerResolvedEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.publisher.DomainEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@AllArgsConstructor
public class ResolvePlayerService implements ResolvePlayerUseCase {

    private final PlayerRepository playerRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public Mono<Player> execute(ResolvePlayerCommand command) {
        PlayerName playerName = PlayerName.create(command.playerName());

        return playerRepository.findByName(playerName)
                .switchIfEmpty(Mono.defer(() ->
                        playerRepository.save(Player.create(playerName.name()))
                                .onErrorResume(
                                        this::isDuplicateKeyError,
                                        error -> playerRepository.findByName(playerName)
                                )
                ))
                .flatMap(player ->
                        publishResolvedEvent(command.gameId(), player)
                                .thenReturn(player)
                )
                .onErrorResume(error ->
                        publishFailedEvent(command.gameId(), command.playerName(), error)
                                .then(Mono.error(error)));
    }

    private boolean isDuplicateKeyError(Throwable error) {
        return error instanceof org.springframework.dao.DataIntegrityViolationException ||
                error instanceof org.springframework.r2dbc.BadSqlGrammarException;
    }

    private Mono<Void> publishResolvedEvent(String gameId, Player player) {
        return domainEventPublisher.publish(new PlayerResolvedEvent(
                gameId,
                player.getPlayerId().toString(),
                player.getPlayerName().name(),
                Instant.now()
        ));
    }

    private Mono<Void> publishFailedEvent(String gameId, String playerName, Throwable error) {
        return domainEventPublisher.publish(new PlayerResolutionFailedEvent(
                gameId,
                playerName,
                error.getMessage(),
                Instant.now()
        ));
    }
}
