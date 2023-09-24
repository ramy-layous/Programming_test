import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents a Bingo game and contains methods for reading bingo boards from a file,
 * marking numbers on the board, checking for winning patterns, and calculating the final score.
 */
public class Bingo {
    private static final int BOARD_SIZE = 5;

    public static void main(String[] args) {
        String filePath = "src/main/input_data.txt";
        List<int[][]> boardList = readBingoBoardsFromFile(filePath);

        int[] numbers = {1, 76, 38, 96, 62, 41, 27, 33, 4, 2, 94, 15, 89, 25, 66, 14, 30, 0, 71, 21, 48, 44, 87, 73, 60,
                50, 77, 45, 29, 18, 5, 99, 65, 16, 93, 95, 37, 3, 52, 32, 46, 80, 98, 63, 92, 24, 35, 55, 12,
                81, 51, 17, 70, 78, 61, 91, 54, 8, 72, 40, 74, 68, 75, 67, 39, 64, 10, 53, 9, 31, 6, 7, 47, 42,
                90, 20, 19, 36, 22, 43, 58, 28, 79, 86, 57, 49, 83, 84, 97, 11, 85, 26, 69, 23, 59, 82, 88, 34,
                56, 13};

        int lastWinningNumber = 0;
        int[][] lastWinningBoard = null;

        // Loop through each drawn number.
        for (int drawnNumber : numbers) {
            for (int i = 0; i < boardList.size(); i++) {
                int[][] board = boardList.get(i);
                boolean marked = markNumberOnBoard(board, drawnNumber);

                // Check if the board is a winning board after marking.
                if (marked && checkForWin(board)) {
                    lastWinningNumber = drawnNumber;
                    lastWinningBoard = board;
                    boardList.remove(i);  // Remove the winning board from the list.
                    i--; // Decrement (i) to account for the removal of the winning board.
                }
            }
        }
        // Calculate and print the final score.
        int finalScore = calculateScore(lastWinningBoard, lastWinningNumber);
        System.out.println("Final score: " + finalScore);
    }

    /**
     * Reads bingo boards from the provided text file.
     *
     * @param filePath The path to the input file containing bingo boards.
     * @return A list of bingo boards represented as 2D arrays.
     */
    private static List<int[][]> readBingoBoardsFromFile(String filePath) {
        List<int[][]> boardList = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(filePath));
            while (scanner.hasNext()) {
                int[][] board = new int[BOARD_SIZE][BOARD_SIZE];
                for (int row = 0; row < BOARD_SIZE; row++) {
                    for (int col = 0; col < BOARD_SIZE; col++) {
                        board[row][col] = scanner.nextInt();
                    }
                }
                boardList.add(board);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found");
        }
        return boardList;
    }

    /**
     * Marks a number on the bingo board if it exists.
     *
     * @param board  The bingo board represented as a 2D array.
     * @param number The number to mark on the board.
     * @return True if the number was found and marked, false otherwise.
     */
    private static boolean markNumberOnBoard(int[][] board, int number) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == number) {
                    board[row][col] = -1; // Mark the number as found.
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a bingo board has a winning pattern.
     *
     * @param board The bingo board represented as a 2D array.
     * @return True if the board has a winning pattern, false otherwise.
     */
    private static boolean checkForWin(int[][] board) {
        for (int i = 0; i < 5; i++) {
            boolean rowWin = true;
            boolean colWin = true;
            for (int j = 0; j < 5; j++) {
                if (board[i][j] != -1) {
                    rowWin = false;  // If a cell is not marked, the row cannot be a win.
                }
                if (board[j][i] != -1) {
                    colWin = false; // If a cell is not marked, the column cannot be a win.
                }
            }
            if (rowWin || colWin) {
                return true;  // If a row or column is marked as a win, return true.
            }
        }
        return false; // No winning pattern found.
    }

    /**
     * Calculates the final score of a winning bingo board.
     *
     * @param board         The winning bingo board represented as a 2D array.
     * @param winningNumber The winning number.
     * @return The final score.
     */
    private static int calculateScore(int[][] board, int winningNumber) {
        int sumOfUnmarkedNumbers = 0;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (board[row][col] != -1) {
                    sumOfUnmarkedNumbers += board[row][col];
                }
            }
        }
        return sumOfUnmarkedNumbers * winningNumber;
    }
}
