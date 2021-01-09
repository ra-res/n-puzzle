import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Solver is a class that contains the methods used to search for and print solutions
 * plus the data structures needed for the search.
 */

public class Solver {

    ArrayList<Node> unexpanded = new ArrayList<>(); // Holds unexpanded node list
    ArrayList<Node> expanded = new ArrayList<>();  // Holds expanded node list
    Node rootNode;                                      // Node representing initial state

    /**
     * Solver
     * Solver is a constructor that sets up an instance of the class
     * with a node corresponding to the initial state as the root node.
     *
     * @param initialBoard
     */
    public Solver(int[][] initialBoard) {
        GameState initialState = new GameState(initialBoard);
        rootNode = new Node(initialState);
    }

    /**
     * getLowest
     *
     * @return Node with lowest cost value
     * For uniform cost search, heuristic will be initialised to 0 to promote versatility
     * so adding it won't change the outcome.
     * Method can be used for both uniform cost search and a* search
     */
    public Node getLowest() {
        Node n = unexpanded.get(0);
        for (Node node : unexpanded) {
            if ((node.getHeuristic() + node.getCost()) < (n.getCost() + n.getHeuristic())) {
                n = node;
            }
        }
        return n;
    }

    /**
     * UniformCostSolve
     * Uniform cost search algorithm implementation to solve the 8 puzzle
     *
     * @param output - where output should be directed
     */
    public void UniformCostSolve(PrintWriter output) {
        if (rootNode.state.isSolvable()) // checks to see if gamestate is not solvable
        {
            output.println("No solution found");
            return;
        }

        unexpanded.add(rootNode); // adds node to unexpanded
        while (!unexpanded.isEmpty()) {
            Node n = getLowest(); // Lowest cost node
            unexpanded.remove(n); // Removes lowest cost node from unexpanded
            if (n.state.isGoal()) { // Checks to see if node is goal{
                reportSolution(n, output);
                return;
            }
            expanded.add(n); // Adds node to expanded
            ArrayList<GameState> moveList = n.state.possibleMoves(); // Expands node and generates all possible moves
            for (GameState gs : moveList) // Loops through every possible move
            {
                if ((Node.findNodeWithState(unexpanded, gs) == null) && (Node.findNodeWithState(expanded, gs) == null)) //Check to see if state already been visited
                {
                    Node newNode = new Node(gs, n, n.getCost() + 1, 0); // Generates new node, increments cost++
                    unexpanded.add(newNode); // Adds new node to unexpanded
                }
            }
        }
        output.println("No solution found");
    }

    /**
     * AStarSolve
     * A* algorithm implementation to solve the 8 puzzle
     *
     * @param output - where output should be directed
     */
    public void AStarSolve(PrintWriter output) {
        if (rootNode.state.isSolvable()) // checks to see if gamestate is not solvable
        {
            output.println("No solution found");
            return;
        }
        int heuristic;
        unexpanded.add(rootNode); // adds node to unexpanded
        while (!unexpanded.isEmpty()) {
            Node n = getLowest(); // Gets lowest cost node
            unexpanded.remove(n); // Removes lowest cost node from unexpanded
            if (n.state.isGoal()) {
                reportSolution(n, output);
                return;
            }
            expanded.add(n); // Adds node to expanded
            ArrayList<GameState> moveList = n.state.possibleMoves(); // Generates possible moves from n.state
            for (GameState gs : moveList) // For loop through every possible move
            {
                if ((Node.findNodeWithState(unexpanded, gs) == null) && (Node.findNodeWithState(expanded, gs) == null)) // Checks to see if move already visited
                {
                    heuristic = gs.calculateHeuristic(); //Gets heuristic for current possible move
                    Node newNode = new Node(gs, n, n.getCost() + 1, heuristic); // Creates new node
                    unexpanded.add(newNode); // Adds new node to unexpanded
                }
            }
        }
        output.println("No solution found");
    }

    /**
     * printSolution is a recursive method that prints all the states in a solution.
     * It uses the parent links to trace from the goal to the initial state then prints
     * each state as the recursion unwinds.
     *
     * @param n      node representing the goal state.
     * @param output - where the output should be directed.
     */
    public void printSolution(@NotNull Node n, PrintWriter output) {
        if (n.parent != null) {
            printSolution(n.parent, output);
        }
        output.println(n.state);
    }

    /**
     * reportSolution prints the solution together with statistics on the number of moves
     * and the number of expanded and unexpanded nodes.
     *
     * @param n      - node representing the goal state.
     * @param output - where the output should be directed.
     */
    public void reportSolution(Node n, PrintWriter output) {
        output.println("Solution found!");
        printSolution(n, output);
        output.println(n.getCost() + " Moves");
        output.println("Nodes expanded: " + expanded.size());
        output.println("Nodes unexpanded: " + unexpanded.size());
        output.println();
    }


    public static void main(String[] args) throws Exception {

        long startTime = System.nanoTime();
        Solver problem = new Solver(GameState.INITIAL_BOARD);  // Set up the problem to be solved.
        File outFile = new File("outputUniCost.txt"); // Create a file as the destination for output
        PrintWriter output = new PrintWriter(outFile);         // Create a PrintWriter for that file
        problem.UniformCostSolve(output);                      // Search for and print the solution
        output.close();                                        // Close the PrintWriter (to ensure output is produced).
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println(duration);


        startTime = System.nanoTime();
        Solver problem2 = new Solver(GameState.INITIAL_BOARD);  // Set up the problem to be solved.
        File outFile2 = new File("outputAstar.txt");   // Create a file as the destination for output
        PrintWriter output2 = new PrintWriter(outFile2);        // Create a PrintWriter for that file
        problem2.AStarSolve(output2);                           // Search for and print the solution
        output2.close();                                        // Close the PrintWriter (to ensure output is produced).
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000;
        System.out.println(duration);

    }
}
