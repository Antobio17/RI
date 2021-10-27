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

        for (String file : directory.list()) {
            // TODO abrimos el csv y linea por linea:
            CSVReader reader = new CSVReader(new FileReader(directoryPath + file));
            List<String[]> allData = reader.readAll();
            for(String[] data : allData)
            {
                for(String s : data)
                {
                    System.out.println(s);
                }
            }
            // Indexer.index();
        }
    }
}
