package ee.vovtech.backend4cash.a_theory.question6.chocolate;

import java.util.List;

public class Cake {

    private Long id;
    private String size;
    private String sweetness;
    private List<String> ingredients;
    private List<String> toppings;

    public List<String> getIngredients() {
        return ingredients;
    }
    public List<String> getToppings() {
        return toppings;
    }
}
