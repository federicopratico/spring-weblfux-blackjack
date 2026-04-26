package cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.persistence.mongo.adapter;

import cat.itacademy.s04.t02.n02.blackjack.game.application.port.out.GameRepository;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.entity.Game;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.identity.GameId;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.persistence.mongo.document.GameDocument;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.persistence.mongo.mapper.GamePersistenceMapper;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.persistence.mongo.repository.MongoGameSpringRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class MongoGameRepositoryAdapter implements GameRepository {

    private final MongoGameSpringRepository gameSpringRepository;
    private final GamePersistenceMapper gamePersistenceMapper;
    private final ReactiveMongoTemplate mongoTemplate;

    @Override
    public Mono<Game> save(Game game) {
//        return gameSpringRepository.save(gamePersistenceMapper.toDocument(game))
//                .map(gamePersistenceMapper::toDomain);

        return Mono.just(game)
                .map(gamePersistenceMapper::toDocument)
                .flatMap(gameSpringRepository::save)
                .map(gamePersistenceMapper::toDomain);
    }

    @Override
    public Mono<Game> findById(GameId gameId) {
        String gameIdReference = gameId.toString();

        return gameSpringRepository.findById(gameIdReference)
                .map(gamePersistenceMapper::toDomain);
    }

    @Override
    public Mono<Boolean> deleteById(GameId gameId) {

        Query query = Query.query(Criteria.where("_id").is(gameId.toString()));

        return mongoTemplate.remove(query, GameDocument.class)
                .map(deleteResult -> deleteResult.getDeletedCount() > 0);
    }
}
