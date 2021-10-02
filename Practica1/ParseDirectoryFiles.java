import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import org.apache.tika.Tika;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.AutoDetectParser;
import org.xml.sax.ContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.TeeContentHandler;
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

    // Creaamos las instancias para las funcionalidades con la configuracion por defecto
    Tika tika = new Tika();
    Metadata metadata = new Metadata();
    ParseContext parseContext = new ParseContext();
    AutoDetectParser parser = new AutoDetectParser();
    LinkContentHandler linkHandler = new LinkContentHandler();
    ContentHandler contentHandler = new BodyContentHandler();
    TeeContentHandler teeHandler = new TeeContentHandler(linkHandler, contentHandler);

    files = directory.listFiles();
    // Elegimos según opción introducida
    switch (option){
      case "-d":
        for (File file : files) {
          InputStream is = new FileInputStream(file);

          parser.parse(is, teeHandler, metadata, parseContext);

          System.out.println("\nNombre: " + file.getName());

          String contentType = metadata.get("Content-Type");
          String[] dataContentType = contentType.split(";");
          System.out.println("Tipo: " + dataContentType[0]);

          System.out.println("Codificación: " + metadata.get("Content-Encoding"));
          
          LanguageIdentifier identifier = new LanguageIdentifier(contentHandler.toString());
          System.out.println("Idioma: " + identifier.getLanguage());
        }
        break;

      case "-l":
        for (File file : files) {
          InputStream is = new FileInputStream(file);

          parser.parse(is, teeHandler, metadata, parseContext);

          System.out.println("\nNombre: " + file.getName());     
          
          if (!linkHandler.getLinks().isEmpty()) {
            System.out.println("\nLinks: " + linkHandler.getLinks());        
          } else {
            System.out.println("\nLinks: El archivo no tiene links.");        
          }
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