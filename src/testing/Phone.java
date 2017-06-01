package testing;

import java.util.HashMap;
import java.util.Map.Entry;

public class Phone {
	private static HashMap<String, String> tMap;
	private String pNumRaw;
	private String type;
	private String pNumClean;
	private String typeCode;
	private String ext; 
		
	static {
		tMap = new HashMap<String, String>();
		tMap.put("Work", "WOR");
		tMap.put("FAX", "FAX");
		tMap.put("TYY", "TYY");
		tMap.put("Cell", "MOB");
		tMap.put("Home", "HOM");
		tMap.put("Other", "OTR");
	}
	
	/**
	 * Constructor that builds raw, stripped, and type
	 * @param number
	 * @param type plan name home, work, cell, fax, tyy, other
	 */
	public Phone(String number, String type){
		pNumRaw = number;
		this.type = type;
		typeCode = getPhoneCode(type);
		pNumClean = number.replaceAll("[^\\d]", "");
		
		if(number.length() != 10)
			System.err.println("Warning, invalid phone number given");
	}
	
	public Phone(String number, String type, String ext){
		pNumRaw = number;
		this.type = type;
		typeCode = getPhoneCode(type);
		this.ext = ext;
		pNumClean = number.replaceAll("[^\\d]", "");
		
		if(pNumClean.length() != 10)
			System.err.println("Warning, invalid phone number given");
		
		if(ext.length() <= 5)
			System.err.println("Warning, invalid phone extension given");
	}
	
	public String getRawNumber(){
		return pNumRaw;
	}
	
	public String getCleanNumber(){
		return pNumClean;
	}
	
	public String getExt(){
		return ext;
	}
	
	public void setExt(String ext){
		this.ext = ext;
	}
	
	public String getAreaCode(){
		return pNumClean.substring(0, 3);
	}
	
	public String getPrefix(){
		return pNumClean.substring(3, 6);
	}
	
	public String getLineCode(){
		return pNumClean.substring(6, 10);
	}
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns the code of the given phone type
	 * @param type Phone type
	 * @return code for phone type
	 */
	public static String getPhoneCode(String type){
		if(tMap.containsKey(type)){
			return tMap.get(type);
		} else {
			System.err.println("Phone code not found for " + type);
			return "NNF";
		}
	}
	
	/**
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}

	/**
	 * Returns the phone Type name given the code
	 * @param phoneCode the code to match to type
	 * @return the phone type in plain text
	 */
	public static String getPhoneType(String phoneCode){
		if(!tMap.containsValue(phoneCode)){//if not found
			System.err.println("County Name not found for code " + phoneCode + "\n");
		} else {//if value exists
			for(Entry<String, String> entry: tMap.entrySet()){
				if(entry.getValue().equalsIgnoreCase(phoneCode)){
					return entry.getKey();
				}
			}
		}
		return "Not Found"; //if not found, return this
	}
}
