import javax.swing.*;

public class Ordenacion {
    public static void main(String[] args) {
        int[] numeros = {5, 3, 8, 4, 2};

        JOptionPane.showMessageDialog(null, "Arreglo original: " + arrayToString(numeros));

        int[] burbuja = numeros.clone();
        burbuja(burbuja);
        JOptionPane.showMessageDialog(null, "Burbuja: " + arrayToString(burbuja));

        int[] seleccion = numeros.clone();
        seleccion(seleccion);
        JOptionPane.showMessageDialog(null, "Selección: " + arrayToString(seleccion));

        int[] insercion = numeros.clone();
        insercion(insercion);
        JOptionPane.showMessageDialog(null, "Inserción: " + arrayToString(insercion));
    }

    public static void burbuja(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    public static void seleccion(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int min = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            int temp = arr[min];
            arr[min] = arr[i];
            arr[i] = temp;
        }
    }

    public static void insercion(int[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    public static String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int n : arr) {
            sb.append(n).append(" ");
        }
        return sb.toString();
    }
}

ola
ol
