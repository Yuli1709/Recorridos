/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author EVOTEC
 */
public class GraphLA<E> {
    
    private LinkedList<Vertex<E>> vertices;
    private boolean directed; // true= dirigido false= no dirigido
    
    public GraphLA(boolean directed){
        this.vertices = new LinkedList<>();
        this.directed = directed;
    }
    
    public boolean isEmpty(){
        return vertices.isEmpty();
    }
    
    public boolean addVertex(E data){
        Vertex<E> v = new Vertex<>(data);
        return (data == null || vertices.contains(v))?false: vertices.add(v);
    }
    
    public boolean addEdge(E origen, E destino, int peso){
        if(origen == null || destino == null) return false;
        Vertex<E> vo = searchVertex(origen);
        Vertex<E> vd = searchVertex(destino);
        if(vo == null || vd == null) return false;
        Edge<E> e = new Edge<>(vo,vd,peso);
        if(vo.getEdges().contains(e)) return false;
        vo.getEdges().add(e);
        if(!directed) vd.getEdges().add(new Edge<>(vd,vo,peso));
        return true;
    }
    
    private Vertex<E> searchVertex(E data){
        for(Vertex<E> v : vertices){
            if(v.getData().equals(data)){
                return v;
            }
        }
        return null;
    }
    
    public int indigree(E data){
        int grado = 0;
        if(data != null){
            for(Vertex<E> v :vertices ){ 
                for(Edge<E> e: v.getEdges()){
                if(e.getDestino().getData().equals(data)){
                    grado++;  
            }
            }
            
        }
            return grado;
        }else {
            return -1;
        }
        
    }
    
    public int outdigree(E data){
        if(data!= null){
            Vertex<E> v = searchVertex(data);
            return v.getEdges().size();
        }
        return -1;
    }
    
    public String toString(){
        StringBuilder s=new StringBuilder("[");
        StringBuilder s2=new StringBuilder("[");
        for(Vertex<E> v: vertices){
            s.append(v.getData());
            if(!v.getData().equals(vertices.getLast().getData()))
                s.append(",");
            
            for(Edge<E> e : v.getEdges()){
                s2.append("(");
                s2.append(e.getOrigen().getData());
                s2.append(",");
                s2.append(e.getDestino().getData());
                s2.append(")");
                if(e.getOrigen().getData().equals(v.getEdges().getLast().getOrigen())||
                    e.getDestino().getData().equals(v.getEdges().getLast().getDestino())    )
                    s2.append(",");
            }
           
        }
        s.append("]");
         s2.append("]");
        return s.toString() +'\n'+ s2.toString();
    }
    
    
    
    public boolean equals(Object o){
        if(o==null || !(o instanceof GraphLA) ){
            return false;
        }else{
            GraphLA<E> g=(GraphLA<E>)o;
        
        if (this.vertices.size()!=g.vertices.size())
            return false;

        Set<Vertex<E>> s1=new HashSet<>();
        Set<Vertex<E>> s2=new HashSet<>();
        Set<Edge<E>> s3=new HashSet<>();
        Set<Edge<E>> s4=new HashSet<>();
        s1.addAll(this.vertices);
        s2.addAll(g.vertices);
        s1.removeAll(s2);
        for (Vertex<E> v:this.vertices){
            s3.addAll(v.getEdges());
        }
        for(Vertex<E> v2:g.vertices){
            s3.addAll(v2.getEdges());
        }
        s3.removeAll(s4);
        
        
        return s1.isEmpty() || s3.isEmpty();
        }
    }
    
    public LinkedList<Vertex<E>> getVertices() {
        return vertices;
    }

    public boolean isDirected() {
        return directed;
    }
    
    // el bfs no recorre necesariamente todo el grafo
    public List<E> bfs (E data){
        List<E> l= new LinkedList<>();
        if(data == null) return l ;
        Vertex<E> v = searchVertex(data);
        if(v == null) 
            return l;
        Queue<Vertex<E>> q= new LinkedList<>();
        q.offer(v);
        v.setVisited(true);
        while(!q.isEmpty()){
            v = q.poll();
            l.add(v.getData());
            for(Edge<E> e : v.getEdges()){
                if(!e.getDestino().isVisited()){
                    q.offer(e.getDestino());
                    e.getDestino().setVisited(true);
                    
                }
            }
        }
        cleanVertex();
        return l;
    }
    
    //setear todos los visitados a no visitados
    private void cleanVertex(){
        for( Vertex<E> v : this.vertices){
            if(v.isVisited()){
                v.setVisited(false);
            }
        }
    }
    
    public GraphLA<E> reverse(){
        GraphLA<E> tmp = new GraphLA(this.directed);
        if(this.isEmpty()){
            return tmp;
        }else {
            for(Vertex<E> v : this.vertices){
                tmp.addVertex(v.getData());
                for(Edge<E> e : v.getEdges()){
                    tmp.addVertex(e.getDestino().getData());
                   tmp.addEdge(e.getDestino().getData(), e.getOrigen().getData(), e.getPeso());
            }
            }
        }
        return tmp;
    }
    
