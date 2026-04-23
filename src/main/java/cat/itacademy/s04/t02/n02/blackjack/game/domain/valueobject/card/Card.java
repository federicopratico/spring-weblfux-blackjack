package cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.card;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Card {
    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public boolean isFaceCard() {
        return rank.isFace();
    }

    public boolean isAce() {
        return rank.isAce();
    }
}
