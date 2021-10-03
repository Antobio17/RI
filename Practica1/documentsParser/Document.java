package documentsParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.Link;
import org.apache.tika.sax.TeeContentHandler;
import org.apache.tika.sax.LinkContentHandler;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ContentHandler;

public class Document {
    
    /******************************* PROPERTIES ******************************/

    private String name;
    private String type;
    private String coding;
    private String language;
    private String body;
    private List<Occurrence> occurrences;

    Metadata metadata = new Metadata();
    ParseContext parseContext = new ParseContext();
    AutoDetectParser parser = new AutoDetectParser();
    LinkContentHandler linkHandler = new LinkContentHandler();
    ContentHandler contentHandler = new BodyContentHandler();
    TeeContentHandler teeHandler = new TeeContentHandler(linkHandler, contentHandler);

    /******************************* CONSTRUCTS ******************************/

    /**
     * Document construct.
     * 
     * @param name String: Nombre del documento.
     */
    public Document(File file) throws FileNotFoundException, 
        IOException, SAXException, TikaException
    {
        InputStream is = new FileInputStream(file);
        this.parser.parse(is, this.teeHandler, this.metadata, this.parseContext);

        String contentType = this.metadata.get("Content-Type");
        String[] type = contentType.split(";");

        String body = contentHandler.toString();

        LanguageIdentifier identifier = new LanguageIdentifier(body);

        this.setName(file.getName())
            .setType(type[0])
            .setCoding(this.metadata.get("Content-Encoding"))
            .setLanguage(identifier.getLanguage())
            .setBody(body);

        occurrences = new ArrayList<>();
    }

    /**
     * Document construct.
     * 
     * @param name String: Nombre del documento.
     * @param type String: Tipo de documento.
     * @param coding String: Codificación del documento.
     * @param language String: Lenguaje del documento.
     * @param body String: Cuerpo del documento.
     */
    public Document(String name, String type, String coding, String language,
        String body)
    {
        this.setName(name)
            .setType(type)
            .setCoding(coding)
            .setLanguage(language)
            .setBody(body);

        occurrences = new ArrayList<>();
    }

    /*************************** GETTER AND SETTER ***************************/
    
    /**
     * Obtiene la propiedad Name de la clase.
     * 
     * @return String
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Establece la propiedad Name de la clase.
     * 
     * @param name String: name a establecer en la clase.
     *
     * @return this
     */
    public Document setName(String name)
    {
        this.name = name;

        return this;
    }

    /**
     * Obtiene la propiedad Type de la clase.
     * 
     * @return String
     */
    public String getType()
    {
        return this.type;
    }

    /**
     * Establece la propiedad Type de la clase.
     * 
     * @param type String: type a establecer en la clase.
     *
     * @return this
     */
    public Document setType(String type)
    {
        this.type = type;

        return this;
    }

    /**
     * Obtiene la propiedad Coding de la clase.
     * 
     * @return String
     */
    public String getCoding()
    {
        return this.coding;
    }

    /**
     * Establece la propiedad Coding de la clase.
     * 
     * @param coding String: coding a establecer en la clase.
     *
     * @return this
     */
    public Document setCoding(String coding)
    {
        this.coding = coding;

        return this;
    }

    /**
     * Obtiene la propiedad Language de la clase.
     * 
     * @return String
     */
    public String getLanguage()
    {
        return this.language;
    }

    /**
     * Establece la propiedad Language de la clase.
     * 
     * @param language String: language a establecer en la clase.
     *
     * @return this
     */
    public Document setLanguage(String language)
    {
        this.language = language;

        return this;
    }

    /**
     * Obtiene la propiedad Body de la clase.
     * 
     * @return String
     */
    public String getBody()
    {
        return this.body;
    }

    /**
     * Establece la propiedad Body de la clase.
     * 
     * @param body String: body a establecer en la clase.
     *
     * @return this
     */
    public Document setBody(String body)
    {
        this.body = body;

        return this;
    }

    /**
     * Obtiene el listado de ocurrencias del documento.
     * 
     * @return List<Occurrence>
     */
    public List<Occurrence> getOccurrences()
    {
        return this.occurrences;
    }

    /***************************** PUBLIC METHODS ****************************/

    /**
     * Obtiene los todos los links del contenido del documento.
     * 
     * @return List<Link>
     */
    public List<Link> getLinks()
    {
        return this.linkHandler.getLinks();
    }

    /**
     * Método para imprimir por pantalla los links del cuerpo del documento.
     */
    public void printLinks()
    {
        if (!this.getLinks().isEmpty()) {
            System.out.println("Links: ");     
            for (Link link : this.getLinks()) {
                System.out.println("  " + link);  
            }
        } else {
            System.out.println("Links:\n  El documento no tiene links.");        
        }
    }

    /***************************** PRIVARE METHODS ***************************/

    /***************************** STATIC METHODS ****************************/
}
