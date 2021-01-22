package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

class GoalBoardSingleton {
    static int[][] GOAL_BOARD;

    public static synchronized int[][] getInstance(final int[][] board) {
        if (GOAL_BOARD != null) {
            return GOAL_BOARD;
        }
        final int n = board.length;
        final int m = board[0].length;
        final int k = n * m;
        int o = 1;
        GOAL_BOARD = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                GOAL_BOARD[i][j] = o++ % k;
            }
        }
        System.out.println(Arrays.deepToString(GOAL_BOARD));
        return GOAL_BOARD;
    }
}

public class GameState {

    private final int[][] board;
    public static int SIZE;
    static int[][] INITIAL_BOARD;
    public int[][] GOAL_BOARD;

    /**
     * GameState
     * Constructor for GameState, sets board to the int[][] passed as argument
     *
     * @param board int matrix holding the N puzzle board configuration
     */
    public GameState(final int[][] board) {
        this.board = board;
        this.GOAL_BOARD = GoalBoardSingleton.getInstance(board);
    }

    /**
     * clone
     *
     * @return a new cloned GameState
     */

    @Override
    public GameState clone() {
        final int[][] clonedBoard = new int[this.board.length][this.board[0].length];
        for (int i = 0; i < this.board.length; i++) {
            System.arraycopy(this.board[i], 0, clonedBoard[i], 0, this.board[0].length);
        }
        return new GameState(clonedBoard);
    }

    /**
     * toString
     *
     * @return toString returns the board configuration of the current GameState as a printable string.
     */

    @Override
    public String toString() {
        return Arrays.deepToString(this.board);
    }

    /**
     * isGoal
     *
     * @return true if and only if the board configuration of the current GameState is the goal configuration.
     */

    public boolean isGoal() {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                if (this.board[i][j] != this.GOAL_BOARD[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * isSolvable
     * reference: https://www.geeksforgeeks.org/check-instance-8-puzzle-solvable/
     *
     * @return true if number of inversions is even, false if number of inversions is odd.
     */
    public boolean isSolvable() {
        int inversions = 0;
        for (int i = 0; i < this.board.length - 1; i++) {
            for (int j = i + 1; j < this.board[i].length; j++) {
                if (this.board[j][i] > 0 &&
                        this.board[j][i] > this.board[i][j]) {
                    inversions++;
                }
            }
        }
        return inversions % 2 != 0;
    }

    /**
     * sameBoard
     *
     * @param gs - GameState to compare this.board to gs.board
     * @return true if and only if the GameState supplied as argument has the same board
     */
    public boolean sameBoard(final GameState gs) {
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                if (this.board[i][j] != gs.board[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * getBoard
     *
     * @return the board corresponding to the object
     */
    public int[][] getBoard() {
        return this.board;
    }

    /**
     * swap
     *
     * @param i - index corresponding to row
     * @param j - index corresponding to col
     * @param x - index corresponding to row
     * @param y - index corresponding to col
     */
    // Code from AI ASSIGNMENT
    public void swap(final int i, final int j, final int x, final int y) {
        final int temp = this.board[i][j];
        this.board[i][j] = this.board[x][y];
        this.board[x][y] = temp;
    }

    /**
     * calculateHeuristic
     *
     * @return estimated distance to goal state
     */
    public int calculateHeuristic() {
        int distance = 0;
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                if (this.board[i][j] != 0 && this.board[i][j] != this.GOAL_BOARD[i][j]) {
                    final int division = (this.board[i][j] - 1) / this.board.length;
                    final int modulo = (this.board[i][j] - 1) % this.board.length;
                    distance += Math.abs(division - i) + Math.abs(modulo - j);
                }
            }
        }
        return distance;
    }

    public int differentHeuristic() {
        int distance = 0;
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                if (this.board[i][j] != this.GOAL_BOARD[i][j]) {
                    distance++;
                }
            }
        }
        return distance;
    }


    /**
     * possibleMoves
     *
     * @return list of all GameStates that can be reached in a single move from the current GameState.
     */
    public ArrayList<GameState> possibleMoves() {
        final ArrayList<GameState> moves = new ArrayList<GameState>();
        final HashMap<String, Integer[]> potentialMoves = new HashMap<>();

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                potentialMoves.put("WEST", new Integer[]{i, j - 1});
                potentialMoves.put("EAST", new Integer[]{i, j + 1});
                potentialMoves.put("SOUTH", new Integer[]{i + 1, j});
                potentialMoves.put("NORTH", new Integer[]{i - 1, j});
                for (final Integer[] dir : potentialMoves.values()) {
                    if (dir[0] >= 0 && dir[1] >= 0 && dir[0] < this.board.length && dir[1] < this.board[0].length && this.board[dir[0]][dir[1]] == 0) {
                        GameState newState = this.clone();
                        newState.swap(i, j, dir[0], dir[1]);
                        moves.add(newState);
                    }
                }
            }
        }
        return moves;
    }
}

