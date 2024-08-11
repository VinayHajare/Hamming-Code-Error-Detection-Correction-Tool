package com.encoder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Util {
	/**
	 * Encodes the whole line using Hamming Code
	 * @param String line
	 * @return String encoded
	 */
	public static String encodeLine(String line) {
		StringBuilder encoded = new StringBuilder();
		for(char c : line.toCharArray()) {
			encoded.append(encodeChar(c)).append(" ");
		}
		return encoded.toString().trim();
	}
	
	
	/**
	 * 
	 * Convert the character to binary and encode using Hamming Code
	 * @param char c
	 * @return String hammingCode
	 */
	private static String encodeChar(char c) {
		String binaryString = String.format("%7s", Integer.toBinaryString(c)).replace(' ', '0');
		return HammingCode.generateHammingCode(binaryString);
	}
	
	/**
	 * Return the file contains as a List<String>
	 * @param File file
	 * @return List<String> lines
	 * @throws IOException
	 */
	public static List<String> readLines(File file) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		List<String> lines = new ArrayList<>();
		String line = null;
		while((line = reader.readLine()) != null) {
			lines.add(line);
		}
		reader.close();
		return lines;
	}
	
	public static String decodeLine(String line, List<String> logCollector) {
		StringBuilder decoded = new StringBuilder();
		String[] encodedChars = line.split(" ");
		
		for(String encodedChar : encodedChars) {
			decoded.append(decodeChar(encodedChar, logCollector));
		}
		return decoded.toString();
	}
	
	private static char decodeChar(String encodedChar, List<String> logCollector) {
		// Decode the encoded binary string
		String binaryString = HammingCode.detectAndCorrectHammingCode(encodedChar, logCollector);
		
		// Remove the parity bits from the binary string as each power of 2 digit has only 1 bit set and other unset
		StringBuilder dataBits = new StringBuilder();
		for(int i = 0; i < binaryString.length(); i++) {
			if(!isParityBit(i)) {
				dataBits.append(binaryString.charAt(i));
			}
		}
		
		try {
	        int asciiValue = Integer.parseInt(dataBits.toString(), 2);
	        return (char) asciiValue;
	    } catch (NumberFormatException e) {
	        // Binary string does not represent a valid ASCII character
	        return '?';
	    }
	}
	
	public static String highlightParityBit(String binaryString) {
		StringBuilder highlightedString = new StringBuilder();

	    // Assuming parity bits are at positions 1, 2, 4, 8, etc. (1-based index)
	    for (int i = 0; i < binaryString.length(); i++) {
	        if (isParityBit(i)) {
	            highlightedString.append("*").append(binaryString.charAt(i)).append("*");
	        } else {
	            highlightedString.append(binaryString.charAt(i));
	        }
	    }
	    return highlightedString.toString();
	}
	// Helper function to check parity
	private static boolean isParityBit(int position) {
		return (position & (position + 1)) == 0;
	}
}
