package example.inner;

public class Laptop {

    // This class is hidden from the outside world e.g. ExampleInner
    // is not in any way coupled to it.
    private class MemoryImpl implements MemoryI {

        private String memoryCapacity;

        private MemoryImpl(String memoryCapacity) {
            this.memoryCapacity = memoryCapacity;
        }

        @Override
        public String getMemoryCapacity() {
            return memoryCapacity;
        }
    }

    // upcasting to an interface reference
    // accessing private class
    public MemoryI setMemoryCapacity(String memoryCapacity) { 
        return new MemoryImpl(memoryCapacity);         		 
    }

}
