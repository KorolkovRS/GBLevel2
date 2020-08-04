import java.util.Arrays;

public class ArrayCalc {
    private float[] array;
    private int offset;

    public ArrayCalc(float[] array, int offset) {
        this.array = array;
        this.offset = offset;
    }

    synchronized public void calculate() {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float)(array[i] * Math.sin(0.2f + (i + offset) / 5) * Math.cos(0.2f + (i + offset) / 5)
                    * Math.cos(0.4f + (i + offset) / 2));
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(array);
    }
}
