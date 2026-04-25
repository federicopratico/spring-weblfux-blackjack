package cat.itacademy.s04.t02.n02.blackjack.game.application.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String message) {
        super(message);
    }
}
