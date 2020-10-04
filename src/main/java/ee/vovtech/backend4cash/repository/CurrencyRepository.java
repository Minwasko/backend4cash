package ee.vovtech.backend4cash.repository;

import ee.vovtech.backend4cash.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {

}
