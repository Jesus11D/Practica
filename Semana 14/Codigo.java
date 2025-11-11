import java.util.*;

public class RyB {

    static class ResultPath {
        boolean found;
        List<int[]> path;
        ResultPath() { found = false; path = new ArrayList<>(); }
    }

    public static long factorialRec(int n) {
        System.out.println("entrando factorialRec(" + n + ")");
        if (n <= 1) {
            System.out.println("saliendo factorialRec(" + n + ") -> 1");
            return 1;
        }
        long r = n * factorialRec(n - 1);
        System.out.println("saliendo factorialRec(" + n + ") -> " + r);
        return r;
    }

    public static long factorialIter(int n) {
        long r = 1;
        for (int i = 2; i <= n; i++) r *= i;
        return r;
    }

    public static void funA(int x) {
        System.out.println("entrando A(" + x + ")");
        if (x > 0) funB(x - 1);
        System.out.println("saliendo A(" + x + ")");
    }

    public static void funB(int x) {
        System.out.println("entrando B(" + x + ")");
        if (x > 0) funA(x - 2);
        System.out.println("saliendo B(" + x + ")");
    }

    private static boolean validCell(int[][] maze, int r, int c) {
        int R = maze.length, C = maze[0].length;
        return r >= 0 && r < R && c >= 0 && c < C && maze[r][c] == 0;
    }

