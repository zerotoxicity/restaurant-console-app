package rrpss.ui;

@FunctionalInterface
public interface Component extends View {
    default void display() {}
    void display(Object ...o);
}
