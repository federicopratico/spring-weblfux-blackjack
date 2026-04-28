package cat.itacademy.s04.t02.n02.blackjack.game.application.service;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.view.GameView;
import cat.itacademy.s04.t02.n02.blackjack.game.application.exception.GameNotFoundException;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.in.GetGameUseCase;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.out.GameRepository;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.identity.GameId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class GetGameService implements GetGameUseCase {

    private final GameRepository gameRepository;

    @Override
    public Mono<GameView> execute(String gameId) {

        return gameRepository.findById(GameId.of(gameId))
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game " + gameId + " not found")))
                .map(GameView::from);
    }
}
