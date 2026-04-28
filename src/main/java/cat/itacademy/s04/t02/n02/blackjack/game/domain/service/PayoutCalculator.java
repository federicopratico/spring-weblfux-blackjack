package cat.itacademy.s04.t02.n02.blackjack.game.domain.service;

import cat.itacademy.s04.t02.n02.blackjack.game.domain.valueobject.RoundOutcome;

import java.math.BigDecimal;

public class PayoutCalculator {
    public BigDecimal calculateProfit(BigDecimal betAmount, RoundOutcome outcome) {
        return switch (outcome) {
            case PLAYER_BLACKJACK -> betAmount.multiply(BigDecimal.valueOf(1.5));
            case PLAYER_WIN -> betAmount;
            case DEALER_WIN, PUSH, PLAYING -> BigDecimal.ZERO;
        };
    }
}
