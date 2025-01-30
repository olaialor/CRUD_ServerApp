/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eus.tartanga.crud.encrypt;

import eus.tartanga.crud.exceptions.EncryptException;
import javax.crypto.Cipher;
import java.io.*;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class AsymmetricalServer {

    public static String decryptData(byte[] encryptedData) throws EncryptException {
        byte[] decryptedData;
        String passwordReceived = null;

        try {
            //Leer la clava privada desde el archivo
            InputStream keyInputStream = AsymmetricalServer.class.getResourceAsStream("Server_Private.key");

            if (keyInputStream == null) {
                throw new FileNotFoundException("No se encontró el archivo de clave privada.");
            }
            
            //.available obtiene el tamaño del archivo
            //new byte crea un array de bytes con ese tamaño
            byte[] privateKeyBytes = new byte[keyInputStream.available()];
            keyInputStream.read(privateKeyBytes);

            // Reconstruir la clave privada a partir de los bytes leidos
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

            //Inicializar el objeto Cipher para el descifrado con la clave privada.
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            //.init + Decrypt le informa que tiene que desencriptar y no encriptar
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            //Descifrar los datos (la contraseña cifrada).
            decryptedData = cipher.doFinal(encryptedData);

            //Convertir los datos descifrados en una cadena de texto (la contraseña original).
            passwordReceived = new String(decryptedData);

            //Si la contraseña es null, lanzar excepción.
            if (passwordReceived == null) {
                throw new EncryptException();
            }
        } catch (Exception e) {
            throw new EncryptException(e.getMessage());
        }
        return passwordReceived;
    }

    /**
     * Hashes the input text using the MD5 algorithm.
     *
     * @param text The text to be hashed.
     * @return The hexadecimal representation of the hashed text.
     */
    public static String hashText(String text) throws EncryptException {
        try {
            //Crear el objeto MessageDigest para usar el algoritmo MD5
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            // Generar el hash de los bytes del texto
            byte[] hashBytes = md5.digest(text.getBytes());
            // Crear un StringBuilder para construir la cadena hexadecimal
            StringBuilder hexStringBuilder = new StringBuilder();

            // Iterar por cada byte del hash
            for (byte b : hashBytes) {
                // Convertir el byte a formato hexadecimal
                String hex = String.format("%02X", b);
                // Agregar el valor hexadecimal al StringBuilder
                hexStringBuilder.append(hex);
            }
            // Devolver la cadena hexadecimal que representa el hash
            return hexStringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new EncryptException(e.getMessage());
        }
    }
}
