package cat.itacademy.s04.t02.n02.blackjack.share.infrstructure.event;

import cat.itacademy.s04.t02.n02.blackjack.share.event.event.DomainEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.publisher.DomainEventPublisher;
import cat.itacademy.s04.t02.n02.blackjack.share.event.subscriber.DomainEventSubscriber;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class DomainEventDispatcher {

    private final List<DomainEventSubscriber> subscribers;

    public DomainEventDispatcher(List<DomainEventSubscriber> subscribers) {
        this.subscribers = List.copyOf(subscribers);
    }

    public Mono<Void> publish(DomainEvent event) {
        return Flux.fromIterable(subscribers)
                .flatMap(subscriber -> subscriber.handle(event))
                .then();
    }
}
