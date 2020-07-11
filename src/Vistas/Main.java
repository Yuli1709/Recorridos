/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Joel
 */
public class Main extends Application{
    
    public static Stage primaryStageR = new Stage();
    private Scene scene;
    Pantalla pantalla = new Pantalla();
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.close();
        primaryStageR.setOnCloseRequest(event -> FinalizarPrograma(event));
        primaryStageR.setTitle("Bienvenido");
        scene = new Scene(pantalla.getRoot(),500,500);
        primaryStageR.setScene(scene);
        primaryStageR.maxHeightProperty().set(500);
        primaryStageR.maxWidthProperty().set(800);
        primaryStageR.minHeightProperty().set(500);
        primaryStageR.minWidthProperty().set(800);
        primaryStageR.show();        
    }
    public void FinalizarPrograma(Event e){
        Runtime.getRuntime().exit(0);
    }
    
}
