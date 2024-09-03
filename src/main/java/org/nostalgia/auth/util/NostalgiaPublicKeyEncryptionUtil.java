package org.nostalgia.auth.util;

import lombok.experimental.UtilityClass;

/**
 * Utility class for public key encryption and decryption operations.
 * This class internally utilizes the NostalgiaDataEncryptionUtil class for encryption and decryption using a salt-based approach.
 */
@UtilityClass
public class NostalgiaPublicKeyEncryptionUtil {

    /**
     * The prefix of the salt used for encryption and decryption operations.
     */
    private static final String PREFIX_OF_SALT = "cExiWUJBSVFTbHVwOXFXSkxSTDhsOGRDVkh1VkxyQ0E=";

    /**
     * The postfix of the salt used for encryption and decryption operations.
     */
    private static final String POSTFIX_OF_SALT = "SlhaNTl3eFBsWTJEWkNSWDdlZFRlaFhPaFhZRzlBN1Q=";

    /**
     * Encrypts the given raw data using the NostalgiaDataEncryptionUtil with the specified salt.
     *
     * @param rawData the raw data to be encrypted
     * @return the encrypted data
     */
    public static String encrypt(String rawData) {
        return NostalgiaDataEncryptionUtil.encrypt(rawData, PREFIX_OF_SALT, POSTFIX_OF_SALT);
    }

    /**
     * Decrypts the given encrypted data using the NostalgiaDataEncryptionUtil with the specified salt.
     *
     * @param encryptedData the encrypted data to be decrypted
     * @return the decrypted data
     */
    public static String decrypt(String encryptedData) {
        return NostalgiaDataEncryptionUtil.decrypt(encryptedData, PREFIX_OF_SALT, POSTFIX_OF_SALT);
    }

}