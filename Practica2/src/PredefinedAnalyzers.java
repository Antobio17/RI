package src;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.standard.UAX29URLEmailAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;

import java.io.File;
import java.io.Writer;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.FileNotFoundException;

import org.xml.sax.SAXException;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import org.apache.tika.exception.TikaException;

public class PredefinedAnalyzers {
    
    /**
     * Método para analizar una cadena de texto
     * @param string: archivo a aplicar los analizadores.
     */
    public void analyze(String fileName) throws IOException, SAXException, TikaException
    {
        File file = new File(fileName);

        Document document = new Document(file);
        String body = document.getBody();

        Analyzer keywordAnalyzer = new KeywordAnalyzer();
        PredefinedAnalyzers.createCSV(
            "keyword",
            keywordAnalyzer.tokenStream(null, body)
        );

        System.exit(0);
        
        Analyzer whitespaceAnalyzer = new WhitespaceAnalyzer();
        PredefinedAnalyzers.createCSV(
            "whitespace",
            whitespaceAnalyzer.tokenStream(null, body)
        );

        Analyzer simpleAnalyzer = new SimpleAnalyzer();
        PredefinedAnalyzers.createCSV(
            "simple",
            simpleAnalyzer.tokenStream(null, body)
        );

        Analyzer stopAnalyzer = new StopAnalyzer();
        PredefinedAnalyzers.createCSV(
            "stop",
            stopAnalyzer.tokenStream(null, body)
        );

        Analyzer standardAnalyzer = new StandardAnalyzer();
        PredefinedAnalyzers.createCSV(
            "standard",
            standardAnalyzer.tokenStream(null, body)
        );

        Analyzer UAX29URLEmailAnalyzer = new UAX29URLEmailAnalyzer();
        PredefinedAnalyzers.createCSV(
            "UAX29URLEmail",
            UAX29URLEmailAnalyzer.tokenStream(null, body)
        );

        Analyzer spanishAnalyzer = new SpanishAnalyzer();
        PredefinedAnalyzers.createCSV(
            "spanish",
            spanishAnalyzer.tokenStream(null, body)
        );
    }


    /**
     * Método para crear un CSV con la ocurrencia de palabras de un archivo.
     * 
     * @param fileName String: nombre del documento de las ocurrencias.
     * @param stream TokenStram: stram de analizador.
     */
    public static void createCSV(String fileName, TokenStream stream) 
        throws FileNotFoundException, SAXException, IOException
    {
        // Creamos el directorio donde almacenar el CSV
        String analyzedDir = "docs_analyzed/";
        File newFolder = new File(analyzedDir);
        newFolder.mkdir();

        // Creamos el CSV
        File csv = new File(analyzedDir + fileName.split("\\.")[0] + ".csv");
        FileOutputStream is = new FileOutputStream(csv);
        OutputStreamWriter osw = new OutputStreamWriter(is);
        Writer w = new BufferedWriter(osw);

        HashMap<String, Integer> occurrences = new HashMap <String, Integer> ();
        stream.reset();
        while (stream.incrementToken())
        {
            String word = stream.getAttribute(CharTermAttribute.class).toString().toLowerCase();
            System.out.println(word);
            occurrences.put(
                word,
                occurrences.containsKey(word) ? occurrences.get(word) + 1 : 1
            );
        }
        stream.end();
        stream.close();

        try {
            AtomicInteger counter = new AtomicInteger(1);
            // Escribimos las ocurrencias en el CSV ordenando los valores de mayor a menor
            occurrences.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(pair -> {
                    try {
                        w.write(pair.getKey() + "; " + pair.getValue() + "; " + counter.getAndIncrement() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            });
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("El CSV de del texto analizado se ha creado correctamente en './docs_analyzed'.");
    }
}