    private static void dfsMaze(int[][] maze, int r, int c, int tr, int tc, boolean[][] vis, List<int[]> path, ResultPath res) {
        if (res.found) return;
        if (!validCell(maze, r, c) || vis[r][c]) return;
        vis[r][c] = true;
        path.add(new int[]{r, c});
        if (r == tr && c == tc) {
            res.found = true;
            res.path = new ArrayList<>(path);
            return;
        }
        int[][] moves = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] m : moves) {
            dfsMaze(maze, r + m[0], c + m[1], tr, tc, vis, path, res);
            if (res.found) return;
        }
        path.remove(path.size() - 1);
        vis[r][c] = false;
    }

    public static ResultPath solveMaze(int[][] maze, int sr, int sc, int tr, int tc) {
        ResultPath res = new ResultPath();
        int R = maze.length, C = maze[0].length;
        boolean[][] vis = new boolean[R][C];
        dfsMaze(maze, sr, sc, tr, tc, vis, new ArrayList<int[]>(), res);
        return res;
    }

    public static List<List<Integer>> permutations(int[] arr) {
        List<List<Integer>> out = new ArrayList<>();
        boolean[] used = new boolean[arr.length];
        backPerm(arr, used, new ArrayList<Integer>(), out);
        return out;
    }

    private static void backPerm(int[] arr, boolean[] used, List<Integer> cur, List<List<Integer>> out) {
        if (cur.size() == arr.length) {
            out.add(new ArrayList<>(cur));
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            if (used[i]) continue;
            used[i] = true;
            cur.add(arr[i]);
            backPerm(arr, used, cur, out);
            cur.remove(cur.size() - 1);
            used[i] = false;
        }
    }

    public static boolean subsetSumBacktrack(int[] vals, int target, List<Integer> solution) {
        return subsetRec(vals, target, 0, 0, solution);
    }

    private static boolean subsetRec(int[] vals, int target, int index, int current, List<Integer> sol) {
        if (current == target) return true;
        if (current > target || index >= vals.length) return false;
        // choose
        sol.add(vals[index]);
        if (subsetRec(vals, target, index + 1, current + vals[index], sol)) return true;
        // unchoose
        sol.remove(sol.size() - 1);
        // skip
        if (subsetRec(vals, target, index + 1, current, sol)) return true;
        return false;
    }

    public static int linearSearch(int[] a, int target) {
        int comps = 0;
        for (int i = 0; i < a.length; i++) {
            comps++;
            if (a[i] == target) {
                System.out.println("lineal: comparaciones = " + comps);
                return i;
            }
        }
        System.out.println("lineal: comparaciones = " + comps);
        return -1;
    }

    public static int binarySearch(int[] a, int target) {
        int l = 0, r = a.length - 1;
        int comps = 0;
        while (l <= r) {
            int m = l + (r - l) / 2;
            comps++;
            if (a[m] == target) {
                System.out.println("binaria: comparaciones = " + comps);
                return m;
            } else if (a[m] < target) l = m + 1;
            else r = m - 1;
        }
        System.out.println("binaria: comparaciones = " + comps);
        return -1;
    }

    private static void printMaze(int[][] maze, List<int[]> path) {
        int R = maze.length, C = maze[0].length;
        char[][] dis = new char[R][C];
        for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) dis[i][j] = maze[i][j] == 1 ? '#' : '.';
        if (path != null) for (int[] p : path) dis[p[0]][p[1]] = 'x';
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) System.out.print(dis[i][j] + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("=== S14 Recursividad y Backtracking (codigo basado solo en el PPT) ===");

            System.out.print("1) Factorial recursivo/iterativo, 2) Recursividad indirecta, 3) Resolver laberinto, 4) Permutaciones, 5) Subset sum backtracking, 6) Busquedas. Elige opcion: ");
            int opt = Integer.parseInt(sc.nextLine().trim());

            if (opt == 1) {
                System.out.print("Ingresa n para factorial: ");
                int n = Integer.parseInt(sc.nextLine().trim());
                long t0 = System.nanoTime();
                long fr = factorialRec(n);
                long t1 = System.nanoTime();
                long fi = factorialIter(n);
                long t2 = System.nanoTime();
                System.out.println("Factorial recursivo(" + n + ") = " + fr + " tiempo(ms): " + ((t1 - t0) / 1_000_000.0));
                System.out.println("Factorial iterativo(" + n + ") = " + fi + " tiempo(ms): " + ((t2 - t1) / 1_000_000.0));
            } else if (opt == 2) {
                System.out.print("Ingresa valor para recursividad indirecta (ej: 5): ");
                int x = Integer.parseInt(sc.nextLine().trim());
                funA(x);
            } else if (opt == 3) {
                int[][] maze = {
                    {0,1,0,0,0},
                    {0,1,0,1,0},
                    {0,0,0,1,0},
                    {1,1,0,1,0},
                    {0,0,0,0,0}
                };
                System.out.println("Laberinto inicial ('.' camino '#' muro):");
                printMaze(maze, null);
                ResultPath rp = solveMaze(maze, 0, 0, 4, 4);
                if (rp.found) {
                    System.out.println("Camino encontrado (x):");
                    printMaze(maze, rp.path);
                    System.out.println("Ruta:");
                    for (int[] p : rp.path) System.out.print(Arrays.toString(p) + " ");
                    System.out.println();
                } else {
                    System.out.println("No existe camino.");
                }
            } else if (opt == 4) {
                System.out.print("Ingrese m (tamanio conjunto para permutar, ej: 3): ");
                int m = Integer.parseInt(sc.nextLine().trim());
                int[] base = new int[m];
                for (int i = 0; i < m; i++) base[i] = i + 1;
                List<List<Integer>> perms = permutations(base);
                System.out.println("Permutaciones de " + Arrays.toString(base) + " total = " + perms.size());
                for (List<Integer> p : perms) System.out.println(p);
            } else if (opt == 5) {
                System.out.print("Ingrese valores separados por espacios (ej: 3 4 5 2): ");
                String[] parts = sc.nextLine().trim().split("\\s+");
                int[] vals = new int[parts.length];
                for (int i = 0; i < parts.length; i++) vals[i] = Integer.parseInt(parts[i]);
                System.out.print("Ingrese target: ");
                int target = Integer.parseInt(sc.nextLine().trim());
                List<Integer> sol = new ArrayList<>();
                boolean ok = subsetSumBacktrack(vals, target, sol);
                if (ok) {
                    System.out.println("Solucion encontrada: " + sol);
                } else {
                    System.out.println("No existe subconjunto con suma = " + target);
                }
            } else if (opt == 6) {
                System.out.print("Ingrese tam arreglo para buscar (ej: 20): ");
                int t = Integer.parseInt(sc.nextLine().trim());
                int[] arr = new int[t];
                for (int i = 0; i < t; i++) arr[i] = i + 1;
                System.out.print("Ingrese numero a buscar: ");
                int objetivo = Integer.parseInt(sc.nextLine().trim());
                long s0 = System.nanoTime();
                int idxL = linearSearch(arr, objetivo);
                long s1 = System.nanoTime();
                int idxB = binarySearch(arr, objetivo);
                long s2 = System.nanoTime();
                System.out.println("Lineal retorno: " + idxL + " tiempo(ms): " + ((s1 - s0) / 1_000_000.0));
                System.out.println("Binaria retorno: " + idxB + " tiempo(ms): " + ((s2 - s1) / 1_000_000.0));
            } else {
                System.out.println("Opcion invalida.");
            }

            System.out.println();
            System.out.println("Fin. Codigo implementa solo lo que aparece en el PPT S14: recursividad y backtracking.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            sc.close();
        }
    }
}
