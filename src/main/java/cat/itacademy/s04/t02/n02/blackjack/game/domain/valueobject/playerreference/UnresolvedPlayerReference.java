package cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.playerreference;

public record UnresolvedPlayerReference(String playerName) implements PlayerReference {

    public UnresolvedPlayerReference {
        if (playerName == null || playerName.isBlank())
            throw new IllegalArgumentException("playerName must not be blank");
    }
}
