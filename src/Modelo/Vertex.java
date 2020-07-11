/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.LinkedList;
import java.util.Objects;

/**
 *
 * @author Joel
 */
public class Vertex<E> {
    
    private E data;
    private LinkedList<Edge<E>> edges;
    private boolean visited;
    private int  distancia;//infinito Integer.MAX.VALUE
    private Vertex<E> antecesor; // es para ver por cual vertice estamos pasando
    
    public Vertex(E data){
        this.data = data;
        edges = new LinkedList<>();
        visited = false;
        distancia = Integer.MAX_VALUE;
        antecesor = null;
        
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.data);
        return hash;
    }
    
    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public LinkedList<Edge<E>> getEdges() {
        return edges;
    }

    public void setEdges(LinkedList<Edge<E>> edges) {
        this.edges = edges;
    }
    
    public boolean equals(Object o){
        if(o == null ){
            return false;
        }else if (!(o instanceof Vertex)){
            return false;
        }else {
            Vertex<E> v = (Vertex<E>)o;
            return this.getData().equals(v.data) && v.getEdges().size()==this.getEdges().size()
                    && this.visited==v.visited;
        }
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public Vertex<E> getAntecesor() {
        System.out.println("---------------------");
        return antecesor;
    }

    public void setAntecesor(Vertex<E> antecesor) {
        System.out.println("---------------------");
        this.antecesor = antecesor;
    }
    
    
    
}
