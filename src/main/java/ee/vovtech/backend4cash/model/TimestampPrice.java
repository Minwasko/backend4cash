package ee.vovtech.backend4cash.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.io.Serializable;

@Entity
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


    public TimestampPrice() {
    }

    public TimestampPrice(Currency currency, long timestamp, String price) {
        this.timestamp = timestamp;
        this.price = price;
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
