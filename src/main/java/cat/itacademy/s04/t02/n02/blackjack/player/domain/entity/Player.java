package cat.itacademy.s04.t02.n02.blackjack.player.domain.entity;

import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerName;

import java.math.BigDecimal;

public class Player {
    private final PlayerId playerId;
    private PlayerName playerName;
    private BigDecimal deposit;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;

    private Player(PlayerId playerId, PlayerName playerName, BigDecimal deposit, int gamesPlayed, int gamesWon, int gamesLost) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.deposit = deposit;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
    }

    public static Player create(String playerName) {
        return new Player(
                PlayerId.generateNewId(),
                PlayerName.create(playerName),
                BigDecimal.valueOf(500),
                0,
                0,
                0
        );
    }

    public static Player reconstruct(PlayerId playerId, PlayerName playerName, BigDecimal deposit, int gamesPlayed, int gamesWon, int gamesLost) {
        return new Player(playerId, playerName, deposit, gamesPlayed, gamesWon, gamesLost);
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public PlayerName getPlayerName() {
        return playerName;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getGamesLost() {
        return gamesLost;
    }
}
