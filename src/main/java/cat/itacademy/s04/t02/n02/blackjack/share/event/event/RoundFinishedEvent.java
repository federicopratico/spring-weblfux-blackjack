package cat.itacademy.s04.t02.n02.blackjack.share.event.event;

import java.math.BigDecimal;
import java.time.Instant;

public record RoundFinishedEvent(
        String gameId,
        String playerId,
        BigDecimal reservedBetAmount,
        BigDecimal profitAmount,
        String roundOutcome,
        Instant occurredAt
) implements DomainEvent {

    @Override
    public Instant occurredAt() {
        return occurredAt;
    }
}