    public boolean removeVertex(E data){
        if( data == null || vertices.contains(data)  ){
            return false;
        }else {
            ListIterator<Vertex<E>> it = this.vertices.listIterator();
            while(it.hasNext()){
                if(it.next().getData().equals(data)){
                    for(Vertex<E> v:vertices){
                        removeEdge(v.getData(),data);
                        removeEdge(data,v.getData());
                    }
                    it.remove();
                }
            }
            return true;
        }
    }
    
    public boolean removeEdge(E origen, E destino){
        if(origen == null || destino == null) return false;
        Vertex<E> vo = searchVertex(origen);
        Vertex<E> vd = searchVertex(destino);
        if(vo == null || vd == null) return false;
        for(Vertex<E> v : vertices){
            if(v.equals(vo)|| v.equals(vd)){
                for(Edge<E> e : v.getEdges()){
                    if(e.getOrigen().equals(vo) && e.getDestino().equals(vd)){
                        v.getEdges().remove(e);
                    }
                }
//                ListIterator<Edge<E>> it = v.getEdges().listIterator();
//                while(it.hasNext()){
//                    if(it.next().getOrigen().equals(vo) || it.next().getDestino().equals(vd)){
//                        it.remove();
//                    }
//                }
            }
        }
        return true;
    }
    
    public List<E> dfs(E data){
        List<E> lista = new LinkedList<>();
        Vertex<E> v = searchVertex(data);
        if(v==null || this.isEmpty()){
            return lista;
        }
        Stack<Vertex<E>> pila = new Stack<>();
        v.setVisited(true);
        pila.push(v);
        while(!pila.isEmpty()){
            Vertex<E> vi = pila.pop();
            lista.add(vi.getData());
            for(Edge<E> e:vi.getEdges()){
                if(!e.getDestino().isVisited()){
                    
                    e.getDestino().setVisited(true);
                    pila.push(e.getDestino());
                }
            }
        }
        cleanVertex();
        return lista;
    }
    
    private List<E> interseccion(List<E> l1, List<E> l2){
        List<E> tmp = new LinkedList<>();
        for(E data:l1){
            if(l2.contains(data)){
                tmp.add(data);
            }
        }
        return tmp;
    }
    
    public boolean isConnected(){
        for(Vertex<E> v1:vertices){
            List<E> lista = bfs(v1.getData());
            for(Vertex<E> v2:vertices){
                if(!lista.contains(v2.getData())){
                    return false;
                }
            }
        }
        if(this.directed){
            GraphLA<E> nuevo = reverse();
            for(Vertex<E> v1:nuevo.vertices){
            List<E> lista = bfs(v1.getData());
            for(Vertex<E> v2:nuevo.vertices){
                if(!lista.contains(v2.getData())){
                    return false;
                }
            }
        }
        }
        return true;
    }
    
    private void dijkstra(E origen)  {
       Vertex<E> verticeInicial = searchVertex(origen);
       verticeInicial.setDistancia(0);
       PriorityQueue<Vertex<E>> colaPrioridad = new PriorityQueue<>((Vertex<E> v1,Vertex<E> v2)->v1.getDistancia()-v2.getDistancia());
       colaPrioridad.offer(verticeInicial);
       
       while(!colaPrioridad.isEmpty()){
           Vertex<E> vertice = colaPrioridad.poll();
           vertice.setVisited(true);
           
           for(Edge<E> edges : vertice.getEdges()){
               Vertex<E> siguienteVertice = edges.getDestino();
               int distanciaEntreVertices = vertice.getDistancia()+edges.getPeso();
               
               if(distanciaEntreVertices < siguienteVertice.getDistancia()){
                   siguienteVertice.setAntecesor(vertice);
                   siguienteVertice.setDistancia(distanciaEntreVertices);
                   colaPrioridad.offer(siguienteVertice);
               }
           }
       }
       cleanVertex();
    }
    
    public LinkedList<String> caminoMinimo(E origen,E destino){
        int total = 0;
        Vertex<E> vo = searchVertex(origen);
        Vertex<E> vf = searchVertex(destino);
        LinkedList<String> lista = new LinkedList<>();
        
        if(vo == null || vf == null)
            return lista;
        else{
            lista.addFirst(String.valueOf(vf.getData()));
            dijkstra(origen);
            Vertex<E> antecesor = vf.getAntecesor();
            lista.addFirst(String.valueOf(antecesor.getData()));
            while(!antecesor.equals(vo)){
                antecesor = antecesor.getAntecesor();
                lista.addFirst(String.valueOf(antecesor.getData()));
            }
        }
        
        return lista;
    }
    
    public boolean existeCiclo(){
        for(Vertex<E> v:vertices){
            if(!v.isVisited()){
                v.setVisited(true);
                for(Edge<E> arco:v.getEdges()){
                    if(arco.getDestino().isVisited()){
                        return true;
                    }
                }
            }
            
        }
        cleanVertex();
        return false;
    }
    
   public int distanciaMinima(E origen, E destino){
       if(origen == null || destino == null){return -1;}
       dijkstra(origen);
       Vertex<E> vo = searchVertex(origen);
       Vertex<E> vd = searchVertex(destino);
       return vd.getDistancia();
   }
    
}
