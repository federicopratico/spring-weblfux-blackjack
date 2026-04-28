package cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.shoe;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.exception.EmptyShoeException;
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

    private Shoe(List<Card> cards, int initialShoeSize) {
        this.cards = new LinkedList<>(cards);
        this.initialShoeSize = initialShoeSize;
    }

    public static Shoe create() {
        return new Shoe(2);
    }

    public static Shoe create(int numberOfDecks) {
        return new Shoe(numberOfDecks);
    }

    public static Shoe reconstruct(List<Card> cards, int initialShoeSize) {
        return new Shoe(cards, initialShoeSize);
    }

    public Card draw() {
        return tryDraw().orElseThrow(() -> new EmptyShoeException("No cards left in the shoe"));
    }

    private Optional<Card> tryDraw() {
        if(cards.isEmpty())
            return Optional.empty();

        return Optional.of(cards.removeFirst());
    }

    public boolean isCutReached() {
        return cards.size() <= CUT_THRESHOLD * initialShoeSize;
    }

    public List<Card> getCards() {
        return List.copyOf(cards);
    }

    public int getInitialShoeSize() {
        return initialShoeSize;
    }
}
