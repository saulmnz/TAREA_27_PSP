package org.example;
import java.io.*;
import java.net.*;

public class Servidor {
    public static void main(String[] args) {
        // DEFINIMOS PUERTO DE ENTRADA
        final int puerto = 6969;

        // CREAMOS EL SOCKET LIGADO AL PUERTO DEFINIDO
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("SERVIDOR ESPERA CONEXIONES DESDE EL PUERTO " + puerto );

            // ACCEPT BLOQUEA LA EJECUCIÓN HASTA QUE EL CLIENTE SE CONECTE.
            // CUANDO LLEGA ESA CONEXIÓN, EL SERVIDOR DEVUELVE UN SOCKET PARA COMUNICARSE DE VUELTA
            Socket clienteSocket = serverSocket.accept();
            System.out.println("CLIENTE CONECTADO.");

            // OBTENEMOS EL FLUJO DE ENTRADA ( LO QUE ENVÍA EL CLIENTE )
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(clienteSocket.getInputStream())
            );

            // OBTENEMOS EL FLUJO DE SALIDA PARA ENVIAR AL CLIENTE
            PrintWriter salida = new PrintWriter(
                    clienteSocket.getOutputStream(), true
            );

            // DECALARAMOS UNA VARIABLE PARA ALMACENAR CADA LÍNEA RECIBIDA.
            // MIENTRAS NO SE CUMPLA LA CONDICIÓN, EL BUCLE SIGUE.
            // SI EL MENSAJE RECIBIDO ES ADIOS, SALIMOS DEL BUCLE
            String linea;
            while ((linea = entrada.readLine()) != null) {
                System.out.println("RECIBIDO DEL CLIENTE: " + linea);

                if ("adios".equalsIgnoreCase(linea.trim())) {
                    salida.println("ECO: ADIOS");
                    break;
                }

                salida.println("ECO: " + linea);
            }

            clienteSocket.close();
            System.out.println("CONEXIÓN CERRADA.");
        } catch (IOException e) {
            System.err.println("ERROR EN EL SERVIDOR: " + e.getMessage());
        }
    }
}