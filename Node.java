import java.util.ArrayList;

/**
The class Node represents nodes.
Its essential features are a GameState and a reference to the node's parent node. The
latter is used to assemble and output the solution path once the goal sate has been reached.
 */

public class Node
{
    GameState state;
    Node parent;
    private int cost;
    private int heuristic;

    /**
     * Node
     * Constructor used to create new nodes
     * @param state - The state associated with the node
     * @param parent 0 The node from which this node was reached.
     * @param cost - The cost of reaching this node from the initial node.
     * @param heuristic - The heuristic value associated with the state. If algorithm is uniform cost, this will be 0
     */
    public Node(GameState state, Node parent, int cost, int heuristic)
    {
        this.state = state;
        this.parent = parent;
        this.cost = cost;
        this.heuristic=heuristic;
    }

    /**
     * Node
     * Constructor used for initial node
     * @param state - initial state associated with root node
     */
    public Node(GameState state)
    {
        this(state,null,0,0);
    }

    /**
     * getCost
     * @return cost variable
     */
    public int getCost()
    {
        return cost;
    }

    /**
     * getHeuristic
     * @return heuristic variable
     */
    public int getHeuristic()
    {
        return heuristic;
    }

    /**
     * cloneNode
     * @return new Node with same values as current node
     */
    public Node cloneNode()
    {
        return new Node(state, parent, cost, heuristic);
    }

    /**
     * toString
     * @return string object representing the state associated with node
     */
    public String toString()
    {
        return "Node:" + state + " ";
    }


    /**
     * findNodeWithState
     * searches nodeList for a node whost state is that specified as second argument
     * @param nodeList - list of nodes
     * @param gs
     * @return n if node found, else null
     */
    public static Node findNodeWithState(ArrayList<Node> nodeList, GameState gs)
    {
        for (Node n : nodeList)
            if (gs.sameBoard(n.state))
                return n;
        return null;
    }
}
