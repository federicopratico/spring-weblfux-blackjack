package cat.itacademy.s04.t02.n02.blackjack.game.application.service;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.DeleteGameCommand;
import cat.itacademy.s04.t02.n02.blackjack.game.application.exception.GameNotFoundException;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.out.GameRepository;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.identity.GameId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteGameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private DeleteGameService deleteGameService;

    @Test
    void executeShouldCompleteWhenRepositoryDeletesGame() {
        GameId gameId = GameId.generateNewId();
        DeleteGameCommand command = new DeleteGameCommand(gameId.toString());

        when(gameRepository.deleteById(gameId)).thenReturn(Mono.just(true));

        StepVerifier.create(deleteGameService.execute(command))
                .verifyComplete();

        verify(gameRepository).deleteById(gameId);
    }

    @Test
    void executeShouldReturnGameNotFoundWhenRepositoryDeletesNothing() {
        GameId gameId = GameId.generateNewId();
        DeleteGameCommand command = new DeleteGameCommand(gameId.toString());

        when(gameRepository.deleteById(gameId)).thenReturn(Mono.just(false));

        StepVerifier.create(deleteGameService.execute(command))
                .expectError(GameNotFoundException.class)
                .verify();

        verify(gameRepository).deleteById(gameId);
    }
}
