/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ConexionaInternet;

import Tipos.Tweet;
import java.util.List;
import java.util.ArrayList;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author julio
 */


public class ManejadordeTwitter {
    private final static String CONSUMER_KEY = "7pvNyKRNvfswc2xD5EPs3hScc";
     private final static String CONSUMER_KEY_SECRET = "tCKnHnbHQjcRoChG6hGHofh7ERmH1TeSQAVr0CYfb7SZnEHrfk";
     private final static String ACCESS_TOKENS = "2437640942-vnNMjx9N0FsiBPiljZqewcxjiuP40MNDKrWJqRZ";
     private final static String ACCESS_TOKENS_SECRET = "4P1H0cTwo6QiNdeixLFHywbovNCoalQz5NUl4hXj4IAnb";
     private Twitter twitter;

    public ManejadordeTwitter() throws Exception
    {
        ConfigurationBuilder   confBuilder = new ConfigurationBuilder(); 
        confBuilder.setOAuthConsumerKey(CONSUMER_KEY); 
        confBuilder.setOAuthConsumerSecret(CONSUMER_KEY_SECRET); 
        confBuilder.setOAuthAccessToken(ACCESS_TOKENS); 
        confBuilder.setOAuthAccessTokenSecret(ACCESS_TOKENS_SECRET); 
        confBuilder.setTweetModeExtended(true);
        Configuration conf = confBuilder.build();
        TwitterFactory twitterFactory = new TwitterFactory(conf);
        
        twitter = twitterFactory.getInstance();


        
    }
    
    public List<Tweet> BuscarTweets(String Keywords) throws Exception
    {
        Query query = new Query(Keywords);
        query.lang("es");     
        query.setCount(60);
        QueryResult qr = twitter.search(query);
        List<Status> listatweets = qr.getTweets();
        List<Long> IDs = new ArrayList<>();
        List<Tweet> resultado = new ArrayList<>();
        String placeaux, Date;
        double lng, lat;
        
        for (Status elemento : listatweets) 
        {    
            if (elemento.isRetweet())
            {
                elemento = elemento.getRetweetedStatus();
            }   
            if (elemento.getInReplyToStatusId() == -1 && !IDs.contains(elemento.getId()))
            {
                placeaux = "";
                lng = 0.0;
                lat = 0.0;
                Date = elemento.getCreatedAt().toString();
                if (elemento.getPlace() != null)
                {
                    placeaux = elemento.getPlace().getFullName();
                }
                if (elemento.getGeoLocation() != null)
                {
                    lng = elemento.getGeoLocation().getLongitude();
                    lat = elemento.getGeoLocation().getLatitude();
                }
                //(String TextoTweet, String Autor, long ID, GeoLocation posicion, Place datoslocacion, Date fecha)
                resultado.add(new Tweet(elemento.getText(), elemento.getUser().getScreenName(), 
                                                 elemento.getId(), lat, lng, placeaux, Date));
                IDs.add(elemento.getId());
            }
        } 
        
        return resultado;
    }
    
    public Tweet ObtenerTweetID(long ID) throws Exception
    {

            Status busqueda = twitter.showStatus(ID);
            if (busqueda != null)
            {
                double lng = 0;
                double ltd = 0;
                String locacion = "";
                if (busqueda.getPlace() != null)
                {
                    locacion = busqueda.getPlace().getFullName();
                }
                if (busqueda.getGeoLocation()!=null)
                {
                    lng = busqueda.getGeoLocation().getLongitude();
                    ltd = busqueda.getGeoLocation().getLatitude();
                }
                return (new Tweet(busqueda.getText(), busqueda.getUser().getScreenName(), busqueda.getId(), lng, ltd, locacion, busqueda.getCreatedAt().toString()));
            }
            else
            {
                return null;
            }       

    }
     
     
}
