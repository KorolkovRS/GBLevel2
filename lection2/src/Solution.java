public class Solution {
    public static final int MATRIX_SIZE = 4;

    public static int sumMatrix(String[][] matrix) throws MyArraySizeException, MyArrayDataException {

        if (matrix.length != MATRIX_SIZE) {
            throw new MyArraySizeException();
        }

        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length != MATRIX_SIZE) {
                throw new MyArraySizeException();
            }
        }
        int sum = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                try {
                    sum += Integer.parseInt(matrix[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(String.format("Incorrect data in cell [%d][%d]", i, j));
                }
            }
        }
        return sum;
    }

    public static void main(String[] args) {
        String[][] data = new String[4][4];

        int count = 1;

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = String.valueOf(count);
                count++;
            }
        }

        data[2][3] = "Hello";

        try {
            System.out.println(sumMatrix(data));
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }
    }
}
