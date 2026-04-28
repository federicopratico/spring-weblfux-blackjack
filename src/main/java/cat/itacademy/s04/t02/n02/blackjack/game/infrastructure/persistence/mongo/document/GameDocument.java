package cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.persistence.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "games")
public record GameDocument(
        @Id
        String gameId,
        DealerDocument dealer,
        SeatDocument seat,
        ShoeDocument shoe,
        String gameStatus,
        String roundOutcome
) {}
