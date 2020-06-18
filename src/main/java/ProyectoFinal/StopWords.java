/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProyectoFinal;

import AccesoADatos.DBStopWords;
import Tipos.Palabra;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author julio
 */
public class StopWords {

    private DBStopWords stopwordsDB;
    List<String> stopwords;
    public StopWords(DBStopWords stopwordsdb) 
    {
        this.stopwordsDB = stopwordsdb;
        this.stopwords = stopwordsdb.Stopwords();
    }

    public List<String> getStopwords() {
        return stopwordsDB.Stopwords();
    }
   
    public List<Palabra> FiltrarStopWords(List<Palabra> listpal)
    {
        List<Palabra> resultado = new ArrayList<>();
        for(Palabra palabra : listpal) 
        {
            boolean existe = stopwordsDB.ExisteStopWords(palabra.getPalabra());
            if (!existe)
            { 
                resultado.add(palabra);
            }
        }
        return resultado;
    }
    public void Agregar(String palabra)
    {
        stopwordsDB.Agregar(palabra);
    }
    
}
