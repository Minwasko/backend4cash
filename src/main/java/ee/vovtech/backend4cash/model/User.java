package ee.vovtech.backend4cash.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class User {

    @Id
    private long id;

    private String nickname;
    private String email;
    private String password;
    private String cash;
    @ElementCollection
    private List<SimpleEntry<String, String>> ownedCoins = new ArrayList<>();

    public User() {

    }

    public User(Long id, String nickname, String email, String password) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public List<SimpleEntry<String, String>> getOwnedCoins() {
        return ownedCoins;
    }

    public void addCoins(String coinId, String amount) {
        Optional<SimpleEntry<String, String>> coin = ownedCoins.stream().filter(e -> e.getKey().equals(coinId)).findAny();
        if(coin.isPresent()) coin.get().setValue(coin.get().getValue() + amount);
        else ownedCoins.add(new SimpleEntry<>(coinId, amount));
    }
}
