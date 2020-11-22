package b_theory.question11;

public class Nr3isL {

    //todo this is a contribution based question so make sure to keep commits separate
    //todo A What does L stand for in SOLID? Explain the principle.
    // subclasses should be substitutable for their base classes
    //todo B Give an example. Write actual or pseudo code.
    // here is a bad example of square extending a base rectangle class
    public class Rectangle {
        protected int width, height;

        public Rectangle() {}

        public Rectangle(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getArea() {
            return this.width * this.height;
        }
    }

    public class Square extends Rectangle {
        public Square() {}

        public Square(int side) {
            super.height = side;
            super.width = side;
        }

        @Override
        public void setHeight(int height) {
            super.height = height;
        }

        @Override
        public int setWidth(int width) {
            super.width = width;
        }
    }
    // you can create a square class with no parameters and then only set its width using setWdith
    // if you afterwards call Square.getArea() expecting to get a result, since you gave it a side length
    // it will fail since Square class only has width set, but not height so it will give you an error
    // this will be a proper Square class
    public class Square extends Rectangle {
        public Square() {}

        public Square(int side) {
            super.height = side;
            super.width = side;
        }

        @Override
        public void setHeight(int height) {
            super.height = height;
            super.width = width;
        }

        @Override
        public int setWidth(int width) {
            super.width = width;
            super.height = height;
        }
    }
    // now we will get area regardless of if we set width or height to square class
}
