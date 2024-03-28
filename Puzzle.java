package asg1;
import java.util.Arrays;
import java.util.PriorityQueue;

class Node {
    Node parent;
    int[][] mat = new int[3][3];
    int x, y;
    int cost;
    int level;

    Node(int[][] mat, int x, int y, int newX, int newY, int level, Node parent) {
        this.parent = parent;
        for (int i = 0; i < 3; i++) {
            this.mat[i] = Arrays.copyOf(mat[i], 3);
        }
        this.mat[x][y] = this.mat[newX][newY];
        this.mat[newX][newY] = 0;
        this.cost = Integer.MAX_VALUE;
        this.level = level;
        this.x = newX;
        this.y = newY;
    }
}

public class Puzzle {

    static int N = 3;

    static int printMatrix(int[][] mat) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(mat[i][j] + " ");
            System.out.println();
        }
        return 0;
    }

    static int calculateCost(int[][] initial, int[][] fin) {
        int count = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (initial[i][j] != 0 && initial[i][j] != fin[i][j])
                    count++;
        return count;
    }

    static boolean isSafe(int x, int y) {
        return (x >= 0 && x < N && y >= 0 && y < N);
    }

    static void printPath(Node root) {
        if (root == null)
            return;
        printPath(root.parent);
        printMatrix(root.mat);
        System.out.println();
    }

    static class Comp implements java.util.Comparator<Node> {
        public int compare(Node lhs, Node rhs) {
            return (lhs.cost + lhs.level) - (rhs.cost + rhs.level);
        }
    }

    static void solve(int[][] initial, int x, int y, int[][] fin) {
        PriorityQueue<Node> pq = new PriorityQueue<>(new Comp());
        Node root = new Node(initial, x, y, x, y, 0, null);
        root.cost = calculateCost(initial, fin);
        pq.add(root);
        while (!pq.isEmpty()) {
            Node min = pq.poll();
            if (min.cost == 0) {
                printPath(min);
                return;
            }
            for (int i = 0; i < 4; i++) {
                if (isSafe(min.x + row[i], min.y + col[i])) {
                    Node child = new Node(min.mat, min.x, min.y, min.x + row[i], min.y + col[i], min.level + 1, min);
                    child.cost = calculateCost(child.mat, fin);
                    pq.add(child);
                }
            }
        }
    }

    static int row[] = {1, 0, -1, 0};
    static int col[] = {0, -1, 0, 1};

    public static void main(String args[]) {
        int initial[][] =
                {
                        {1, 2, 3},
                        {5, 6, 0},
                        {7, 8, 4}
                };
        int fin[][] =
                {
                        {1, 2, 3},
                        {5, 8, 6},
                        {0, 7, 4}
                };
        int x = 1, y = 2;
        solve(initial, x, y, fin);
    }
}
