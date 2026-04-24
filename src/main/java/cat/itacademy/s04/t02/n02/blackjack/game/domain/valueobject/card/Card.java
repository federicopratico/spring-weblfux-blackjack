package cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.card;

import java.util.Objects;

public class Card {
    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }

    public boolean isFaceCard() {
        return rank.isFace();
    }

    public boolean isAce() {
        return rank.isAce();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return getSuit() == card.getSuit() && getRank() == card.getRank();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSuit(), getRank());
    }
}
