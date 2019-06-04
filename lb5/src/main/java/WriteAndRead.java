import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class WriteAndRead {
    private static File file = new File("test.txt");
    public static void main(String[] args) throws Exception {
        file.createNewFile();
        new Thread(() -> {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get("test.txt"))) {
                while(true) {
                    synchronized (file) {
                        String line;
                        while((line = reader.readLine()) != null) {
                            System.out.println(line);
                        }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException i){}
                }
            } catch (IOException ignored) {
            }
        }).start();

        new Thread(() -> {
            try (BufferedWriter reader = Files.newBufferedWriter(Paths.get("test.txt"))) {
                while(true) {
                    synchronized (file) {
                        reader.write(new Random().nextInt(5000) + System.lineSeparator());
                        reader.flush();
                    }
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException i){}
                }
            } catch (IOException ignored) {
            }
        }).start();
    }
}
