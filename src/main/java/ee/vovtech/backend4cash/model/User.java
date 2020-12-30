package ee.vovtech.backend4cash.model;

import ee.vovtech.backend4cash.security.DbRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity @Getter @Setter @NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "user_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 50)
    private long id;

    private String username;
    private String email;
    private String password;
    private String cash;
    private String status;
    @Enumerated(EnumType.STRING)
    private DbRole role;
    @ElementCollection
    private List<SimpleEntry<String, String>> ownedCoins = new ArrayList<>();

    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public SimpleEntry<String, String> getOwnedCoinByCoinId(String coinId) {
        return ownedCoins.stream().filter(e -> e.getKey().equals(coinId)).findAny().orElse(null);
    }

    public void addCoins(String coinId, String amount) {
        Optional<SimpleEntry<String, String>> coin = ownedCoins.stream().filter(e -> e.getKey().equals(coinId)).findAny();
        if(coin.isPresent()) coin.get().setValue(new BigDecimal(coin.get().getValue()).add(new BigDecimal(amount)).toString());
        else ownedCoins.add(new SimpleEntry<>(coinId, amount));
    }

    public void setCoinsAmount(String coinId, String amount) {
        if (new BigDecimal(amount).equals(BigDecimal.ZERO)) {
            ownedCoins = ownedCoins.stream().filter(e -> !e.getKey().equals(coinId)).collect(Collectors.toList());
        } else {
            ownedCoins.stream().filter(e -> e.getKey().equals(coinId)).findAny()
                    .ifPresent(stringStringSimpleEntry -> stringStringSimpleEntry.setValue(amount));
        }
    }

    public void addCash(Long amount){
        this.cash = new BigDecimal(amount).add(BigDecimal.valueOf(amount)).toString();
    }
}
