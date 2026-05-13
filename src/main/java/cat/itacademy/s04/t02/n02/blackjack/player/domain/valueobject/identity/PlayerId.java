package cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity;

import java.util.UUID;

public record PlayerId(UUID uuid) {

    public static PlayerId generateNewId() {
        return new PlayerId(UUID.randomUUID());
    }

    public static PlayerId of(String uuid) {
        return new PlayerId(UUID.fromString(uuid));
    }

    @Override
    public String toString() {
        return uuid.toString();
    }
}
