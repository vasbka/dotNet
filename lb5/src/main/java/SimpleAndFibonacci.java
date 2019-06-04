import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SimpleAndFibonacci {
    public static final String FIBOACCI_TXT = "fiboacci.txt";
    public static final String PRIME_TXT = "prime.txt";
    private static boolean isCalculating = true;
    public static void main(String[] args) throws Exception {
        List<Thread> list = new ArrayList<>();
        list.add(new Thread(() -> {
            BufferedWriter bufferedWriter = null;
            try {
                new File(FIBOACCI_TXT).createNewFile();
                bufferedWriter = new BufferedWriter(new FileWriter(FIBOACCI_TXT));
            } catch (IOException e) {
                return;
            }
            long previous = 0, next = 1;

            while (isCalculating) {
                long summ = previous + next;
                previous = next;
                next = summ;
                try {
                    Thread.sleep(50);
                    bufferedWriter.write(summ + System.lineSeparator());
                } catch (IOException | InterruptedException e){}
            }

            try {
                bufferedWriter.close();
            } catch (IOException e){}
        }));
        list.add(new Thread(() -> {
            BufferedWriter bufferedWriter = null;
            try {
                new File(PRIME_TXT).createNewFile();
                bufferedWriter = new BufferedWriter(new FileWriter(PRIME_TXT));
            } catch (IOException e) {
                return;
            }
            int start = 0;
            while (isCalculating) {
                if (checkForPrime(++start)) {
                    try {
                        Thread.sleep(50);
                        bufferedWriter.write(start + System.lineSeparator());
                    } catch (IOException | InterruptedException e){
                    }
                }
            }

            try {
                bufferedWriter.close();
            } catch (IOException e){}

        }));
        list.get(0).start();
        list.get(1).start();


        Thread.sleep(800);
        isCalculating = false;

        if (new File(FIBOACCI_TXT).canRead()) {
            System.out.println("Fibo");
            Files.lines(Paths.get(FIBOACCI_TXT)).forEach(s -> System.out.print(s + " "));
        }
        System.out.println();
        if (new File(PRIME_TXT).canRead()) {
            System.out.println("Prime");
            Files.lines(Paths.get(PRIME_TXT)).forEach(s -> System.out.print(s + " "));
        }
    }
    static boolean checkForPrime(int number) {
        int i , lastCheckedValue;
        lastCheckedValue = number / 2;
        if(number == 0|| number == 1) {
            return false;
        } else {
            for(i = 2; i <= lastCheckedValue; i++){
                if( number % i == 0){
                    return false;
                }
            }
        }
        return true;
    }
}
