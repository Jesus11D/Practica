import java.awt.HeadlessException;
import java.util.Arrays;
import java.util.Random;
import javax.swing.JOptionPane;

public class BusquedasInternas {

    public static int busquedaLineal(int[] arreglo, int valor) {
        for (int i = 0; i < arreglo.length; i++) {
            if (arreglo[i] == valor) {
                return i;
            }
        }
        return -1;
    }

    public static int busquedaBinaria(int[] arreglo, int valor) {
        int inicio = 0;
        int fin = arreglo.length - 1;
        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            if (arreglo[medio] == valor) {
                return medio;
            } else if (arreglo[medio] < valor) {
                inicio = medio + 1;
            } else {
                fin = medio - 1;
            }
        }
        return -1;
    }

    public static String imprimirArreglo(int[] arreglo) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arreglo.length; i++) {
            sb.append(arreglo[i]).append(" ");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Random random = new Random();
        int[] arreglo = new int[20];
        for (int i = 0; i < arreglo.length; i++) {
            arreglo[i] = random.nextInt(100) + 1;
        }

        JOptionPane.showMessageDialog(null, "Arreglo generado:\n" + imprimirArreglo(arreglo));
        Arrays.sort(arreglo);
        JOptionPane.showMessageDialog(null, "Arreglo ordenado:\n" + imprimirArreglo(arreglo));

        int opcion = 0;
        while (opcion != 3) {
            String menu = """
                          Seleccione un algoritmo de b\u00fasqueda:
                          1. B\u00fasqueda Lineal
                          2. B\u00fasqueda Binaria
                          3. Salir""";
            try {
                opcion = Integer.parseInt(JOptionPane.showInputDialog(menu));
            } catch (HeadlessException | NumberFormatException e) {
                opcion = 3;
            }

            switch (opcion) {
                case 1:
                    {
                        int valor = Integer.parseInt(JOptionPane.showInputDialog("Ingrese número a buscar:"));
                        int indice = busquedaLineal(arreglo, valor);
                        if (indice != -1) {
                            JOptionPane.showMessageDialog(null, "Valor encontrado en posición: " + indice);
                        } else {
                            JOptionPane.showMessageDialog(null, "Valor no encontrado.");
                        }       break;
                    }
                case 2:
                    {
                        int valor = Integer.parseInt(JOptionPane.showInputDialog("Ingrese número a buscar:"));
                        int indice = busquedaBinaria(arreglo, valor);
                        if (indice != -1) {
                            JOptionPane.showMessageDialog(null, "Valor encontrado en posición: " + indice);
                        } else {
                            JOptionPane.showMessageDialog(null, "Valor no encontrado.");
                        }       break;
                    }
                case 3:
                    JOptionPane.showMessageDialog(null, "Saliendo del programa...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida.");
                    break;
            }
        }
    }
}
