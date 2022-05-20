package advancedalgorithmhomework;


import java.util.ArrayList;

/**
 *
 * @author Mohammad Ali
 */
public class AdvancedAlgorithmHomeWork {

    public static void main(String[] args) throws Exception {

//        Graph graph1 = new Graph(10, 10);
//        graph1.generateRandomUnWeightedGraph();
//        VertexCover algorithm1 = new VertexCover(graph1);
//        ArrayList<Integer> U = algorithm1.findApproximateSolutionByLinearProgramin();
//        
//        Graph graph2 = new Graph(10, 10);
//        graph2.generateRandomWeightedCompleteGraph();
//        ChristofidesAlgo algorithm2 = new ChristofidesAlgo(graph2);
//        algorithm2.solve();
//        System.out.println(algorithm2.computePath());
//        
//        
//        
//        // The Time Complexity of the above algorithm is O(V + E).
//        Graph graph3 = new Graph(10, 10);
//        graph3.generateRandomUnWeightedGraph();
//        VertexCover algorithm3 = new VertexCover(graph3);
//        ArrayList<Integer> UU = algorithm3.findApproximateSolutionByRemovingEdges();
//        
//        
//        
        Graph graph4 = new Graph(3, 3);
        MaxFlowAlgorithm algo1 = new MaxFlowAlgorithm(graph4, 0, 2);
        algo1.inputGraphFromKeyBoard();
        System.out.println("Max Flow using Ford Fulkerson is : " + algo1.solveByFordFulkerson());
        
        
        Graph graph5 = new Graph(10, 10);
        MaxFlowAlgorithm algo2 = new MaxFlowAlgorithm(graph5, 0, 9);
        algo2.inputGraphFromKeyBoard();
        System.out.println("Max Flow using Edmonds Karp is : " + algo2.solveByEdmondsKarp());
        
        
        
        
        
        
        

    }

}
