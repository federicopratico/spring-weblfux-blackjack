package cat.itacademy.s04.t02.n02.blackjack.player.domain.entity;

import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerName;

import java.math.BigDecimal;

public class Player {
    private final PlayerId playerId;
    private PlayerName playerName;
    private BigDecimal deposit;
    private BigDecimal reservedDeposit;
    private int gamesPlayed;
    private int gamesWon;
    private int gamesLost;
    private int gamesDrawn;

    private Player(PlayerId playerId, PlayerName playerName, BigDecimal deposit, BigDecimal reservedDeposit, int gamesPlayed, int gamesWon, int gamesLost, int gamesDrawn) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.deposit = deposit;
        this.reservedDeposit = reservedDeposit;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.gamesDrawn = gamesDrawn;
    }

    public static Player create(String playerName) {
        return new Player(
                PlayerId.generateNewId(),
                PlayerName.create(playerName),
                BigDecimal.valueOf(500),
                BigDecimal.ZERO,
                0,
                0,
                0,
                0
        );
    }

    public static Player reconstruct(PlayerId playerId, PlayerName playerName, BigDecimal deposit, BigDecimal reservedDeposit, int gamesPlayed, int gamesWon, int gamesLost, int gamesDrawn) {
        return new Player(playerId, playerName, deposit, reservedDeposit, gamesPlayed, gamesWon, gamesLost, gamesDrawn);
    }

    public void reserveBet(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Bet amount must be greater than zero");
        }

        if (getAvailableDeposit().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient available deposit");
        }
        reservedDeposit = reservedDeposit.add(amount);
    }

    public BigDecimal getAvailableDeposit() {
        return deposit.subtract(reservedDeposit);
    }

    public void persistRound(BigDecimal reservedBetAmount, BigDecimal profitAmount, String roundOutcome) {
        if (reservedBetAmount == null || reservedBetAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Reserved bet amount must be greater than zero");
        }

        if (profitAmount == null || profitAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Profit amount cannot be negative");
        }

        if (reservedDeposit.compareTo(reservedBetAmount) < 0) {
            throw new IllegalStateException("Reserved deposit is lower than reserved bet amount");
        }

        if ("DEALER_WIN".equals(roundOutcome)) {
            consumeReservedBet(reservedBetAmount);
            registerLoss();
            return;
        }

        releaseReservedBet(reservedBetAmount);
        deposit = deposit.add(profitAmount);

        if ("PLAYER_WIN".equals(roundOutcome) || "PLAYER_BLACKJACK".equals(roundOutcome)) {
            registerWin();
            return;
        }

        if ("PUSH".equals(roundOutcome)) {
            registerPush();
            return;
        }

        throw new IllegalArgumentException("Unsupported round outcome: " + roundOutcome);
    }

    private void releaseReservedBet(BigDecimal amount) {
        reservedDeposit = reservedDeposit.subtract(amount);
    }

    private void consumeReservedBet(BigDecimal amount) {
        reservedDeposit = reservedDeposit.subtract(amount);
        deposit = deposit.subtract(amount);
    }

    private void registerWin() {
        gamesPlayed++;
        gamesWon++;
    }

    private void registerLoss() {
        gamesPlayed++;
        gamesLost++;
    }

    private void registerPush() {
        gamesPlayed++;
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

    public BigDecimal getReservedDeposit() {
        return reservedDeposit;
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

    public int getGamesDrawn() {
        return gamesDrawn;
    }
}
