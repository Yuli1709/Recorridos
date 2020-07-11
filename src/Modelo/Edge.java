/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Objects;

/**
 *
 * @author Joel
 */
public class Edge<E> {
    
    private Vertex<E> origen;
    private Vertex<E> destino;
    private int peso;
    
    public Edge(Vertex<E> origen, Vertex<E> destino, int peso){
        this.origen= origen;
        this.destino= destino;
        this.peso = peso;
    }
    
    
    
    public boolean equals(Object o){
        if(o==null ){
            return false;
        }else if (!(o instanceof Edge)){
            return false;
        }else {
            Edge<E> e = (Edge<E>)o;
            return this.destino.equals(e.destino) && this.origen.equals(e.origen)&&this.peso==e.peso;
        }
    }

    public Vertex<E> getOrigen() {
        return origen;
    }

    public void setOrigen(Vertex<E> origen) {
        this.origen = origen;
    }

    public Vertex<E> getDestino() {
        return destino;
    }

    public void setDestino(Vertex<E> destino) {
        this.destino = destino;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.origen);
        hash = 59 * hash + Objects.hashCode(this.destino);
        hash = 59 * hash + this.peso;
        return hash;
    }

    
    
    
    
}
