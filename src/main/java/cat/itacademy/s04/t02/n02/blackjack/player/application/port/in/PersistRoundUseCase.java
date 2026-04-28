package cat.itacademy.s04.t02.n02.blackjack.player.application.port.in;

import cat.itacademy.s04.t02.n02.blackjack.player.application.command.PersistRoundCommand;
import reactor.core.publisher.Mono;

public interface PersistRoundUseCase {
    Mono<Void> execute(PersistRoundCommand command);
}
