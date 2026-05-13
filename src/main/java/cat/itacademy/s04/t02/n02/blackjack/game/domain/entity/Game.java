package cat.itacademy.s04.t02.n02.blackjack.game.domain.entity;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.GameStatus;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.RoundOutcome;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.dealer.Dealer;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.identity.GameId;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.playerreference.PlayerReference;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.seat.Seat;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.shoe.Shoe;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;

import java.math.BigDecimal;

public class Game {
    private final GameId gameId;
    private Dealer dealer;
    private Seat seat;
    private Shoe shoe;
    private GameStatus gameStatus;
    private RoundOutcome roundOutcome;

    private Game(GameId gameId, Dealer dealer, Seat seat, Shoe shoe, GameStatus gameStatus, RoundOutcome roundOutcome) {
        this.gameId = gameId;
        this.dealer = dealer;
        this.seat = seat;
        this.shoe = shoe;
        this.gameStatus = gameStatus;
        this.roundOutcome = roundOutcome;
    }

    public static Game create(String playerName) {
        return new Game(
                GameId.generateNewId(),
                Dealer.create(),
                Seat.withUnresolvedPlayer(playerName),
                Shoe.create(),
                GameStatus.PENDING_PLAYER,
                RoundOutcome.PLAYING
        );
    }

    public static Game reconstruct(GameId gameId, Dealer dealer, Seat seat, Shoe shoe, GameStatus gameStatus, RoundOutcome roundOutcome) {
        return new Game(
                gameId,
                dealer,
                seat,
                shoe,
                gameStatus,
                roundOutcome
        );
    }

    public void resolvePlayer(PlayerId playerId) {
        if (gameStatus != GameStatus.PENDING_PLAYER)
            throw new IllegalStateException("Game is not waiting for player resolution");

        seat.resolvePlayer(playerId);
        gameStatus = GameStatus.WAITING_FOR_BETS;
    }

    public boolean isPlayerResolved() {
        return seat.hasResolvedPlayer();
    }

    public void placeBet(BigDecimal betAmount) {
        if(gameStatus != GameStatus.WAITING_FOR_BETS)
            throw new IllegalStateException("Game is not waiting for bets");

        seat.placeBet(betAmount);
        gameStatus = GameStatus.DEALING;
    }

    public void deal() {
        if (gameStatus != GameStatus.DEALING)
            throw new IllegalStateException("Game is not in the DEALING state");

        dealInitialCards();

        if (seat.isBlackJack()) {
            seat.finishTurn();
            gameStatus = GameStatus.DEALER_TURN;
            return;
        }

        gameStatus = GameStatus.PLAYERS_TURN;
    }

    public void hit () {
        if (gameStatus != GameStatus.PLAYERS_TURN)
            throw new IllegalStateException("Game is not in the PLAYERS_TURN state");

        seat.receiveCard(shoe.draw());

        if(seat.isBusted()) {
            seat.finishTurn();
            gameStatus = GameStatus.OUTCOME;
            return;
        }

        if(seat.getHand().isTwentyOne()) {
            seat.finishTurn();
            gameStatus = GameStatus.DEALER_TURN;
        }
    }

    public void stand () {
        if (gameStatus != GameStatus.PLAYERS_TURN)
            throw new IllegalStateException("Game is not in the PLAYERS_TURN state");

        seat.finishTurn();
        gameStatus = GameStatus.DEALER_TURN;
    }

    public void playDealerTurn() {
        if (gameStatus != GameStatus.DEALER_TURN)
            throw new IllegalStateException("Game is not in the DEALER_TURN state");

        while(dealer.getHand().getHandValue() < 17) {
            dealer.receiveCard(shoe.draw());
        }

        gameStatus = GameStatus.OUTCOME;
    }

    public void resolveRound() {
        if (gameStatus != GameStatus.OUTCOME)
            throw new IllegalStateException("Game is not in the OUTCOME state");

        evaluateOutcome();
        gameStatus = GameStatus.ROUND_OVER;
    }

    public void clear() {
        if (gameStatus != GameStatus.ROUND_OVER)
            throw new IllegalStateException("Game is not in the ROUND_OVER state");

        seat.clear();
        dealer.clear();
        gameStatus = GameStatus.WAITING_FOR_BETS;
        roundOutcome = RoundOutcome.PLAYING;
    }

    public boolean isDealerTurn() {
        return gameStatus == GameStatus.DEALER_TURN;
    }

    public boolean isOutcomeReady() {
        return gameStatus == GameStatus.OUTCOME;
    }

    public boolean isRoundOver() {
        return gameStatus == GameStatus.ROUND_OVER;
    }

    private void dealInitialCards() {
        if (gameStatus != GameStatus.DEALING)
            throw new IllegalStateException("Game is not in the DEALING state");

        seat.receiveCard(shoe.draw());
        dealer.receiveCard(shoe.draw());
        seat.receiveCard(shoe.draw());
        dealer.receiveCard(shoe.draw());
    }

    private void evaluateOutcome() {
        boolean playerBlackjack = seat.isBlackJack();
        boolean dealerBlackjack = dealer.getHand().isBlackJack();

        if (playerBlackjack && dealerBlackjack) {
            roundOutcome = RoundOutcome.PUSH;
            return;
        }

        if (playerBlackjack) {
            roundOutcome = RoundOutcome.PLAYER_BLACKJACK;
            return;
        }

        if (seat.isBusted()) {
            roundOutcome = RoundOutcome.DEALER_WIN;
            return;
        }

        if (dealer.getHand().isBusted()) {
            roundOutcome = RoundOutcome.PLAYER_WIN;
            return;
        }

        int playerHandValue = seat.getHand().getHandValue();
        int dealerHandValue = dealer.getHand().getHandValue();

        if (playerHandValue > dealerHandValue) {
            roundOutcome = RoundOutcome.PLAYER_WIN;
            return;
        }

        if (playerHandValue < dealerHandValue) {
            roundOutcome = RoundOutcome.DEALER_WIN;
            return;
        }

        roundOutcome = RoundOutcome.PUSH;
    }

    public PlayerReference getPlayerReference() {
        return seat.getPlayerReference();
    }

    public GameId getGameId() {
        return gameId;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public Seat getSeat() {
        return seat;
    }

    public Shoe getShoe() {
        return shoe;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public RoundOutcome getRoundOutcome() {
        return roundOutcome;
    }
}
