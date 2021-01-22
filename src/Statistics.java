package src;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Statistics {

    private final String BFS = "outputBFS.txt";
    private final String DFS = "outputDFS.txt";
    private final String DEPTH_LIMITED = "outputDepthLimited.txt";
    private final String GREEDY = "outputGreedy.txt";
    private final String DIJKSTRA = "outputUniCost.txt";
    private final String A_STAR = "outputAstar.txt";
    private final String ITERATIVE_DEEPENING = "outputIterativeDeepening.txt";

    final private String[] files = new String[]{this.BFS, this.DFS, this.DEPTH_LIMITED, this.GREEDY, this.DIJKSTRA, this.A_STAR, this.ITERATIVE_DEEPENING};
    Map<String, ArrayList<String>> filesContent;

    public Statistics() {

        this.filesContent = new HashMap<>();
        Scanner scan;
        File curFile;
        ArrayList<String> curContent;

        for (final String fileName : this.files) {
            try {
                curFile = new File(fileName);
                scan = new Scanner(curFile);
                curContent = new ArrayList<>();

                while (scan.hasNext()) {
                    curContent.add(scan.nextLine());
                }
                this.filesContent.put(fileName, curContent);
            } catch (FileNotFoundException e1) {
                System.out.println("Could not analyse this file");
            }
        }
    }

    public void printMovesComparison() {
        for (final String key : this.filesContent.keySet()) {
            int move = this.getMoves(key);
            if (move == 0) {
                System.out.println("Algorithm " + key + " : No solution found");
            } else {
                System.out.println("Algorithm " + key + " : " + this.getMoves(key) + " Moves");
            }
        }

    }

    public int getMoves(final String fileName) {
        if (this.filesContent.isEmpty()) {
            return 0;
        }
        if (!this.filesContent.containsKey(fileName)) {
            return 0;
        }

        for (final String str : this.filesContent.get(fileName)) {
            if (str.contains("Moves")) {
                final String[] curLine = str.split(" ");
                return Integer.parseInt(curLine[0]);
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        Statistics statistics = new Statistics();
        statistics.printMovesComparison();
    }
}
