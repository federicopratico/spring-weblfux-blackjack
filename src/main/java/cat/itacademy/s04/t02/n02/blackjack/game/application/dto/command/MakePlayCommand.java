package cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command;

import java.math.BigDecimal;

public record MakePlayCommand(
        String gameId,
        PlayType playType,
        BigDecimal betAmount
) {
}
