package cat.itacademy.s04.t02.n02.blackjack.game.application.port.in;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.MakePlayCommand;
import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.result.GameResult;
import reactor.core.publisher.Mono;

public interface MakePlayUseCase {
    Mono<GameResult> execute(MakePlayCommand command);
}
