/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.GraphLA;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Joel
 */
public class Controlador {
    /**
     * Método cargarCiudades
     * @param nombreArchivo el cual es es .txt con el nombre de ciudades
     * @return HashMap cuya clave será el código de la ciudad y el valor será el nombre de la ciudad
     */
    public static HashMap<String,String> cargarCiudades(String nombreArchivo){
        try{
            FileInputStream EntraArchivo = new FileInputStream(nombreArchivo);
            try ( 
                DataInputStream entrada = new DataInputStream(EntraArchivo)) {
                BufferedReader LecturaArchivo = new BufferedReader(new InputStreamReader(entrada));
                String strLinea;
                HashMap<String,String> mapa = new HashMap();
                while ((strLinea = LecturaArchivo.readLine()) != null) {
                    String[] arreglo;
                    arreglo = strLinea.split("\\,");
                    mapa.put(arreglo[0], arreglo[1]);
                    }
                //System.out.println(mapa.toString());
                return mapa;
                }     
            }    
            catch (IOException e){
                System.err.println("Ocurrio un error: " + e.getMessage());
                        return null;
               }
    }
    
    
    public static ObservableList<String> ciudadesLLenar(String archivo){
        ObservableList<String> ciu=FXCollections.observableArrayList();
        try(BufferedReader input=new BufferedReader(new FileReader(archivo))){
            String linea;
            while((linea=input.readLine())!=null){
                String[] arreglo=linea.split(",");
                ciu.add(arreglo[1]);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ciu;
        
    }
    
    
    
    /**
     * Método cargarGrafo
     * @param nombreArchivo se refiere al .txt con las relaciones de las ciudades entre sí
     * @param ciudades se refiere al HashMap obtenido en el método cargarCiudades
     * @return un grafo cuyos vértices son los códigos de las ciudades y cuyos vértices son la distancia entre ellas
     */
    public static GraphLA<String> cargarGrafo(String nombreArchivo, HashMap<String,String> ciudades){
        GraphLA<String> grafo = new GraphLA(false);
        Iterator<String> codigo = ciudades.keySet().iterator();
        while(codigo.hasNext()){
            grafo.addVertex(codigo.next());
        }
        
        LeerArchivo lectura = new LeerArchivo();
        LinkedList listaInfo = lectura.lecturaArchivo(nombreArchivo);
        Iterator i = listaInfo.iterator();
        while(i.hasNext()){
            String[] datos = (String[]) i.next();
            String comparar = datos[0]; //origen
            for(int j = 1; j<datos.length;j++){
                String[] arcosDistancia = datos[j].split(","); //arcosDistancia[0]=destino
                if(Integer.parseInt(arcosDistancia[1]) > 0){
                    grafo.addEdge(comparar, arcosDistancia[0], Integer.parseInt(arcosDistancia[1]));
                    //System.out.println(comparar+"|"+arcosDistancia[0]+"-"+arcosDistancia[1]);
                }
            }
        }
        //System.out.println(grafo.toString());
        return grafo;
    }
    
    /**
     * Método buscarCodigoCiudad
     * @param codigos es el HashMap obtenido en el método cargarCiudades
     * @param ciudad es el nombre de una ciudad
     * @return el código de dicha ciudad que se envió como parámetro
     */
    public static String buscarCodigoCiudad(HashMap<String,String> codigos, String ciudad){
        for (HashMap.Entry<String, String> entry : codigos.entrySet()) {
            if(entry.getValue().equals(ciudad))
                return entry.getKey();
        }
        return "No hay ciudad";
    }
    
    
    
}

