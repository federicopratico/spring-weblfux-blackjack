package cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.seat;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.hand.Hand;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.playerreference.PlayerReference;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.playerreference.ResolvedPlayerReference;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.playerreference.UnresolvedPlayerReference;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
                new Hand(),
                BigDecimal.ZERO,
                PlayerStatus.WAITING_FOR_BET);
    }

    public void resolvePlayer(PlayerId playerId) {
        if (playerReference instanceof ResolvedPlayerReference)
            return;

        playerReference = new ResolvedPlayerReference(playerId);
    }

    public boolean hasResolvedPlayer() {
        return playerReference instanceof ResolvedPlayerReference;
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
