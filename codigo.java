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

    public static void shellSort(int[] a, Metrics m) {
        int n = a.length;
        int h = 1;
        while (h < n / 3) h = 3 * h + 1;
        long start = System.nanoTime();
        while (h >= 1) {
            for (int i = h; i < n; i++) {
                int temp = a[i];
                int j = i;
                while (j >= h) {
                    m.comparisons++;
                    if (a[j - h] > temp) {
                        a[j] = a[j - h];
                        m.swaps++;
                        j -= h;
                    } else break;
                }
                a[j] = temp;
            }
            h = h / 3;
        }
        m.timeNs = System.nanoTime() - start;
    }

    public static void quickSort(int[] a, Metrics m) {
        long start = System.nanoTime();
        quickSortRec(a, 0, a.length - 1, m);
        m.timeNs = System.nanoTime() - start;
    }

    private static void quickSortRec(int[] a, int lo, int hi, Metrics m) {
        if (lo >= hi) return;
        int p = partition(a, lo, hi, m);
        quickSortRec(a, lo, p - 1, m);
        quickSortRec(a, p + 1, hi, m);
    }

    private static int partition(int[] a, int lo, int hi, Metrics m) {
        int mid = lo + (hi - lo) / 2;
        int pivot = medianOfThree(a, lo, mid, hi, m);
        int i = lo;
        int j = hi;
        while (i <= j) {
            while (true) {
                m.comparisons++;
                if (a[i] < pivot) i++;
                else break;
            }
            while (true) {
                m.comparisons++;
                if (a[j] > pivot) j--;
                else break;
            }
            if (i <= j) {
                swap(a, i, j, m);
                i++;
                j--;
            }
        }
        return i - 1;
    }

    private static int medianOfThree(int[] a, int i, int j, int k, Metrics m) {
        int x = a[i];
        int y = a[j];
        int z = a[k];
        m.comparisons += 3;
        if ((x <= y && y <= z) || (z <= y && y <= x)) return y;
        if ((y <= x && x <= z) || (z <= x && x <= y)) return x;
        return z;
    }

    private static void swap(int[] a, int i, int j, Metrics m) {
        if (i == j) return;
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
        m.swaps++;
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
            System.out.println("=== Ordenacion con ShellSort y QuickSort ===");
            System.out.print("Tamano del arreglo: ");
            int n = Integer.parseInt(sc.nextLine().trim());
            System.out.print("Generar: 1) Aleatorio  2) Ordenado  3) Reverso  Elige: ");
            int modo = Integer.parseInt(sc.nextLine().trim());
            int[] original;
            switch (modo) {
                case 2 -> original = generateSorted(n);
                case 3 -> original = generateReverse(n);
                default -> {
                    System.out.print("Semilla aleatoria: ");
                    String s = sc.nextLine().trim();
                    long seed = s.isEmpty() ? Long.MIN_VALUE : Long.parseLong(s);
                    original = generateRandom(n, Math.max(100, n * 2), seed);
                }
            }

            int[] a1 = Arrays.copyOf(original, original.length);
            int[] a2 = Arrays.copyOf(original, original.length);

            Metrics ms = new Metrics();
            shellSort(a1, ms);

            Metrics mq = new Metrics();
            quickSort(a2, mq);

            System.out.println();
            System.out.println("Resultados:");
            System.out.println("Shell Sort -> " + ms.toString());
            System.out.println("Quick Sort -> " + mq.toString());
            System.out.println();

            boolean okShell = isSorted(a1);
            boolean okQuick = isSorted(a2);
            System.out.println("Verificacion ordenado: ShellSort: " + okShell + " | QuickSort: " + okQuick);
            System.out.println();

            System.out.println("Complejidad teorica:");
            System.out.println("ShellSort: mejor O(n log n), promedio entre O(n^(4/3)) y O(n log^2 n), peor variable.");
            System.out.println("QuickSort: mejor O(n log n), promedio O(n log n), peor O(n^2).");
            System.out.println();
            System.out.println("Recomendaciones:");
            System.out.println("- QuickSort suele ser mas rapido en promedio.");
            System.out.println("- ShellSort mejora el ordenamiento por insercion.");
        }
    }

    private static boolean isSorted(int[] a) {
        for (int i = 1; i < a.length; i++) if (a[i - 1] > a[i]) return false;
        return true;
    }
}
