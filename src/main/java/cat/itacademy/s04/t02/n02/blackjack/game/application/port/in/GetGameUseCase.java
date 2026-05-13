package cat.itacademy.s04.t02.n02.blackjack.game.application.port.in;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.view.GameView;
import reactor.core.publisher.Mono;

public interface GetGameUseCase {
    Mono<GameView> execute(String gameId);
}
