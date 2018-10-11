package com.webstore.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PLConstants {
	
	public final static String PL = "PL";
	
	public final static Map<String, String> mapOfPLStates = new HashMap<String, String>() {
		{
			put("DOL", "Dolnośląskie");
			put("KUJ", "Kujawsko-pomorskie");
			put("LBE", "Lubelskie");
            put("LBU", "Lubuskie");
            put("LDZ", "Łódzkie");
            put("MAL", "Małopolskie");
            put("MAZ", "Mazowieckie");
            put("OPO", "Opolskie");
            put("PDK", "Podkarpackie");
            put("PDL", "Podlaskie");
            put("POM", "Pomorskie");
            put("SLK", "Śląskie");
            put("SWI", "Świętokrzyskie");
            put("WAR", "Warmińsko-mazurskie");
            put("WLK", "Wielkopolskie");
            put("ZAC", "Zachodniopomorskie");
		}
	};
	
	public final static List<String> listOfPLStatesCode = new ArrayList<>(mapOfPLStates.keySet());
	public final static List<String> listOfPLStatesName = new ArrayList<>(mapOfPLStates.values());

}
