package cat.itacademy.s04.t02.n02.blackjack.game.application.service;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.GameCreateCommand;
import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.result.GameResult;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.in.CreateGameUseCase;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.out.GameRepository;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.entity.Game;
import cat.itacademy.s04.t02.n02.blackjack.share.event.event.PlayerResolutionRequestedEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.publisher.DomainEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@AllArgsConstructor
public class CreateGameService implements CreateGameUseCase {

    private GameRepository gameRepository;
    private DomainEventPublisher domainEventPublisher;

    @Override
    public Mono<GameResult> execute(GameCreateCommand command) {

        Game game = Game.create(command.playerName());

        return gameRepository.save(game)
                .flatMap(savedGame ->
                        domainEventPublisher.publish(
                            new PlayerResolutionRequestedEvent(
                                    savedGame.getGameId().toString(),
                                    command.playerName(),
                                    Instant.now()
                            )
                        ).thenReturn(savedGame)
                )
                .map(GameResult::from);
    }
}
