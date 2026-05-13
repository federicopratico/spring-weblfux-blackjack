package cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.playerreference;

public sealed interface PlayerReference
        permits ResolvedPlayerReference, UnresolvedPlayerReference {
}
