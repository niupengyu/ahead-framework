package com.github.niupengyu.core.util.entrypt;

import com.github.niupengyu.core.util.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;


/**
 * AES 算法 对称加密，密码学中的高级加密标准 2005年成为有效标准 
 * @author stone
 * @date 2014-03-10 06:49:19
 */
public class AES {
	//private static Cipher cipher;
	private static final String KEY_ALGORITHM = "AES";
	private static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";
	/*
	 * 
	 */
	private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
	/*
	 * AES/CBC/NoPadding 要求
	 * 密钥必须是16位的；Initialization vector (IV) 必须是16位
	 * 待加密内容的长度必须是16的倍数，如果不是16的倍数，就会出如下异常：
	 * javax.crypto.IllegalBlockSizeException: Input length not multiple of 16 bytes
	 * 
	 *  由于固定了位数，所以对于被加密数据有中文的, 加、解密不完整
	 *  
	 *  可 以看到，在原始数据长度为16的整数n倍时，假如原始数据长度等于16*n，则使用NoPadding时加密后数据长度等于16*n，
	 *  其它情况下加密数据长 度等于16*(n+1)。在不足16的整数倍的情况下，假如原始数据长度等于16*n+m[其中m小于16]，
	 *  除了NoPadding填充之外的任何方 式，加密数据长度都等于16*(n+1).
	 */
	private static final String CIPHER_ALGORITHM_CBC_NoPadding = "AES/CBC/NoPadding";
	
	//private static SecretKey secretKey;

	private static final Logger logger= LoggerFactory.getLogger(AES.class);

	//private String key="1233456";

	//private int keySize=128;


	public static void main(String[] args) throws Exception {
		AES aes=new AES();
		String key="1234567899456123";
//		String str="a*jal)k32J8czx囙国为国宽";
		String str="asdasdad12345678";
		int keySize=128;
		String iv="1234567899456123";
		String encrypt=aes.method1ENC(str,key,keySize);
		System.out.println(encrypt);
		System.out.println(aes.method1DEC(encrypt,key,keySize));

		encrypt=aes.method2ENC(str,key,keySize);
		System.out.println(encrypt);
		System.out.println(aes.method2DEC(encrypt,key,keySize));

		encrypt=aes.method3ENC(str,iv,key,keySize);
		System.out.println(encrypt);
		System.out.println(aes.method3DEC(encrypt,iv,key,keySize));

		encrypt=aes.method4ENC(str,iv,key,keySize);
		System.out.println(encrypt);
		System.out.println(aes.method4DEC(encrypt,iv,key,keySize));


		
	}



	private String encCipher(String model,String str,String key,int keySize) throws Exception {
		Cipher cipher = Cipher.getInstance(model);
		//KeyGenerator 生成aes算法密钥
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		byte[] keyBytes = key.getBytes();
		kg.init(keySize, new SecureRandom(keyBytes));
		SecretKey secretKey = kg.generateKey();
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);//使用加密模式初始化 密钥
		byte[] encrypt = cipher.doFinal(str.getBytes()); //按单部分操作加密或解密数据，或者结束一个多部分操作。
		return Hex.byte2HexStr(encrypt);
	}

	private String decCipher(String model,String encrypt,String key,int keySize) throws Exception {
		Cipher cipher = Cipher.getInstance(model);
		//KeyGenerator 生成aes算法密钥
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		byte[] keyBytes = key.getBytes();
		kg.init(keySize, new SecureRandom(keyBytes));
		SecretKey secretKey = kg.generateKey();
		cipher.init(Cipher.DECRYPT_MODE, secretKey);//使用加密模式初始化 密钥
		byte[] decrypt = cipher.doFinal(Hex.hexStringToBytes(encrypt));
		return new String(decrypt);

	}

	public  String encCipher(String model,String str,String iv,String key,int keySize) throws Exception {
		Cipher cipher = Cipher.getInstance(model);
		//KeyGenerator 生成aes算法密钥
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		byte[] keyBytes = key.getBytes();
		kg.init(keySize, new SecureRandom(keyBytes));
		//System.out.println("密钥的长度为：" + secretKey.getEncoded().length);
		SecretKey secretKey = kg.generateKey();
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(getIV(iv)));//使用加密模式初始化 密钥
		byte[] encrypt = cipher.doFinal(str.getBytes()); //按单部分操作加密或解密数据，或者结束一个多部分操作。
		return Hex.byte2HexStr(encrypt);

	}

	public String decCipher(String model,String encrypt,String iv,String key,int keySize) throws Exception {
		Cipher cipher = Cipher.getInstance(model);
		//KeyGenerator 生成aes算法密钥
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		byte[] keyBytes = key.getBytes();
		kg.init(keySize, new SecureRandom(keyBytes));
		//System.out.println("密钥的长度为：" + secretKey.getEncoded().length);
		SecretKey secretKey = kg.generateKey();
		cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(getIV(iv)));//使用解密模式初始化 密钥
		byte[] decrypt = cipher.doFinal(Hex.hexStringToBytes(encrypt));
		return new String(decrypt);
	}

	/**
	 * 使用AES 算法 加密，默认模式  AES/ECB
	 */
	public String method1ENC(String str,String key,int keySize) throws Exception {
		return encCipher(KEY_ALGORITHM,str,key,keySize);

	}

	public String method1DEC(String encrypt,String key,int keySize) throws Exception {
		return decCipher(KEY_ALGORITHM,encrypt,key,keySize);
	}

	/**
	 * 使用AES 算法 加密，默认模式 AES/ECB/PKCS5Padding
	 */
	public  String method2ENC(String str,String key,int keySize) throws Exception {
		return encCipher(CIPHER_ALGORITHM_ECB,str,key,keySize);
	}


	public String method2DEC(String encrypt,String key,int keySize) throws Exception {

		return decCipher(CIPHER_ALGORITHM_ECB,encrypt,key,keySize);
	}

	public  byte[] getIV(String iv) {
		//String iv = "1234567812345678"; //IV length: must be 16 bytes long
		return iv.getBytes();
	}
	
	/**
	 * 使用AES 算法 加密，默认模式 AES/CBC/PKCS5Padding
	 */
	public  String method3ENC(String str,String iv,String key,int keySize) throws Exception {
		return encCipher(CIPHER_ALGORITHM_CBC,str,iv,key,keySize);

	}

	public String method3DEC(String encrypt,String iv,String key,int keySize) throws Exception {
		return decCipher(CIPHER_ALGORITHM_CBC,encrypt,iv,key,keySize);
	}

	/**
	 * 使用AES 算法 加密，默认模式 AES/CBC/NoPadding  参见上面对于这种mode的数据限制
	 */
	public String method4ENC(String str,String iv,String key,int keySize) throws Exception {
		return encCipher(CIPHER_ALGORITHM_CBC_NoPadding,str,iv,key,keySize);
	}

	public String method4DEC(String encrypt,String iv,String key,int keySize) throws Exception {
		return decCipher(CIPHER_ALGORITHM_CBC_NoPadding,encrypt,iv,key,keySize);
	}

}
