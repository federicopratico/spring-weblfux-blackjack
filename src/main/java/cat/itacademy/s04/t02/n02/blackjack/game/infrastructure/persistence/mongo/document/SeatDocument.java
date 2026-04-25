package cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.persistence.mongo.document;

import java.math.BigDecimal;

public record SeatDocument(
        PlayerReferenceDocument playerReference,
        HandDocument hand,
        BigDecimal bet,
        String playerStatus
) {
}
