package des.cfb.project;

import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DES_CFB{
	private static Cipher encryptCipher;
	private static Cipher decryptCipher;
	private static final byte[] iv = { 11, 22, 33, 44, 99, 88, 77, 66 };

	public static void main(String[] args) throws Exception{
  try{
	File f1 = new File("Message.txt");
	File f2 = new File("Alice.txt");
	File f3 = new File("Bob.txt");
	if (f1.createNewFile()) {
        System.out.println("File created: " + f1.getName());
      } 
	if (f2.createNewFile()) {
        System.out.println("File created: " + f2.getName());
      } 
	if (f3.createNewFile()) {
        System.out.println("File created: " + f3.getName());
      } 
	}
	catch (Exception ex) {
	      System.out.println("An error occurred.");
	      ex.printStackTrace();
	}
  Scanner in = new Scanner(System.in);
  System.out.print("Enter Text : ");
  String str = in.nextLine();
  System.out.println("Choose Option :\n1.Encryption\n2.Decryption");
  int a = in.nextInt();
  if(a==1)
  {
  try {
		FileWriter myWriter = new FileWriter("Message.txt");
	      myWriter.write(str);
	      myWriter.close();
	    } 
	catch (Exception exc) {
	      System.out.println("An error occurred.");
	      exc.printStackTrace();
	    }
	String clearTextFile = "Message.txt";
	String cipherTextFile = "Alice.txt";
	String clearTextNewFile = "Bob.txt";
		try {
			String st = "94926483";
			//create SecretKey using KeyGenerator
			SecretKey key = KeyGenerator.getInstance("DES").generateKey();
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(st.getBytes());
			
			String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
			System.out.println("Key : "+encodedKey);
			
			// get Cipher instance and initiate in encrypt mode
			encryptCipher = Cipher.getInstance("DES/CFB/PKCS5Padding");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			
			encrypt enc = new encrypt();
			// method to encrypt clear text file to encrypted file
			enc.encryption(new FileInputStream(clearTextFile), new FileOutputStream(cipherTextFile), encryptCipher);
			String data = ""; 
			try { 
				data = new String(Files.readAllBytes(Paths.get("Alice.txt")));
				} 	
			catch (IOException e) { 
				e.printStackTrace(); 
				} 
			System.out.println("Encrypted Text : "+data);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
  else if(a==2)
	  {
	  System.out.print("Enter Key (12 Characters) : ");
	  String str1 = in.next();
		String clearTextFile = "Message.txt";
		String cipherTextFile = "Alice.txt";
		String clearTextNewFile = "Bob.txt";
	  try {
			FileWriter myWriter = new FileWriter("Alice.txt");
		      myWriter.write(str);
		      myWriter.close();
		    } 
		catch (Exception exc) {
		      System.out.println("An error occurred.");
		      exc.printStackTrace();
		    }
		clearTextFile = "Message.txt";
		cipherTextFile = "Alice.txt";
		clearTextNewFile = "Bob.txt";
		
			try {
				  /* Decodes a Base64 encoded String into a byte array */
				  byte[] decodedKey = Base64.getDecoder().decode(str1);

				  /* Constructs a secret key from the given byte array */
				  SecretKey key = new SecretKeySpec(decodedKey,0, decodedKey.length, "DES");
				
				String st = "94926483";
				AlgorithmParameterSpec paramSpec = new IvParameterSpec(st.getBytes());
				
				// get Cipher instance and initiate in decrypt mode
				decryptCipher = Cipher.getInstance("DES/CFB/PKCS5Padding");
				decryptCipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

				decrypt dec = new decrypt();
				// method to decrypt encrypted file to clear text file
				dec.decryption(new FileInputStream(cipherTextFile), new FileOutputStream(clearTextNewFile), decryptCipher);
				String data = ""; 
				try { 
					data = new String(Files.readAllBytes(Paths.get("Bob.txt")));
					} 	
				catch (IOException e) { 
					e.printStackTrace(); 
					} 
				System.out.println("Decrypted Text : "+data);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
  		else
  		{
  			System.out.println("Invalid Choice");
  		}
	}
}
