package src;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;

import org.apache.lucene.analysis.custom.CustomAnalyzer;

import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;

public class OwnAnalyzer {

    /******************************* PROPERTIES ******************************/

    Analyzer analyzer;

    /******************************* CONSTRUCTS ******************************/

    /**
     * OwnAnalyzer construct.
     */
    public OwnAnalyzer()
    {
        analyzer = CustomAnalyzer.builder(Paths.get("./docs"))
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
    public TokenStream getStreamAnalizedFromDocument(String fileName)
    {
        File file = new File(fileName);

        Document document = new Document(file);
        String body = document.getBody();

        return this.analyzer.tokenStream(null, body);
    }
}
