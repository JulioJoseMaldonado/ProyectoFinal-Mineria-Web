/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProyectoFinal;

import AccesoADatos.DBDiccionario;
import Tipos.Palabra;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author julio
 */
public class Diccionario {
    
    DBDiccionario diccionariodb;

    public Diccionario(DBDiccionario diccionariodb) 
    {
        this.diccionariodb = diccionariodb;
    }

    
    public List<String> getListDiccionario() {
        return diccionariodb.DiccionarioString();
    }
    

    public Palabra GetPalabraString(String palstring)
    {
        return diccionariodb.GetPalabra(palstring);
    }
    
    public void RecuperarPalabraOK(Palabra palabra)
    {
        Palabra palaux = diccionariodb.GetPalabra(palabra.getPalabra());
        if (palaux != null)
        {
            palaux.setCanttweetcorrectos(palaux.getCanttweetcorrectos()+1);
            palaux.setCanttweetincorrectos(palaux.getCanttweetincorrectos()-1);
            diccionariodb.ReemplazarOK(palaux);
        }
        else
        {
            AnalizarPalabra(palabra);
        }
    }
    public void BorrarPalabraOK(Palabra palabra)
    {
        Palabra palaux = diccionariodb.GetPalabra(palabra.getPalabra());
        palaux.setCanttweetcorrectos(palaux.getCanttweetcorrectos()-1);
        palaux.setCanttweetincorrectos(palaux.getCanttweetincorrectos()+1);
        diccionariodb.ReemplazarOK(palaux);
    }
    public void AnalizarPalabra(Palabra palabra)
    {
        if (diccionariodb.ExistePalabra(palabra))
        {
            diccionariodb.ReemplazarPalabra(palabra);
        } 
        else
        {
            diccionariodb.Agregar(palabra);
        }
    }
    
    public void BorrarPalabra(Palabra palabra)
    {
        diccionariodb.borrarpalabra(palabra);
    }
    
    public String GenerarConsulta()
    {
        String consulta;
        List<Palabra> ListDiccionario = diccionariodb.DiccionarioforSearch();
        if (ListDiccionario.size()>=15)
        {
            Set<Integer> numerosgen = new HashSet<>();
            double nro;
            int nrorandom;
            consulta = ListDiccionario.get(0).getPalabra() + " AND ";
            numerosgen.add(0);
            while (numerosgen.size()<7)
            {        
                nro = Math.random()*ListDiccionario.size();
                nrorandom = (int) nro;
                if (!numerosgen.contains(nrorandom))
                {
                    numerosgen.add(nrorandom);
                    consulta = consulta + ListDiccionario.get(nrorandom).getPalabra();
                    if (numerosgen.size()<7)
                    {
                        consulta = consulta + " OR ";
                    }
                } 
                
            }
        }
        else
        {
            consulta = "robaron OR ladrones OR chorros AND celular OR auto OR moto OR billetera OR cartera";
        }
        
        return consulta;

        
         
    }
    

    
}
