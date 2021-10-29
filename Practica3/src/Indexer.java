package src;

import java.io.IOException;

import java.nio.file.Paths;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;
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
            document.add(new TextField(headers[i], data[i], Field.Store.YES));
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
