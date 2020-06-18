/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProyectoFinal;

import AccesoADatos.*;
import Tipos.*;
import Tipos.Tweet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author julio
 */
public class AnalisisdeTexto {
    
    private DBTweets tweetsdb;
    private DBTweetsRechazados tweetsRechadosdb;
    private StopWords stopwords;
    private Diccionario diccionario;

    public AnalisisdeTexto(StopWords stopwords, DBTweets tweetsdb, DBTweetsRechazados tweetsRechadosdb, Diccionario diccionario) 
    {
        this.stopwords = stopwords;
        this.tweetsdb = tweetsdb;
        this.tweetsRechadosdb = tweetsRechadosdb;
        this.diccionario = diccionario;
    }
    //Suponiendo que la cantidad de palabra es igual
    public boolean SonIguales(List<Palabra> tweet1, List<Palabra> tweet2)
    {
        boolean iguales = true;
        int i;
        for (i=0; i<tweet1.size();i++) 
        {
            if (!tweet1.get(i).getPalabra().equals(tweet2.get(i).getPalabra()))
            {
                return false;
            }
        }
        return iguales;
    }
    
    public List<Tweet> FiltrarTweetIguales(List<Tweet> LTweets)
    {
        List<Tweet> filtrado = new ArrayList<>();
        int i = 0;
        int j = 0;
        String tweet1, tweet2;
        Tweet tweetaux;
        while(LTweets.size()>0) 
        {
            tweet1 = LTweets.get(0).getTextoTweet(); 
            filtrado.add(LTweets.get(0));
            LTweets.remove(i);
            j=0;
            while(j<LTweets.size())
            {
                tweet2 = LTweets.get(j).getTextoTweet();
               if (tweet1.equals(tweet2))
               {
                   tweetsRechadosdb.Agregar(LTweets.get(j).getID());
                   LTweets.remove(j);
               }
               else
               {
                   j++;              
               }
            }
        }
        return filtrado;
    }
    
    private List<Tweet> FiltrarporID(List<Tweet> listtweet)
    {
       List<Tweet> resultado = new ArrayList<>();
       for (Tweet tweet : listtweet) 
       {
            if(!tweetsdb.ExisteID(tweet.getID()))
                {
                    if(!tweetsRechadosdb.ExisteID(tweet.getID()))
                    {
                            resultado.add(tweet);
                    }
                }
       }
       return resultado;
    }
    
    public List<Tweet> ConseguirTweetIndecisos(List<Tweet> listtweet)
    {
        Palabra palaux;
        float puntajetweet = 0;
        List<Palabra> paltweet;
        List<Tweet> resultado = new ArrayList<>();
        List<Tweet> listaux = FiltrarporID(listtweet);
        listaux = FiltrarTweetIguales(listaux);
        for (Tweet tweet : listaux) 
        {         
            paltweet = tweet.GetPalabrasTweet();
            paltweet = stopwords.FiltrarStopWords(paltweet);
            for (Palabra palabra : paltweet) 
            {
                palaux = diccionario.GetPalabraString(palabra.getPalabra());
                if (palaux != null)
                {
                  puntajetweet += (float) (palaux.getCanttweetcorrectos()-palaux.getCanttweetincorrectos())/palaux.getCanttweettotal();
                }
            }
            if (puntajetweet<-2.0)
            {
                System.out.println(String.valueOf(puntajetweet) + " - ID: " + String.valueOf(tweet.getID())+ " - Rechazado: "+ tweet.getTextoTweet());
                AgregarTweetNoOK(tweet);
            }
            if (puntajetweet>0)
            {
                System.out.println(String.valueOf(puntajetweet) + " - ID: "+ String.valueOf(tweet.getID())+ " - Aceptados: "+ tweet.getTextoTweet());
                AgregarTweetOK(tweet);
            }
            if (-2.0<=puntajetweet && puntajetweet<=0.0)
            {
                tweet.setPuntaje(puntajetweet);
                resultado.add(tweet);
                
            }
            puntajetweet = 0;     
        }
        
        return resultado;
    }
    
    public void AgregarTweetOK(Tweet tweet)
    {
        tweetsdb.Agregar(tweet);
        List<Palabra> listsinfiltrar = tweet.GetPalabrasTweet();
        List<Palabra> listfiltrado = stopwords.FiltrarStopWords(listsinfiltrar);
        for (Palabra palabra : listfiltrado)
          {
            palabra.AumentarTweetCorrectos();
            diccionario.AnalizarPalabra(palabra);
          }
    }
    
    public void BorrarTweetOK(Tweet tweet)
    {
        List<Palabra> listsinfiltrar = tweet.GetPalabrasTweet();
        List<Palabra> listfiltrado = stopwords.FiltrarStopWords(listsinfiltrar);
        tweetsRechadosdb.Agregar(tweet.getID());
        tweetsdb.BorrarTweet(tweet);
        for (Palabra palabra : listfiltrado) 
        {
            diccionario.BorrarPalabraOK(palabra);    
        }
    }
    
    public void RecuperarTweetOK(Tweet tweet)
    {
       List<Palabra> listsinfiltrar = tweet.GetPalabrasTweet();
       List<Palabra> listfiltrado = stopwords.FiltrarStopWords(listsinfiltrar);
       tweetsRechadosdb.BorrarTweetID(tweet.getID()); 
       tweetsdb.Agregar(tweet);
        for (Palabra palabra : listfiltrado) 
        {
            diccionario.RecuperarPalabraOK(palabra);    
        }
    }
    
    public void AgregarTweetNoOK(Tweet tweet)
    {
        List<Palabra> listsinfiltrar = tweet.GetPalabrasTweet();
        List<Palabra> listfiltrado = stopwords.FiltrarStopWords(listsinfiltrar);
        tweetsRechadosdb.Agregar(tweet.getID());
        for (Palabra palabra : listfiltrado)
        {
            palabra.AumentarTweetIncorretos();
            diccionario.AnalizarPalabra(palabra);
        }
        
    }
   
}
