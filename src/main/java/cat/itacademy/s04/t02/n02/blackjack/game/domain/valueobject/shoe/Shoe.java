package cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.shoe;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.card.Card;

import java.util.*;

public class Shoe {

    static final private double CUT_THRESHOLD = 0.20;

    private List<Card> cards;
    private int initialShoeSize;

    private Shoe(int numberOfDecks) {
        List<Card> tempCards = new ArrayList<>();

        for (int i = 0; i < numberOfDecks; i++) {
            tempCards.addAll(Deck.getCards());
        }

        Collections.shuffle(tempCards);
        this.cards = new LinkedList<>(tempCards);
        this.initialShoeSize = cards.size();
    }

    public static Shoe create() {
        return new Shoe(2);
    }

    public static Shoe create(int numberOfDecks) {
        return new Shoe(numberOfDecks);
    }

    public Optional<Card> draw() {
        if(cards.isEmpty())
            return Optional.empty();

        return Optional.of(cards.removeFirst());
    }

    public boolean isCutReached() {
        return cards.size() <= CUT_THRESHOLD * initialShoeSize;
    }
}
