import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class MultiThread {
    public static void main(String[] args) throws Exception{
        BufferedReader in = new BufferedReader(new FileReader("input.txt"));
        List<String> collect = in.lines().collect(Collectors.toList());
        String stringToSearch = "r";
        int onePart = collect.size() / 3;
        new Thread(new CustomRunnable(0, onePart, collect, stringToSearch)).start();
        new Thread(new CustomRunnable(onePart, onePart + onePart, collect, stringToSearch)).start();
        new Thread(new CustomRunnable(2 * onePart, collect.size() - 1, collect, stringToSearch)).start();
    }


}
class CustomRunnable implements Runnable{
    private int start;
    private int end;
    private List<String> list;
    private String search;

    public CustomRunnable(int start, int end, List<String> list, String search) {
        this.start = start;
        this.end = end;
        this.list = list;
        this.search = search;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            if (list.get(i).equals(search)) {
                System.out.println("Searched string : " + search + ", index : " + i);
            }
        }
    }
}

