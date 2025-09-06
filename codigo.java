import java.io.*;
import java.util.*;

public class ExternalSort {
    
    static final int MAX_MEMORIA = 1000;

    public static void main(String[] args) throws IOException {
        String inputFile = "entrada.txt";
        String outputFile = "salida_ordenada.txt";

        List<String> tempFiles = dividirEnBloques(inputFile);

        mezclarBloques(tempFiles, outputFile);

        System.out.println("Ordenación externa completada. Resultado en: " + outputFile);
    }

    public static List<String> dividirEnBloques(String inputFile) throws IOException {
        List<String> tempFiles = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        List<Integer> buffer = new ArrayList<>(MAX_MEMORIA);
        String linea;
        int count = 0;

        while ((linea = reader.readLine()) != null) {
            buffer.add(Integer.parseInt(linea.trim()));
            if (buffer.size() == MAX_MEMORIA) {
                Collections.sort(buffer);
                String tempFileName = "temp_" + (count++) + ".txt";
                escribirArchivo(tempFileName, buffer);
                tempFiles.add(tempFileName);
                buffer.clear();
            }
        }

        if (!buffer.isEmpty()) {
            Collections.sort(buffer);
            String tempFileName = "temp_" + (count++) + ".txt";
            escribirArchivo(tempFileName, buffer);
            tempFiles.add(tempFileName);
        }

        reader.close();
        return tempFiles;
    }

    public static void escribirArchivo(String filename, List<Integer> data) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Integer num : data) {
            writer.write(num.toString());
            writer.newLine();
        }
        writer.close();
    }

    public static void mezclarBloques(List<String> tempFiles, String outputFile) throws IOException {
        PriorityQueue<EntradaArchivo> pq = new PriorityQueue<>();
        List<BufferedReader> readers = new ArrayList<>();

        for (String file : tempFiles) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            readers.add(br);
            String linea = br.readLine();
            if (linea != null) {
                pq.add(new EntradaArchivo(Integer.parseInt(linea), br));
            }
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        while (!pq.isEmpty()) {
            EntradaArchivo entrada = pq.poll();
            writer.write(Integer.toString(entrada.valor));
            writer.newLine();

            String linea = entrada.reader.readLine();
            if (linea != null) {
                pq.add(new EntradaArchivo(Integer.parseInt(linea), entrada.reader));
            }
        }

        writer.close();

        for (BufferedReader br : readers) {
            br.close();
        }
        for (String file : tempFiles) {
            new File(file).delete();
        }
    }
    static class EntradaArchivo implements Comparable<EntradaArchivo> {
        int valor;
        BufferedReader reader;

        public EntradaArchivo(int valor, BufferedReader reader) {
            this.valor = valor;
            this.reader = reader;
        }

        @Override
        public int compareTo(EntradaArchivo o) {
            return Integer.compare(this.valor, o.valor);
        }
    }
}
l
