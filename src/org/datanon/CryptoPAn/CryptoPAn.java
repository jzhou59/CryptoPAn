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

package org.datanon.CryptoPAn;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * CryptoPAn class for ip address anonymization, the class provides not only
 * CryptoPAn itself, but only the combination of CryptoPAn and times. Also, this
 * class provides both String format ip address and integer format ip address
 * for the convenience of computation.
 * 
 * @author Junjie Zhou
 * @date Dec 17, 2020
 * @version 1.0
 */
public class CryptoPAn {

    /**
     * AES block cipher for encryption.
     */
    private AES aes;

    /**
     * The byte vector used for padding 0~31 bits to 32 bits.
     */
    private byte[] CryptoPAnPad;

    /**
     * The vector is precomputed for padding, after which shifts are skipped and
     * only exclusive-or operator exists inside the CryptoPAn.
     */
    private int[][] CryptoPAnPadGenerator = new int[32][2];

    /**
     * Initialize CryptoPAn object with String type key.
     * 
     * @param key String type, 32 single-byte words in UTF8.
     * @throws CryptoPAnRunTimeException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public CryptoPAn(String key) throws CryptoPAnRunTimeException, IllegalBlockSizeException, BadPaddingException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        this(key.getBytes());
    }

    /**
     * Initialize CryptoPAn, after this step block cipher and padding are prepared.
     * 
     * @param key Byte vector with its length being 32.
     * @throws CryptoPAnRunTimeException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     */
    public CryptoPAn(byte[] key) throws CryptoPAnRunTimeException, IllegalBlockSizeException, BadPaddingException,
            InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        if (key == null || key.length != 32) {
            throw new CryptoPAnRunTimeException("Key used for CryptoPAn should be exact 32 bytes length.");
        }
        aes = new AES(Arrays.copyOfRange(key, 0, 16));
        CryptoPAnPad = aes.encrypt(Arrays.copyOfRange(key, 16, 32));
        if (CryptoPAnPad == null) {
            throw new CryptoPAnRunTimeException("AES encryption exception in CryptoPAn.");
        }
        byte[] first4BytesPad = Arrays.copyOf(CryptoPAnPad, 4);
        int padGenerator = (first4BytesPad[0] << 24) | (first4BytesPad[1] << 24 >>> 8)
                | (first4BytesPad[2] << 24 >>> 16) | (first4BytesPad[3] << 24 >>> 24);
        CryptoPAnPadGenerator[0][0] = 0x00000000;
        CryptoPAnPadGenerator[0][1] = padGenerator;
        for (int i = 1; i < 32; i++) {
            CryptoPAnPadGenerator[i][0] = 0xffffffff >>> (32 - i) << (32 - i);
            CryptoPAnPadGenerator[i][1] = (~CryptoPAnPadGenerator[i][0]) & padGenerator;
        }
    }

    /**
     * Positively CryptoPAn given ip(in the form of 32 bits integer) according to
     * given times.
     * 
     * @param ip    Given ip in the form of 32 bits integer.
     * @param times Given times for CryptoPAn.
     * @return Anonymized ip address(in the form of 32 bits integer) after given
     *         times' CryptoPAn.
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public int forward(int ip, int times) throws IllegalBlockSizeException, BadPaddingException {
        if (times <= 0) {
            return ip;
        }
        if (times != 1) {
            ip = forward(ip, times - 1);
        }
        int[] toEncrypts = new int[32];
        for (int i = 0; i < 32; i++) {
            toEncrypts[i] = (ip & CryptoPAnPadGenerator[i][0]) | CryptoPAnPadGenerator[i][1];
        }
        int EXOR = 0x00000000, i = 0;
        for (int t : toEncrypts) {
            byte[] toEncrypt = new byte[16];
            toEncrypt[0] = (byte) (t >>> 24);
            toEncrypt[1] = (byte) (t >>> 16);
            toEncrypt[2] = (byte) (t >>> 8);
            toEncrypt[3] = (byte) (t);
            System.arraycopy(Arrays.copyOfRange(CryptoPAnPad, 4, 16), 0, toEncrypt, 4, 12);
            int mostByte = 0x00000001 & (aes.encrypt(toEncrypt)[0] >>> 7);
            EXOR |= (mostByte << (31 - i));
            i++;
        }
        ip ^= EXOR;
        return ip;
    }

    /**
     * Reversly CryptoPAn given ip(in the form of 32 bits integer) according to
     * given times.
     * 
     * @param ip    Given ip in the form of 32 bits integer.
     * @param times Given times for CryptoPAn.
     * @return Ip address(in the form of 32 bits integer) after given times' reverse
     *         CryptoPAn.
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public int reverse(int ip, int times) throws IllegalBlockSizeException, BadPaddingException {
        if (times <= 0)
            return ip;
        if (times != 1)
            ip = reverse(ip, times - 1);
        int former = 0xFFFFFFFF;
        for (int i = 0; i < 32; i++) {
            int t = former & CryptoPAnPadGenerator[i][0] | CryptoPAnPadGenerator[i][1];
            byte[] toEncrypt = new byte[32];
            toEncrypt[0] = (byte) (t >>> 24);
            toEncrypt[1] = (byte) (t >>> 16);
            toEncrypt[2] = (byte) (t >>> 8);
            toEncrypt[3] = (byte) (t);
            System.arraycopy(Arrays.copyOfRange(CryptoPAnPad, 4, 16), 0, toEncrypt, 4, 12);
            int flipByte = (0x00000001 & (aes.encrypt(toEncrypt)[0] >>> 7)) << (31 - i);
            if (((former ^ flipByte) >>> (31 - i) & 0x00000001) != (ip >>> (31 - i) & 0x00000001))
                former ^= (0x00000001 << 31 - i);
        }
        return former;
    }

    /**
     * Positively CryptoPAn given ip(in the form of String).
     * 
     * @param ip Given ip in the form of String.
     * @return Anonymized ip address(in the form of string).
     * @throws CryptoPAnRunTimeException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public String forward(String ip) throws CryptoPAnRunTimeException, IllegalBlockSizeException, BadPaddingException {
        String[] ipInterval = ip.split("\\.");
        if (ipInterval.length != 4) {
            throw new CryptoPAnRunTimeException("IP is not valid.");
        }
        int ipInt = (Integer.parseInt(ipInterval[0]) << 24 & 0xff000000)
                | (Integer.parseInt(ipInterval[1]) << 16 & 0x00ff0000)
                | (Integer.parseInt(ipInterval[2]) << 8 & 0x0000ff00) | (Integer.parseInt(ipInterval[3]) & 0x000000ff);
        int destIp = forward(ipInt, 1);
        return (((byte) (destIp >>> 24)) & 0x000000FF) + "." + (((byte) (destIp >>> 16)) & 0x000000FF) + "."
                + (((byte) (destIp >>> 8)) & 0x000000FF) + "." + (((byte) (destIp)) & 0x000000FF);
    }

    /**
     * Reversly CryptoPAn given ip(in the form of String) to its original
     * appearance.
     * 
     * @param ip Given ip in the form of String.
     * @return Original ip address(in the form of String).
     * @throws CryptoPAnRunTimeException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public String reverse(String ip) throws CryptoPAnRunTimeException, IllegalBlockSizeException, BadPaddingException {
        String[] ipInterval = ip.split("\\.");
        if (ipInterval.length != 4) {
            throw new CryptoPAnRunTimeException("IP is not valid.");
        }
        int ipInt = (Integer.parseInt(ipInterval[0]) << 24 & 0xff000000)
                | (Integer.parseInt(ipInterval[1]) << 16 & 0x00ff0000)
                | (Integer.parseInt(ipInterval[2]) << 8 & 0x0000ff00) | (Integer.parseInt(ipInterval[3]) & 0x000000ff);
        int originIp = reverse(ipInt, 1);
        return (((byte) (originIp >>> 24)) & 0x000000FF) + "." + (((byte) (originIp >>> 16)) & 0x000000FF) + "."
                + (((byte) (originIp >>> 8)) & 0x000000FF) + "." + (((byte) (originIp)) & 0x000000FF);
    }
}
