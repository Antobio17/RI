package src;

import java.util.List;

import java.io.File;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

import com.opencsv.CSVReader;

public class InformationRetrievalSystem {
    
    public static void main(String[] args) throws Exception {
        String directoryPath = "docs/";
        File directory = new File(directoryPath);
        if (!directory.exists()){
            System.out.println("\nError: El directorio " + directoryPath + " no existe.");
            System.exit(-1);
        }

        Indexer indexer = null;
        String[] headers = null;

        System.out.println("Indexando documentos...\n");

        for (String file : directory.list()) {
            CSVReader reader = new CSVReader(new FileReader(directoryPath + file));

            
            // Guardamos las cabeceras para la creación de documentos y las borramos de allData
            headers = reader.readNext();
            
            // Creamos el indexer en caso de no haberlo creado
            if (indexer == null)
                indexer = new Indexer(headers);
            
            System.out.print("  -> Indexando " + file.toString() + "...\n");
            String [] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                indexer.index(nextLine, headers);
            }
        }
        
        indexer.closeIndexer();

        System.out.println("\nIndexación finalizada.");
    }
}
