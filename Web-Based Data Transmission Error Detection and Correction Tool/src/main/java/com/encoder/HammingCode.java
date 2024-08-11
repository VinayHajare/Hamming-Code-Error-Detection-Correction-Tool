package com.encoder;

import java.util.Arrays;
import java.util.List;

public class HammingCode {
	public static String generateHammingCode(String data) {
		int dataLength = data.length();
        int r = 0;

        // Calculate the number of parity bits required for data
        while (Math.pow(2, r) < (dataLength + r + 1)) {
            r++;
        }

        int hammingCodeLength = dataLength + r;
        char[] hammingCode = new char[hammingCodeLength];

        // Initialize parity bits as '?'
        Arrays.fill(hammingCode, '?');

        // Place the data bits in their respective positions
        int j = 0;
        for (int i = 0; i < hammingCodeLength; i++) {
            if (Math.pow(2, j) - 1 == i) {
                j++;
            } else {
                hammingCode[i] = data.charAt(data.length() - dataLength);
                dataLength--;
            }
        }

        // Calculate parity bits
        for (int i = 0; i < r; i++) {
            int position = (int) Math.pow(2, i) - 1;
            int parity = 0;

            for (int k = position; k < hammingCodeLength; k += (position + 1) * 2) {
                for (int l = k; l < k + position + 1 && l < hammingCodeLength; l++) {
                    if (hammingCode[l] == '1') {
                        parity = parity ^ 1;
                    }
                }
            }

            hammingCode[position] = (parity == 0) ? '0' : '1';
        }
     
		return new String(hammingCode);
	}
	
	public static String detectAndCorrectHammingCode(String hammingCode, List<String> logCollector) {
		int r = 0;
		int length = hammingCode.length();
		
		// Calculate the number of parity bits
		while(Math.pow(2, r) < length) {
			r++;
		}
		
		int errorPosition = 0;
		
		// Check parity bits
		for(int i = 0; i < r; i++) {
			int position = (int) Math.pow(2, i) - 1;
			int parity = 0;
			
			for(int k = position; k < length; k += (position + 1)*2) {
				for(int l = k; l < k + position + 1 && l < length; l++) {
					if(hammingCode.charAt(l) == '1') {
						parity = parity ^ 1;
					}
				}
			}
			
			if(parity != 0) {
				errorPosition += position + 1;
			}
		}
		
		// correct the error if found
		if(errorPosition > 0) {
			// Error Occurred
			logCollector.add("Error Detected At "+errorPosition+" And Corrected");
			char[] correctedCode = hammingCode.toCharArray();
			correctedCode[errorPosition - 1] = (correctedCode[errorPosition - 1] == '0') ? '1' : '0';
 			return new String(correctedCode);
		}else {
			// No Error 
			return hammingCode;
		}
	}
}
