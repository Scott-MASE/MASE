package example.inner;

public class ExampleInner {
    public static void main(String[] args) {
        Laptop laptop = new Laptop();

        // Using a public method that returns the interface reference that
        // the (private) inner class implements.
        // This way the inner class is completely invisible to the outside world...
        MemoryI memory = laptop.setMemoryCapacity("4MB");
        System.out.println("The memory capacity is: " + memory.getMemoryCapacity());
    }
}
