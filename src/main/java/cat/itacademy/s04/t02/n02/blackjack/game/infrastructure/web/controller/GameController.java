package cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.controller;

import cat.itacademy.s04.t02.n02.blackjack.game.application.port.in.CreateGameUseCase;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.in.DeleteGameUseCase;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.in.GetGameUseCase;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.in.MakePlayUseCase;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.request.DeleteGameRequest;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.request.GameCreateRequest;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.request.MakePlayRequest;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.response.GameCreateResponse;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.response.GameDetailsResponse;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.dto.response.MakePlayResponse;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.web.mapper.GameWebMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/games")
@AllArgsConstructor
public class GameController {

    private final CreateGameUseCase createGameUseCase;
    private final DeleteGameUseCase deleteGameUseCase;
    private final MakePlayUseCase makePlayUseCase;
    private final GetGameUseCase getGameUseCase;
    private final GameWebMapper mapper;

    @PostMapping
    public Mono<ResponseEntity<GameCreateResponse>> createGame(@RequestBody @Valid GameCreateRequest request) {

        return createGameUseCase.execute(mapper.toCommand(request))
                .map(mapper::toResponse)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @DeleteMapping
    public Mono<ResponseEntity<Void>> deleteGame(@RequestBody @Valid DeleteGameRequest request) {

        return deleteGameUseCase.execute(mapper.toCommand(request))
                .thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @PostMapping("/{gameId}")
    public Mono<ResponseEntity<MakePlayResponse>> makePlay(@PathVariable String gameId, @RequestBody @Valid MakePlayRequest request) {

        return makePlayUseCase.execute(mapper.toCommand(gameId, request))
                .map(mapper::toMakePlayResponse)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{gameId}")
    public Mono<ResponseEntity<GameDetailsResponse>> getGame(@PathVariable String gameId) {

        return getGameUseCase.execute(gameId)
                .map(mapper::toGameDetailsResponse)
                .map(ResponseEntity::ok);
    }
}
