package cat.itacademy.s04.t02.n02.blackjack.game.application.service;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.BindPlayerCommand;
import cat.itacademy.s04.t02.n02.blackjack.game.application.exception.GameNotFoundException;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.in.PlayerBindUseCase;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.out.GameRepository;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.identity.GameId;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GamePlayerBinderService implements PlayerBindUseCase {

    private final GameRepository gameRepository;

    public GamePlayerBinderService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Mono<Void> execute(BindPlayerCommand command) {
        return gameRepository.findById(GameId.of(command.gameId()))
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game " + command.gameId() + " not found")))
                .flatMap(game -> {
                    game.resolvePlayer(PlayerId.of(command.playerId()));
                    return gameRepository.save(game).then();
                });
    }




}
