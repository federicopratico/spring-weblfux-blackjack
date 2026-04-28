package cat.itacademy.s04.t02.n02.blackjack.share.infrstructure.web;

import java.time.Instant;
import java.util.List;

public record ApiErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        List<String> details
) {
}