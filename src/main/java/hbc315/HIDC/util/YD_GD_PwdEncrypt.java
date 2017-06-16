package hbc315.HIDC.util;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import javax.crypto.Cipher;
import org.apache.commons.lang.StringUtils;

public class YD_GD_PwdEncrypt {

	 private static String RSAKeyStore = "RSAKey.txt";

	 /**
	 * * 生成密钥对 *
	 * @return KeyPair *
	 * @throws EncryptException
	 */
	 public static KeyPair generateKeyPair(String basePath) throws Exception {
	 try {
	 KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
	 new org.bouncycastle.jce.provider.BouncyCastleProvider());
	 //大小
	 final int KEY_SIZE = 1024;
	 keyPairGen.initialize(KEY_SIZE, new SecureRandom());
	 KeyPair keyPair = keyPairGen.generateKeyPair();
	 saveKeyPair(keyPair, basePath);
	 return keyPair;
	 } catch (Exception e) {
	 throw new Exception(e.getMessage());
	 }
	 }

	 /**
	 * 获取密钥对
	 * @return
	 * @throws Exception
	 */
	 public static KeyPair getKeyPair(String basePath) throws Exception {
	 FileInputStream fis = new FileInputStream(StringUtils.isNotBlank(basePath) ? (basePath + RSAKeyStore) : RSAKeyStore);
	 ObjectInputStream oos = new ObjectInputStream(fis);
	 KeyPair kp = (KeyPair) oos.readObject();
	 oos.close();
	 fis.close();
	 return kp;
	 }

	 /**
	 * 保存密钥
	 * @param kp
	 * @throws Exception
	 */
	 public static void saveKeyPair(KeyPair kp, String basePath) throws Exception {
	 FileOutputStream fos = new FileOutputStream(StringUtils.isNotBlank(basePath) ? (basePath + RSAKeyStore) : RSAKeyStore);
	 ObjectOutputStream oos = new ObjectOutputStream(fos);
	 // 生成密钥
	 oos.writeObject(kp);
	 oos.close();
	 fos.close();
	 }

	 /**
	 * * 生成公钥 *
	 * @param modulus *
	 * @param publicExponent *
	 * @return RSAPublicKey *
	 * @throws Exception
	 */
	 public static RSAPublicKey generateRSAPublicKey(byte[] modulus,
	 byte[] publicExponent) throws Exception {
	 KeyFactory keyFac = null;
	 try {
	 keyFac = KeyFactory.getInstance("RSA",
	 new org.bouncycastle.jce.provider.BouncyCastleProvider());
	 } catch (NoSuchAlgorithmException ex) {
	 throw new Exception(ex.getMessage());
	 }
	 RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(
	 modulus), new BigInteger(publicExponent));
	 try {
	 return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
	 } catch (InvalidKeySpecException ex) {
	 throw new Exception(ex.getMessage());
	 }
	 }

	 /**
	 * * 生成私钥 *
	 * @param modulus *
	 * @param privateExponent *
	 * @return RSAPrivateKey *
	 * @throws Exception
	 */
	 public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus,
	 byte[] privateExponent) throws Exception {
	 KeyFactory keyFac = null;
	 try {
	 keyFac = KeyFactory.getInstance("RSA",
	 new org.bouncycastle.jce.provider.BouncyCastleProvider());
	 } catch (NoSuchAlgorithmException ex) {
	 throw new Exception(ex.getMessage());
	 }
	 RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(
	 modulus), new BigInteger(privateExponent));
	 try {
	 return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
	 } catch (InvalidKeySpecException ex) {
	 throw new Exception(ex.getMessage());
	 }
	 }

	 /**
	 * * 加密 *
	 * @param key
	 * 加密的密钥 *
	 * @param data
	 * 待加密的明文数据 *
	 * @return 加密后的数据 *
	 * @throws Exception
	 */
	 public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
	 try {
	 Cipher cipher = Cipher.getInstance("RSA",
	 new org.bouncycastle.jce.provider.BouncyCastleProvider());
	 cipher.init(Cipher.ENCRYPT_MODE, pk);
	 // 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
	 int blockSize = cipher.getBlockSize();
	 // 加密块大小为127
	 // byte,加密后为128个byte;因此共有2个加密块，第一个127
	 // byte第二个为1个byte
	 int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
	 int leavedSize = data.length % blockSize;
	 int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
	 byte[] raw = new byte[outputSize * blocksSize];
	 int i = 0;
	 while (data.length - i * blockSize > 0) {
	 if (data.length - i * blockSize > blockSize) {
	 cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
	 } else {
	 cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
	 }
	 i++;
	 }
	 return raw;
	 } catch (Exception e) {
	 throw new Exception(e.getMessage());
	 }
	 }

	 /**
	 * * 解密 *
	 *
	 * @param key
	 * 解密的密钥 *
	 * @param raw
	 * 已经加密的数据 *
	 * @return 解密后的明文 *
	 * @throws Exception
	 */
	 @SuppressWarnings("static-access")
	 public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {
	 try {
	 Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
	 cipher.init(cipher.DECRYPT_MODE, pk);
	 int blockSize = cipher.getBlockSize();
	 ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
	 int j = 0;
	 while (raw.length - j * blockSize > 0) {
	 bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
	 j++;
	 }
	 return bout.toByteArray();
	 } catch (Exception e) {
	 throw new Exception(e.getMessage());
	 }
	 }

	 /**
	 * 解密方法
	 * paramStr ->密文
	 * basePath ->RSAKey.txt所在的文件夹路径
	 **/
	 public static String decryptStr(String paramStr, String basePath) throws Exception{
	 byte[] en_result = new BigInteger(paramStr, 16).toByteArray();
	 byte[] de_result = decrypt(getKeyPair(basePath).getPrivate(), en_result);
	 StringBuffer sb = new StringBuffer();
	 sb.append(new String(de_result));
	 //返回解密的字符串
	 return sb.reverse().toString();
	 }

	
	public static void main(String[] args) {
		

	}

}
