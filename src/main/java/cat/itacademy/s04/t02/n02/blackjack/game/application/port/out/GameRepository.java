package cat.itacademy.s04.t02.n02.blackjack.game.application.port.out;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.entity.Game;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.indentity.GameId;
import reactor.core.publisher.Mono;

public interface GameRepository {
    Mono<Game> save(Game game);
    Mono<Game> findById(GameId gameId);
    Mono<Boolean> deleteById(GameId gameId);
}