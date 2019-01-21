package com.app.money.transfer.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONObject;

import com.app.money.transfer.exception.InvalidUserIdException;
import com.app.money.transfer.exception.UncaughtException;
import com.app.money.transfer.model.AccountBalance;
import com.app.money.transfer.model.BankDetails;

public class AccountBalanceDAO {

	static String LOGPREFIX = "AccountBalanceDAO |";

	public ArrayList<AccountBalance> getAccountBalance(String userid) {

		LOGPREFIX = LOGPREFIX + " getAccountBalance";
		ArrayList<AccountBalance> accountBalanceArr = new ArrayList<AccountBalance>();
		
		
		try {

			if (InitializeDB.userIdChashMap.get(userid) == null) {

				throw new InvalidUserIdException("INVALID_USER_ID");

			} else {

				// Fetch the account id for the given user
				ArrayList<BankDetails> bankDetails = InitializeDB.userIdChashMap.get(userid);
				
				Iterator<BankDetails> itr = bankDetails.iterator();
				while(itr.hasNext()) {
					BankDetails bDetailsTempObj = itr.next();
					String accountId = bDetailsTempObj.getAccountId();
					System.out.println(LOGPREFIX + " Account Id::" + accountId);

					// Once account ID is there , fetch the account balance
					BigDecimal accountBalance = InitializeDB.bankBalance.get(accountId);
					System.out.println(LOGPREFIX + " Account Balance::" + accountBalance);
					
					// Prepare the response
					AccountBalance accBalanceObj = new AccountBalance();
					accBalanceObj.setAccountId(accountId);
					accBalanceObj.setAccountbalance(accountBalance.toString());
					
					accountBalanceArr.add(accBalanceObj);
				}
				
				// print the response before return
				JSONObject accBalanceRespObj = new JSONObject(accountBalanceArr);
				System.out.println(LOGPREFIX + " |Account Balance response::" + accBalanceRespObj);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(LOGPREFIX + "Exception::" + e);

			if (!(e instanceof UncaughtException))
				throw e;
			else
				throw new UncaughtException(e.toString());
		}
		return accountBalanceArr;
	}
}
