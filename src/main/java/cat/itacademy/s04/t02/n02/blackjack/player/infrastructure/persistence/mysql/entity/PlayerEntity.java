package cat.itacademy.s04.t02.n02.blackjack.player.infrastructure.persistence.mysql.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Table("players")
public class PlayerEntity implements Persistable<String> {
    @Id
    private String id;

    @Column("name")
    private String name;

    @Column("deposit")
    private BigDecimal deposit;

    @Column("reserved_deposit")
    private BigDecimal reservedDeposit;

    @Column("games_played")
    private int gamesPlayed;

    @Column("games_won")
    private int gamesWon;

    @Column("games_lost")
    private int gamesLost;

    @Column("games_drawn")
    private int gamesDrawn;

    @Transient
    private boolean isNew;

    private PlayerEntity(String id, String name, BigDecimal deposit, BigDecimal reservedDeposit, int gamesPlayed, int gamesWon, int gamesLost, int gamesDrawn) {
        this.id = id;
        this.name = name;
        this.deposit = deposit;
        this.reservedDeposit = reservedDeposit;
        this.gamesPlayed = gamesPlayed;
        this.gamesWon = gamesWon;
        this.gamesLost =gamesLost;
        this.isNew = true;
        this.gamesDrawn = gamesDrawn;
    }

    public static PlayerEntity newEntity(String id, String name, BigDecimal deposit) {
        return new PlayerEntity(id, name, deposit, BigDecimal.ZERO, 0, 0, 0, 0);
    }

    public void markNotNew() {
        this.isNew = false;
    }

    @Override
    public boolean isNew() {
        return isNew || id == null;
    }
}
