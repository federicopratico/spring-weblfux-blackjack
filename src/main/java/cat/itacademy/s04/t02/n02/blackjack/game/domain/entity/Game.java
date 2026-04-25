package cat.itacademy.s04.t02.n02.blackjack.game.domain.entity;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.GameStatus;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.dealer.Dealer;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.indentity.GameId;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.seat.Seat;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.shoe.Shoe;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;

public class Game {
    private final GameId gameId;
    private Dealer dealer;
    private Seat seat;
    private Shoe shoe;
    private GameStatus gameStatus;

    private Game(GameId gameId, Dealer dealer, Seat seat, Shoe shoe, GameStatus gameStatus) {
        this.gameId = gameId;
        this.dealer = dealer;
        this.seat = seat;
        this.shoe = shoe;
        this.gameStatus = gameStatus;
    }

    public static Game create(String playerName) {
        return new Game(
                GameId.generateNewId(),
                Dealer.create(),
                Seat.withUnresolvedPlayer(playerName),
                Shoe.create(),
                GameStatus.PENDING_PLAYER
        );
    }

    public static Game reconstruct(GameId gameId, Dealer dealer, Seat seat, Shoe shoe, GameStatus gameStatus) {
        return new Game(
                gameId,
                dealer,
                seat,
                shoe,
                gameStatus
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
}
