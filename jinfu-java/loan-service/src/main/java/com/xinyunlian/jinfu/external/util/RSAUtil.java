package com.xinyunlian.jinfu.external.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/*****
 * RSA签名工具类
 * @author Yasong
 *
 */
public class RSAUtil {
	private static Logger log = Logger.getLogger(RSAUtil.class);
	private static RSAUtil instance;

	public static RSAUtil getInstance() {
		if (instance == null)
			return new RSAUtil();
		return instance;
	}

	/*****
	 *
	 * 公钥、私钥文件生成
	 *
	 * @param keyPath：保存文件的路径
	 * @param keyFlag：文件名前缀
	 */
	private void generateKeyPair(String key_path, String name_prefix) {
		KeyPairGenerator keygen = null;
		try {
			keygen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e1) {
			log.error(e1.getMessage());
		}
		SecureRandom secrand = new SecureRandom();
		secrand.setSeed("3500".getBytes());
		keygen.initialize(1024, secrand);
		KeyPair keys = keygen.genKeyPair();
		PublicKey pubkey = keys.getPublic();
		PrivateKey prikey = keys.getPrivate();

		String pubKeyStr = new String(org.apache.commons.codec.binary.Base64.encodeBase64(pubkey.getEncoded()));
		String priKeyStr = new String(org.apache.commons.codec.binary.Base64
				.encodeBase64(org.apache.commons.codec.binary.Base64.encodeBase64(prikey.getEncoded())));
		File file = new File(key_path);
		if (!file.exists()) {
			file.mkdirs();
		}

		try {
			FileOutputStream fos = new FileOutputStream(new File(key_path + name_prefix + "_RSAKey_private.txt"));
			fos.write(priKeyStr.getBytes());
			fos.close();

			fos = new FileOutputStream(new File(key_path + name_prefix + "_RSAKey_public.txt"));
			fos.write(pubKeyStr.getBytes());
			fos.close();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/*****
	 *
	 * 读取密钥文件内容
	 *
	 * @param key_file:文件路径
	 * @return
	 */
	private static String getKeyContent(String key_file) {
		File file = new File(key_file);
		BufferedReader br = null;
		InputStream ins = null;
		StringBuffer sReturnBuf = new StringBuffer();
		try {
			ins = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
			String readStr = null;
			readStr = br.readLine();
			while (readStr != null) {
				sReturnBuf.append(readStr);
				readStr = br.readLine();
			}
		} catch (IOException e) {
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();
					br = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (ins != null) {
				try {
					ins.close();
					ins = null;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sReturnBuf.toString();
	}

	/*****
	 *
	 * RSA签名处理：生成签名结果
	 *
	 * @param prikeyvalue：RSA私钥
	 * @param sign_str：待签名字符串源内容
	 * @return
	 */
	public static String sign(String prikeyvalue, String sign_str) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64Util.byteToBase64Decoding(prikeyvalue));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey myprikey = keyf.generatePrivate(priPKCS8);

			Signature signet = Signature.getInstance("MD5withRSA");
			signet.initSign(myprikey);
			signet.update(sign_str.getBytes("UTF-8"));
			byte[] signed = signet.sign();

			return new String(org.apache.commons.codec.binary.Base64.encodeBase64(signed));
		} catch (Exception e) {
			log.error("签名失败," + e.getMessage());
		}
		return null;
	}

	/*****
	 *
	 * RSA签名校验验证
	 *
	 * @param pubkeyvalue：RSA公钥
	 * @param sign_str：待签名字符串源内容
	 * @param signed_str：RSA签名结果
	 * @return
	 */
	public static boolean checksign(String pubkeyvalue, String sign_str, String signed_str) {
		try {
			X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(Base64Util.byteToBase64Decoding(pubkeyvalue));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec);
			byte[] signed = Base64Util.byteToBase64Decoding(signed_str);
			Signature signetcheck = Signature.getInstance("MD5withRSA");
			signetcheck.initVerify(pubKey);
			signetcheck.update(sign_str.getBytes("UTF-8"));
			return signetcheck.verify(signed);
		} catch (Exception e) {
			log.error("签名验证异常," + e.getMessage());
		}
		return false;
	}

}
