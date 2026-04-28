package cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.mapper;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.DeleteGameCommand;
import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.GameCreateCommand;
import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.MakePlayCommand;
import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.result.GameResult;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.request.DeleteGameRequest;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.request.GameCreateRequest;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.request.MakePlayRequest;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.response.GameCreateResponse;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.response.MakePlayResponse;
import org.springframework.stereotype.Component;

@Component
public class GameWebMapper {

    public MakePlayCommand toCommand(String gameId, MakePlayRequest request) {
        return new MakePlayCommand(
                gameId,
                request.playType(),
                request.betAmount()
        );
    }

    public MakePlayResponse toMakePlayResponse(GameResult result) {
        return new MakePlayResponse(
                result.gameId(),
                result.gameStatus(),
                result.roundOutcome(),
                result.bet(),
                result.playerStatus(),
                result.playerHandValue(),
                result.dealerHandValue()
        );
    }

    public GameCreateCommand toCommand(GameCreateRequest request) {
        return new GameCreateCommand(request.playerName());
    }

    public GameCreateResponse toResponse(GameResult result) {
        return new GameCreateResponse(
                result.gameId(),
                result.gameStatus());
    }

    public DeleteGameCommand toCommand(DeleteGameRequest request) {
        return new DeleteGameCommand(request.gameId());
    }
}
