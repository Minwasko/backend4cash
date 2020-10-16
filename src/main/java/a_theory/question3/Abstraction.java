package a_theory.question3;

import org.springframework.data.util.Pair;

import java.util.List;

public class Abstraction {

    //todo
    // Abstra-ca-dabra

    //todo p1
    // In your words (do not use wiki definitions)
    // What is abstraction? (P.S It has nothing to do with keyword abstract)
    //Answer: Its a concept of OOP. According to this concept, out of all the info developer is given, he only uses
    // the neccessary one. For example in the next task I have created an elephant class. Of course, all the living elephants
    // have a heart for example. But if my program does not require to interact with it, I dont even need to init it.

    //todo p2
    // Create an abstraction of an animal of your choice.
    // It should have at least 1 property and 1 method.
    // Create it as a real java class inside this package.

    private class Elephant {

        List<Object> legs;
        Pair<Object, Object> ears;
        Pair<Object, Object> eyes;

        private void cutLeg(int index){
            try {
                legs.remove(index);
            } catch (ArrayIndexOutOfBoundsException e){
                System.err.println("No such leg");
            }
        }

        public Pair<Object, Object> getEars() {
            return ears;
        }

        public Pair<Object, Object> getEyes() {
            return eyes;
        }
    }
}
