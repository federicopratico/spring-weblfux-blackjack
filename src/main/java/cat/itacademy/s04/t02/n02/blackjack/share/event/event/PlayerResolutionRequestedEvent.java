package cat.itacademy.s04.t02.n02.blackjack.share.event.event;

import java.time.Instant;

public record PlayerResolutionRequestedEvent(
        String gameId,
        String playerName,
        Instant occurredAt
) implements DomainEvent {}
