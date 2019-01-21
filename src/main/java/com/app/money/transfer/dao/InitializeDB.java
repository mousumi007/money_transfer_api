package com.app.money.transfer.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;

import com.app.money.transfer.model.BankDetails;
import com.app.money.transfer.model.TransactionDetails;

public class InitializeDB {

	static String LOGPREFIX = "InitializeDB |";

	static ArrayList<BankDetails> bankDetailsArrUserOne = new ArrayList<BankDetails>();
	static ArrayList<BankDetails> bankDetailsArrUserTwo = new ArrayList<BankDetails>();
	static ArrayList<BankDetails> bankDetailsArrUserThree = new ArrayList<BankDetails>();

	static ConcurrentHashMap<String, ArrayList<BankDetails>> userIdChashMap = new ConcurrentHashMap<>();
	static ConcurrentHashMap<String, BigDecimal> bankBalance = new ConcurrentHashMap<>();
	static MultiValuedMap<String, TransactionDetails> transactionMap = new HashSetValuedHashMap<>();

	public static String initializeDB() {

		LOGPREFIX = LOGPREFIX + " initializeDAO";

		try {

			// User 1 bank details
			bankDetailsArrUserOne.add(new BankDetails("800120678", "800"));
			bankDetailsArrUserOne.add(new BankDetails("800120679", "800"));

			// User 2 bank details
			bankDetailsArrUserTwo.add(new BankDetails("800120680", "800"));
			bankDetailsArrUserTwo.add(new BankDetails("800121679", "900"));

			// User 3 bank details
			bankDetailsArrUserThree.add(new BankDetails("800121680", "900"));

			// Load users userIdChashMap<userid, BankDetails>
			userIdChashMap.put("u800120", bankDetailsArrUserOne);
			userIdChashMap.put("u800121", bankDetailsArrUserTwo);
			userIdChashMap.put("u900122", bankDetailsArrUserThree);

			// Load bank balance into users accounts amountChashMap<accountid, amount>
			// User 1 bank balance
			bankBalance.put("800120678", new BigDecimal(5000.00));
			bankBalance.put("800120679", new BigDecimal(15000.00));

			// User 2 bank balance
			bankBalance.put("800120680", new BigDecimal(25000.00));
			bankBalance.put("800121679", new BigDecimal(5000.00));

			// User 2 bank balance
			bankBalance.put("800121680", new BigDecimal(15000.00));

			System.out.println(LOGPREFIX + " |userIdChashMap::" + userIdChashMap.size());
			System.out.println(LOGPREFIX + " |amountChashMap::" + bankBalance.size());

		} catch (Exception e) {
			System.out.println(LOGPREFIX + "Exception::" + e);
		}
		return "DB Initialized";
	}
}
