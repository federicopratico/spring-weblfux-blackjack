package cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.dealer;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.hand.Hand;

public class Dealer {

    private Hand hand;

    private Dealer() {
        this.hand = Hand.create();
    }

    private Dealer(Hand hand) {
        this.hand = hand;
    }

    public static Dealer create() {
        return new Dealer();
    }

    public static Dealer reconstruct(Hand hand) {
        return new Dealer(hand);
    }

    public Hand getHand() {
        return hand;
    }
}
