/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartangalh.cud.crypto;

import javax.crypto.Cipher;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class Server {
    public static void main(String[] args) {
        try {
            // Cargar la clave privada desde un recurso del classpath
            byte[] privateKeyBytes;
            try (InputStream keyInputStream = Server.class.getResourceAsStream("EjemploRSA_Private.key");
                 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                if (keyInputStream == null) {
                    throw new FileNotFoundException("No se encontró el archivo de clave privada.");
                }
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = keyInputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, bytesRead);
                }
                privateKeyBytes = baos.toByteArray();
            }

            // Reconstruir la clave privada
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            // Iniciar el servidor
            try (ServerSocket serverSocket = new ServerSocket(12345)) {
                System.out.println("Servidor escuchando en el puerto 12345...");

                while (true) {
                    // Manejar conexiones de clientes
                    try (Socket clientSocket = serverSocket.accept();
                         InputStream inputStream = clientSocket.getInputStream();
                         ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

                        // Leer los datos cifrados
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            baos.write(buffer, 0, bytesRead);
                        }
                        byte[] encryptedData = baos.toByteArray();

                        // Descifrar los datos
                        Cipher cipher = Cipher.getInstance("RSA");
                        cipher.init(Cipher.DECRYPT_MODE, privateKey);
                        byte[] decryptedData = cipher.doFinal(encryptedData);

                        System.out.println("Mensaje recibido del cliente: " + new String(decryptedData));
                    } catch (Exception e) {
                        System.err.println("Error al manejar una conexión del cliente: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
