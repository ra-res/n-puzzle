import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class GameState {

    private final int[][] board;
    public static final int SIZE = 3;
    static final int[][] INITIAL_BOARD = {{8,7,6},{5,4,3},{2,1,0}};
    static final int[][] GOAL_BOARD = {{1,2,3},{4,5,6},{7,8,0}};

    /**
     * GameState
     * Constructor for GameState, sets board to the int[][] passed as argument
     * @param board int matrix holding the 8 puzzle board configuration
     */
    public GameState(int[][] board)
    {
        this.board = board;
    }

    /**
     * clone
     * @return a new GameState with the same board configuration as the current GameState.
     */
    public GameState clone()
    {
        int[][] clonedBoard = new int[SIZE][SIZE];
        for (int i = 0; i < this.board.length; i++)
            System.arraycopy(this.board[i], 0, clonedBoard[i], 0, this.board[0].length);
        return new GameState(clonedBoard);
    }

    /**
     * toString
     * @return toString returns the board configuration of the current GameState as a printable string.
     */
    @Override
    public String toString()
    {
        String temp = Arrays.deepToString(this.board);
        return temp;
    }

    /**
     * isGoal
     * @return true if and only if the board configuration of the current GameState is the goal configuration.
     */
    public boolean isGoal()
    {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (this.board[i][j] != GOAL_BOARD[i][j]) return false;
        return true;
    }

    /**
     * isSolvable
     * reference: https://www.geeksforgeeks.org/check-instance-8-puzzle-solvable/
     * @return true if number of inversions is even, false if number of inversions is odd.
     */
    public boolean isSolvable()
    {
        int inversions = 0;
        for (int i = 0; i < SIZE - 1; i++)
            for (int j = i + 1; j < SIZE; j++)
                if (this.board[j][i] > 0 &&
                        this.board[j][i] > this.board[i][j])
                    inversions++;
        return inversions%2==0;
    }

    /**
     * sameBoard
     * @param gs - GameState to compare this.board to gs.board
     * @return true if and only if the GameState supplied as argument has the same board
     */
    public boolean sameBoard(GameState gs)
    {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (this.board[i][j] != gs.board[i][j]) return false;
        return true;
    }

    /**
     * getBoard
     * @return the board corresponding to the object
     */
    public int[][] getBoard()
    {
        return this.board;
    }

    /**
     * swap
     * @param i - index corresponding to row
     * @param j - index corresponding to col
     * @param x - index corresponding to row
     * @param y - index corresponding to col
     */
    public void swap(int i, int j, int x, int y)
    {
        int temp = this.board[i][j];
        this.board[i][j] = this.board[x][y];
        this.board[x][y] = temp;
    }

    /**
     * calculateHeuristic
     * @return estimated distance to goal state
     */
    public int calculateHeuristic()
    {
        int distance = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if (board[i][j] != 0 && board[i][j] != GOAL_BOARD[i][j])
                {
                    int division = (board[i][j] - 1) / SIZE;
                    int modulo = (board[i][j] - 1) % SIZE;
                    distance += Math.abs(division-i) + Math.abs(modulo-j);
                }
        return distance;
    }

    /**
     * possibleMoves
     * @return list of all GameStates that can be reached in a single move from the current GameState.
     */
    public ArrayList<GameState> possibleMoves()
    {
        ArrayList<GameState> moves = new ArrayList<GameState>();
        HashMap<String, Integer[]> potentialMoves = new HashMap<>();

        for (int i = 0; i < SIZE; i++)
        {
            for( int j = 0; j < SIZE; j++)
            {
                potentialMoves.put("WEST",new Integer[]{i, j-1});
                potentialMoves.put("EAST",new Integer[]{i, j+1});
                potentialMoves.put("SOUTH",new Integer[]{i+1, j});
                potentialMoves.put("NORTH",new Integer[]{i-1, j});
                for (Integer[] dir  : potentialMoves.values())
                {
                    if (dir[0] >= 0 && dir[1] >= 0 && dir[0] < SIZE && dir[1] < SIZE && board[dir[0]][dir[1]] == 0)
                    {
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

