/**
 * Copyright 2020 JunjieZhou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.junjiezhou;

import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @author Junjie Zhou
 * @date Dec 17, 2020
 * @version 1.0
 */
public class AES {
    /**
     * secret key space used for initializing AES cipher
     */
    private SecretKeySpec secretKey;

    /**
     * Cipher object used for hold AES with ECB mode and zero padding. Hint: in
     * CryptoPAn, secret and to-be-encrypted vector are all 128bits, so no bit
     * padding is actually needed.
     */
    private Cipher cipher;

    /**
     * Initialize an AES object
     * 
     * @param secret secret key used for AES cipher
     * @throws InvalidKeyException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     */
    public AES(byte[] secret) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        secretKey = new SecretKeySpec(secret, "AES");
        cipher = Cipher.getInstance("AES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
    }

    /**
     * Encrypt a byte vector and return the result.
     * 
     * @param toEncrypt the byte vector needed to be encrypted
     * @return encrypted result of toEncrypt
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    protected byte[] encrypt(byte[] toEncrypt) throws IllegalBlockSizeException, BadPaddingException {
        return cipher.doFinal(toEncrypt);
    }
}
