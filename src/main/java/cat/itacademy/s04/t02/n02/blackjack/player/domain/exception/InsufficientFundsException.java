package cat.itacademy.s04.t02.n02.blackjack.player.domain.exception;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
