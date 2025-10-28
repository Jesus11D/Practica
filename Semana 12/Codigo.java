import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class DyV {

    static class Metrics {
        long swaps = 0;
        long comparisons = 0;
        long timeNs = 0;
        Metrics() {}
        @Override
        public String toString() {
            return "Comparisons: " + comparisons + ", Swaps: " + swaps + ", Time (ms): " + (timeNs / 1_000_000.0);
        }
    }

    public static int shellSort(int[] a, Metrics m, int target) {
        long start = System.nanoTime();
        for (int i = 0; i < a.length; i++) {
            m.comparisons++;
            if (a[i] == target) {
                m.timeNs = System.nanoTime() - start;
                return i;
            }
        }
        m.timeNs = System.nanoTime() - start;
        return -1;
    }

    public static int quickSort(int[] a, Metrics m, int target) {
        long start = System.nanoTime();
        int left = 0;
        int right = a.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            m.comparisons++;
            if (a[mid] == target) {
                m.timeNs = System.nanoTime() - start;
                return mid;
            } else if (a[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        m.timeNs = System.nanoTime() - start;
        return -1;
    }

    private static int[] generateRandom(int n, int maxVal, long seed) {
        Random r = (seed == Long.MIN_VALUE) ? new Random() : new Random(seed);
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = r.nextInt(maxVal) + 1;
        return a;
    }

    private static int[] generateSorted(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i + 1;
        return a;
    }

    private static int[] generateReverse(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = n - i;
        return a;
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("=== Busqueda con ShellSearch y QuickSearch ===");
            System.out.print("Tamano del arreglo: ");
            int n = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Generar: 1) Aleatorio  2) Ordenado  3) Reverso  Elige: ");
            int modo = Integer.parseInt(sc.nextLine().trim());
            int[] original;
            switch (modo) {
                case 2:
                    original = generateSorted(n);
                    break;
                case 3:
                    original = generateReverse(n);
                    break;
                default:
                    System.out.print("Semilla aleatoria: ");
                    String s = sc.nextLine().trim();
                    long seed = s.isEmpty() ? Long.MIN_VALUE : Long.parseLong(s);
                    original = generateRandom(n, Math.max(100, n * 2), seed);
                    break;
            }

            int[] aUnsorted = Arrays.copyOf(original, original.length);
            int[] aSorted = Arrays.copyOf(original, original.length);
            Arrays.sort(aSorted);

            System.out.print("Ingresa el numero a buscar: ");
            int target = Integer.parseInt(sc.nextLine().trim());

            Metrics ms = new Metrics();
            int idxLineal = shellSort(aUnsorted, ms, target);

            Metrics mq = new Metrics();
            int idxBinaria = quickSort(aSorted, mq, target);

            System.out.println();
            System.out.println("Resultados:");
            System.out.println("ShellSearch (lineal sobre arreglo sin ordenar) -> idx: " + idxLineal + " | " + ms.toString());
            System.out.println("QuickSearch (binaria sobre arreglo ordenado)   -> idx: " + idxBinaria + " | " + mq.toString());
            System.out.println();

            boolean foundLineal = (idxLineal >= 0);
            boolean foundBinaria = (idxBinaria >= 0);
            System.out.println("Verificacion: Lineal encontro? " + foundLineal + " | Binaria encontro? " + foundBinaria);
            System.out.println();

            System.out.println("Complejidad teorica:");
            System.out.println("Busqueda lineal -> mejor O(1), promedio O(n), peor O(n).");
            System.out.println("Busqueda binaria -> mejor O(1), promedio O(log n), peor O(log n) (si el arreglo esta ordenado).");
            System.out.println();
            System.out.println("Recomendaciones:");
            System.out.println("- Usar Busqueda binaria cuando el arreglo este ordenado.");
            System.out.println("- Busqueda lineal sirve para arreglos pequenos o sin ordenar.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i - 1] > a[i]) return false;
        return true;
    }
}
