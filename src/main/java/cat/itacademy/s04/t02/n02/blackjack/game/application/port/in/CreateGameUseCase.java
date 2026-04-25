package cat.itacademy.s04.t02.n02.blackjack.game.application.port.in;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.GameCreateCommand;
import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.result.GameResult;
import reactor.core.publisher.Mono;

public interface CreateGameUseCase {
    Mono<GameResult> execute(GameCreateCommand command);
}
