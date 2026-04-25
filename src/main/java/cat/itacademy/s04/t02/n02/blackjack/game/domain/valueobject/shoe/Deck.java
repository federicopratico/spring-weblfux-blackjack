package cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.shoe;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.card.Card;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.card.Rank;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.card.Suit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private static final List<Card> CARDS = createDeck();

    private static List<Card> createDeck() {
        List<Card> cardList = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cardList.add(new Card(suit, rank));
            }
        }

        return Collections.unmodifiableList(cardList);
    }

    public static List<Card> getCards() {
        return CARDS;
    }
}
