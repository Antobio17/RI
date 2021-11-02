package src;

import java.io.IOException;

import java.nio.file.Paths;

import java.util.Map;
import java.util.HashMap;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;  // Con esta clase no se almacena el valor, es mas rápido que IntField
import org.apache.lucene.document.LongPoint; // Con esta clase no se almacena el valor, es mas rápido que IntField
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.StringField;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;


import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;


public class Indexer {

    IndexWriter writer;

    /**
     * Constructor de la clase indexer.
     * 
     * @throws IOException
     */
    public Indexer(String[] headers) throws IOException
    {
        FSDirectory directory = FSDirectory.open(Paths.get("./index"));

        // Analyzer standardAnalyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(this.getPerFieldAnalyzer(headers));
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        
        writer = new IndexWriter(directory, config);
    }

    /**
     * 
     * @param data
     * @return
     */
    public boolean index(String[] data, String[] headers) throws IOException
    {
        Document document = new Document();
        String title = null;
        
        for(int i = 0; i < data.length; i++)
        {
            // System.out.print(headers[i] + " -> ");
            // System.out.println(data[i] + "\n");
            if (!data[i].equals("")){
                switch (headers[i].toLowerCase()) {
                    
                    case "year": // 2022
                    case "page count":
                        int[] intField = new int[1];
                        intField[0] = Integer.parseInt(data[i]);
                        document.add(new IntPoint(headers[i], intField));
                        break;

                    case "author(s) id": // 57277263900";"37090401400";"16309293000";"
                    case "title": // Physicochemical characterization of flours and starches derived from selected underutilized roots and tuber crops grown in Sri Lanka
                    case "source title": // Food Hydrocolloids
                    case "volume": // 124
                    case "issue":
                    case "art. no.": // 107272
                    case "page start":
                    case "page end":
                    case "cited by":
                    case "doi": // 10.1016/j.foodhyd.2021.107272
                    case "link": // https://www.scopus.com/inward/record.uri?eid=2-s2.0-85117174269&doi=10.1016%2fj.foodhyd.2021.107272&partnerID=40&md5=ba2d55c8cb90137b96c60f9e9f8911f1
                    case "document type": // Review
                    case "publication stage": // Final
                    case "open access": // Scopus
                    case "eid": // 2-s2.0-85117174269
                        if(headers[i].equalsIgnoreCase("title")){
                            title = data[i];
                        }
                        document.add(new StringField(headers[i], data[i], Field.Store.YES));
                        break;
                    
                    case "authors": // Chiranthika N.N.G., Chandrasekara A., Gunathilake K.D.P.P.
                    case "affiliations": // Afiliaciones con comas y separados por ";"
                    case "authors with affiliations": // Autores junto con las afiliaciones a las que pertenecen separados por ";"
                    case "abstract": // Resumen
                    case "author keywords": // Flours; Physicochemical properties; Roots and tubers; Starches
                    case "index keywords":
                    case "source":
                    default:
                        document.add(new TextField(headers[i], data[i], Field.Store.YES));
                        break;

                }
            }
        }

        if(title != null) {
            Term term = new Term("Title", title);
            this.writer.updateDocument(term, document);
        } else {
            this.writer.addDocument(document);
        }

        return true;
    }

    /**
     * 
     * @return
     */
    public boolean closeIndexer()
    {
        try {
            writer.commit();
            writer.close();
        } catch(IOException e) {
            return false;
        }

        return true;
    }

    /**
     * 
     * @param headers
     * @return
     */
    protected PerFieldAnalyzerWrapper getPerFieldAnalyzer(String[] headers)
    {
        Map<String, Analyzer> analyzerPerField = new HashMap<String, Analyzer>();
        for(String header : headers)
        {
            switch (header.toLowerCase()) {
                case "authors":
                case "source title":
                case "issue":
                case "cited by":
                case "author keywords":
                case "index keywords":
                    analyzerPerField.put(header, new StandardAnalyzer());
                    break;
                
                case "title":
                case "affiliations":
                case "authors with affiliations":
                case "abstract":
                    analyzerPerField.put(header, new EnglishAnalyzer());
                    break;
                
                case "author(s) id":
                case "year":
                case "volume":
                case "art. no.":
                case "page start":
                case "page end":
                case "page count":
                case "doi":
                case "link":
                case "document type":
                case "publication stage":
                case "open access":
                case "source":
                case "eid":
                    analyzerPerField.put(header, new KeywordAnalyzer());
                    break;
                
                default:
                    analyzerPerField.put(header, new WhitespaceAnalyzer());
                    break;
            }
        }

        return new PerFieldAnalyzerWrapper(new WhitespaceAnalyzer(), analyzerPerField);
    }
}

// Dudas
// Se podrían eliminar los ; para que al tokenizar separe bien los tokens?
// Como identificar el articulo o el documento?
