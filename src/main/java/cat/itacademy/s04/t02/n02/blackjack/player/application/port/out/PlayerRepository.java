package cat.itacademy.s04.t02.n02.blackjack.player.application.port.out;

import cat.itacademy.s04.t02.n02.blackjack.player.domain.entity.Player;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerName;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PlayerRepository {
    Mono<Player> save(Player player);
    Mono<Player> findByName(PlayerName name);
    Mono<Player> findById(PlayerId playerId);
}
