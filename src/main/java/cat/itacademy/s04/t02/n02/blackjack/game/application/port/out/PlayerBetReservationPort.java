package cat.itacademy.s04.t02.n02.blackjack.game.application.port.out;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.identity.GameId;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface PlayerBetReservationPort {
    Mono<Void> reserveBet(GameId gameId, PlayerId playerId, BigDecimal betAmount);
}
