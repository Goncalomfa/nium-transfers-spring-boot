package com.nium.interview.transfers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TransfersApplicationTests {
	
	private static Transfer transfer;

	@BeforeAll
	static void inita(){
		try {
			transfer = new Transfer(2222, 4444, (float)2.4,
				new SimpleDateFormat("dd/MM/yyyy").parse("10/08/2055"), 8888);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void testGetDate() {
		Date tdate = transfer.getDate();
		Date mdate;
		try {
			mdate = new SimpleDateFormat("dd/MM/yyyy").parse("10/08/1099");
			assertNotEquals(tdate, mdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testGetSource() {
		int tsource = transfer.getSource();
		int msource = 2222;
		assertEquals(tsource, msource);
	}

	@Test
	void testSetDestination() {
		int tdestiny = 99;
		transfer.setDestination(tdestiny);
		assertEquals(transfer.getDestination(), tdestiny);
	}

	@Test
	void testSetAmount() {
		float tamount = (float)9.9;
		float previous = transfer.getAmount();
		transfer.setAmount(tamount);
		assertNotEquals(transfer.getAmount(), previous);
	}
	
	@Test
	void testGetKey() {
		Pair test = new Pair(7, 8);
		test.setValue(1);
		assertEquals(test.getKey(), 7);
	}

	@Test
	void testformattingTransfers() throws IOException, URISyntaxException {
		final URL file = getClass().getClassLoader().getResource("transfers.txt");
		String[] transferReading = Files.readAllLines(Path.of(file.toURI())).stream()
				.map(String::trim)
				.filter(t -> Character.isDigit(t.charAt(0)))
				.toArray(String[]::new);
		try {
			ArrayList<Transfer> array = FunctionTransfer.formatTransfers(transferReading);
			assertEquals(array.size(), 6);
		} catch (NumberFormatException | ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testOutputResults() throws NumberFormatException, ParseException, IOException, URISyntaxException {
		final URL file = getClass().getClassLoader().getResource("transfers.txt");
		String[] transferReading = Files.readAllLines(Path.of(file.toURI())).stream()
				.map(String::trim)
				.filter(t -> Character.isDigit(t.charAt(0)))
				.toArray(String[]::new);
			
			ArrayList<Transfer> transfersArray = FunctionTransfer.formatTransfers(transferReading);
			HashMap<Integer, Float> balance = new HashMap<>();
			HashMap<Integer, Integer> usedSource = new HashMap<>();

			Pair highest = new Pair
				(transfersArray.get(0).getDestination(),transfersArray.get(0).getAmount());
			
			for(Transfer transfer: transfersArray) {
				if(transfer.getSource() != 0 ) {
					balance.merge(transfer.getSource(), -transfer.getAmount(), (oldVal, newVal)-> oldVal + newVal);
					usedSource.merge(transfer.getSource(), 1, (oldVal, newVal) -> oldVal + newVal);
				}
				balance.merge(transfer.getDestination(), transfer.getAmount(), (oldVal, newVal)-> oldVal + newVal);
				
				if(balance.get(transfer.getDestination()) > balance.get(highest.getKey())) {
					highest.setValue(balance.get(transfer.getDestination()));
					highest.setKey(transfer.getDestination());
				}
			}
		FunctionTransfer.outputResults(highest, balance, usedSource);
		assertEquals(FunctionTransfer.getMax(), 112233);
	}


}
