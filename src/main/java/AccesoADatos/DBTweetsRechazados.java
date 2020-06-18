/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoADatos;

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
public class DBTweetsRechazados {
    
    private DBConnection conexion;
    private MongoCollection<Document> tabletweetsrechados;

    public DBTweetsRechazados(DBConnection conexion) throws Exception
    {
        this.conexion = conexion;
        this.tabletweetsrechados = conexion.GetDBTweetsRechazados();
    }
    
    public void Agregar(long ID)
    {
        Document doc =  new Document("ID", ID);
        tabletweetsrechados.insertOne(doc);
    }
    
    public boolean ExisteID(long id)
    {
        boolean resultado = false;
        FindIterable<Document> busqueda = tabletweetsrechados.find(eq("ID", id));
        if(busqueda.first() != null)
        {
            System.out.println("DBTweetRechazados: " + busqueda.first().getLong("ID") + " - " + id);
            resultado = true;
        }
        return (resultado);
    }
    
    public List<Long> ListaIDRechazados()
    {
        List<Long> resultado = new ArrayList<>();
        FindIterable<Document> busqueda = tabletweetsrechados.find();
        for (Document document : busqueda) 
        {
            resultado.add(document.getLong("ID"));
        }
        return resultado;
    }
    
    public void BorrarTweetID(long ID)
    {
        tabletweetsrechados.deleteOne(eq("ID", ID));
    }
    
    
}
