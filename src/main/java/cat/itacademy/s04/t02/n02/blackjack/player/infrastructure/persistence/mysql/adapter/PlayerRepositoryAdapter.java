package cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.persistence.mysql.adapter;

import cat.itacademy.s04.t02.n02.blackjack.player.application.port.out.PlayerRepository;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.entity.Player;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerId;
import cat.itacademy.s04.t02.n02.blackjack.player.domain.valueobject.identity.PlayerName;
import cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.persistence.mysql.mapper.PlayerPersistenceMapper;
import cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.persistence.mysql.repository.DataPlayerSpringRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@AllArgsConstructor
public class PlayerRepositoryAdapter implements PlayerRepository {

    private final DataPlayerSpringRepository playerSpringRepository;
    private final PlayerPersistenceMapper mapper;

    @Override
    public Mono<Player> save(Player player) {
        return playerSpringRepository.save(mapper.toEntity(player))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Player> findByName(PlayerName name) {
        return playerSpringRepository.findByName(name.name())
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Player> findById(PlayerId playerId) {
        return playerSpringRepository.findById(playerId.toString())
                .map(mapper::toDomain);
    }
}
