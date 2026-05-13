package cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.web.mapper;

import cat.itacademy.s04.t02.n02.blackjack.player.application.dto.command.view.PlayerRankingView;
import cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.web.dto.reponse.PlayerRankingResponse;
import org.springframework.stereotype.Component;

@Component
public class PlayerWebMapper {

    public PlayerRankingResponse toResponse(PlayerRankingView view) {
        return new PlayerRankingResponse(
                view.position(),
                view.playerId(),
                view.name(),
                view.gamesPlayed(),
                view.gamesWon(),
                view.gamesLost(),
                view.gamesDrawn(),
                view.score(),
                view.deposit()
        );
    }
}
