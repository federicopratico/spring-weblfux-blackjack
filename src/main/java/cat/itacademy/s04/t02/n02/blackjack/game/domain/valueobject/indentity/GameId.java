package cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.indentity;

import java.util.UUID;

public record GameId(UUID uuid) {

    public static GameId generateNewId() {
        return new GameId(UUID.randomUUID());
    }
}
