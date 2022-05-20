package advancedalgorithmhomework;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Graph {

    private int V;
    private int E;
    ArrayList<ArrayList<Node>> adjList;
   

    Graph() {

    }

    Graph(int V, int E) {
        this.V = V;
        this.E = E;
        adjList = new ArrayList<>();
        for (int i = 0; i < this.V; i++) {
            adjList.add(new ArrayList<>());
        }
    }
    
    public int[][] convertListToMatrix() {
        int[][] adjMatrix = new int[this.getV()][this.getV()];

        for (int i = 0; i < this.getAdjList().size(); i++) {
            for (int j = 0; j < this.getAdjList().get(i).size(); j++) {
                adjMatrix[i][this.getAdjList().get(i).get(j).getDest()]
                        = this.getAdjList().get(i).get(j).getWeight();

                adjMatrix[this.getAdjList().get(i).get(j).getDest()][i]
                        = this.getAdjList().get(i).get(j).getWeight();

            }
        }
        return adjMatrix;
    }
    

    public void generateRandomWeightedCompleteGraph() {
        int[][] adjMatrix = new int[this.V][this.V];
        for (int i = 0; i < this.V; i++) {
            for (int j = 0; j < this.V; j++) {
                adjMatrix[i][j] = 0;
            }
        }

        int cnt = 0;
        Random random = new Random();
        for (int i = 0; i < this.V; i++) {
            for (int j = 0; j < this.V; j++) {
                if (i != j && adjMatrix[i][j] == 0 && adjMatrix[j][i] == 0) {
                    cnt++;
                    int w = random.nextInt(1000);
                    adjMatrix[i][j] = w;
                    adjMatrix[j][i] = w;
                }
            }
        }

        for (int u = 0; u < this.V; u++) {
            for (int v = 0; v < this.V; v++) {
                if (u != v && adjMatrix[u][v] > 0) {
                    this.adjList.get(u).add(new Node(v, adjMatrix[u][v]));

                }
            }
        }

    }

    
    public void generateRandomWeightedGraph() {
        int[][] adjMatrix = new int[this.V][this.V];
        for (int i = 0; i < this.V; i++) {
            for (int j = 0; j < this.V; j++) {
                adjMatrix[i][j] = 0;
            }
        }

        int cnt = 0;
        Random random = new Random();
        for (int i = 0; i < this.V; i++) {
            for (int j = 0; j < this.V; j++) {
                if (i != j && adjMatrix[i][j] == 0) {
                    cnt++;
                    int w = random.nextInt(1000);
                    adjMatrix[i][j] = w;
                    
                }
            }
        }

        for (int u = 0; u < this.V; u++) {
            for (int v = 0; v < this.V; v++) {
                if (u != v && adjMatrix[u][v] > 0) {
                    this.adjList.get(u).add(new Node(v, adjMatrix[u][v]));

                }
            }
        }

    }

    
    public void generateRandomUnWeightedGraph() {
        Random random = new Random();
        int[][] adjMatrix = new int[this.V][this.V];
        for (int i = 0; i < this.V; i++) {
            for (int j = 0; j < this.V; j++) {
                adjMatrix[i][j] = 0;
            }
        }

        int cnt = 0;
        while (cnt < this.getE()) {
            int u = random.nextInt(this.getV());
            int v = random.nextInt(this.getV());
            if (u != v && adjMatrix[u][v] == 0) {
                adjMatrix[u][v] = 1;
                cnt++;
            }

        }
        
        for (int u = 0; u < this.V; u++) {
            for (int v = 0; v < this.V; v++) {
                if (u != v && adjMatrix[u][v] > 0) {
                    this.adjList.get(u).add(new Node(v, adjMatrix[u][v]));

                }
            }
        }
        
    }
    
    
    
    

    public int getV() {
        return V;
    }

    public int getE() {
        return E;
    }

    public ArrayList<ArrayList<Node>> getAdjList() {
        return adjList;
    }

    public int weightOfEdge(int u, int v) {
        int weight = Integer.MAX_VALUE;
        for (int i = 0; i < adjList.get(u).size(); i++) {
            if (adjList.get(u).get(i).getDest() == v) {
                return adjList.get(u).get(i).getWeight();
            }
        }
        return weight;
    }

}
