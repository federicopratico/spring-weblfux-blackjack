package cat.itacademy.s04.t02.n02.blackjack.game.application.service;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.DeleteGameCommand;
import cat.itacademy.s04.t02.n02.blackjack.game.application.exception.GameNotFoundException;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.in.DeleteGameUseCase;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.out.GameRepository;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.indentity.GameId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class DeleteGameService implements DeleteGameUseCase {

    private GameRepository gameRepository;

    @Override
    public Mono<Void> execute(DeleteGameCommand command) {
        GameId gameId = GameId.of(command.gameId());

        return gameRepository.deleteById(gameId)
                .flatMap(deleted -> {
                    if(!deleted)
                        return Mono.error(new GameNotFoundException( "Game " + gameId + " does not exist"));
                    return Mono.empty();
                });
    }
}
