package cat.itacademy.s04.t02.n02.blackjack.player.application.dto.command.view;

import java.math.BigDecimal;

public record PlayerRankingView(
        int position,
        String playerId,
        String name,
        int gamesPlayed,
        int gamesWon,
        int gamesLost,
        int gamesDrawn,
        BigDecimal score,
        BigDecimal deposit
) {
}
