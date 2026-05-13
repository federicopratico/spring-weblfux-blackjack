package cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.persistence.mysql.adapter;

import cat.itacademy.s04.t02.n02.blackjack.game.application.port.out.PlayerBetReservationPort;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.identity.GameId;
import cat.itacademy.s04.t02.n02.blackjack.player.application.dto.command.ReserveBetCommand;
import cat.itacademy.s04.t02.n02.blackjack.player.application.port.in.ReserveBetUseCase;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class PlayerReservationAdapter implements PlayerBetReservationPort {

    private final ReserveBetUseCase reserveBetUseCase;

    @Override
    public Mono<Void> reserveBet(GameId gameId, PlayerId playerId, BigDecimal betAmount) {
        ReserveBetCommand command = new ReserveBetCommand(
                playerId.toString(),
                gameId.toString(),
                betAmount
        );

        return reserveBetUseCase.execute(command);
    }
}
