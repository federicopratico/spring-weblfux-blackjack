package cat.itacademy.s04.t02.n02.blackjack.player.application.port.in;

import cat.itacademy.s04.t02.n02.blackjack.player.application.dto.command.ResolvePlayerCommand;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.entity.Player;
import reactor.core.publisher.Mono;

public interface ResolvePlayerUseCase {
    Mono<Player> execute(ResolvePlayerCommand command);
}
