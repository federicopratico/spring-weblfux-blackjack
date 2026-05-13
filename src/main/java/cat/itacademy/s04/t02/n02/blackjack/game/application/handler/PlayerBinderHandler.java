package cat.itacademy.s04.t02.n02.blackjack.game.application.handler;

import cat.itacademy.s04.t02.n02.blackjack.game.application.dto.command.BindPlayerCommand;
import cat.itacademy.s04.t02.n02.blackjack.game.application.port.in.PlayerBindUseCase;
import cat.itacademy.s04.t02.n02.blackjack.game.application.service.GamePlayerBinderService;
import cat.itacademy.s04.t02.n02.blackjack.share.event.event.DomainEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.event.PlayerResolvedEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.subscriber.DomainEventSubscriber;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class PlayerBinderHandler implements DomainEventSubscriber {

    PlayerBindUseCase playerBindUseCase;

    @Override
    public Mono<Void> handle(DomainEvent event) {
        if (event instanceof PlayerResolvedEvent playerResolvedEvent) {
            return bindPlayerToGame(playerResolvedEvent);
        }
        return Mono.empty();
    }

    private Mono<Void> bindPlayerToGame(PlayerResolvedEvent event) {
        BindPlayerCommand command = new BindPlayerCommand(event.gameId(), event.playerId());

        return playerBindUseCase.execute(command);
    }
}
