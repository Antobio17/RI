package src;

import java.io.IOException;

import java.nio.file.Paths;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint; // Con esta clase no se almacena el valor, es mas rápido que IntField
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.StringField;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;


public class Indexer {

    IndexWriter writer;

    /**
     * Constructor de la clase indexer.
     * 
     * @throws IOException
     */
    public Indexer() throws IOException
    {
        FSDirectory directory = FSDirectory.open(Paths.get("./index"));

        Analyzer standardAnalyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(standardAnalyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        
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

        for(int i = 0; i < data.length; i++)
        {
            switch (headers[i].toLowerCase()) {
                case "authors":
                    document.add(new TextField(headers[i], data[i], Field.Store.YES));
                    break;
                case "author(s) id":
                    String[] idsStr = data[i].split(";");
                    int[] ids = new int[idsStr.length];
                    for (int j = 0; j < idsStr.length; j++) {
                        // Añadir try/catch
                        ids[j] = Integer.parseInt(idsStr[j]);
                     }
                    document.add(new IntPoint(headers[i], ids));
                    break;
                case "title":
                    System.out.println(data[i]);
                    System.exit(0);
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "year":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "source title":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "volume":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "issue":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "art. no.":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "page start":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "page end":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "page count":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "cited by":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "doi":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "link":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "affiliations":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "authors with affiliations":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "abstract":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "author keywords":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "index keywords":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "document type":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "publication stage":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "open access":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "source":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;
                case "eid":
                    document.add(new StringField(headers[i], data[i], Field.Store.YES));
                    break;

                default:
                    document.add(new TextField(headers[i], data[i], Field.Store.YES));
                    break;
            }
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
}
