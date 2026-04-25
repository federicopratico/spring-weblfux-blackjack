package cat.itacademy.s04.t02.n02.blackjack.share.event.subscriber;

import cat.itacademy.s04.t02.n02.blackjack.share.event.event.DomainEvent;
import reactor.core.publisher.Mono;

public interface DomainEventSubscriber {
    Mono<Void> handle(DomainEvent event);
}
