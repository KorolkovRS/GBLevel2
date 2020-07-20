public class Human implements Participatable {
    private String name;
    private int runDistance;
    private double jumpHight;

    public Human(String name) {
        this.name = name;
        runDistance = 3000;
        jumpHight = 0.6;
    }

    public Human(String name, int runDistance, double jumpHight) {
        this.name = name;
        this.runDistance = runDistance;
        this.jumpHight = jumpHight;
    }

    @Override
    public boolean jump(Wall wall) {
        if (wall.getHight() <= jumpHight) {
            System.out.println(String.format("%s jumped %.2f meters", this, wall.getHight()));
            return true;
        }
        System.out.println(String.format("%s could not jumped %.2f meters", this, wall.getHight()));
        return false;
    }

    @Override
    public boolean run(Treadmill treadmill) {
        if (treadmill.getDistance() <= runDistance) {
            System.out.println(String.format("%s run %d meters", this, treadmill.getDistance()));
            return true;
        }
        System.out.println(String.format("%s could not run %d meters", this, treadmill.getDistance()));
        return false;
    }

    @Override
    public String toString() {
        return "Human " + name;
    }
}
