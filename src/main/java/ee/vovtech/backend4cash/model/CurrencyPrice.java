package ee.vovtech.backend4cash.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class CurrencyPrice {

    @Id
    private String name;
    @ElementCollection
    private List<String> date;
    @ElementCollection
    private List<Double> price;


    public CurrencyPrice() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDate() {
        return date;
    }

    public void setDate(List<String> date) {
        this.date = date;
    }

    public List<Double> getPrice() {
        return price;
    }

    public void setPrice(List<Double> price) {
        this.price = price;
    }
}
