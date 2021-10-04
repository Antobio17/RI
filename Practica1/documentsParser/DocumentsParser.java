package documentsParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class DocumentsParser {
    public static void main(String[] args) throws Exception {
        // Comprobamos que el número de argumentos es correcto
        if (args.length != 2){
            System.out.println(
                "\nError: Número de parámetros esperados (2) distinto del número de parámetros introducidos (" 
                + args.length 
                + ")."
            );
            System.exit(-1);
        }

        // Recuperamos los parámetros de entrada
        String option = args[0];
        String directoryPath = args[1];

        // Comprobamos que el directorio existe
        File directory = new File(directoryPath);
        if (!directory.exists()){
            System.out.println("\nError: El directorio " + directoryPath + " no existe.");
            System.exit(-1);
        }

        for (File file : directory.listFiles()) {
            Document document = new Document(file);

            switch (option){
                case "-d":
                    System.out.println("\nNombre: " + document.getName());
                    System.out.println("Tipo: " + document.getType());
                    System.out.println("Codificación: " + document.getCoding());
                    System.out.println("Idioma: " + document.getLanguage());
                    break;

                case "-l":
                    System.out.println("\nNombre: " + document.getName());
                    document.printLinks();
                    break;

                case "-t":
                    System.out.println("\nNombre: " + document.getName());
                    createCSV(document.getName(), document.getOccurrences());
                    break;

                default:
                    System.out.println(
                        "Error: La opción " 
                        + option 
                        + " no existe.\nOpciones disponibles:\n '-d' '-l', '-t'."
                    );
                    System.exit(-1);
                    break;
            }
        }
    }

    /**
     * Método para crear un CSV con la ocurrencia de palabras de un archivo.
     * 
     * @param fileName String: nombre del documento de las ocurrencias.
     * @param occurrences HasMap: ocurrencias del documento.
     */
    public static void createCSV(String fileName, HashMap<String, Integer> occurrences) 
        throws FileNotFoundException
    {
        // Creamos el directorio donde almacenar el CSV
        String ocurrencesDir = "ocurrencias/";
        File newFolder = new File(ocurrencesDir);
        newFolder.mkdir();

        // Creamos el CSV
        File csv = new File(ocurrencesDir + fileName.split("\\.")[0] + ".csv");
        FileOutputStream is = new FileOutputStream(csv);
        OutputStreamWriter osw = new OutputStreamWriter(is);
        Writer w = new BufferedWriter(osw);

        try {
            // Escribimos las ocurrencias en el CSV ordenando los valores de mayor a menor
            occurrences.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(pair -> {
                    try {
                        w.write(pair.getKey() + "; " + pair.getValue() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            });
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("El CSV de ocurrencias se ha creado correctamente en './ocurrencias'.");
    }
}
