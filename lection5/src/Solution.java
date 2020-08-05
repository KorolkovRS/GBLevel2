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
        ArrayCalc array;

        public MyRunnableClass(ArrayCalc array) {
            this.array = array;
        }

        @Override
        public void run() {
            array.calculate();
        }
    }

    public void fillDefault() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
    }

    public void doAsynchronously() {
        ArrayCalc originalArray = new ArrayCalc(arr, 0);
        long startTime = System.currentTimeMillis();
        originalArray.calculate();
        long completeTime = originalArray.getTime() - startTime;
        System.out.printf("Without multithreading calculate completed in %d ms.\n",
                completeTime);
    }

    public void doSynchronize() {
        long startTime = System.currentTimeMillis();

        float subArr1[] = new float[h];
        float subArr2[] = new float[size - h];

        System.arraycopy(arr, 0, subArr1, 0, h);
        System.arraycopy(arr, h, subArr2, 0, size - h);

        ArrayCalc a1 = new ArrayCalc(subArr1, 0);
        ArrayCalc a2 = new ArrayCalc(subArr2, h);

        Thread thread1 = new Thread(new MyRunnableClass(a1));
        Thread thread2 = new Thread(new MyRunnableClass(a2));

        thread1.start();
        thread2.start();

        long copyStart = System.currentTimeMillis();
        System.arraycopy(subArr1, 0, arr, 0, h);
        System.arraycopy(subArr2, 0, arr, h, size - h);
        long copyCompleteTime = System.currentTimeMillis() - copyStart;

        long completeTime = (a1.getTime() > a2.getTime()) ? (a1.getTime() - startTime) : (a2.getTime() - startTime);

        System.out.printf("With multithreading calculate completed in %d ms.\n",
                completeTime + copyCompleteTime);
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        solution.fillDefault();
        solution.doAsynchronously();

        solution.fillDefault();
        solution.doSynchronize();
    }
}
