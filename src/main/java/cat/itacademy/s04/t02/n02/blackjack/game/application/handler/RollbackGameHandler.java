package cat.itacademy.s04.t02.n02.blackjack.game.application.handler;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.DeleteGameCommand;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.in.DeleteGameUseCase;
import cat.itacademy.s04.t02.n02.blackjack.share.event.event.DomainEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.event.PlayerResolutionFailedEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.subscriber.DomainEventSubscriber;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class RollbackGameHandler implements DomainEventSubscriber {

    private final DeleteGameUseCase deleteGameUseCase;

    @Override
    public Mono<Void> handle(DomainEvent event) {
        if(event instanceof PlayerResolutionFailedEvent playerResolutionFailedEvent)
            return onPlayerResolutionFailed(playerResolutionFailedEvent);

        return Mono.empty();
    }

    private Mono<Void> onPlayerResolutionFailed(PlayerResolutionFailedEvent event) {
        DeleteGameCommand command = new DeleteGameCommand(event.gameId());

        return deleteGameUseCase.execute(command);
    }
}
