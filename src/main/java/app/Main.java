package app;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        System.out.println("123");
        new Controller(Main::print).start();
    }

    public static void print(Object object) {
        System.out.println(object);
        try {
            System.in.read();
        } catch (IOException ignored) {}
    }
}