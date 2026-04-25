package cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeleteGameRequest(
        @NotNull @NotBlank(message = "Provide a gameId to delete a game")
        String gameId
) {}
