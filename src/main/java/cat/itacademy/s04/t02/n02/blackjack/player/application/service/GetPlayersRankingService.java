package cat.itacademy.s04.t02.n02.blackjack.player.application.service;

import cat.itacademy.s04.t02.n02.blackjack.player.application.dto.command.view.PlayerRankingView;
import cat.itacademy.s04.t02.n02.blackjack.player.application.port.in.GetPlayersRankingUseCase;
import cat.itacademy.s04.t02.n02.blackjack.player.application.port.out.PlayerRepository;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.entity.Player;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class GetPlayersRankingService implements GetPlayersRankingUseCase {

    private final PlayerRepository playerRepository;

    @Override
    public Flux<PlayerRankingView> execute() {
        Comparator<Player> byScoreDesc = Comparator
                .comparing(GetPlayersRankingService::scoreOf) // BigDecimal
                .reversed()
                .thenComparing(Player::getGamesPlayed, Comparator.reverseOrder())
                .thenComparing(Player::getDeposit, Comparator.nullsLast(Comparator.reverseOrder()))
                .thenComparing(p -> p.getPlayerName().name());

        AtomicInteger position = new AtomicInteger(0);

        return playerRepository.findAll()
                .sort(byScoreDesc)
                .map(p -> new PlayerRankingView(
                        position.incrementAndGet(),
                        p.getPlayerId().toString(),
                        p.getPlayerName().name(),
                        p.getGamesPlayed(),
                        p.getGamesWon(),
                        p.getGamesLost(),
                        p.getGamesDrawn(),
                        scoreOf(p),
                        p.getDeposit()
                ));
    }

    private static BigDecimal scoreOf(Player p) {
        int played = p.getGamesPlayed();
        if (played <= 0) return BigDecimal.ZERO.setScale(4);
        BigDecimal wins = BigDecimal.valueOf(p.getGamesWon());
        return wins
                .divide(BigDecimal.valueOf(played), 4, RoundingMode.HALF_UP); // win rate in [0,1]
    }
}
