package cat.itacademy.s04.t02.n02.blackjack.player.application.exception;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
