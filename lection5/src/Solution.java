import java.util.Arrays;

/**1. Необходимо написать два метода, которые делают следующее:
 1) Создают одномерный длинный массив, например:

 static final int size = 10000000;
 static final int h = size / 2;
 float[] arr = new float[size];

 2) Заполняют этот массив единицами;
 3) Засекают время выполнения: long a = System.currentTimeMillis();
 4) Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
 arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
 5) Проверяется время окончания метода System.currentTimeMillis();
 6) В консоль выводится время работы: System.out.println(System.currentTimeMillis() - a);
 Отличие первого метода от второго:
 Первый просто бежит по массиву и вычисляет значения.
 Второй разбивает массив на два массива, в двух потоках высчитывает новые значения и потом склеивает эти массивы обратно в один.
 Пример деления одного массива на два:

 System.arraycopy(arr, 0, a1, 0, h);
 System.arraycopy(arr, h, a2, 0, h);
 Пример обратной склейки:

 System.arraycopy(a1, 0, arr, 0, h);
 System.arraycopy(a2, 0, arr, h, h);
 Примечание:
 System.arraycopy() – копирует данные из одного массива в другой:
 System.arraycopy(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение,
 откуда начинаем записывать данные в массив-назначение, сколько ячеек копируем)
 По замерам времени:
 Для первого метода надо считать время только на цикл расчета:

 for (int i = 0; i < size; i++) {
 arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
 }

 Для второго метода замеряете время разбивки массива на 2, просчета каждого из двух массивов и склейки.
 */

public class Solution {
    static final int size = 10_000_000;
    static final int h = size / 2;
    float[] arr = new float[size];

    public class MyRunnableClass implements Runnable {
        float[] array;
        int offset;

        public MyRunnableClass(float[] array, int offset) {
            this.array = array;
            this.offset = offset;
        }

        @Override
        public void run() {
            calculate(array, offset);
        }
    }

    public void fillDefault() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
    }

    public void calculateWithoutMultithreading() {
        long a = System.currentTimeMillis();
        calculate(arr, 0);
        System.out.printf("Without multithreading calculate completed in %d ms.\n", System.currentTimeMillis() - a);
    }

    public void calculateWithMultithreading() {
        long a = System.currentTimeMillis();
        float a1[] = new float[h];
        float a2[] = new float[size - h];

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, size - h);

        Thread thread1 = new Thread(new MyRunnableClass(a1, 0));
        Thread thread2 = new Thread(new MyRunnableClass(a2, h));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, size - h);
        System.out.printf("With multithreading calculate completed in %d ms.\n", System.currentTimeMillis() - a);
    }

    private void calculate(float[] array, int offset) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float)(array[i] * Math.sin(0.2f + (i + offset) / 5) * Math.cos(0.2f + (i + offset) / 5)
                    * Math.cos(0.4f + (i + offset) / 2));
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.fillDefault();
        //System.out.println(Arrays.toString(solution.arr));
        solution.calculateWithoutMultithreading();
        //System.out.println(Arrays.toString(solution.arr));

        solution.fillDefault();

        //System.out.println(Arrays.toString(solution.arr));
        solution.calculateWithMultithreading();
        //System.out.println(Arrays.toString(solution.arr));
    }
}
