package cat.itacademy.s04.t02.n02.blackjack.player.application.port.in;

import cat.itacademy.s04.t02.n02.blackjack.player.application.dto.command.view.PlayerRankingView;
import reactor.core.publisher.Flux;

public interface GetPlayersRankingUseCase {
    Flux<PlayerRankingView> execute();
}
