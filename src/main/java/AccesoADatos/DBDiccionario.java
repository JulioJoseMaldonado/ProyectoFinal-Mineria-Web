/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoADatos;

import java.util.List;
import Tipos.Palabra;
import com.kennycason.kumo.WordFrequency;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import java.util.ArrayList;
import org.bson.Document;


public class DBDiccionario {
    
    private DBConnection conexion;
    private MongoCollection<Document> tableDiccionary;
    private long lastindex;
    
    public DBDiccionario(DBConnection conexion) throws Exception
    {
        this.conexion = conexion;
        this.tableDiccionary = conexion.GetDBdictionary();
    }
    
    public void Agregar(Palabra palabra)
    {
        Document doc = new Document("palabra", palabra.getPalabra());
        doc.append("tweettotal", palabra.getCanttweettotal());
        doc.append("tweetok", palabra.getCanttweetcorrectos());
        doc.append("tweetnotok", palabra.getCanttweetincorrectos());
        tableDiccionary.insertOne(doc);   
    }
    
    public boolean ExistePalabra(Palabra palabra)
    {
        FindIterable<Document> busqueda = tableDiccionary.find(eq("palabra", palabra.getPalabra()));
        return (busqueda.first() != null);
    }
    
    public void ReemplazarPalabra(Palabra palabra)
    {
        Document original = tableDiccionary.findOneAndDelete(eq("palabra", palabra.getPalabra()));
        int tweetotal_nuevo = palabra.getCanttweettotal() + original.getInteger("tweettotal");
        int tweetok_nuevo =  palabra.getCanttweetcorrectos() + original.getInteger("tweetok");
        int tweetnotok_nuevo = palabra.getCanttweetincorrectos() + original.getInteger("tweetnotok");
        Document reemplazo = new Document("palabra", palabra.getPalabra());
        reemplazo.append("tweettotal", tweetotal_nuevo);
        reemplazo.append("tweetok", tweetok_nuevo );
        reemplazo.append("tweetnotok", tweetnotok_nuevo );
        tableDiccionary.insertOne(reemplazo);
    }
    
    public void ReemplazarOK(Palabra palabra)
    {
        Document original = tableDiccionary.findOneAndDelete(eq("palabra", palabra.getPalabra()));
        Document reemplazo = new Document("palabra", palabra.getPalabra());
        reemplazo.append("tweettotal", palabra.getCanttweettotal());
        reemplazo.append("tweetok", palabra.getCanttweetcorrectos());
        reemplazo.append("tweetnotok", palabra.getCanttweetincorrectos());
        tableDiccionary.insertOne(reemplazo);
    }
    
    public List<String> DiccionarioString()
    {
        List<String> resultado = new ArrayList<>();
        FindIterable<Document> busqueda = tableDiccionary.find().sort(eq("palabra", 1));
        for (Document document : busqueda) 
        {
            resultado.add(document.getString("palabra"));
        }
        return resultado;      
    }
    public List<Palabra> DiccionarioforSearch()
    {
        List<Palabra> resultado = new ArrayList<>();
        FindIterable<Document> busqueda = tableDiccionary.find(gte("tweetok", 1)).sort(eq("tweetok", -1));
        for (Document document : busqueda) 
        {
            resultado.add(new Palabra(document.getString("palabra"), document.getInteger("tweettotal"), document.getInteger("tweetok"), document.getInteger("tweetnotok")));  
        }
        return resultado;      
    }
    
    public List<WordFrequency> DiccionarioforWordCloud()
    {
        List<WordFrequency> resultado = new ArrayList<>();
        FindIterable<Document> busqueda = tableDiccionary.find(gte("tweetok", 1)).sort(eq("tweetok", -1));
        for (Document document : busqueda) 
        {
            resultado.add(new WordFrequency(document.getString("palabra"), document.getInteger("tweetok")));  
        }
        return resultado;      
    }
    
    public Palabra GetPalabra(String stringpal)
    {
        FindIterable<Document> busqueda = tableDiccionary.find(eq("palabra", stringpal));
        Document document = busqueda.first();
        if (document != null)
        {
            return (new Palabra(document.getString("palabra"), document.getInteger("tweettotal"), document.getInteger("tweetok"), document.getInteger("tweetnotok"))); 
        }
        else
        {
            return (null);
        } 
    }
    
    public void borrarpalabra(Palabra palabra)
    {
       tableDiccionary.deleteOne(eq("palabra", palabra.getPalabra()));
    }
    
    
}
