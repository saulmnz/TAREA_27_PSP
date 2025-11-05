# TAREA 27 🌚

> **Modifica la práctica anterior para que la comunicación sea interactiva y continua, definiendo el siguiente protocolo 🐙🐠:**
> 1. **Cliente y Servidor se conectan.** 
> 2. **El Cliente entra en un bucle donde lee una línea de texto desde el teclado del usuario.** 
> 3. **El Cliente envía esa línea al Servidor.**
> 4. **Si la línea enviada es "adios", el bucle del cliente termina y se cierra la conexión.** 
> 5. **El Servidor entra en un bucle donde lee la línea del cliente.** 
> 6. **Si la línea recibida es null o "adios", el bucle del servidor termina.** 
> 7. **El Servidor envía la misma línea de vuelta al Cliente, prefijada con "ECO: ".** 
> 8. **El Cliente lee la respuesta "ECO:" del servidor y la imprime por pantalla.**

---

# CLASE CLIENTE 🌷:
```java
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
```

---

# CLASE SERVIDOR 🌼: 

```java
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
```
