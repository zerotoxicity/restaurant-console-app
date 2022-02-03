package rrpss.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

/**
 *  @author SSP4 Group 1
 *  @version 1.0
 *  @since 2021-10-26
 */
public class Crypto {
    public static final String ALGORITHM = "PBKDF2WithHmacSHA1";

    public static final int SALT_BYTES = 24;

    /**
     * Generate a unique UUID
     * @return
     */
    public static String genUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Generate hash for password
     * @param password
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static String genHash(String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTES];
        random.nextBytes(salt);
        byte[] hash = pbkdf2(password, salt);
        return  toHex(salt) + ":" + toHex(hash);
    }

    /**
     * Compare hash
     * @param password
     * @param givenHash
     * @return
     * @throws InvalidKeySpecException
     * @throws NoSuchAlgorithmException
     */
    public static Boolean compare(String password, String givenHash) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String[] salt_hash = givenHash.split(":");
        byte[] salt = fromHex(salt_hash[0]);
        byte[] hash = fromHex(salt_hash[1]);

        byte[] toCompare = pbkdf2(password, salt);
        return equals(toCompare, hash);
    }

    /**
     * Taken from https://gist.github.com/jtan189/3804290
     * Compares two byte arrays in length-constant time. This comparison method
     * is used so that password hashes cannot be extracted from an on-line
     * system using a timing attack and then attacked off-line.
     *
     * @param   a       the first byte array
     * @param   b       the second byte array
     * @return          true if both byte arrays are the same, false if not
     */
    private static boolean equals(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    private static byte[]  pbkdf2(String password, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        SecretKeyFactory fac = SecretKeyFactory.getInstance(ALGORITHM);
        return fac.generateSecret(spec).getEncoded();
    }


    /**
     * Converts a string of hexadecimal characters into a byte array.
     *
     * @param   hex         the hex string
     * @return              the hex string decoded into a byte array
     */
    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for(int i = 0; i < binary.length; i++)
        {
            binary[i] = (byte)Integer.parseInt(hex.substring(2*i, 2*i+2), 16);
        }
        return binary;
    }

    static String toHex(byte[] hash) {
        BigInteger bi = new BigInteger(1, hash);
        String hex = bi.toString(16);
        int paddingLength = (hash.length * 2) - hex.length();
        if(paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }
}
