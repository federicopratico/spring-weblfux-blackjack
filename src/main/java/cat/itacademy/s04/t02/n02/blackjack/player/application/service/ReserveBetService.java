package cat.itacademy.s04.t02.n02.blackjack.player.application.service;

import cat.itacademy.s04.t02.n02.blackjack.player.application.command.ReserveBetCommand;
import cat.itacademy.s04.t02.n02.blackjack.player.application.exception.PlayerNotFoundException;
import cat.itacademy.s04.t02.n02.blackjack.player.application.port.in.ReserveBetUseCase;
import cat.itacademy.s04.t02.n02.blackjack.player.application.port.out.PlayerRepository;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ReserveBetService implements ReserveBetUseCase {

    private final PlayerRepository playerRepository;

    @Override
    public Mono<Void> execute(ReserveBetCommand command) {
        PlayerId playerId = PlayerId.of(command.playerId());

        return playerRepository.findById(playerId)
                .switchIfEmpty(Mono.error(new PlayerNotFoundException("Player " + playerId.toString() + " not found")))
                .flatMap(player -> {
                    player.reserveBet(command.betAmount());
                    return playerRepository.save(player);
                })
                .then();
    }
}
