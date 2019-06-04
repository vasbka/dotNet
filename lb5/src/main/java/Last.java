import java.util.concurrent.atomic.DoubleAccumulator;

import static java.lang.Math.*;

public class Last {
    private static double value = 1.0;
    public static void main(String[] args) {
        new Thread(() -> {
            for (int x = 0; x < 1000000000; x++) {
                value = 1 + sin(x) - 2 * cos(x) + 4 * pow(sin(x), 2) - 8 * pow(cos(x), 2);
            }
        }).start();
        while(true) {
            System.out.println(value);
        }
    }
}
