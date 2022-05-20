package advancedalgorithmhomework;

import it.ssc.log.SscLogger;
import it.ssc.pl.milp.LP;
import it.ssc.pl.milp.Solution;
import it.ssc.pl.milp.SolutionType;
import it.ssc.pl.milp.Variable;
import java.util.ArrayList;

public class VertexCover {

    private Graph graph;
    private int[][] adjMatrix;

    public VertexCover() {

    }

    public VertexCover(Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

    public ArrayList<Integer> findApproximateSolutionByLinearProgramin() throws Exception {
        ArrayList<Integer> U = new ArrayList<>();
        ArrayList< String> constraints = new ArrayList< String>();
        String tmp = "";

        for (int i = 0; i < this.graph.getV(); i++) {
            if (i == 0) {
                tmp += "x" + (i + 1);
            } else {
                tmp += "+x" + (i + 1);
            }
        }
        // goal function
        constraints.add("min:  " + tmp);

        // add equation (constraints) for each edge
        for (int i = 0; i < this.graph.getV(); i++) {
            for (int j = 0; j < this.graph.adjList.get(i).size(); j++) {
                tmp = "";
                int u = (i + 1);
                int v = this.graph.adjList.get(i).get(j).getDest() + 1;
                tmp = "x" + u + "x" + v + "       >= 1";
                constraints.add(tmp);
            }
        }

        LP lp = new LP(constraints);
        SolutionType solution_type = lp.resolve();

        if (solution_type == SolutionType.OPTIMUM) {
            Solution soluzione = lp.getSolution();
            for (Variable var : soluzione.getVariables()) {
                SscLogger.log("Variable name :" + var.getName() + " value :" + var.getValue());
                // add vertex to the solution
                if (var.getValue() >= 0.5) {
                    int u = Integer.parseInt(var.getName().substring(1));
                    U.add(u);
                }
            }
            SscLogger.log("Value:" + soluzione.getOptimumValue());
        }

        return U;
    }

    public ArrayList<Integer> findApproximateSolutionByRemovingEdges() throws Exception {
        ArrayList<Integer> U = new ArrayList<>();
        adjMatrix = graph.convertListToMatrix();
        for (int u = 0; u < this.graph.getV(); u++) {
            for (int v = 0; v < this.graph.getV(); v++) {

                if (adjMatrix[u][v] > 0) {
                    U.add(u);
                    U.add(v);
                    removeAllEdgesRelated(u, v);
                    break;

                }

            }
        }

        return U;
    }

    private void removeAllEdgesRelated(int u, int v) {

        for (int i = 0; i < this.graph.getV(); i++) {
            // remove all outdegree
            adjMatrix[u][i] = 0;
            adjMatrix[v][i] = 0;

            // remove all indegree
            adjMatrix[i][u] = 0;
            adjMatrix[i][v] = 0;
        }

    }

}
