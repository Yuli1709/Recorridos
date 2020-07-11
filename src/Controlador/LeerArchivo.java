package Controlador;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 *
 * @author Joel
 */
public class LeerArchivo {
    
    /**
     * Método que permite leer el archivo de Traducciones.txt
     * @param path recibe la ruta del archivo a leer
     * @return un ArrayList con la información de las letras, numeros y signos con su respectivo codigo Morse
     */
    public static LinkedList lecturaArchivo(String path){
        try{
            FileInputStream EntraArchivo = new FileInputStream(path);
            try ( 
                DataInputStream entrada = new DataInputStream(EntraArchivo)) {
                BufferedReader LecturaArchivo = new BufferedReader(new InputStreamReader(entrada));
                
                String strLinea;
                LinkedList listaDatos = new LinkedList();
                
                while ((strLinea = LecturaArchivo.readLine()) != null) {
                    String[] arreglo;
                    arreglo = strLinea.split("\\|");
                    listaDatos.add(arreglo);
                    }
                return listaDatos;
                }     
            }    
            catch (IOException e){
                System.err.println("Ocurrio un error: " + e.getMessage());
                        return null;
               }   
    }
}
