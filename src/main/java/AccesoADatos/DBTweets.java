/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoADatos;

import Tipos.Tweet;
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
public class DBTweets {

    private DBConnection conexion;
    private MongoCollection<Document> tabletweets;
    
    public DBTweets(DBConnection conexion) throws Exception
    {
        this.conexion = conexion;
        this.tabletweets = conexion.GetDBTweets();
    }
    
    public void Agregar(Tweet tweet)
    {
        Document doc = new Document("id", tweet.getID());  
        doc.append("Autor", tweet.getAutor());
        doc.append("fecha", tweet.getFecha());
        doc.append("Origen", tweet.getLocacion());
        doc.append("Latitud", tweet.getLatitud());
        doc.append("Longitud", tweet.getLongitud());
        doc.append("texto", tweet.getTextoTweet());
        tabletweets.insertOne(doc);
    }
    
    public boolean ExisteID (long id)
    {
        boolean resultado = false;
        FindIterable<Document> busqueda = tabletweets.find(eq("id", id));
        if(busqueda.first() != null)
        {
            System.out.println("DBTweet: " + busqueda.first().getLong("id") + " - " + id);
            resultado = true;
        }
        return (resultado);
    }
    
    public void BorrarTweet(Tweet tweet)
    {
        tabletweets.deleteOne(eq("id",tweet.getID()));
    }
    
    public List<Long> GetTweetDBID()
    {
        List<Long> resultado = new ArrayList<>();
        FindIterable<Document> busqueda = tabletweets.find();
        for (Document document : busqueda) 
        {
            resultado.add(document.getLong("id"));
        }
        return resultado;
    }
    public List<Tweet> TweetsDB()
    {
        List<Tweet> resultado = new ArrayList<>();
        FindIterable<Document> busqueda = tabletweets.find();
        String TextoTweet;
        String Autor;
        String fecha;
        long ID;
        double latitud;
        double longitud;
        String locacion;
        for (Document document : busqueda) {
            ID = document.getLong("id");
            Autor = document.getString("Autor");
            fecha = document.getString("fecha");
            locacion = document.getString("Origen");
            latitud = document.getDouble("Latitud");
            longitud = document.getDouble("Longitud");
            TextoTweet = document.getString("texto");
            resultado.add(new Tweet(TextoTweet, Autor, ID, latitud, longitud, locacion, fecha));              
        }
        return resultado;
    }
    
    public Tweet getTweetID(long id)
    {
       FindIterable<Document> busqueda = tabletweets.find(eq("id", id));
       Document DocTweet = busqueda.first();
       
       long ID = DocTweet.getLong("id");
       String Autor = DocTweet.getString("Autor");
       String fecha = DocTweet.getString("fecha");
       String locacion = DocTweet.getString("Origen");
       double latitud = DocTweet.getDouble("Latitud");
       double longitud = DocTweet.getDouble("Longitud");
       String TextoTweet = DocTweet.getString("texto");
       Tweet resultado = new Tweet(TextoTweet, Autor, ID, latitud, longitud, locacion, fecha);
       return resultado;
    }
}
