/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;


import Controlador.Controlador;
import static Controlador.Controlador.ciudadesLLenar;
import Modelo.GraphLA;
import Modelo.Vertex;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author Joel
 */
public class Pantalla {
    private BorderPane ventanaRoot;
    private Label titulo, ciudadI, ciudadF, resultSet;
    private Button aceptar;
    private ComboBox ciudadInicial, ciudadFinal;
    private VBox ventana;
    private HBox ciudadIni, ciudadFin;
    
    
    public Pantalla(){
        ventanaRoot = new BorderPane();
        Inicializar();
    }
    
    public BorderPane getRoot(){
        return ventanaRoot;
    }
    
    private void Inicializar(){
        titulo = new Label("Bienvenido a la aplicación distancias minimas");
        titulo.setFont(new Font("Times New Roman",20));
        
        ciudadI = new Label("Ingrese ciudad inicial");
        ciudadI.setFont(new Font("Times New Roman",15));
        ciudadF = new Label("Ingrese ciudad destino");
        ciudadF.setFont(new Font("Times New Roman",15));
        resultSet = new Label();
        resultSet.setFont(new Font("Times New Roman",15));
        resultSet.setAlignment(Pos.CENTER);
        
        ciudadInicial = new ComboBox();
        ciudadInicial.setItems(ciudadesLLenar("ciudades.txt"));
        ciudadFinal = new ComboBox();
        ciudadFinal.setItems(ciudadesLLenar("ciudades.txt"));
        
        aceptar = new Button("Aceptar");
        aceptar.setPrefSize(160, 80);
        aceptar.setOnMouseClicked(event ->ejecutarAccion(event));
        
        ciudadIni = new HBox();
        ciudadIni.setSpacing(10);
        ciudadIni.getChildren().addAll(ciudadI,ciudadInicial);
        ciudadIni.setAlignment(Pos.CENTER);
        
        ciudadFin = new HBox();
        ciudadFin.setSpacing(10);
        ciudadFin.getChildren().addAll(ciudadF,ciudadFinal);
        ciudadFin.setAlignment(Pos.CENTER);
        
        ventana = new VBox();
        ventana.setSpacing(15);
        ventana.getChildren().addAll(titulo,ciudadIni,ciudadFin,resultSet,aceptar);
        ventana.setAlignment(Pos.CENTER);
        ventanaRoot.setCenter(ventana);
        
    }
    
    public void ejecutarAccion(Event e){
        String rutaCorta = "Ruta de viaje más corta: \n";
        HashMap<String,String> ciudades = Controlador.cargarCiudades("ciudades.txt");
        GraphLA<String> grafo = Controlador.cargarGrafo("distancias.txt", ciudades);
        String codigoCiudadInicial = Controlador.buscarCodigoCiudad(ciudades, ciudadInicial.getValue().toString());
        String codigoCiudadFinal = Controlador.buscarCodigoCiudad(ciudades, ciudadFinal.getValue().toString());
        if(codigoCiudadFinal.equals("No hay ciudad") || codigoCiudadInicial.equals("No hay ciudad")){
            resultSet.setText("Error al ingresar informacion, verificar ingreso correcto\nRevise mayúsculas, espacios o que las ciudades se encuentren en el archivo");
        }else{
            LinkedList<String> lista = grafo.caminoMinimo(codigoCiudadInicial, codigoCiudadFinal);
            Iterator<String> i = lista.iterator();
            while(i.hasNext()){
            String clave = i.next();
                for(String codigo: ciudades.keySet()){
                    if(codigo.equals(clave)){
                        rutaCorta += ciudades.get(codigo)+"\n";
                    }
                }
            }
            System.out.println(lista);
            int distancia = grafo.distanciaMinima(codigoCiudadInicial, codigoCiudadFinal);
            resultSet.setText(rutaCorta+"Distancia: "+distancia+" Km");
        }
    }
}
