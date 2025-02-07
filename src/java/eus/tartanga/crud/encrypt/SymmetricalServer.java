package eus.tartanga.crud.encrypt;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * This class provides methods for symmetric encryption and decryption using AES
 * encryption in CBC mode. It also includes functionality to send emails using
 * decrypted credentials.
 */
public class SymmetricalServer {

    private static byte[] salt = "esta es la salt!".getBytes();
    private static Logger logger = Logger.getLogger(SymmetricalServer.class.getName());

    /**
     * Decrypts a text using AES, CBC mode, and PKCS5Padding (symmetric) and
     * returns the decrypted text.
     *
     * @param key The user key to generate the secret key.
     * @param fileContent The encrypted file content as a byte array.
     * @return The decrypted text.
     * @throws Exception If an error occurs during the decryption process.
     */
    public String decryptData(byte[] key, byte[] fileContent) throws Exception {
        String ret = null;
        try {
            // Creamos un SecretKeySpec a partir de la clave del usuario y la sal (salt)
            SecretKey secretKey = new SecretKeySpec(key, 0, key.length, "AES");
            // Configuración del Cipher para descifrado
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // El IV se extrae de los primeros 16 bytes del archivo
            IvParameterSpec ivParam = new IvParameterSpec(Arrays.copyOfRange(fileContent, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParam);
            // Los datos cifrados comienzan después de los primeros 16 bytes (IV)
            byte[] encryptedData = Arrays.copyOfRange(fileContent, 16, fileContent.length);
            // Realizamos el descifrado
            byte[] decodedMessage = cipher.doFinal(encryptedData);
            //el texto descifrado
            ret = new String(decodedMessage);
        } catch (NoSuchAlgorithmException e) {
            // Registra el error en el log y lo imprime en consola
            logger.log(Level.SEVERE, "Error durante el descifrado", e);
        }
        return ret;
    }

    /**
     * Reads the content of a file and returns it as a byte array.
     *
     * @param path The file path.
     * @return The content of the file as a byte array.
     * @throws IOException If an error occurs while reading the file.
     */
    private byte[] fileReader(String path) throws IOException {
        File file = new File(path);
        // Leemos el archivo completo y retornamos su contenido
        return Files.readAllBytes(file.toPath());
    }

    /**
     * Retrieves decrypted credentials from an encrypted file.
     *
     * @param key The decryption key.
     * @param filePath The path to the encrypted credentials file.
     * @return An array containing the decrypted email and password.
     */
    public String[] getDecryptedCredentials(byte[] key, String filePath) {
        try {
            // Leemos el archivo cifrado
            byte[] encryptedContent = fileReader(filePath);
            // Desencriptamos los datos
            String decryptedText = decryptData(key, encryptedContent);

            if (decryptedText.contains(":")) {
                // Separamos el email y la contraseña
                return decryptedText.split(":", 2);
            } else {
                logger.severe("Formato incorrecto en credenciales desencriptadas");
                return new String[]{"", ""};
            }
        } catch (Exception e) {
            logger.severe("Error obteniendo credenciales desencriptadas");
            return new String[]{"", ""};
        }
    }

    /**
     * Sends an email using the decrypted credentials.
     *
     * @param clientEmail The recipient email address.
     * @param key The decryption key.
     * @param credentialsPath The path to the encrypted credentials file.
     * @param messageType True if the email is for a changed password, false if
     * generating a new password.
     */
    public void sendEmail(String clientEmail, byte[] key, String credentialsPath, boolean messageType) {
        final String HOST = "localhost";
        final String TLS_PORT = "25";

        // Obtener credenciales desencriptadas
        String[] credentials = getDecryptedCredentials(key, credentialsPath);
        final String senderEmail = credentials[0];
        final String senderPassword = credentials[1];

        // System.getProperties devuelve las propiedades del sistema
        // NO puede devolver NULL
        Properties props = System.getProperties();

        props.setProperty("mail.smtps.host", HOST);                                                          // for gmail
        props.setProperty("mail.smtp.port", TLS_PORT);
        props.setProperty("mail.smtp.starttls.enable", "false");
        props.setProperty("mail.smtps.auth", "false");

        Session session = Session.getInstance(props, null); // null porque no hay autenticación
        //getInstance siempre devuelve un objeto

        Transport transport = null;
        try {

            // Crear el mensaje
            MimeMessage msg = new MimeMessage(session);
            // Variable para determinar el mensaje
            boolean isPasswordChanged = messageType; // Cambiar según corresponda
            // De qué email se envía
            msg.setFrom(new InternetAddress(senderEmail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(clientEmail, false));

            if (isPasswordChanged) {
                msg.setSubject("Changed password");
                msg.setText("Tu contraseña ha sido modificada", "utf-8", "html");
            } else {
                // Generate new user password
                int leftLimit = 48;
                int rightLimit = 122;
                int targetStringLength = 10;
                Random random = new Random();

                String generatedUserClave = random.ints(leftLimit, rightLimit + 1)
                        .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                        .limit(targetStringLength)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
                msg.setSubject("New password");
                msg.setText("Tu nueva contraseña es: " + generatedUserClave, "utf-8", "html");
            }
            msg.setSentDate(new Date());

            // selecciona el protocolo de correo a usar
            transport = session.getTransport("smtp");

            // Conectar al servidor SMTP
            transport.connect(HOST, senderEmail, senderPassword);
            // Enviar el mensaje
            transport.sendMessage(msg, msg.getAllRecipients());

        } catch (MessagingException e) {
            // En caso de que no exista proveedor de smtp lanza una excepción
            logger.severe("Error enviando el correo");
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    logger.severe("Error cerrando conexión SMTP");
                }
            }
        }

    }

}
