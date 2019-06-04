import java.util.Random;

public class Third {
    public static void main(String[] args) {
        Team t1 = new Team(100, null, "first");
        Team t2 = new Team(100, t1, "second");
        t1.setEnemy(t2);
        initTeam(t1);
        initTeam(t2);
    }

    private static void initTeam(Team team) {
        new Thread(() -> {
            int i = 5;
            while (i >= 0) {
                --i;
                try {
                    team.arrivet(new Random().nextInt(15));
                    Thread.sleep(new Random().nextInt(500));
                    team.killed(new Random().nextInt(15));
                    Thread.sleep(new Random().nextInt(500));
                } catch (InterruptedException e) {
                }
            }
        }).start();
    }
}

class Team {
    private String name;
    private int fighters;
    private Team enemy;

    public Team(int fighters, Team enemy, String name) {
        this.fighters = fighters;
        this.enemy = enemy;
        this.name = name;
    }

    public void killed(int count) {
        System.out.println("Enemy killed " + count + " people for team : " + name);
        this.fighters -= count;
    }

    public void arrivet(int count) {
        System.out.println("To : " + name + " arrivet " + count + " people.");
        this.fighters += count;
    }

    public void setEnemy (Team enemy) {
        this.enemy = enemy;
    }
}
