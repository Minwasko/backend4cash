package b_theory.question11;

public class Nr4isI {

    //todo this is a contribution based question so make sure to keep commits separate
    //todo A What does I stand for in SOLID? Explain the principle.
    //todo B Give an example. Write actual or pseudo code.
    public interface BarkWoof {
            public void bork() {
                System.out.println("Bork");
            }

            public void woof() {
                System.out.println("Auf");
            }
        }

        public class Dog implements  BarkWoof {}

        public class Wolf implements BarkWoof {}

        // wolf is able to bark in this example, but we do not want that to happen. We need wolf to only woof

        // this is a proper example where
        public interface Barkable {
            public void bork() {
                System.out.println("Bork");
            }
        }

        public interface Woofable {
            public void woof() {
                System.out.println("Auf");
            }
        }

        public class Dog implements Barkable, Woofable {}

        public class Wolf implements Woofable {}

        // here Wolf can only woof, but Dog can do both things, just what we needed in the beginning.
}
