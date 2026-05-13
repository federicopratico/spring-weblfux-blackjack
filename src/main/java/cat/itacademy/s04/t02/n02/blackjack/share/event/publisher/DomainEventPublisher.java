package cat.itacademy.s04.t02.n02.blackjack.share.event.publisher;

import cat.itacademy.s04.t02.n02.blackjack.share.event.event.DomainEvent;
import reactor.core.publisher.Mono;

public interface DomainEventPublisher {
    Mono<Void> publish(DomainEvent event);
}
