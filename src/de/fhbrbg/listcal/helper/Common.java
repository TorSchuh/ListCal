package de.fhbrbg.listcal.helper;

public class Common {

	public static String[] concatArrays(String[]... arrays) {
	    int lengh = 0;
	    for (String[] array : arrays) {
	        lengh += array.length;
	    }
	    String[] result = new String[lengh];
	    int pos = 0;
	    for (String[] array : arrays) {
	        for (String element : array) {
	            result[pos] = element;
	            pos++;
	        }
	    }
	    return result;
	}
	
}
