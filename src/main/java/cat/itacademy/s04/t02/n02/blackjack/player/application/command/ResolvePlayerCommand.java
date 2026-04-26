package cat.itacademy.s04.t02.n02.blackjack.player.application.command;

public record ResolvePlayerCommand(
        String gameId,
        String playerName) {
}
