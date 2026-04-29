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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/games")
@AllArgsConstructor
@Tag(name = "Blackjack Game Controller", description = "Endpoints for managing blackjack game sessions")
public class GameController {

    private final CreateGameUseCase createGameUseCase;
    private final DeleteGameUseCase deleteGameUseCase;
    private final MakePlayUseCase makePlayUseCase;
    private final GetGameUseCase getGameUseCase;
    private final GameWebMapper mapper;


    @Operation(summary = "Start a new game", description = "Creates a new blackjack session for a specific player")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Game created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public Mono<ResponseEntity<GameCreateResponse>> createGame(@RequestBody @Valid GameCreateRequest request) {

        return createGameUseCase.execute(mapper.toCommand(request))
                .map(mapper::toResponse)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @Operation(summary = "Delete a game", description = "Removes a game session from the database")
    @ApiResponse(responseCode = "204", description = "Game deleted successfully")
    @DeleteMapping
    public Mono<ResponseEntity<Void>> deleteGame(@RequestBody @Valid DeleteGameRequest request) {

        return deleteGameUseCase.execute(mapper.toCommand(request))
                .thenReturn(ResponseEntity.status(HttpStatus.NO_CONTENT).build());
    }

    @Operation(summary = "Make a play", description = "Performs an action (BET/HIT/STAND) in an active game.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Action processed"),
            @ApiResponse(responseCode = "404", description = "Game ID not found")
    })
    @PostMapping("/{gameId}")
    public Mono<ResponseEntity<MakePlayResponse>> makePlay(@PathVariable String gameId, @RequestBody @Valid MakePlayRequest request) {

        return makePlayUseCase.execute(mapper.toCommand(gameId, request))
                .map(mapper::toMakePlayResponse)
                .map(ResponseEntity::ok);
    }

    @Operation(summary = "Get game details", description = "Returns the current state of a specific game")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the game"),
            @ApiResponse(responseCode = "404", description = "Game ID not found")
    })
    @GetMapping("/{gameId}")
    public Mono<ResponseEntity<GameDetailsResponse>> getGame(@PathVariable String gameId) {

        return getGameUseCase.execute(gameId)
                .map(mapper::toGameDetailsResponse)
                .map(ResponseEntity::ok);
    }
}
