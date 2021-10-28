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

        Indexer indexer = new Indexer();

        for (String file : directory.list()) {
            CSVReader reader = new CSVReader(new FileReader(directoryPath + file));
            List<String[]> allData = reader.readAll();

            // Guardamos las cabeceras para la creación de documentos y las borramos de allData
            String[] headers = allData.get(0);
            allData.remove(0);

            for(String[] data : allData)
            {
                indexer.index(data);
            }
        }
    }
}
