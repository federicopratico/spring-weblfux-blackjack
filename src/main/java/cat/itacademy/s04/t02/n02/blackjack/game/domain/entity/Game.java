package cat.itacademy.s04.t02.n02.blackjack.game.domain.entity;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.GameStatus;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.dealer.Dealer;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.indentity.GameId;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.seat.Seat;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.shoe.Shoe;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;
import lombok.Getter;

@Getter
public class Game {
    private final GameId gameId;
    private Dealer dealer;
    private Seat seat;
    private Shoe shoe;
    private GameStatus gameStatus;

    public Game(GameId gameId, Dealer dealer, Shoe shoe,String playerName) {
        this.gameId = gameId;
        this.dealer = dealer;
        this.shoe = shoe;
        this.seat = Seat.withUnresolvedPlayer(playerName);
        this.gameStatus = GameStatus.PENDING_PLAYER;
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

}
