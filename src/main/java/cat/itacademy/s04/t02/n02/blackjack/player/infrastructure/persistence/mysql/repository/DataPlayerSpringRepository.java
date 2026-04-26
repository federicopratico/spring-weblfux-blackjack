package cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.persistence.mysql.repository;

import cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.persistence.mysql.entity.PlayerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface DataPlayerSpringRepository extends ReactiveCrudRepository<PlayerEntity, String> {
    Mono<PlayerEntity> findByName(String name);
}
