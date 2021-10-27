package src;

import java.io.IOException;

import java.nio.file.Paths;

import org.apache.lucene.document.Field;
import org.apache.lucene.document.Document;

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
        FSDirectory directory = FSDirectory.open(Paths.get("index/"));

        Analyzer standardAnalyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(standardAnalyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        
        writer = new IndexWriter(directory, config);
    }

    /**
     * 
     * @param data
     * @return
     */
    public boolean index(String[] data) 
    {
        
        return true;
    }
}
