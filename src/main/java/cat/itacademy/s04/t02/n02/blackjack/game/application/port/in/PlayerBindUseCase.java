package cat.itacademy.s04.t02.n02.blackjack.game.application.port.in;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.BindPlayerCommand;
import reactor.core.publisher.Mono;

public interface PlayerBindUseCase {
    Mono<Void> execute(BindPlayerCommand command);
}
