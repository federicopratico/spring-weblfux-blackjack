package cat.itacademy.s04.t02.n02.blackjack.player.application.handler;

import cat.itacademy.s04.t02.n02.blackjack.player.application.command.ResolvePlayerCommand;
import cat.itacademy.s04.t02.n02.blackjack.player.application.port.in.ResolvePlayerUseCase;
import cat.itacademy.s04.t02.n02.blackjack.share.event.event.DomainEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.event.PlayerResolutionFailedEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.event.PlayerResolutionRequestedEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.event.PlayerResolvedEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.publisher.DomainEventPublisher;
import cat.itacademy.s04.t02.n02.blackjack.share.event.subscriber.DomainEventSubscriber;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Component
public class ResolvePlayerHandler implements DomainEventSubscriber {

    private final ResolvePlayerUseCase resolvePlayerUseCase;
    private final DomainEventPublisher eventPublisher;

    public ResolvePlayerHandler(ResolvePlayerUseCase resolvePlayerUseCase, DomainEventPublisher eventPublisher) {
        this.resolvePlayerUseCase = resolvePlayerUseCase;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Mono<Void> handle(DomainEvent event) {
        if(event instanceof PlayerResolutionRequestedEvent playerResolutionRequestedEvent)
            return onPlayerResolutionRequested(playerResolutionRequestedEvent);

        return Mono.empty();
    }

    private Mono<Void> onPlayerResolutionRequested(PlayerResolutionRequestedEvent event) {
        ResolvePlayerCommand command = new ResolvePlayerCommand(event.playerName());

        return resolvePlayerUseCase.execute(command)
                .doOnNext(player -> eventPublisher.publish(new PlayerResolvedEvent(
                        event.gameId(),
                        player.getPlayerId().toString(),
                        player.getPlayerName().name(),
                        Instant.now()
                )))
                .then()
                .onErrorResume(error -> {
                    eventPublisher.publish(new PlayerResolutionFailedEvent(
                            event.gameId(),
                            event.playerName(),
                            error.getMessage(),
                            Instant.now()));

                    return Mono.error(error);
                });
    }
}
