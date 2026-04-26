package cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity;

public record PlayerName(String name) {

    public PlayerName {
        if(name == null || name.isBlank())
            throw new IllegalArgumentException("Player name cannot be empty");
    }

    public static PlayerName create(String name) {
        return new PlayerName(name);
    }

}
