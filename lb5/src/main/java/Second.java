import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Second {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int width = 10;
        int height = 10;
        int[] vector = new int[] {2,2,2,2,2,2,2,2,2,2};
        int[][] matrix = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                matrix[i][j] = i * height + j;
            }
        }
        print(width, height, matrix);
        System.out.println();

        List<FutureTask<int[]>> tasks = new ArrayList<>();
        for (int i = 0; i < matrix.length; i++) {
            tasks.add(new FutureTask<>(new RowCalculator(matrix[i], vector[i])));
            new Thread(tasks.get(i)).start();
        }
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = tasks.get(i).get();
        }
        print(width, height, matrix);
    }

    private static void print(int width, int height, int[][] matrix) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}

class RowCalculator implements Callable<int[]> {
    private int[] arr;
    private int multiply;

    public RowCalculator(int[] arr, int multiply) {
        this.arr = arr;
        this.multiply = multiply;
    }

    @Override
    public int[] call() throws Exception {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = arr[i] * multiply;
        }
        return arr;
    }
}
