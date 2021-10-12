package src;

import java.util.Arrays;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;

import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class TokenFilters {

    private Analyzer analyzer;

    /**
     * Método para analizar una cadena de texto
     * @param string: cadena de texto a analizar.
     * @param option: analizador a utilizar.
     */
    public void analyze(String string, String option) throws IOException
    {
        switch(option)
        {
            case "-l":
                this.analyzer = this.lowerCaseFilterAnalyzer(string);
                break;
            case "-t":
                this.analyzer = this.stopFilterAnalyzer(string);
                break;
            case "-s":
            default:
                this.analyzer = this.standardFilterAnalyzer(string);
                break;
        }
        
        TokenStream stream = this.analyzer.tokenStream(null, string);
        
        stream.reset();
        while (stream.incrementToken())
        {
            System.out.println(stream.getAttribute(CharTermAttribute.class));
        }
        stream.end();
        stream.close();
    }

    /**
     * Método para inicializar el analizador con el StandardFilter.
     */
    public Analyzer standardFilterAnalyzer(String string) throws IOException
    {
        return new Analyzer() 
        {
            @Override
            protected Analyzer.TokenStreamComponents createComponents(String string)
            {
                final Tokenizer source = new StandardTokenizer();

                TokenStream filter = new StandardFilter(source);

                return new TokenStreamComponents(source, filter);
            }
        };
    }

    /**
     * Método para inicializar el analizador con el LowerCaseFilter.
     */
    public Analyzer lowerCaseFilterAnalyzer(String string) throws IOException
    {
        return new Analyzer() 
        {
            @Override
            protected Analyzer.TokenStreamComponents createComponents(String string)
            {
                final Tokenizer source = new StandardTokenizer();

                TokenStream filter = new LowerCaseFilter(source);

                return new TokenStreamComponents(source, filter);
            }
        };
    }

    /**
     * Método para inicializar el analizador con el LowerCaseFilter.
     */
    public Analyzer stopFilterAnalyzer(String string) throws IOException
    {
        return new Analyzer() 
        {
            @Override
            protected Analyzer.TokenStreamComponents createComponents(String string)
            {
                final Tokenizer source = new StandardTokenizer();

                TokenStream filter = new StopFilter(
                    source,
                    StopFilter.makeStopSet(Arrays.asList("a", "la", "se"))
                );

                return new TokenStreamComponents(source, filter);
            }
        };
    }
}