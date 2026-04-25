package cat.itacademy.s04.t02.n02.blackjack.share.infrstructure.event;

import cat.itacademy.s04.t02.n02.blackjack.share.event.event.DomainEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.publisher.DomainEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class InMemoryDomainEventPublisher implements DomainEventPublisher {

    private final DomainEventDispatcher dispatcher;

    public InMemoryDomainEventPublisher(@Lazy DomainEventDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public Mono<Void> publish(DomainEvent event) {
        return dispatcher.publish(event);
    }
}
