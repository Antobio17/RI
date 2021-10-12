package src;

import src.TokenFilters;

public class TextAnalyzer {

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
        String file = args[1];

        TokenFilters tokenFilters = new TokenFilters();
        tokenFilters.analyze("Hola vamos a probar esto no se si irá la verdad", option);
    }
}
