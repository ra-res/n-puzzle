package src;

import java.util.Arrays;

public class InputFormatter {

    static void takeInput(String rawInput) {
        char[] splitInput = rawInput.toCharArray();
        StringBuilder sb = new StringBuilder(rawInput);
        int i = sb.indexOf("(");
        int j = sb.indexOf(")");
        StringBuilder indexes = new StringBuilder(sb.substring(i + 1, j));
        int row = Integer.parseInt(indexes.toString().split(",")[0].trim());
        int column = Integer.parseInt(indexes.toString().split(",")[1].trim());
        int[][] matrix;
        matrix = new int[row][column];
        int start = sb.indexOf("[");
        int end = sb.indexOf("]");
        String[] values = sb.substring(start + 1, end).split(" ");
        int valuesIndex = 0;
        for (int curRow = 0; curRow < row; curRow++) {
            for (int curCol = 0; curCol < column; curCol++) {
                matrix[curRow][curCol] = Integer.parseInt(values[valuesIndex++]);
            }
        }
        System.out.println(Arrays.deepToString(matrix));
    }

    public static void main(String[] args) {
        InputFormatter inputf = new InputFormatter();
        takeInput("(3, 3) [3 8 6 5 0 2 7 4 1]");
    }
}
