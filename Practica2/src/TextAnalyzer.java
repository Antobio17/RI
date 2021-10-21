package src;

import src.TokenFilters;
import src.PredefinedAnalyzers;

import org.apache.lucene.analysis.TokenStream;


public class TextAnalyzer {

    public static void main(String[] args) throws Exception {
        // Comprobamos que el número de argumentos es correcto
        if (args.length != 1){
            System.out.println(
                "\nError: Número de parámetros esperados (2) distinto del número de parámetros introducidos (" 
                + args.length 
                + ")."
            );
            System.exit(-1);
        }

        // Recuperamos los parámetros de entrada
        String option = args[0];
        // String file = args[1];

        switch(option){
            case "-i":
                PredefinedAnalyzers predefinedAnalyzers = new PredefinedAnalyzers();
                predefinedAnalyzers.analyze("docs/Don Quijote.txt");
                break;

            case "-ii":
                TokenFilters tokenFilters = new TokenFilters();
                tokenFilters.analyzeWithFilter("Hola! Esta es la prueba para el apartado 2 de la práctica. Se aplicarán los distintos filtros de tokens.");
                break;

            case "-iii":
                TokenStream stream = new OwnAnalyzer().getStreamAnalizedFromDocument("docs/Don Quijote.txt");
                PredefinedAnalyzers.createCSV("analizador-propio" , stream);
                break;
        }
    }
}
