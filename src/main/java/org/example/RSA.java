package org.example;

import java.math.BigInteger;
import java.util.Random;
import java.util.*;
import java.io.*;

public class RSA {

    int tamPrimo;
    BigInteger n, q, p;
    BigInteger totient;
    BigInteger e, d;
    /** Constructor de la clase RSA */
    public RSA(int tamPrimo) {
        this.tamPrimo = tamPrimo;
        generaPrimos(); //Genera p y q
        generaClaves(); //Genera e y d
    }

    /*
    Se elijen dos numeros grandes para P y Q.
    NOTA: entre mas grandes los numeros es mas segura la encriptacion pero se demora un poco
    mas el proceso de encriptar/desencriptar.
    P y Q deben ser diferentes y totalmente independientes uno del otro,
     */
    public void generaPrimos(){
        p = new BigInteger(tamPrimo, 10, new Random());
        do q = new BigInteger(tamPrimo, 10, new Random());
        while (q.compareTo(p)==0);
    }

    public void generaClaves(){
        //Calculamos: n = p * q
        n = p.multiply(q);
        /*
        Calculamos la funcion toltient o phi de Euler:
        toltient(n) = (p-1) * (q-1)
         */
        totient = p.subtract(BigInteger.valueOf(1));
        totient = totient.multiply(q.subtract(BigInteger.valueOf(1)));
        /*
        Elegimos un 'e',de tal forma que 1 < e < totient(n) y además que
        sea coprimo con totient(n)
         */
        do e = new BigInteger(2 * tamPrimo, new Random());
        while ((e.compareTo(totient) != -1) ||
                (e.gcd(totient).compareTo(BigInteger.valueOf(1)) != 0 ));
        //Calculamos un d, de tal forma que d*e = 1 mod totient(n)
        d = e.modInverse(totient);
    }

    /*
    La clave pública será entonces (n,e) y la privada (n,d)
     */


    /*** Encripta el texto usando la clave pública
     * ** @param mensaje Ristra que contiene el mensaje a encriptar
     * *@return El mensaje cifrado como un vector de BigIntegers*/
    public BigInteger[] encripta(String mensaje){
        int i;
        byte[] temp = new byte[1];
        byte[] digitos = mensaje.getBytes();
        BigInteger[] bigdigitos = new BigInteger[digitos.length];

        for(i=0; i < bigdigitos.length; i++){
            temp[0] = digitos[i];
            bigdigitos[i] = new BigInteger(temp);
        }
        BigInteger[] encriptado = new BigInteger[bigdigitos.length];

        for(i=0; i < bigdigitos.length; i++)
            encriptado[i] = bigdigitos[i].modPow(e,n);

        return(encriptado);
    }


    //Desencripta el texto cifrado usando la clave privada**
    //@param encriptado Array de objetos BigInteger que contiene el texto cifrado que será desencriptado
    //@return The decrypted plaintext*/

    public String desencripta(BigInteger[] encriptado){
        BigInteger[] desencriptado = new BigInteger[encriptado.length];

        for(int i=0; i<desencriptado.length; i++)
            desencriptado[i] = encriptado[i].modPow(d,n);

        char[] charArray = new char[desencriptado.length];

        for(int i=0; i<charArray.length; i++)
            charArray[i] = (char) (desencriptado[i].intValue());

        return(new String(charArray));
    }

    public BigInteger getP() {return(p);}
    public BigInteger getQ() {return(q);}
    public BigInteger getTotient() {return(totient);}
    public BigInteger getN() {return(n);}
    public BigInteger getE() {return(e);}
    public BigInteger getD() {return(d);}
}
