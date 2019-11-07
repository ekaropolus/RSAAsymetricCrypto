/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rsaasymetriccrypto;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Scanner;
import javax.crypto.Cipher;

/**
 *
 * @author edvaldes
 */
public class RSAAsymetricCrypto {

    private static Cipher rsa;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here

        // Generar el par de claves
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // Obtener la clase para encriptar/desencriptar
        rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");

        // Texto a encriptar
        Scanner input = new Scanner(System.in);
        int numero = 1;
        boolean seguir = true;
        while (seguir == true) {
            System.out.println("\nIntroduzca un entero de 4 digitos");
            numero = input.nextInt(); //numero del producto
            if (numero >= 999 && numero <= 9999) {
                seguir = false;
            } else {
                System.out.println("\nNo es un entero de 4 digitos");
                seguir = true;
            }
        }
        String text = Integer.toString(numero);

        // Se encripta
        rsa.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encriptado = rsa.doFinal(text.getBytes());

        // Escribimos el encriptado para verlo, con caracteres visibles
        for (byte b : encriptado) {
            System.out.print(Integer.toHexString(0xFF & b));
        }
        System.out.println();

        // Se desencripta
        rsa.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytesDesencriptados = rsa.doFinal(encriptado);
        String textoDesencripado = new String(bytesDesencriptados);

        // Se escribe el texto desencriptado
        System.out.println(textoDesencripado);

    }

}
