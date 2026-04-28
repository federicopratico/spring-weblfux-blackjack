package cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.web.dto.reponse;

import java.math.BigDecimal;

public record PlayerRankingResponse(
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
