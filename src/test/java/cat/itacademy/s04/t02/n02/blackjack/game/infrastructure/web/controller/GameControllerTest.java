package cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.controller;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.DeleteGameCommand;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.in.CreateGameUseCase;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.in.DeleteGameUseCase;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.request.DeleteGameRequest;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.mapper.GameWebMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.when;

@WebFluxTest(GameController.class)
@ContextConfiguration(classes = GameControllerTest.TestBootConfiguration.class)
class GameControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private CreateGameUseCase createGameUseCase;

    @MockitoBean
    private DeleteGameUseCase deleteGameUseCase;

    @MockitoBean
    private GameWebMapper gameWebMapper;

    @Test
    void deleteGameShouldReturnNoContentWhenDeletionSucceeds() {
        DeleteGameRequest request = new DeleteGameRequest("550e8400-e29b-41d4-a716-446655440000");
        DeleteGameCommand command = new DeleteGameCommand(request.gameId());

        when(gameWebMapper.toCommand(request)).thenReturn(command);
        when(deleteGameUseCase.execute(command)).thenReturn(Mono.empty());

        webTestClient.method(HttpMethod.DELETE)
                .uri("/games")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deleteGameShouldReturnBadRequestWhenGameIdIsBlank() {
        webTestClient.method(HttpMethod.DELETE)
                .uri("/games")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DeleteGameRequest(""))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @SpringBootConfiguration
    @EnableAutoConfiguration
    @Import(GameController.class)
    static class TestBootConfiguration {
    }
}
