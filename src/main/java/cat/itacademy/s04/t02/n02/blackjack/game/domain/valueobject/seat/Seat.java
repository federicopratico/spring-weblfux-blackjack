package cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.seat;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.card.Card;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.hand.Hand;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.playerreference.PlayerReference;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.playerreference.ResolvedPlayerReference;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.playerreference.UnresolvedPlayerReference;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;

import java.math.BigDecimal;

public class Seat {
    private PlayerReference playerReference;
    private Hand hand;
    private BigDecimal bet;
    private PlayerStatus playerStatus;

    private Seat(PlayerReference playerReference, Hand hand, BigDecimal bet, PlayerStatus playerStatus) {
        this.playerReference = playerReference;
        this.hand = hand;
        this.bet = bet;
        this.playerStatus = playerStatus;
    }

    public static Seat withUnresolvedPlayer(String playerName) {
        PlayerReference unresolvedPlayerReference = new UnresolvedPlayerReference(playerName);
        return new Seat(
                unresolvedPlayerReference,
                Hand.create(),
                BigDecimal.ZERO,
                PlayerStatus.WAITING_FOR_BET);
    }

    public static Seat reconstruct(PlayerReference playerReference, Hand hand, BigDecimal bet, PlayerStatus playerStatus) {
        return new Seat(
                playerReference,
                hand,
                bet,
                playerStatus);
    }

    public void resolvePlayer(PlayerId playerId) {
        if (playerReference instanceof ResolvedPlayerReference)
            return;

        playerReference = new ResolvedPlayerReference(playerId);
    }

    public void finishTurn() {
        if(playerStatus != PlayerStatus.ACTING)
            throw new IllegalStateException("Player is not acting");

        playerStatus = PlayerStatus.TURN_ENDED;
    }

    public void receiveCard(Card card) {
        hand.addCard(card);
    }

    public void placeBet(BigDecimal betAmount) {
        if(playerStatus != PlayerStatus.WAITING_FOR_BET)
            throw new IllegalStateException("Player is not waiting for bet");

        if(betAmount == null || betAmount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Bet amount must be greater than zero");

        this.bet = betAmount;
        this.playerStatus = PlayerStatus.ACTING;
    }

    public void clear() {
        if(playerStatus != PlayerStatus.TURN_ENDED)
            throw new IllegalStateException("Player is not in a resettable state");

        this.hand.clear();
        this.playerStatus = PlayerStatus.WAITING_FOR_BET;
        this.bet = BigDecimal.ZERO;
    }

    public boolean hasResolvedPlayer() {
        return playerReference instanceof ResolvedPlayerReference;
    }

    public boolean isBusted() {
        return hand.isBusted();
    }

    public boolean isBlackJack() {
        return hand.isBlackJack();
    }

    public PlayerReference getPlayerReference() {
        return playerReference;
    }

    public Hand getHand() {
        return hand;
    }

    public BigDecimal getBet() {
        return bet;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }
}
