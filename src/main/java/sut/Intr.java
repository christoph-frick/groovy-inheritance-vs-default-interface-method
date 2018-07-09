package sut;

public interface Intr {
    default void doSomething() {
        System.out.println("Intr");
    }
}
