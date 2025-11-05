package org.example;
import java.io.*;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        final String host = "localhost";
        final int puerto = 6969;

        try (
                // CREAMOS EL SOCKET QUE SE CONECTA AL SERVIDOR CON localhost:6969
                Socket socket = new Socket(host, puerto);

                // HACEMOS LA LLAMADA
                BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

                // LEEMOS LA INFORMACIÓN QUE NOS ENVÍA EL SERVIDOR
                BufferedReader entrada = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );

                // ENVIAMOS EL TEXTO AL SERVIDOR
                PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String linea;
            System.out.println("CONECTADO AL SERVIDOOOR. ESCRIBE MENSAJES WEY ( O ESCRIBE 'adios' PARA SALIR):");

            // CREAMOS UN BUCLE INFINITO DONDE El CLIENTE ESCRIBE ENTRADAS PARA EL SERVIDOR ( FINALIZA BUCLE CUANDO EL CLIENTE ESCRIBE "adios" )
            // EL SERVIDOR NOS DEVUELVE LAS ENTRADAS INTRODUCIDAS SIMULANDO "ECO"
            while (true) {
                System.out.print("TÚ: ");
                linea = teclado.readLine();

                if (linea == null) break;

                salida.println(linea);

                if ("adios".equalsIgnoreCase(linea.trim())) {
                    break;
                }

                String eco = entrada.readLine();
                if (eco == null) break;

                System.out.println("SERVIDOR: " + eco);
            }

        } catch (IOException e) {
            System.err.println("ERROR EN EL CLIENTE: " + e.getMessage());
        }
    }
}