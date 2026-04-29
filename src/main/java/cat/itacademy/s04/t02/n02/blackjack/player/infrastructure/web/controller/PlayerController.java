package cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.web.controller;

import cat.itacademy.s04.t02.n02.blackjack.player.application.port.in.GetPlayersRankingUseCase;
import cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.web.dto.reponse.PlayerRankingResponse;
import cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.web.mapper.PlayerWebMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/players")
@AllArgsConstructor
@Tag(name = "Blackjack Player Controller", description = "Endpoints for managing player info and statistics")
public class PlayerController {

    private final GetPlayersRankingUseCase getPlayersRankingUseCase;
    private final PlayerWebMapper mapper;


    @Operation(summary = "Get players ranking", description = "Returns players ranking ordered by win rate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Players ranking retrieved successfully"),
            @ApiResponse(responseCode = "200", description = "[] the empty list. No players founded")
    })
    @GetMapping()
    public ResponseEntity<Flux<PlayerRankingResponse>> getPlayersRanking() {
        Flux<PlayerRankingResponse> rankingFlux = getPlayersRankingUseCase.execute()
                .map(mapper::toResponse);

        return ResponseEntity.ok(rankingFlux);
    }
}
