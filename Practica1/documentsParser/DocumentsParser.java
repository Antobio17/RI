package documentsParser;

import java.io.File;

import java.util.List;
import java.util.ArrayList;

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
}
