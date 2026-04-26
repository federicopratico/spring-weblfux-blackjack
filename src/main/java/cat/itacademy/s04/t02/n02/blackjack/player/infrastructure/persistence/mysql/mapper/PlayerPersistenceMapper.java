package cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.persistence.mysql.mapper;

import cat.itacademy.s04.t02.n02.blackjack.player.domain.entity.Player;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerName;
import cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.persistence.mysql.entity.PlayerEntity;
import org.springframework.stereotype.Component;

@Component
public class PlayerPersistenceMapper {

    public PlayerEntity toEntity(Player player) {
        PlayerEntity entity = PlayerEntity.newEntity(
                player.getPlayerId().toString(),
                player.getPlayerName().name(),
                player.getDeposit()
        );
        entity.setGamesPlayed(player.getGamesPlayed());
        entity.setGamesWon(player.getGamesWon());
        entity.setGamesLost(player.getGamesLost());
        return entity;
    }

    public Player toDomain(PlayerEntity entity) {
        return Player.reconstruct(
                PlayerId.of(entity.getId()),
                PlayerName.create(entity.getName()),
                entity.getDeposit(),
                entity.getGamesPlayed(),
                entity.getGamesWon(),
                entity.getGamesLost()
        );
    }
}
