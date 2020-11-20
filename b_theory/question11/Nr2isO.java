package b_theory.question11;

public class Nr2isO {

    //todo this is a contribution based question so make sure to keep commits separate
    //todo A What does O stand for in SOLID? Explain the principle.
    // Open-closed Principle, Objects or entities should be open for extension, but closed for modification.
    //todo B Give an example. Write actual or pseudo code.
    // this is improper function, since it's extension would require modifications in it
    public int sumOfAreas(ArrayList shapes) {
        int sum = 0;
        for (Shape shape : shapes) {
            if (shape.form() == "Circle") {
                sum += shape.getRadius() * shape.getRadius() * math.pi;
            } else if (shape.form() = "Square") {
                sum += shape.getSideLength() * shape.getSideLength();
            }
        }
        return sum;
    }
    // in this case for each new shape we would require to type in new if blocks
    // this would be a proper solution
    public int sumOfAreas(ArrayList shapes) {
        int sum = 0;
        for (Shape shape : shapes) {
            sum += shape.getArea();
        }
    }
    return sum;
}
