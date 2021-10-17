package src;

import java.util.Arrays;

import java.io.IOException;

import org.apache.lucene.util.CharsRef;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.LowerCaseFilter;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;

import org.apache.lucene.analysis.snowball.SnowballFilter;

import org.apache.lucene.analysis.shingle.ShingleFilter;

import org.apache.lucene.analysis.ngram.NGramTokenFilter;

import org.apache.lucene.analysis.commongrams.CommonGramsFilter;

import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.analysis.synonym.SynonymFilter;

import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import org.tartarus.snowball.ext.SpanishStemmer;

public class TokenFilters {

    private Analyzer analyzer;

    /**
     * Método para analizar una cadena de texto
     * @param string: cadena de texto a analizar.
     */
    public void analyzeWithFilter(String string) throws IOException
    {
        System.out.println("Frase a a la que se le aplicaran los filtros: \n");
        System.out.println("    " + string + "\n");

        for (String option : new String[]{"-l", "-t", "-b", "-h", "-n", "-c", "-m", "-s"}) {
            switch(option)
            {
                case "-l":
                    System.out.println("LowerCaseFilter ->");
                    this.analyzer = this.lowerCaseFilterAnalyzer(string);
                    break;
                case "-t":
                    System.out.println("StopFilter ->");
                    this.analyzer = this.stopFilterAnalyzer(string);
                    break;
                case "-b":
                    System.out.println("SnowballFilter ->");
                    this.analyzer = this.snowballFilterAnalyzer(string, "Spanish");
                    break;
                case "-h":
                    System.out.println("ShingleFilter ->");
                    this.analyzer = this.shingleFilterAnalyzer(string);
                    break;
                case "-n":
                    System.out.println("NGramTokenFilterFilter ->");
                    this.analyzer = this.nGramTokenFilterAnalyzer(string);
                    break;
                case "-c":
                    System.out.println("CommonGramsFilter ->");
                    this.analyzer = this.commonGramsFilterAnalyzer(string);
                    break;
                case "-m":
                    System.out.println("SynonymFilter ->");
                    this.analyzer = this.synonymFilterAnalyzer(string);
                    break;
                case "-s":
                default:
                    System.out.println("StandarFilter ->");
                    this.analyzer = this.standardFilterAnalyzer(string);
                    break;
            }
            TokenStream stream = this.analyzer.tokenStream(null, string);
        
            stream.reset();
            while (stream.incrementToken())
            {
                System.out.println("   " + stream.getAttribute(CharTermAttribute.class));
            }
            stream.end();
            stream.close();
        }
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
     * Método para inicializar el analizador con el StopFilter (Eliminador de palabras vacías).
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
                    StopFilter.makeStopSet(Arrays.asList("a", "la", "se", "el", "de", "los", "se"))
                );

                return new TokenStreamComponents(source, filter);
            }
        };
    }

    /**
     * Método para inicializar el analizador con el SnowballFilter.
     */
    public Analyzer snowballFilterAnalyzer(String string, String language) throws IOException
    {
        return new Analyzer() 
        {
            @Override
            protected Analyzer.TokenStreamComponents createComponents(String string)
            {
                final Tokenizer source = new StandardTokenizer();

                TokenStream filter = new SnowballFilter(source, language);

                return new TokenStreamComponents(source, filter);
            }
        };
    }

    /**
     * Método para inicializar el analizador con el ShingleFilter.
     */
    public Analyzer shingleFilterAnalyzer(String string) throws IOException
    {
        return new Analyzer() 
        {
            @Override
            protected Analyzer.TokenStreamComponents createComponents(String string)
            {
                final Tokenizer source = new StandardTokenizer();

                TokenStream filter = new ShingleFilter(source);

                return new TokenStreamComponents(source, filter);
            }
        };
    }

    /**
     * Método para inicializar el analizador con el NnGramTokenFilter.
     * Primero ordena los n-grams por su desplazamiento en el token original,
     * luego aumentando la longitud 
     * (lo que significa que "abc" dará "a", "ab", "abc", "b", "bc", "c").
     */
    public Analyzer nGramTokenFilterAnalyzer(String string) throws IOException
    {
        return new Analyzer() 
        {
            @Override
            protected Analyzer.TokenStreamComponents createComponents(String string)
            {
                final Tokenizer source = new StandardTokenizer();

                TokenStream filter = new NGramTokenFilter(source);

                return new TokenStreamComponents(source, filter);
            }
        };
    }

    /**
     * Método para inicializar el analizador con el CommonGramsFilter.
     * Construye bigramas para términos frecuentes durante la indexación.
     * Los términos individuales también están indexados, con bigramas superpuestos.
     */
    public Analyzer commonGramsFilterAnalyzer(String string) throws IOException
    {
        return new Analyzer() 
        {
            @Override
            protected Analyzer.TokenStreamComponents createComponents(String string)
            {
                final Tokenizer source = new StandardTokenizer();

                TokenStream filter = new CommonGramsFilter(
                    source,
                    new CharArraySet(Arrays.asList("a", "la", "se"), true)
                );

                return new TokenStreamComponents(source, filter);
            }
        };
    }

    /**
     * Método para inicializar el analizador con el SynonymFilter.
     * Coincide con sinónimos de una o varias palabras en una secuencia de tokens.
     */
    public Analyzer synonymFilterAnalyzer(String string) throws IOException
    {
        // Contruimos el diccionario de sinónimos.
        SynonymMap.Builder builder = new SynonymMap.Builder(true);
        builder.add(new CharsRef("hola"), new CharsRef("buenas"), true);
        builder.add(new CharsRef("prueba"), new CharsRef("test"), true);
        builder.add(new CharsRef("práctica"), new CharsRef("objetivo"), true);
        SynonymMap synonymMap = builder.build();

        return new Analyzer() 
        {
            @Override
            protected Analyzer.TokenStreamComponents createComponents(String string)
            {
                final Tokenizer source = new StandardTokenizer();

                TokenStream filter = new SynonymFilter(
                    source,
                    synonymMap,
                    true
                );

                return new TokenStreamComponents(source, filter);
            }
        };
    }
}