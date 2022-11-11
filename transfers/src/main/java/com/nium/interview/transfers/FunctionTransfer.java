package com.nium.interview.transfers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class FunctionTransfer {

    private static int max;
    
    public static ArrayList<Transfer> formatTransfers(String[] transferReading)
					throws NumberFormatException, ParseException {
		ArrayList<Transfer> transfersArray = new ArrayList<>();

		for(String transf: transferReading){
			String[] array = transf.split(", ");
			Transfer a = new Transfer(Integer.parseInt(array[0].trim()), Integer.parseInt(array[1].trim()),Float.parseFloat(array[2].trim()),
					new SimpleDateFormat("dd/MM/yyyy").parse(array[3].trim()), Integer.parseInt(array[4].trim()));
			
					transfersArray.add(a);
		}
		return transfersArray;
	}


	public static void outputResults(Pair highest, HashMap<Integer, Float> balance, HashMap<Integer, Integer> usedSource) {
		String newLine = System.getProperty("line.separator");

		System.out.println(newLine + "#Balances");
		balance.entrySet().forEach(input -> System.out.println(input.getKey() + " " + input.getValue()));

		System.out.println(newLine + "#Bank Account with highest balance" + newLine + highest.getKey() + newLine);

		max = usedSource.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
		//int maxval = usedSource.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getValue();
		System.out.println("#Frequently used source bank account" + newLine + max);
	}

    public static int getMax() {
        return max;
    }
}
