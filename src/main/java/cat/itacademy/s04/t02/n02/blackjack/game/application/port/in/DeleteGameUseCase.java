package cat.itacademy.s04.t02.n02.blackjack.game.application.port.in;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.DeleteGameCommand;
import reactor.core.publisher.Mono;

public interface DeleteGameUseCase {
    Mono<Void> execute(DeleteGameCommand command);
}
