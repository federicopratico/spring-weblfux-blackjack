package cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.request;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.PlayType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record MakePlayRequest(
        @NotNull
        PlayType playType,

        @Positive
        BigDecimal betAmount
) {
}
