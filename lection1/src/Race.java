import java.util.ArrayList;

public class Race {
    private ArrayList<Participatable> participants;
    private ArrayList<Let> lets;

    public Race(ArrayList<Participatable> participants, ArrayList<Let> lets) {
        this.participants = participants;
        this.lets = lets;
    }

    public void go() {
        boolean win;
        for (Participatable participant : participants) {
            win = true;
            for (Let let : lets) {
                if (let instanceof Wall) {
                    if (!participant.jump((Wall) let)) {
                        System.out.println("***********************************");
                        System.out.println(participant + " LOSE");
                        System.out.println("***********************************");
                        win = false;
                        break;
                    }
                }

                if (let instanceof Treadmill) {
                    if (!participant.run((Treadmill) let)) {
                        System.out.println("***********************************");
                        System.out.println(participant + " LOSE");
                        System.out.println("***********************************");
                        win = false;
                        break;
                    }
                }
            }
            if (win) {
                System.out.println("***********************************");
                System.out.println(participant + " WIN!");
                System.out.println("***********************************");
            }
        }
    }

    public static void main(String[] args) {
        ArrayList<Participatable> runners = new ArrayList<>();
        runners.add(new Human("Runner1"));
        runners.add(new Human("Runner2", 10000, 1));
        runners.add(new Cat("Runner3"));
        runners.add(new Cat("Runner4", 5000, 2.3));
        runners.add(new Robot("Runner5"));
        runners.add(new Robot("Runner6", 12000, 1));

        ArrayList<Let> raceTrek = new ArrayList<>();
        raceTrek.add(new Wall(0.5));
        raceTrek.add(new Wall(0.7));
        raceTrek.add(new Treadmill(1000));
        raceTrek.add(new Wall(0.8));
        raceTrek.add(new Treadmill(4000));
        raceTrek.add(new Wall(0.5));
        raceTrek.add(new Treadmill(1000));

        Race race = new Race(runners, raceTrek);

        race.go();
    }
}
