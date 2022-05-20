package advancedalgorithmhomework;

import java.util.ArrayList;

public class ChristofidesAlgo {

    private Graph graph;
    private int[][] adjMatrix;
    String euler_path = "";
    ArrayList<Integer> path;
    ArrayList<Integer> finalPath;
    int costPath = 0;
    String detlaTSPPath = "";

    ArrayList<ArrayList<Node>> adjListAfterMST;
    boolean[] visited;
    boolean[] visitedPath;

    ChristofidesAlgo(Graph graph) {
        this.graph = graph;
        adjListAfterMST = new ArrayList<>();
        visitedPath = new boolean[this.graph.getV()];
        for (int i = 0; i < graph.getV(); i++) {
            adjListAfterMST.add(new ArrayList<>());
            visitedPath[i] = false;
        }
        path = new ArrayList<>();
        finalPath = new ArrayList<>();
    }

    private void clearVisitedArray() {
        visited = new boolean[this.graph.getV()];
        for (int i = 0; i < this.graph.getV(); i++) {
            visited[i] = false;
        }
    }

    public void computeMSTAndDuplicateEdge() {
        Mst algo = new Mst(graph);
        algo.solve();
        algo.solve();
        for (int i = 1; i < graph.getV(); i++) {
            if (i != algo.getParent()[i]) {
                adjListAfterMST.get(i).add(new Node(algo.getParent()[i], algo.getDistance()[i]));
                adjListAfterMST.get(algo.getParent()[i]).add(new Node(i, algo.getDistance()[i]));

                // Duplicate edges
                adjListAfterMST.get(i).add(new Node(algo.getParent()[i], algo.getDistance()[i]));
                adjListAfterMST.get(algo.getParent()[i]).add(new Node(i, algo.getDistance()[i]));
            }

        }

    }

    int DFSCount(int node) {

        visited[node] = true;
        int cnt = 1;
        for (int i = 0; i < this.adjListAfterMST.get(node).size(); i++) {
            if (!visited[this.adjListAfterMST.get(node).get(i).getDest()]) {
                cnt += DFSCount(this.adjListAfterMST.get(node).get(i).getDest());
            }
        }

        return cnt;
    }

    boolean isValidNextEdge(int u, int v) {

        if (this.adjListAfterMST.get(u).size() == 1) {
            return true;
        }

        clearVisitedArray();
        int cnt1 = DFSCount(u);

        removeEdge(u, v);
        clearVisitedArray();
        int cnt2 = DFSCount(u);

        addEdge(u, v);

        return (cnt1 > cnt2) ? false : true;
    }

    void printEulerUtil(int u) {

        for (int i = 0; i < this.adjListAfterMST.get(u).size(); i++) {
            int v = this.adjListAfterMST.get(u).get(i).getDest();

            if (isValidNextEdge(u, v)) {
                path.add(u);
                path.add(v);
                removeEdge(u, v);
                printEulerUtil(v);
            }
        }
    }

    

    public boolean checkIfTrangleHold() {
        adjMatrix = graph.convertListToMatrix();
        for (int i = 0; i < this.graph.getV(); i++) {
            for (int j = 0; j < this.graph.getV(); j++) {
                for (int k = 0; k < this.graph.getV(); k++) {
                    if (adjMatrix[i][j] + adjMatrix[i][k] < adjMatrix[j][k]
                            || adjMatrix[i][k] + adjMatrix[k][j] < adjMatrix[i][j]
                            || adjMatrix[j][i] + adjMatrix[j][k] < adjMatrix[i][k]
                            && adjMatrix[i][j] > 0 && adjMatrix[i][k] > 0
                            && adjMatrix[j][k] > 0) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean checkIfGraphIsComplete() {
        boolean t = true;

        for (int i = 0; i < this.graph.getV(); i++) {
            if (this.graph.adjList.get(i).size() != this.graph.getV() - 1) {
                t = false;
                break;
            }
        }

        return t;
    }

    private boolean checkGraphIfConnected() {
        clearVisitedArray();
        DFS(0);
        for (int i = 1; i < this.graph.getV(); i++) {
            if (visited[i] == false && adjListAfterMST.get(i).size() > 0) {
                return false;
            }
        }
        return true;
    }

    public void DFS(int node) {
        visited[node] = true;
        for (int i = 0; i < adjListAfterMST.get(node).size(); i++) {
            if (!visited[adjListAfterMST.get(node).get(i).getDest()]) {
                DFS(adjListAfterMST.get(node).get(i).getDest());
            }
        }
    }

    public int isEulerian() {
        if (checkGraphIfConnected() == false) {
            return 0;
        }

        int odd = 0;
        for (int i = 0; i < this.graph.getV(); i++) {
            if (adjListAfterMST.get(i).size() % 2 != 0) {
                odd++;
            }
        }
        System.out.println("the number of odd vertex " + odd);

        if (odd > 2) {
            return -1;
        }

        if (odd == 2) {
            return 1;
        }
        if (odd == 0) {
            return 1;
        }
        return -1;

    }

    public int testEuler() {
        int res = isEulerian();
        if (res == 0) {
            System.out.println("graph is not Eulerian");
            return 0;
        } else if (res == 1) {
            System.out.println("graph has a Euler path");
            return 1;
        } else {
            System.out.println("graph has a Euler cycle");
            return 2;
        }
    }

    public void solve() {
        if (checkIfGraphIsComplete()) {
            computeMSTAndDuplicateEdge();

            if (testEuler() == 1) {
                printEulerUtil(0);
            } else {
                System.out.println("Cann't find euler path");
            }

        } else {
            System.out.println("Graph Not Complete");
        }

    }

    public String computePath() {
        detlaTSPPath = "TSP Path \n";
        euler_path = "Euler Path \n";
        for (int i = 0; i < this.path.size(); i++) {
            euler_path += this.path.get(i) + " --> ";

            if (!visitedPath[this.path.get(i)]) {
                finalPath.add(this.path.get(i));
                detlaTSPPath += this.path.get(i) + " --> ";
                visitedPath[this.path.get(i)] = true;
            }
        }

        return detlaTSPPath;
    }

    private void removeEdge(int u, int v) {

        for (int i = 0; i < this.adjListAfterMST.get(u).size(); i++) {
            int tmp = this.adjListAfterMST.get(u).get(i).getDest();
            if (tmp == v) {
                this.adjListAfterMST.get(u).remove(i);
                break;
            }
        }

        for (int i = 0; i < this.adjListAfterMST.get(v).size(); i++) {
            int tmp = this.adjListAfterMST.get(v).get(i).getDest();
            if (tmp == u) {
                this.adjListAfterMST.get(v).remove(i);
                break;
            }
        }

    }

    private void addEdge(int u, int v) {
        int w = this.graph.weightOfEdge(u, v);
        this.adjListAfterMST.get(u).add(new Node(v, w));
        this.adjListAfterMST.get(v).add(new Node(u, w));
    }

    public int computePathCost() {
        int cost = 0;

        for (int i = 0; i < finalPath.size() - 1; i++) {

            cost += this.adjMatrix[finalPath.get(i)][this.finalPath.get(i + 1)];

        }

        return cost;
    }

}
