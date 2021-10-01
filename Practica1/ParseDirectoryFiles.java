import java.io.File;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.language.LanguageIdentifier;

public class ParseDirectoryFiles {
  public static void main(String[] args) throws Exception {
  
    // Comprobamos que el número de argumentos es correcto
    if (args.length != 2){
      System.out.println("Error: Número de parámetros esperados (2) distinto del número de parámetros introducidos (" + args.length + ").");
      System.exit(-1);
    }

    // Recuperamos los parámetros de entrada
    String option = args[0];
    String directoryPath = args[1];
    File[] files = null;
    
    // Comprobamos que el directorio existe
    File directory = new File(directoryPath);
    if (!directory.exists()){
      System.out.println("Error: El directorio " + directoryPath + " no existe.");
      System.exit(-1);
    }

    files = directory.listFiles();
    // Elegimos según opción introducida
    switch (option){
      case "-d":
        for (File file : files) {
          System.out.println(file.getName());
        }
        break;

      case "-l":
        for (File file : files) {
            
        }
        break;  

      case "-t":
        for (File file : files) {
            
        }
        break;

      default:
        System.out.println("Error: La opción " + option + " no existe.\nOpciones disponibles:\n '-d' '-l', '-t'.");
        System.exit(-1);
        break; 
    }

  }

}