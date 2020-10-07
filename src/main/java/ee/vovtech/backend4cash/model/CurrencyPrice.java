package ee.vovtech.backend4cash.model;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Map;

@Entity
public class CurrencyPrice {

    @Id
    private String name;
    @ElementCollection
    private Map<Long, BigDecimal> datePriceMap;


    public CurrencyPrice() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, BigDecimal> getDatePriceMap() {
        return datePriceMap;
    }

    public void setDatePriceMap(Map<Long, BigDecimal> datePriceMap) {
        this.datePriceMap = datePriceMap;
    }
}
