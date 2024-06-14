import java.util.*;

// Represents an edge in the flow network
class FlowEdge {
    int from, to, capacity, flow;

    public FlowEdge(int from, int to, int capacity) {
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.flow = 0;
    }
}

// Represents the flow network
class FlowNetwork {
    int V;
    List<FlowEdge>[] adj;

    public FlowNetwork(int V) {
        this.V = V;
        adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    // Adds a directed edge to the flow network
    public void addEdge(int from, int to, int capacity) {
        FlowEdge e1 = new FlowEdge(from, to, capacity);
        FlowEdge e2 = new FlowEdge(to, from, 0); // Residual edge
        adj[from].add(e1);
        adj[to].add(e2);
    }
}

public class FordFulkerson {
    private static final int INF = Integer.MAX_VALUE;

    // Finds augmenting path using DFS
    private static boolean dfs(FlowNetwork G, int s, int t, int[] parent) {
        boolean[] visited = new boolean[G.V];
        Arrays.fill(visited, false);
        Stack<Integer> stack = new Stack<>();
        stack.push(s);
        visited[s] = true;
        while (!stack.isEmpty()) {
            int u = stack.pop();
            for (FlowEdge e : G.adj[u]) {
                int v = e.to;
                if (!visited[v] && e.capacity - e.flow > 0) {
                    parent[v] = u;
                    visited[v] = true;
                    stack.push(v);
                }
            }
        }
        return visited[t];
    }

    // Finds the maximum flow in the flow network using Ford-Fulkerson algorithm
    public static int maxFlow(FlowNetwork G, int s, int t) {
        int[] parent = new int[G.V];
        int maxFlow = 0;
        while (dfs(G, s, t, parent)) {
            int bottleneck = INF;
            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                for (FlowEdge e : G.adj[u]) {
                    if (e.to == v) {
                        bottleneck = Math.min(bottleneck, e.capacity - e.flow);
                        break;
                    }
                }
            }
            for (int v = t; v != s; v = parent[v]) {
                int u = parent[v];
                for (FlowEdge e : G.adj[u]) {
                    if (e.to == v) {
                        e.flow += bottleneck;
                        break;
                    }
                }
                for (FlowEdge e : G.adj[v]) {
                    if (e.to == u) {
                        e.flow -= bottleneck;
                        break;
                    }
                }
            }
            maxFlow += bottleneck;
        }
        return maxFlow;
    }

    // Example usage
    public static void main(String[] args) {
        // Example graph
        int V = 6;
        FlowNetwork graph = new FlowNetwork(V);
//        graph.addEdge(0, 1, 16);
//        graph.addEdge(0, 2, 13);
//        graph.addEdge(1, 2, 10);
//        graph.addEdge(1, 3, 12);
//        graph.addEdge(2, 1, 4);
//        graph.addEdge(2, 4, 14);
//        graph.addEdge(3, 2, 9);
//        graph.addEdge(3, 5, 20);
//        graph.addEdge(4, 3, 7);
//        graph.addEdge(4, 5, 4);

        int source = 0;
        int sink = 5;
        System.out.println("Maximum flow possible: " + maxFlow(graph, source, sink));
    }
}
