package cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.card;

import lombok.Getter;

@Getter
public enum Suit {
    HEARTS("Hearts"),
    DIAMONDS("Diamonds"),
    CLUBS("Clubs"),
    SPADES("Spades");

    private String name;

    Suit(String name) {
        this.name = name;
    }
}
