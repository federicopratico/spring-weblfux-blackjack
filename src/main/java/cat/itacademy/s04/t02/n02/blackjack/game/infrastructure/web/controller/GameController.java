package cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.controller;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.GameCreateCommand;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.in.CreateGameUseCase;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.request.GameCreateRequest;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.response.GameCreateResponse;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.mapper.GameWebMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/games")
@AllArgsConstructor
public class GameController {

    private CreateGameUseCase createGameUseCase;
    private GameWebMapper mapper;

    @PostMapping()
    public Mono<ResponseEntity<GameCreateResponse>> createGame(@RequestBody @Valid GameCreateRequest request) {

        return createGameUseCase.execute(mapper.toCommand(request))
                .map(mapper::toResponse)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }
}
