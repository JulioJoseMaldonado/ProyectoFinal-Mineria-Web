/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tipos;


public class Palabra {
    
    private String palabra;
    private int canttweettotal;
    private int canttweetcorrectos;
    private int canttweetincorrectos;
    private float puntaje;

    public Palabra(String palabra) {
        this.palabra = palabra;
        this.canttweetcorrectos = 0;
        this.canttweetincorrectos = 0;
        this.canttweettotal = 0;
        this.puntaje = 0;
    }

    public Palabra(String palabra, int canttweettotal, int canttweetcorrectos, int canttweetincorrectos) {
        this.palabra = palabra;
        this.canttweettotal = canttweettotal;
        this.canttweetcorrectos = canttweetcorrectos;
        this.canttweetincorrectos = canttweetincorrectos;
    }

    public Palabra(String palabra, float puntaje) {
        this.palabra = palabra;
        this.puntaje = puntaje;
    }
    
    
    
    

    public String getPalabra() {
        return palabra;
    }

    public int getCanttweettotal() {
        return canttweettotal;
    }

    public int getCanttweetcorrectos() {
        return canttweetcorrectos;
    }

    public int getCanttweetincorrectos() {
        return canttweetincorrectos;
    }

   
    public void AumentarTweetCorrectos()
    {
        canttweetcorrectos++;
        canttweettotal++;
    }
    public void AumentarTweetIncorretos()
    {
        canttweetincorrectos++;
        canttweettotal++;
    }

    public void setPuntaje(float puntaje) {
        this.puntaje = puntaje;
    }

    public float getPuntaje() {
        return puntaje;
    }

    public void setCanttweetcorrectos(int canttweetcorrectos) {
        this.canttweetcorrectos = canttweetcorrectos;
    }

    public void setCanttweetincorrectos(int canttweetincorrectos) {
        this.canttweetincorrectos = canttweetincorrectos;
    }

    public void setCanttweettotal(int canttweettotal) {
        this.canttweettotal = canttweettotal;
    }
    
    
    
    
    
    
    
    
    
   
    
    
    
    
    
    
}
