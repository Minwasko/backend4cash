package ee.vovtech.backend4cash.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;

@Entity
public class CurrencyPrice {

    @Id
    private String name;
    @ElementCollection
    private Map<String, Double> datePriceMap;


    public CurrencyPrice() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Double> getDatePriceMap() {
        return datePriceMap;
    }

    public void setDatePriceMap(Map<String, Double> datePriceMap) {
        this.datePriceMap = datePriceMap;
    }
}
