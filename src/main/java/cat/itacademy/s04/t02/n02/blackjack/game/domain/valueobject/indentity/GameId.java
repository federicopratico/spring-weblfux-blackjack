package cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.indentity;

import java.util.UUID;

public record GameId(UUID uuid) {

    public static GameId generateNewId() {
        return new GameId(UUID.randomUUID());
    }

    public static GameId of(String uuid) {
        return new GameId(UUID.fromString(uuid));
    }

    public String toString() {
        return uuid.toString();
    }
}
