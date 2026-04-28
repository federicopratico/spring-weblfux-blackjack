package cat.itacademy.s04.t02.n02.blackjack.player.application.handler;

import cat.itacademy.s04.t02.n02.blackjack.player.application.command.PersistRoundCommand;
import cat.itacademy.s04.t02.n02.blackjack.player.application.port.in.PersistRoundUseCase;
import cat.itacademy.s04.t02.n02.blackjack.share.event.event.DomainEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.event.RoundFinishedEvent;
import cat.itacademy.s04.t02.n02.blackjack.share.event.subscriber.DomainEventSubscriber;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class RoundFinishedHandler implements DomainEventSubscriber {

    private final PersistRoundUseCase persistRoundUseCase;

    @Override
    public Mono<Void> handle(DomainEvent event) {
        if(event instanceof RoundFinishedEvent roundFinishedEvent)
            return onRoundFinished(roundFinishedEvent);

        return Mono.empty();
    }

    private Mono<Void> onRoundFinished(RoundFinishedEvent event) {
        PersistRoundCommand command = new PersistRoundCommand(
                event.gameId(),
                event.playerId(),
                event.reservedBetAmount(),
                event.profitAmount(),
                event.roundOutcome()
        );

        return persistRoundUseCase.execute(command);
    }
}
