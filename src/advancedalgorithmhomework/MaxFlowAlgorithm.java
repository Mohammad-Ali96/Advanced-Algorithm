package advancedalgorithmhomework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class MaxFlowAlgorithm {

    private Graph graph;
    private int source;
    private int sink;

    private int[][] adjMatrix;
    private int flow[][];
    private int capacityOfFlow[][];
    int maxflow;
    private Queue<Integer> Q;
    boolean isvisited[];
    private int capacity = 0;
    private int parent[];

    public MaxFlowAlgorithm(Graph graph, int source, int sink) {
        this.graph = graph;
        this.source = source;
        this.sink = sink;

        graph.generateRandomWeightedCompleteGraph();
        this.adjMatrix = graph.convertListToMatrix();

        for (int i = 0; i < this.graph.getV(); i++) {
            for (int j = 0; j < this.graph.getV(); j++) {
                System.out.print(this.adjMatrix[i][j] + "   ");
            }
            System.out.println("");
        }

        // adjMatrix = new int[graph.getV()][graph.getV()];
        flow = new int[graph.getV()][graph.getV()];
        capacityOfFlow = new int[graph.getV()][graph.getV()];
        isvisited = new boolean[graph.getV()];

        parent = new int[graph.getV()];
        maxflow = 0;
        Q = new LinkedList<Integer>();
        for (int i = 0; i < graph.getV(); i++) {
            for (int j = 0; j < graph.getV(); j++) {
                flow[i][j] = 0;
                capacityOfFlow[i][j] = 0;
            }
        }
    }

    public void inputGraphFromKeyBoard() throws IOException {

        adjMatrix = new int[this.graph.getV()][this.graph.getV()];

        for (int i = 0; i < this.graph.getV(); i++) {
            for (int j = 0; j < this.graph.getV(); j++) {

                adjMatrix[i][j] = 0;
            }
        }

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int u, v, capacity;
        int cnt = 0;
        while (cnt < this.graph.getE()) {

            System.out.println("enter an edge (u, v, w)");
            String s = br.readLine();
            String ss[] = s.split(" ");
            u = Integer.parseInt(ss[0]);
            v = Integer.parseInt(ss[1]);
            capacity = Integer.parseInt(ss[2]);
           
            if (u != v && adjMatrix[u][v] == 0 && adjMatrix[v][u] == 0) {
                if (v == source || u == sink) {
                    continue;
                }
                adjMatrix[u][v] = capacity;
                cnt++;
            }
        }
    }

    void DFS(int v) {
        isvisited[v] = true;
        for (int u = 0; u < graph.getV(); u++) {
            if (capacityOfFlow[v][u] > 0) {
                if (isvisited[u] == false) {
                    parent[u] = v;
                    DFS(u);

                }
            }
        }

    }

    void updateresidualnetwork() {
        for (int u = 0; u < graph.getV(); u++) {
            for (int v = 0; v < graph.getV(); v++) {
                if (this.adjMatrix[u][v] > 0) {
                    capacityOfFlow[u][v] = adjMatrix[u][v] - flow[u][v];
                    capacityOfFlow[v][u] = flow[u][v];
                }

            }
        }
    }

    boolean findapathinresdaulnetworkByBFS() {

        for (int i = 0; i < graph.getV(); i++) {
            isvisited[i] = false;
            parent[i] = -1;
        }
        int v;
        Q.add(source);
        parent[source] = source;
        isvisited[source] = true;
        while (!Q.isEmpty()) {
            v = Q.remove();
            for (int w = 0; w < graph.getV(); w++) {
                if (capacityOfFlow[v][w] > 0) {
                    if (!isvisited[w]) {
                        parent[w] = v;
                        Q.add(w);
                        isvisited[w] = true;
                    }
                }
            }
        }
        return isvisited[sink];
    }

    boolean findapathinresdaulnetworkByDFS() {

        for (int i = 0; i < graph.getV(); i++) {
            isvisited[i] = false;
        }
        for (int i = 0; i < graph.getV(); i++) {
            parent[i] = -1;
        }
        DFS(source);
        return isvisited[sink];
    }

    void initFlowOfGraph() {
        for (int i = 0; i < graph.getV(); i++) {
            for (int j = 0; j < graph.getV(); j++) {
                flow[i][j] = 0;
            }
        }
    }

    public int solveByFordFulkerson() {
        maxflow = 0;
        initFlowOfGraph();
        updateresidualnetwork();

        int cp = Integer.MAX_VALUE;

        while (findapathinresdaulnetworkByDFS()) {
            int u;
            for (int v = sink; v != source; v = parent[v]) {
                u = parent[v];
                cp = Math.min(cp, capacityOfFlow[u][v]);
            }
            for (int v = sink; v != source; v = parent[v]) {
                u = parent[v];

                if (this.adjMatrix[u][v] > 0) {
                    flow[u][v] += cp;
                } else {
                    flow[v][u] -= cp;
                }

            }

            updateresidualnetwork();
            maxflow += cp;
        }

        return maxflow;

    }

    public int solveByEdmondsKarp() {
        maxflow = 0;
        initFlowOfGraph();
        updateresidualnetwork();

        int cp = Integer.MAX_VALUE;

        while (findapathinresdaulnetworkByBFS()) {
            int u;

            for (int v = sink; v != source; v = parent[v]) {
                u = parent[v];
                cp = Math.min(cp, capacityOfFlow[u][v]);
            }
            for (int v = sink; v != source; v = parent[v]) {
                u = parent[v];

                if (this.adjMatrix[u][v] > 0) {
                    flow[u][v] += cp;
                } else {
                    flow[v][u] -= cp;
                }

            }

            updateresidualnetwork();
            maxflow += cp;
        }

        return maxflow;

    }

}
