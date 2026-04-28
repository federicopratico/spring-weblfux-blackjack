package cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.persistence.mongo.mapper;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.entity.Game;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.GameStatus;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.RoundOutcome;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.card.Card;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.card.Rank;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.card.Suit;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.dealer.Dealer;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.hand.Hand;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.identity.GameId;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.playerreference.PlayerReference;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.playerreference.ResolvedPlayerReference;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.playerreference.UnresolvedPlayerReference;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.seat.PlayerStatus;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.seat.Seat;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.shoe.Shoe;
import cat.itacademy.s04.t02.n02.blackjack.game.infrastructure.persistence.mongo.document.*;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;
import org.springframework.stereotype.Component;

@Component
public class GamePersistenceMapper {

    public GameDocument toDocument(Game game) {
        return new GameDocument(
                game.getGameId().toString(),
                this.toDealerDocument(game.getDealer()),
                this.toSeatDocument(game.getSeat()),
                this.toShoeDocument(game.getShoe()),
                game.getGameStatus().name(),
                game.getRoundOutcome().name()
        );
    }

    public Game toDomain(GameDocument document) {
        return Game.reconstruct(
                GameId.of(document.gameId()),
                this.toDealer(document.dealer()),
                this.toSeat(document.seat()),
                this.toShoe(document.shoe()),
                GameStatus.valueOf(document.gameStatus()),
                RoundOutcome.valueOf(document.roundOutcome())
        );
    }

    private SeatDocument toSeatDocument(Seat seat) {
        return new SeatDocument(
                this.toPlayerReferenceDocument(seat.getPlayerReference()),
                this.toHandDocument(seat.getHand()),
                seat.getBet(),
                seat.getPlayerStatus().name()
        );
    }

    private Seat toSeat(SeatDocument seatDocument) {
        return Seat.reconstruct(
                this.toPlayerReference(seatDocument.playerReference()),
                this.toHand(seatDocument.hand()),
                seatDocument.bet(),
                PlayerStatus.valueOf(seatDocument.playerStatus())
        );
    }

    private PlayerReferenceDocument toPlayerReferenceDocument(PlayerReference playerReference) {
        if(playerReference instanceof ResolvedPlayerReference resolved) {
            return new PlayerReferenceDocument(
                    "RESOLVED",
                    resolved.playerId().toString(),
                    null);
        }

        if(playerReference instanceof UnresolvedPlayerReference unresolved) {
            return new PlayerReferenceDocument(
                    "UNRESOLVED",
                    null,
                    unresolved.playerName());
        }

        throw new IllegalArgumentException("Unknown player reference type");
    }

    private PlayerReference toPlayerReference(PlayerReferenceDocument playerReferenceDocument) {
        return switch (playerReferenceDocument.referenceType()) {
                case "RESOLVED" -> new ResolvedPlayerReference(PlayerId.of(playerReferenceDocument.playerId()));
                case "UNRESOLVED" -> new UnresolvedPlayerReference(playerReferenceDocument.playerName());
                default -> throw new IllegalArgumentException("Unknown player reference type: " + playerReferenceDocument.referenceType());
            };
    }

    private DealerDocument toDealerDocument(Dealer dealer) {
        return new DealerDocument(
                this.toHandDocument(dealer.getHand()));
    }

    private Dealer toDealer(DealerDocument dealerDocument) {
        return Dealer.reconstruct(
                this.toHand(dealerDocument.hand())
        );
    }

    private HandDocument toHandDocument(Hand hand) {
        return new HandDocument(
                hand.getHand().stream()
                        .map(this::toCardDocument)
                        .toList()
        );
    }

    private Hand toHand(HandDocument handDocument) {
        return Hand.reconstruct(
                handDocument.cards().stream()
                .map(this::toCard)
                .toList());
    }

    private ShoeDocument toShoeDocument(Shoe shoe) {
        return new ShoeDocument(
                shoe.getCards().stream()
                        .map(this::toCardDocument)
                        .toList(),
                shoe.getInitialShoeSize());
    }

    private Shoe toShoe(ShoeDocument shoeDocument) {
        return Shoe.reconstruct(
                shoeDocument.cards().stream()
                        .map(this::toCard)
                        .toList(),
                shoeDocument.initialShoeSize());
    }

    private CardDocument toCardDocument(Card card) {
        return new CardDocument(
                card.getSuit().name(),
                card.getRank().name());
    }

    private Card toCard(CardDocument cardDocument) {
        return new Card(
                Suit.valueOf(cardDocument.suite()),
                Rank.valueOf(cardDocument.rank())
        );
    }
}
