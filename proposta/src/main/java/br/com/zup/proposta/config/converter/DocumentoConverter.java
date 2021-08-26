package br.com.zup.proposta.config.converter;

import javax.crypto.*;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Converter
public class DocumentoConverter implements AttributeConverter<String, String> {

    private static SecretKey key;

    static {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(128);
            key = generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipherText = cipher.doFinal(attribute.getBytes());
            return Base64.getEncoder().encodeToString(cipherText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(dbData));
            return new String(plainText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException  |
                IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
