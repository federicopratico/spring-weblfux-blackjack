package cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.hand;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.card.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Card> cards;

    private Hand() {
        cards = new ArrayList<>();
    }

    private Hand(List<Card> cards) {
        this.cards = new ArrayList<>(cards);
    }

    public static Hand create() {
        return new Hand();
    }

    public static Hand reconstruct(List<Card> cards) {
        return new Hand(cards);
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<Card> getHand() {
        return List.copyOf(cards);
    }

    public int getHandValue() {
        int handValue = 0;
        int aces = 0;

        for(Card card : cards) {
            handValue += card.getRank().getNumericValue();
            if(card.isAce())
                aces++;
        }

        while(handValue > 21 && aces > 0) {
            handValue -= 10;
            aces--;
        }

        return handValue;
    }

    public boolean isTwentyOne() {
        return getHandValue() == 21;
    }

    public boolean isBusted() {
        return getHandValue() > 21;
    }

    public boolean isBlackJack() {
        return (cards.size() == 2 && getHandValue() == 21);
    }

    public void clear() {
        cards.clear();
    }
}
