package cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.card;

import lombok.Getter;

@Getter
public enum Rank {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(10),
    QUEEN(10),
    KING(10),
    ACE(11);

    private int numericValue;

    Rank(int numericValue) {
        this.numericValue = numericValue;
    }

    boolean isFace() {
        return this == JACK || this == QUEEN || this == KING;
    }

    boolean isAce() {
        return this == ACE;
    }
}
