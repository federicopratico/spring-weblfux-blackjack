package cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.persistence.mongo.repository;

import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.persistence.mongo.document.GameDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface MongoGameSpringRepository extends ReactiveMongoRepository<GameDocument, String> {
}
