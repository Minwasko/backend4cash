package ee.vovtech.backend4cash.model;

import org.hibernate.annotations.Columns;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
public class CurrencyPrice {

    @Id
    private String name;
    @ElementCollection
    private Map<Long, String> datePriceMap;


    public CurrencyPrice() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Long, String> getDatePriceMap() {
        return datePriceMap;
    }

    public void setDatePriceMap(Map<Long, String> datePriceMap) {
        this.datePriceMap = datePriceMap;
    }
}
