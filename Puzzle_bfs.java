//package Assignment 2.tanmoy;

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

public class Puzzle_bfs {

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

    static boolean solveBFS(int[][] initial, int[][] fin) {
        Queue<Node> queue = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        Node root = new Node(initial, 1, 2, 1, 2, 0, null);
        queue.add(root);
        visited.add(Arrays.deepToString(root.mat));

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (Arrays.deepEquals(current.mat, fin)) {
                printPath(current);
                return true;
            }
            for (int i = 0; i < 4; i++) {
                int newX = current.x + row[i];
                int newY = current.y + col[i];
                if (isSafe(newX, newY)) {
                    Node child = new Node(current.mat, current.x, current.y, newX, newY, current.level + 1, current);
                    if (!visited.contains(Arrays.deepToString(child.mat))) {
                        queue.add(child);
                        visited.add(Arrays.deepToString(child.mat));
                    }
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

        if (!solveBFS(initial, fin)) {
            System.out.println("Solution not found!");
        }
    }
}
