package cat.itacademy.s04.t02.n02.blackjack.player.application.service;

import cat.itacademy.s04.t02.n02.blackjack.player.application.command.PersistRoundCommand;
import cat.itacademy.s04.t02.n02.blackjack.player.application.exception.PlayerNotFoundException;
import cat.itacademy.s04.t02.n02.blackjack.player.application.port.in.PersistRoundUseCase;
import cat.itacademy.s04.t02.n02.blackjack.player.application.port.out.PlayerRepository;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PersistRoundService implements PersistRoundUseCase {

    private final PlayerRepository playerRepository;

    @Override
    public Mono<Void> execute(PersistRoundCommand command) {
        PlayerId playerId = PlayerId.of(command.playerId());

        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player " + command.playerId() + " not found")))
                .flatMap(player -> {
                    player.persistRound(
                            command.reservedBetAmount(),
                            command.profitAmount(),
                            command.roundOutcome()
                    );

                    return playerRepository.save(player);
                })
                .then();
    }
}
