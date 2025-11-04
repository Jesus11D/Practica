import java.util.*;

public class RyB {

    static class Resultado {
        boolean encontrado;
        List<int[]> path;
        Resultado(boolean e) { encontrado = e; path = new ArrayList<>(); }
    }

    public static long factorialRecursivo(int n) {
        System.out.println("entrando factorialRecursivo(" + n + ")");
        if (n == 0 || n == 1) {
            System.out.println("salida factorialRecursivo(" + n + ") -> 1");
            return 1;
        }
        long res = n * factorialRecursivo(n - 1);
        System.out.println("salida factorialRecursivo(" + n + ") -> " + res);
        return res;
    }

    public static long factorialIterativo(int n) {
        long r = 1;
        for (int i = 2; i <= n; i++) r *= i;
        return r;
    }

    public static void funcionA(int x) {
        System.out.println("entrando A(" + x + ")");
        if (x > 0) funcionB(x - 1);
        System.out.println("saliendo A(" + x + ")");
    }

    public static void funcionB(int x) {
        System.out.println("entrando B(" + x + ")");
        if (x > 0) funcionA(x - 2);
        System.out.println("saliendo B(" + x + ")");
    }

    public static boolean esValida(int[][] lab, int r, int c) {
        int R = lab.length, C = lab[0].length;
        return r >= 0 && r < R && c >= 0 && c < C && lab[r][c] == 0;
    }

    public static Resultado resolverLaberinto(int[][] lab, int sr, int sc, int dr, int dc) {
        int R = lab.length, C = lab[0].length;
        boolean[][] visit = new boolean[R][C];
        List<int[]> path = new ArrayList<>();
        Resultado res = new Resultado(false);

        dfsMaze(lab, sr, sc, dr, dc, visit, path, res);
        return res;
    }

    private static void dfsMaze(int[][] lab, int r, int c, int dr, int dc, boolean[][] visit, List<int[]> path, Resultado res) {
        if (res.encontrado) return;
        if (!esValida(lab, r, c) || visit[r][c]) return;
        visit[r][c] = true;
        path.add(new int[] {r, c});
        if (r == dr && c == dc) {
            res.encontrado = true;
            res.path = new ArrayList<>(path);
            return;
        }
        int[][] moves = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] m : moves) {
            dfsMaze(lab, r + m[0], c + m[1], dr, dc, visit, path, res);
            if (res.encontrado) return;
        }
        path.remove(path.size() - 1);
        visit[r][c] = false;
    }

    public static List<List<Integer>> permutaciones(int[] arr) {
        List<List<Integer>> out = new ArrayList<>();
        boolean[] used = new boolean[arr.length];
        List<Integer> cur = new ArrayList<>();
        backtrackPerm(arr, used, cur, out);
        return out;
    }

    private static void backtrackPerm(int[] arr, boolean[] used, List<Integer> cur, List<List<Integer>> out) {
        if (cur.size() == arr.length) {
            out.add(new ArrayList<>(cur));
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            if (used[i]) continue;
            used[i] = true;
            cur.add(arr[i]);
            backtrackPerm(arr, used, cur, out);
            cur.remove(cur.size() - 1);
            used[i] = false;
        }
    }

    public static int busquedaLineal(int[] a, int target) {
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

    public static int busquedaBinaria(int[] a, int target) {
        int l = 0, r = a.length - 1;
        int comps = 0;
        while (l <= r) {
            int m = l + (r - l) / 2;
            comps++;
            if (a[m] == target) {
                System.out.println("binaria: comparaciones = " + comps);
                return m;
            } else if (a[m] < target) {
                l = m + 1;
            } else {
                r = m - 1;
            }
        }
        System.out.println("binaria: comparaciones = " + comps);
        return -1;
    }

    private static void imprimirLaberinto(int[][] lab, List<int[]> path) {
        int R = lab.length, C = lab[0].length;
        char[][] display = new char[R][C];
        for (int i = 0; i < R; i++) for (int j = 0; j < C; j++) display[i][j] = lab[i][j] == 1 ? '#' : '.';
        if (path != null) for (int[] p : path) display[p[0]][p[1]] = 'x';
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) System.out.print(display[i][j] + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("=== Recursividad y Backtracking ===");
            System.out.print("Ingrese n para factorial (ej: 6): ");
            int n = Integer.parseInt(sc.nextLine().trim());
            System.out.println();
            long t0 = System.nanoTime();
            long fr = factorialRecursivo(n);
            long t1 = System.nanoTime();
            long ir = factorialIterativo(n);
            long t2 = System.nanoTime();
            System.out.println("Factorial recursivo(" + n + ") = " + fr + "  tiempo(ms): " + ((t1 - t0) / 1_000_000.0));
            System.out.println("Factorial iterativo(" + n + ") = " + ir + "  tiempo(ms): " + ((t2 - t1) / 1_000_000.0));
            System.out.println();
            
            System.out.print("Ingrese valor para demostrar recursion indirecta (ej: 5): ");
            int x = Integer.parseInt(sc.nextLine().trim());
            funcionA(x);
            System.out.println();
            
            int[][] lab = {
                {0, 1, 0, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 0, 1, 0},
                {1, 1, 0, 1, 0},
                {0, 0, 0, 0, 0}
            };
            System.out.println("Laberinto ('.' camino libre '#' muro). Se intentara resolver de (0,0) a (4,4).");
            imprimirLaberinto(lab, null);
            Resultado res = resolverLaberinto(lab, 0, 0, 4, 4);
            if (res.encontrado) {
                System.out.println("Camino encontrado (x indica camino):");
                imprimirLaberinto(lab, res.path);
                System.out.println("Ruta (orden):");
                for (int[] p : res.path) System.out.print(Arrays.toString(p) + " ");
                System.out.println();
            } else {
                System.out.println("No existe camino.");
            }
            System.out.println();
            
            System.out.print("Ingrese tamaño m para permutaciones (ej: 3): ");
            int m = Integer.parseInt(sc.nextLine().trim());
            int[] base = new int[m];
            for (int i = 0; i < m; i++) base[i] = i + 1;
            List<List<Integer>> perms = permutaciones(base);
            System.out.println("Permutaciones de " + Arrays.toString(base) + " (total = " + perms.size() + "):");
            for (List<Integer> p : perms) System.out.println(p);
            System.out.println();
            
            System.out.print("Ingrese tam arreglo para probar busquedas (ej: 20): ");
            int t = Integer.parseInt(sc.nextLine().trim());
            int[] arr = new int[t];
            for (int i = 0; i < t; i++) arr[i] = i + 1;
            System.out.print("Ingrese numero a buscar: ");
            int objetivo = Integer.parseInt(sc.nextLine().trim());
            long s0 = System.nanoTime();
            int idxL = busquedaLineal(arr, objetivo);
            long s1 = System.nanoTime();
            int idxB = busquedaBinaria(arr, objetivo);
            long s2 = System.nanoTime();
            System.out.println("Lineal retorno: " + idxL + " tiempo(ms): " + ((s1 - s0) / 1_000_000.0));
            System.out.println("Binaria retorno: " + idxB + " tiempo(ms): " + ((s2 - s1) / 1_000_000.0));
            System.out.println();
            
            System.out.println("Resumen rápido:");
            System.out.println("Recursividad: divide el problema y llama a funciones hasta caso base.");
            System.out.println("Backtracking: probar opciones, retroceder si no llevan a solucion.");
            System.out.println("Recursivo vs Iterativo: recursion es legible y natural para algunos problemas; iteracion suele usar menos memoria.");
        }
    }
}
