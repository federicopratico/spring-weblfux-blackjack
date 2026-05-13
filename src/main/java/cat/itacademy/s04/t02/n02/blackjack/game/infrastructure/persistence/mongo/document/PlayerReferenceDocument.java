package cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.persistence.mongo.document;

public record PlayerReferenceDocument(
        String referenceType,
        String playerId,
        String playerName
) {}
