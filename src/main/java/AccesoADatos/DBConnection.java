/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AccesoADatos;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author julio
 */
public class DBConnection {
    
            MongoClient mongoClient;
             MongoDatabase db;
            public DBConnection() throws Exception
            {
               mongoClient = new MongoClient( "localhost" , 27017 );  
               db = mongoClient.getDatabase("MineriaWeb");
            }
            public MongoCollection<Document> GetDBdictionary () throws Exception
            {
                return(db.getCollection("dictionary"));
            }
            public MongoCollection<Document> GetDBTweets () throws Exception
            {
                return(db.getCollection("tweets"));
            }
            public MongoCollection<Document> GetDBStopWords () throws Exception
            {
                return(db.getCollection("StopWords"));
            }
            public MongoCollection<Document> GetDBTweetsRechazados () throws Exception
            {
                return(db.getCollection("TweetsRechazados"));
            }

            
            
            
            
    
}
