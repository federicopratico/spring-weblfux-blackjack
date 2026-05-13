package cat.itacademy.s04.t02.n02.blackjack.player.application.port.in;

import cat.itacademy.s04.t02.n02.blackjack.player.application.dto.command.ReserveBetCommand;
import reactor.core.publisher.Mono;

public interface ReserveBetUseCase {
    Mono<Void> execute(ReserveBetCommand command);
}
