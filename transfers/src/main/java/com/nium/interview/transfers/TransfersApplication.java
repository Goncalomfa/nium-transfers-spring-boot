package com.nium.interview.transfers;

import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Log4j2
public class TransfersApplication implements CommandLineRunner {

	private HashMap<Integer, Float> balance = new HashMap<>();
	private HashMap<Integer, Integer> usedSource = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(TransfersApplication.class, args);
	}

	@Override
	public void run(final String... args) {

		final URL file = getClass().getClassLoader().getResource("transfers.txt");
		try {

			String[] transferReading = Files.readAllLines(Path.of(file.toURI())).stream()
				.map(String::trim)
				.filter(t -> Character.isDigit(t.charAt(0)))
				.toArray(String[]::new);
			
			ArrayList<Transfer> transfersArray = FunctionTransfer.formatTransfers(transferReading);

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
			
			//results
			FunctionTransfer.outputResults(highest, balance, usedSource);

		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
