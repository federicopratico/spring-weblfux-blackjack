package cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.mapper;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.GameCreateCommand;
import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.result.GameResult;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.request.GameCreateRequest;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.response.GameCreateResponse;
import org.springframework.stereotype.Component;

@Component
public class GameWebMapper {

    public GameCreateCommand toCommand(GameCreateRequest request) {
        return new GameCreateCommand(request.playerName());
    }

    public GameCreateResponse toResponse(GameResult result) {
        return new GameCreateResponse(
                result.gameId(),
                result.gameStatus());
    }
}
