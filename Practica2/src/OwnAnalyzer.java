package src;

import src.Document;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.io.FileNotFoundException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.LowerCaseFilter;

import org.apache.lucene.analysis.custom.CustomAnalyzer;

import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;

import org.xml.sax.SAXException;
import org.apache.tika.exception.TikaException;


public class OwnAnalyzer {

    /******************************* PROPERTIES ******************************/

    Analyzer analyzer;

    /******************************* CONSTRUCTS ******************************/

    /**
     * OwnAnalyzer construct.
     */
    public OwnAnalyzer() throws IOException
    {
        analyzer = CustomAnalyzer.builder(Paths.get("./src"))
                        .withTokenizer("standard")
                        .addTokenFilter("lowercase")
                        .addTokenFilter(
                            "stop",
                            "ignoreCase",
                            "false",
                            "words",
                            "stopwords-spanish.txt",
                            "format",
                            "wordset"
                        )
                        .build();
    }

    /***************************** PUBLIC METHODS ****************************/


    /**
     * Método para obtener el TokenStream del cuerpo de un documento analizado
     * con el analizador custom.
     * 
     * @param fileName Nombre del documento a analizar.
     * 
     * @return TokenStream
     */
    public TokenStream getStreamAnalizedFromDocument(String fileName) throws IOException, SAXException, TikaException, FileNotFoundException
    {
        File file = new File(fileName);

        Document document = new Document(file);
        String body = document.getBody();

        return this.analyzer.tokenStream(null, body);
    }
}
