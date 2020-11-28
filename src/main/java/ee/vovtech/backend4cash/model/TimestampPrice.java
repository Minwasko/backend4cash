package ee.vovtech.backend4cash.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.io.Serializable;

@Entity @Getter @Setter @NoArgsConstructor
public class TimestampPrice implements Serializable {

    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;

    @JoinColumn(name = "timestamp")
    private long timestamp;

    @JoinColumn(name = "price")
    private String price;

    @ManyToOne
    @JoinColumn(name = "FK_CurrencyId")
    @JsonIgnore
    private Currency currency;

    public TimestampPrice(Currency currency, long timestamp, String price) {
        this.timestamp = timestamp;
        this.price = price;
        this.currency = currency;
    }
}
