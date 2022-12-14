package org.example;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws IOException {

        //String encriptado1 = null;

        String encriptado1 = null;
        try {
            final String claveEncriptacion1 = "Clave-Hiper-Secreta321%$#@";
            //final String claveEncriptacion2 = "Clave-Hiper-Secreta567~!@#";
            //String userNAme = "ChawarMan";
            System.out.println("\n####################      AES        ############################\n");
            System.out.println("Cadena a encriptar AES: ");
            String cadenaUno = (new BufferedReader(new InputStreamReader(System.in))).readLine();
            //String passWord = "Hola-Mundo-Soy-Una-Cadena-Para-Encriptar-#%$&";
            //System.out.println("Password a encriptar: ");
            // String passWord = ( new BufferedReader(new InputStreamReader(System.in))).readLine();

            EncriptadorAES encriptador = new EncriptadorAES();

            encriptado1 = encriptador.encriptar(cadenaUno, claveEncriptacion1);
            //String encriptado2 = encriptador.encriptar(passWord, claveEncriptacion2);
            String desencriptado1 = encriptador.desencriptar(encriptado1, claveEncriptacion1);
            //String desencriptado2 = encriptador.desencriptar(encriptado2, claveEncriptacion2);

            System.out.println("\ncadenaUno Original       : " + cadenaUno);
            System.out.println("cadenaUno Encriptada     : " + encriptado1);
            System.out.println("Verificacion de cadenaUno Desencriptada  : " + desencriptado1);
            System.out.println("");
            //System.out.println("passWord Original       : " + passWord);
            // System.out.println("passWord Encriptado     : " + encriptado2);
            //System.out.println("passWord Desencriptado  : " + desencriptado2);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException |
                 NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //################################################################################################
        if (args.length != 1) {
            System.out.println("\n####################      RSA       ############################\n");

            System.out.println("Sintaxis: java RSA [tama??o de los primos]");
            System.out.println("por ejemplo: java RSA 512");
            args = new String[1];
            args[0] = "1024";
        }
        int tamPrimo = Integer.parseInt(args[0]);
        RSA rsa = new RSA(tamPrimo);

        System.out.println("Tam Clave: [" + tamPrimo + "]\n");

        System.out.println("p: [ " + rsa.getP().toString(16).toUpperCase() + " ]");
        System.out.println("q: [ " + rsa.getQ().toString(16).toUpperCase() + " ]\n");

        System.out.println("Clave publica (n,e)");
        System.out.println("n: [ " + rsa.getN().toString(16).toUpperCase() + " ]");
        System.out.println("e: [ " + rsa.getE().toString(16).toUpperCase() + " ]\n");

        System.out.println("Clave publica (n,d)");
        System.out.println("n: [ " + rsa.getN().toString(16).toUpperCase() + " ]");
        System.out.println("d: [ " + rsa.getD().toString(16).toUpperCase() + " ]\n");

        System.out.println("Cadena a encriptar RSA: ");
        //String textoPlano = ( new BufferedReader(new InputStreamReader(System.in))).readLine();
        String textoPlano = encriptado1;

        BigInteger[] textoCifrado = rsa.encripta(textoPlano);

        System.out.println("\nCadena encriptada RSA: [ ");
        for (int i = 0; i < textoCifrado.length; i++) {
            System.out.print(textoCifrado[i].toString(16).toUpperCase());
            if (i != textoCifrado.length - 1)
                System.out.println("");
        }
        System.out.println(" ]\n");

        String recuperarTextoPlano = rsa.desencripta(textoCifrado);
        System.out.println("Cadena desencritada RSA: [ " + recuperarTextoPlano + " ]");
    }

}