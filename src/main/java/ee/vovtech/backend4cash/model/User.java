package ee.vovtech.backend4cash.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import ee.vovtech.backend4cash.security.DbRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity @Getter @Setter @NoArgsConstructor @AllArgsConstructor
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
        if (new BigDecimal(amount).compareTo(new BigDecimal("0.0000001")) < 0) {
            ownedCoins = ownedCoins.stream().filter(e -> !e.getKey().equals(coinId)).collect(Collectors.toList());
        } else {
            ownedCoins.stream().filter(e -> e.getKey().equals(coinId)).findAny()
                    .ifPresent(stringStringSimpleEntry -> stringStringSimpleEntry.setValue(amount));
        }
    }

    public User addCash(Long amount){
        this.cash = new BigDecimal(cash).add(BigDecimal.valueOf(amount)).toString();
        return this;
    }

    // overriding this method because Json is unable to be parsed into AbstractMap.SimpleEntry, since it has no default
    // constructor
    public List<Map.Entry<String, String>> getOwnedCoins() {
        List<Map.Entry<String, String>> listForJson = new ArrayList<>();
        for (SimpleEntry<String, String> entry : ownedCoins) {
            Map.Entry<String, String> jsonEntry = new Map.Entry<>() {
                @Override
                public String getKey() {
                    return entry.getKey();
                }

                @Override
                public String getValue() {
                    return entry.getValue();
                }

                @Override
                public String setValue(String value) {
                    return entry.getValue();
                }
            };
            listForJson.add(jsonEntry);
        }
        return listForJson;
    }
}
