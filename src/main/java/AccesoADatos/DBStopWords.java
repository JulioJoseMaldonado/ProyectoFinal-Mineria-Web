/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoADatos;

import Tipos.Palabra;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author julio
 */
public class DBStopWords {
    
    private DBConnection connection;
    private MongoCollection<Document> tableStopWords;
    private long lastID;
    
    public DBStopWords(DBConnection conexcion) throws Exception
    {
        this.connection = conexcion;
        this.tableStopWords = conexcion.GetDBStopWords();
    }
    
    public void Agregar(String palabra)
    {
        Document doc = new Document("stopword", palabra);
        tableStopWords.insertOne(doc);
        
    }
    
    public boolean ExisteStopWords(String palabra)
    {
        FindIterable<Document> busqueda = tableStopWords.find(eq("stopword", palabra));
        return (busqueda.first() != null);
    }
    
    public List<String> Stopwords()
    {
       List<String> resultado = new ArrayList<>();
       FindIterable<Document> busqueda = tableStopWords.find();
        for (Document document : busqueda) 
        {
           resultado.add(document.getString("stopword"));
        }
        return resultado;
    }
}
