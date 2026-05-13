package cat.itacademy.s04.t02.n02.blackjack.player.application.handler;

import cat.itacademy.s04.t02.n02.blackjack.player.application.dto.command.ResolvePlayerCommand;
import cat.itacademy.s04.t02.n02.blackjack.player.application.port.in.ResolvePlayerUseCase;
import cat.itacademy.s04.t02.n02.blackjack.share.event.event.DomainEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.event.PlayerResolutionRequestedEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.subscriber.DomainEventSubscriber;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class ResolvePlayerHandler implements DomainEventSubscriber {

    private final ResolvePlayerUseCase resolvePlayerUseCase;

    public ResolvePlayerHandler(ResolvePlayerUseCase resolvePlayerUseCase) {
        this.resolvePlayerUseCase = resolvePlayerUseCase;
    }

    @Override
    public Mono<Void> handle(DomainEvent event) {
        if(event instanceof PlayerResolutionRequestedEvent playerResolutionRequestedEvent)
            return onPlayerResolutionRequested(playerResolutionRequestedEvent);

        return Mono.empty();
    }

    private Mono<Void> onPlayerResolutionRequested(PlayerResolutionRequestedEvent event) {
        ResolvePlayerCommand command = new ResolvePlayerCommand(event.gameId(), event.playerName());

        return resolvePlayerUseCase.execute(command)
                .then();
    }
}
