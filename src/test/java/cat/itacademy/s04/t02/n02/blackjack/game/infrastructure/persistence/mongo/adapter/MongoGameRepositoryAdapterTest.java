package cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.persistence.mongo.adapter;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.indentity.GameId;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.persistence.mongo.document.GameDocument;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.persistence.mongo.mapper.GamePersistenceMapper;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.persistence.mongo.repository.MongoGameSpringRepository;
import com.mongodb.client.result.DeleteResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MongoGameRepositoryAdapterTest {

    @Mock
    private MongoGameSpringRepository gameSpringRepository;

    @Mock
    private GamePersistenceMapper gamePersistenceMapper;

    @Mock
    private ReactiveMongoTemplate mongoTemplate;

    private MongoGameRepositoryAdapter mongoGameRepositoryAdapter;

    @BeforeEach
    void setUp() {
        mongoGameRepositoryAdapter = new MongoGameRepositoryAdapter(
                gameSpringRepository,
                gamePersistenceMapper,
                mongoTemplate
        );
    }

    @Test
    void deleteByIdShouldReturnTrueWhenMongoDeletesOneDocument() {
        GameId gameId = GameId.generateNewId();

        when(mongoTemplate.remove(any(Query.class), eq(GameDocument.class)))
                .thenReturn(Mono.just(DeleteResult.acknowledged(1)));

        StepVerifier.create(mongoGameRepositoryAdapter.deleteById(gameId))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void deleteByIdShouldReturnFalseWhenMongoDeletesNoDocuments() {
        GameId gameId = GameId.generateNewId();

        when(mongoTemplate.remove(any(Query.class), eq(GameDocument.class)))
                .thenReturn(Mono.just(DeleteResult.acknowledged(0)));

        StepVerifier.create(mongoGameRepositoryAdapter.deleteById(gameId))
                .expectNext(false)
                .verifyComplete();
    }
}
