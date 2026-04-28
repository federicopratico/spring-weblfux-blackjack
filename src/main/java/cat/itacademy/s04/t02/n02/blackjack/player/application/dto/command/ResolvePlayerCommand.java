package cat.itacademy.s04.t02.n02.blackjack.player.application.dto.command;

public record ResolvePlayerCommand(
        String gameId,
        String playerName) {
}
