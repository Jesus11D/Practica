import javax.swing.*;
import java.io.*;
import java.util.*;

public class BusquedasExternas {

    public static void main(String[] args) {
        try {
            File archivo = new File("datos.txt");
            if (!archivo.exists()) {
                try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
                    for (int i = 1; i <= 100; i++) {
                        pw.println(i * 2);
                    }
                }
            }

            List<Integer> datos = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    datos.add(Integer.parseInt(linea));
                }
            }

            int[] arreglo = datos.stream().mapToInt(i -> i).toArray();

            int clave = Integer.parseInt(
                    JOptionPane.showInputDialog("Ingrese número a buscar")
            );

            StringBuilder resultado = new StringBuilder();

            int posBloques = busquedaSecuencialBloques(arreglo, clave, 10);
            resultado.append("Búsqueda Secuencial por Bloques: ")
                    .append(posBloques >= 0 ? "Encontrado en índice " + posBloques : "No encontrado")
                    .append("\n");

            int posIndices = busquedaSecuencialConIndices(arreglo, clave, 10);
            resultado.append("Búsqueda Secuencial con Índices: ")
                    .append(posIndices >= 0 ? "Encontrado en índice " + posIndices : "No encontrado")
                    .append("\n");

            int posHash = busquedaTransformacionClaves(arreglo, clave, arreglo.length * 2);
            resultado.append("Búsqueda por Transformación de Claves: ")
                    .append(posHash >= 0 ? "Encontrado en índice " + posHash : "No encontrado")
                    .append("\n");

            JOptionPane.showMessageDialog(null, resultado.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    public static int busquedaSecuencialBloques(int[] arr, int clave, int tamBloque) {
        int n = arr.length;
        int inicio = 0;
        while (inicio < n) {
            int fin = Math.min(inicio + tamBloque, n);
            if (arr[fin - 1] >= clave) {
                for (int i = inicio; i < fin; i++) {
                    if (arr[i] == clave) return i;
                }
                return -1;
            }
            inicio += tamBloque;
        }
        return -1;
    }

    public static int busquedaSecuencialConIndices(int[] arr, int clave, int salto) {
        int n = arr.length;
        int[] indices = new int[(n + salto - 1) / salto];
        for (int i = 0; i < indices.length; i++) indices[i] = i * salto;

        int bloque = -1;
        for (int indice : indices) {
            if (arr[indice] <= clave) bloque = indice;
            else break;
        }
        if (bloque == -1) return -1;

        int fin = Math.min(bloque + salto, n);
        for (int i = bloque; i < fin; i++) {
            if (arr[i] == clave) return i;
        }
        return -1;
    }

    public static int busquedaTransformacionClaves(int[] arr, int clave, int m) {
        int n = arr.length;
        int[] tabla = new int[m];
        Arrays.fill(tabla, -1);

        for (int i = 0; i < n; i++) {
            int pos = arr[i] % m;
            while (tabla[pos] != -1) {
                pos = (pos + 1) % m;
            }
            tabla[pos] = i;
        }

        int pos = clave % m;
        int intentos = 0;
        while (tabla[pos] != -1 && intentos < m) {
            if (arr[tabla[pos]] == clave) return tabla[pos];
            pos = (pos + 1) % m;
            intentos++;
        }
        return -1;
    }
}
