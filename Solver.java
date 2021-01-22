import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Solver is a class that contains the methods used to search for and print solutions
 * plus the data structures needed for the search.
 */

public class Solver {

    ArrayList<Node> unexpanded = new ArrayList<>();
    ArrayList<Node> expanded = new ArrayList<>();
    Node rootNode;

    /**
     * Solver
     * Solver is a constructor that sets up an instance of the class
     * with a node corresponding to the initial state as the root node.
     *
     * @param initialBoard
     */
    public Solver(int[][] initialBoard) {
        GameState initialState = new GameState(initialBoard);
        this.rootNode = new Node(initialState);
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
        Node n = this.unexpanded.get(0);
        for (final Node node : this.unexpanded) {
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
    public void UniformCostSolve(final PrintWriter output) {
        this.unexpanded.add(this.rootNode);
        while (!this.unexpanded.isEmpty()) {
            Node n = this.getLowest();
            this.unexpanded.remove(n);
            System.out.println(n.state.toString() + " " + n.getCost() + " " + n.getHeuristic());
            if (n.state.isGoal()) {
                this.reportSolution(n, output);
                return;
            }
            this.expanded.add(n);
            ArrayList<GameState> moveList = n.state.possibleMoves();
            for (GameState gs : moveList) {
                if ((Node.findNodeWithState(this.unexpanded, gs) == null) && (Node.findNodeWithState(this.expanded, gs) == null)) {
                    Node newNode = new Node(gs, n, n.getCost() + 1, 0);
                    this.unexpanded.add(newNode);
                }
            }
        }
        output.println("No solution found");
    }

    /**
     * AStarSolve
     * A* algorithm implementation to solve the N puzzle
     *
     * @param output - where output should be directed
     */
    public void AStarSolve(final PrintWriter output) {
        int heuristic;
        this.unexpanded.add(this.rootNode);
        while (!this.unexpanded.isEmpty()) {
            Node n = this.getLowest();
            this.unexpanded.remove(n);
            System.out.println(n.state.toString() + " " + n.getCost() + " " + n.getHeuristic());
            if (n.state.isGoal()) {
                this.reportSolution(n, output);
                return;
            }
            this.expanded.add(n);
            ArrayList<GameState> moveList = n.state.possibleMoves();
            for (GameState gs : moveList) {
                if ((Node.findNodeWithState(this.unexpanded, gs) == null) && (Node.findNodeWithState(this.expanded, gs) == null)) {
                    heuristic = gs.calculateHeuristic();
                    Node newNode = new Node(gs, n, n.getCost() + 1, heuristic);
                    this.unexpanded.add(newNode);
                }
            }
        }
        output.println("No solution found");
    }


    public void BreathFirstSearch(PrintWriter output) {
        this.unexpanded.add(this.rootNode);
        while (!this.unexpanded.isEmpty()) {
            Node n = this.unexpanded.get(0);
            this.unexpanded.remove(n);
            if (n.state.isGoal()) {
                this.reportSolution(n, output);
                return;
            }
            this.expanded.add(n);
            ArrayList<GameState> moveList = n.state.possibleMoves();
            for (GameState gs : moveList) {
                if ((Node.findNodeWithState(this.unexpanded, gs) == null) && (Node.findNodeWithState(this.expanded, gs) == null)) {
                    Node newNode = new Node(gs, n, n.getCost() + 1, 0);
                    this.unexpanded.add(newNode);
                }
            }
        }
        output.println("No solution found");
    }

    public void DepthFirstSearch(PrintWriter output) {
        this.unexpanded.add(this.rootNode);
        while (!this.unexpanded.isEmpty()) {
            Node n = this.unexpanded.get(this.unexpanded.size() - 1);
            this.unexpanded.remove(n);
            if (n.state.isGoal()) {
                this.reportSolution(n, output);
                return;
            }
            this.expanded.add(n);
            ArrayList<GameState> moveList = n.state.possibleMoves();
            for (GameState gs : moveList) {
                if ((Node.findNodeWithState(this.unexpanded, gs) == null) && (Node.findNodeWithState(this.expanded, gs) == null)) {
                    Node newNode = new Node(gs, n, n.getCost() + 1, 0);
                    this.unexpanded.add(newNode);
                }
            }
        }
        output.println("No solution found");
    }

    public void DepthLimitedSearch(PrintWriter output) {
        this.unexpanded.add(this.rootNode);
        int limit = 10;
        int depth = 0;
        while (!this.unexpanded.isEmpty() && limit >= depth) {
            depth++;
            Node n = this.unexpanded.get(this.unexpanded.size() - 1);
            this.unexpanded.remove(n);
            if (n.state.isGoal()) {
                this.reportSolution(n, output);
                return;
            }
            this.expanded.add(n);
            ArrayList<GameState> moveList = n.state.possibleMoves();
            for (GameState gs : moveList) {
                if ((Node.findNodeWithState(this.unexpanded, gs) == null) && (Node.findNodeWithState(this.expanded, gs) == null)) {
                    Node newNode = new Node(gs, n, n.getCost() + 1, 0);
                    this.unexpanded.add(newNode);
                }
            }
        }
        output.println("No solution found");
    }

    public void GreedySearch(final PrintWriter output) {
        int heuristic;
        this.unexpanded.add(this.rootNode);
        while (!this.unexpanded.isEmpty()) {
            Node n = this.getLowest();
            this.unexpanded.remove(n);
            if (n.state.isGoal()) {
                this.reportSolution(n, output);
                return;
            }
            this.expanded.add(n);
            ArrayList<GameState> moveList = n.state.possibleMoves();
            for (GameState gs : moveList) {
                if ((Node.findNodeWithState(this.unexpanded, gs) == null) && (Node.findNodeWithState(this.expanded, gs) == null)) {
                    heuristic = gs.calculateHeuristic();
                    Node newNode = new Node(gs, n, 0, heuristic);
                    this.unexpanded.add(newNode);
                }
            }
        }
        output.println("No solution found");
    }


    /**
     * printSolution is a recursive method that prints all the states in a solution.
     * It uses parent attribute to backtrack back to root
     *
     * @param n      - goal state node.
     * @param output - out stream.
     */
    public static void printSolution(final Node n, final PrintWriter output) {
        if (n.parent != null) {
            printSolution(n.parent, output);
        }
        output.println(n.state + " " + n.getCost() + " " + n.getHeuristic());
    }

    /**
     * reportSolution prints the solution and the number of expanded and unexpanded nodes.
     *
     * @param n      - goal state node.
     * @param output - out stream.
     */
    public void reportSolution(final Node n, final PrintWriter output) {
        output.println("Solution found!");
        Solver.printSolution(n, output);
        output.println(n.getCost() + " Moves");
        output.println("Nodes expanded: " + this.expanded.size());
        output.println("Nodes unexpanded: " + this.unexpanded.size());
        output.println();
    }


    public static void main(final String[] args) throws Exception {
        Solver problem;
        File outFile;
        PrintWriter output;
        Scanner scan = new Scanner(System.in);
        int choice = scan.nextInt();
        choice = choice > 7 || choice < 1 ? -1 : choice;
        int[][] initialConfig = new int[][]{{1, 2}, {3, 4}, {0, 5}};

        switch (choice) {
            case -1:
                System.out.println("Invalid command!");
                System.exit(-1);
            case 2: // Dijkstra's case
                long startTime = System.nanoTime();
                problem = new Solver(initialConfig);
                outFile = new File("outputUniCost.txt");
                output = new PrintWriter(outFile);
                problem.UniformCostSolve(output);
                output.close();
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;
                System.out.println(duration + " ms");
                break;
            case 3: // Astar case
                startTime = System.nanoTime();
                problem = new Solver(initialConfig);
                outFile = new File("outputAstar.txt");
                output = new PrintWriter(outFile);
                problem.AStarSolve(output);
                output.close();
                endTime = System.nanoTime();
                duration = (endTime - startTime) / 1000000;
                System.out.println(duration + " ms");
                break;
            case 4:
                startTime = System.nanoTime();
                problem = new Solver(initialConfig);
                outFile = new File("outputBFS.txt");
                output = new PrintWriter(outFile);
                problem.BreathFirstSearch(output);
                output.close();
                endTime = System.nanoTime();
                duration = (endTime - startTime) / 1000000;
                System.out.println(duration + " ms");
                break;
            case 5:
                startTime = System.nanoTime();
                problem = new Solver(initialConfig);
                outFile = new File("outputDFS.txt");
                output = new PrintWriter(outFile);
                problem.DepthFirstSearch(output);
                output.close();
                endTime = System.nanoTime();
                duration = (endTime - startTime) / 1000000;
                System.out.println(duration + " ms");
                break;
            case 6:
                startTime = System.nanoTime();
                problem = new Solver(initialConfig);
                outFile = new File("outputGreedy.txt");
                output = new PrintWriter(outFile);
                problem.GreedySearch(output);
                output.close();
                endTime = System.nanoTime();
                duration = (endTime - startTime) / 1000000;
                System.out.println(duration + " ms");
                break;
            case 7:
                startTime = System.nanoTime();
                problem = new Solver(initialConfig);
                outFile = new File("outputDepthLimited.txt");
                output = new PrintWriter(outFile);
                problem.GreedySearch(output);
                output.close();
                endTime = System.nanoTime();
                duration = (endTime - startTime) / 1000000;
                System.out.println(duration + " ms");
                break;
        }
    }
}
