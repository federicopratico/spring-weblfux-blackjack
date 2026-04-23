package cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.playerreference;

import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;

public record ResolvedPlayerReference(PlayerId playerId) implements PlayerReference {

    public ResolvedPlayerReference {
        if (playerId == null)
            throw new IllegalArgumentException("playerId must not be null");
    }
}
