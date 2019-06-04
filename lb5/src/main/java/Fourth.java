import java.util.*;

public class Fourth {
    public static void main(String[] args) {
        Wolf wolf = new Wolf(3, 3);
        List<Sheep> sheep = new ArrayList<>();
        sheep.add(new Sheep(1,2));
        sheep.add(new Sheep(3,4));
        Field field = new Field(wolf, sheep);
        for (Sheep sheep1 : sheep) {
            new Thread(() -> {
                while(true) {
                    sheep1.move();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e){};
                }
            }).start();
        }
        new Thread(() -> {
            while (true) {
                wolf.move();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
                field.play();
            }
        }).start();
    }
}

class Hero {
    protected int x;
    protected int y;

    public void move () {
        checkForBorderValues();
        switch (new Random().nextInt(4)) {
            case 0: x+=1;break;
            case 1: x-=1;break;
            case 2: y+=1;break;
            case 3: y-=1;break;
        }
    }

    private void checkForBorderValues() {
        if (x == 6) {
            x -= 1;
        }
        if (y == 6) {
            y -= 1;
        }
        if (x == 0) {
            x += 1;
        }
        if (y ==0) {
            y += 1;
        }
    }
}

class Wolf extends Hero{
    private char symbol = 'W';
    public Wolf(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public char getSymbol() {
        return this.symbol;
    }

}

class Sheep extends Hero {
    private char symbol = 'S';
    public Sheep(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public char getSymbol() {
        return this.symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sheep sheep = (Sheep) o;
        return symbol == sheep.symbol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol);
    }
}

class Field {
    private static final int FIELD_WIDTH = 7;
    private static final int FIELD_HEIGHT = 7;
    private Wolf wolf;
    private List<Sheep> sheeps;
    private static final char SHEEP = '@';
    private static final char[][] field = new char[FIELD_HEIGHT][FIELD_WIDTH];

    public Field(Wolf wolf, List<Sheep> sheep) {
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_HEIGHT; j++) {
                field[i][j] = '.';
            }
        }
        this.wolf = wolf;
        this.sheeps = sheep;
    }

    public void play() {
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_HEIGHT; j++) {
                field[i][j] = '.';
            }
        }
        field[wolf.x][wolf.y] = wolf.getSymbol();
        sheeps.forEach(sheep -> field[sheep.x][sheep.y] = sheep.getSymbol());
        for (int i = 0; i < sheeps.size(); i++) {
            if (wolf.x == sheeps.get(i).x && wolf.y == sheeps.get(i).y) {
                sheeps.remove(i);
            }
        }

        for (int i = 0; i < 5; i++) {
            System.out.println();
        }
        for (int i = 0; i < FIELD_WIDTH; i++) {
            for (int j = 0; j < FIELD_HEIGHT; j++) {
                System.out.print(field[i][j] + " ");
            }
            System.out.println();
        }
    }

}

