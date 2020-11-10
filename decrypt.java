package des.cfb.project;

import java.util.*;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class decrypt {
	void decryption(InputStream is, OutputStream os, Cipher decryptCipher) throws Exception {

		// create CipherOutputStream to decrypt the data using decryptCipher
		is = new CipherInputStream(is, decryptCipher);
		writeData(is, os);
	}
	// utility method to read data from input stream and write to output stream
			private static void writeData(InputStream is, OutputStream os) throws Exception {
				try{
					byte[] buf = new byte[1024];
					int numRead = 0;
					// read and write operation
					while ((numRead = is.read(buf)) >= 0) {
						os.write(buf, 0, numRead);
					}
					os.close();
					is.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
}
