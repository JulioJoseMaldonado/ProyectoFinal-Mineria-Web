/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tipos;


import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringEscapeUtils;
import twitter4j.GeoLocation;
import twitter4j.Place;

/**
 *
 * @author julio
 */
public class Tweet {
    
    private String TextoTweet;
    private String Autor;
    private String fecha;
    private long ID;
    private double latitud;
    private double longitud;
    private String locacion;
    private float puntaje;

    

    public Tweet(String TextoTweet, String Autor, long ID, double lat, double lng, String locacion, String fecha) {
        this.TextoTweet = TextoTweet;
        this.Autor = Autor;
        this.ID = ID;
        this.longitud = lng;
        this.latitud = lat;
        this.locacion = locacion;
        this.fecha = fecha;
        this.puntaje = 0;
    }

   
    public long getID() {
        return ID;
    }

    public String getFecha() {
        return fecha;
    }

    public String getAutor() {
        return Autor;
    }

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }
    
    public String getLocacion() {
        return locacion;
    }

    public String getTextoTweet() {
        return TextoTweet;
    }

    public float getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(float puntaje) {
        this.puntaje = puntaje;
    }
    
    
    
    public List<Palabra> GetPalabrasTweet()
    {
        String texto = TextoTweet.replaceAll(",", " ");
        texto = texto.replaceAll("https?:\\/\\/.*\\s|https?:\\/\\/.*$", " ");
        texto = texto.replaceAll("\\.", " ");
        String[] arrayresultado = texto.split(" ");
        List<Palabra> resultado = new ArrayList<>();
        for (String palabra : arrayresultado) 
        {
            if (!palabra.contains("@") && !palabra.contains("http") && !palabra.contains("#"))
            {
                palabra = palabra.replaceAll("[^a-z ^A-Z ^á ^é ^í ^ú ^ó Á ^É ^Í ^Ú ^Ó ^Ü ^Ñ ^ñ ^ü]", "");
                palabra = palabra.toLowerCase();
                if (!"".equals(palabra) && palabra.length()>1)
                {
                    if (palabra.contains("jaja"))
                    {
                        palabra = "jaja";
                    }
                    resultado.add(new Palabra(palabra));
                }
                
            }
        }
        return resultado;
    }
    
    
    
    
    
    
    
}
