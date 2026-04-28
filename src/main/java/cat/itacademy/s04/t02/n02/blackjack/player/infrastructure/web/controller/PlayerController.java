package cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.web.controller;

import cat.itacademy.s04.t02.n02.blackjack.player.application.port.in.GetPlayersRankingUseCase;
import cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.web.dto.reponse.PlayerRankingResponse;
import cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.web.mapper.PlayerWebMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/players")
@AllArgsConstructor
public class PlayerController {

    private final GetPlayersRankingUseCase getPlayersRankingUseCase;
    private final PlayerWebMapper mapper;

    @GetMapping()
    public ResponseEntity<Flux<PlayerRankingResponse>> getPlayersRanking() {
        Flux<PlayerRankingResponse> rankingFlux = getPlayersRankingUseCase.execute()
                .map(mapper::toResponse);

        return ResponseEntity.ok(rankingFlux);
    }
}
