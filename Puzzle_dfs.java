import java.util.*;

class Node {
    Node parent;
    int[][] mat = new int[3][3];
    int x, y;
    int level;

    Node(int[][] mat, int x, int y, int newX, int newY, int level, Node parent) {
        this.parent = parent;
        for (int i = 0; i < 3; i++) {
            this.mat[i] = Arrays.copyOf(mat[i], 3);
        }
        this.mat[x][y] = this.mat[newX][newY];
        this.mat[newX][newY] = 0;
        this.level = level;
        this.x = newX;
        this.y = newY;
    }
}

public class Puzzle_dfs {

    static int N = 3;

    static int printMatrix(int[][] mat) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(mat[i][j] + " ");
            System.out.println();
        }
        return 0;
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

    static boolean solveDFS(Node node, int[][] fin, HashSet<String> visited) {
        if (Arrays.deepEquals(node.mat, fin)) {
            printPath(node);
            return true;
        }
        visited.add(Arrays.deepToString(node.mat));
        for (int i = 0; i < 4; i++) {
            if (isSafe(node.x + row[i], node.y + col[i])) {
                Node child = new Node(node.mat, node.x, node.y, node.x + row[i], node.y + col[i], node.level + 1, node);
                if (!visited.contains(Arrays.deepToString(child.mat))) {
                    if (solveDFS(child, fin, visited))
                        return true;
                }
            }
        }
        return false;
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
        Node root = new Node(initial, x, y, x, y, 0, null);
        HashSet<String> visited = new HashSet<>();
        solveDFS(root, fin, visited);
    }
}
