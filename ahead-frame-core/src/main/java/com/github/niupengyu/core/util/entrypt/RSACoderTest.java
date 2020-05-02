package com.github.niupengyu.core.util.entrypt;
	
import java.util.Map;

/**
 * 
 * @author 梁栋
 * @version 1.0
 * @since 1.0
 */
public class RSACoderTest {
	private static String publicKey;
	private static String privateKey;
	
	public static void main(String[] args) throws Exception {

		Map<String, Object> keyMap = RSACoder.initKey();

		publicKey = RSACoder.getPublicKey(keyMap);
		privateKey = RSACoder.getPrivateKey(keyMap);
		System.err.println("公钥: \n\r" + publicKey);
		System.err.println("私钥： \n\r" + privateKey);
	
		
		
		///--------------------

		System.err.println("公钥加密——私钥解密");
		String inputStr = "abc";
		byte[] data = inputStr.getBytes();

		byte[] encodedData = RSACoder.encryptByPublicKey(data, publicKey);

		byte[] decodedData = RSACoder.decryptByPrivateKey(encodedData,
				privateKey);

		String outputStr = new String(decodedData);
		System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
//		assertEquals(inputStr, outputStr);

		
		
		///--------------------

		System.err.println("私钥加密——公钥解密");
		inputStr = "sign";
		data = inputStr.getBytes();

		encodedData = RSACoder.encryptByPrivateKey(data, privateKey);

		decodedData = RSACoder
				.decryptByPublicKey(encodedData, publicKey);

		outputStr = new String(decodedData);
		System.err.println("加密前: " + inputStr + "\n\r" + "解密后: " + outputStr);
//		assertEquals(inputStr, outputStr);

		System.err.println("私钥签名——公钥验证签名");
		// 产生签名
		String sign = RSACoder.sign(encodedData, privateKey);
		System.err.println("签名:\r" + sign);

		// 验证签名
		boolean status = RSACoder.verify(encodedData, publicKey, sign);
		System.err.println("状态:\r" + status);
//		assertTrue(status);
		keyMap=DSACoder.initKey("12314123123");
		System.out.println(DSACoder.getPrivateKey(keyMap));
		System.out.println(DSACoder.getPublicKey(keyMap));
		System.out.println(DSACoder.sign( inputStr.getBytes(), DSACoder.getPrivateKey(keyMap)));
		System.out.println(DSACoder.verify(inputStr.getBytes(), DSACoder.getPublicKey(keyMap), DSACoder.sign( inputStr.getBytes(), DSACoder.getPrivateKey(keyMap))));
	}
	
}