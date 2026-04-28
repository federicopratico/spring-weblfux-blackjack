package cat.itacademy.s04.t02.n02.blackjack.share.infrstructure.web;

import cat.itacademy.s04.t02.n02.blackjack.game.application.exception.GameNotFoundException;
import cat.itacademy.s04.t02.n02.blackjack.game.domain.exception.EmptyShoeException;
import cat.itacademy.s04.t02.n02.blackjack.player.application.exception.PlayerNotFoundException;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.exception.InsufficientFundsException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            GameNotFoundException.class,
            PlayerNotFoundException.class
    })
    public Mono<ResponseEntity<ApiErrorResponse>> handleNotFound(RuntimeException exception) {
        return buildResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public Mono<ResponseEntity<ApiErrorResponse>> handleInsufficientFunds(InsufficientFundsException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<ApiErrorResponse>> handleIllegalArgument(IllegalArgumentException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ApiErrorResponse>> handleGenericException(Exception exception) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected internal server error");
    }

    @ExceptionHandler(EmptyShoeException.class)
    public Mono<ResponseEntity<ApiErrorResponse>> handleEmptyShoe(EmptyShoeException exception) {
        return buildResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    private Mono<ResponseEntity<ApiErrorResponse>> buildResponse(HttpStatus status, String message) {
        return buildResponse(status, message, List.of());
    }

    private Mono<ResponseEntity<ApiErrorResponse>> buildResponse(HttpStatus status, String message, List<String> errors) {
        ApiErrorResponse response = new ApiErrorResponse(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                errors
        );

        return Mono.just(ResponseEntity.status(status).body(response));
    }
}