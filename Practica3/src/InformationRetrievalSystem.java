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

            // TODO Guardamos las cabeceras

            for(String[] data : allData)
            {
                indexer.index(data);
            }
        }
    }
}
