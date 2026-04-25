package cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record GameCreateRequest(
        @NotNull @NotBlank(message = "provide a player name to create a new game")
        String playerName
) {
}
