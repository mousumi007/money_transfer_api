package com.app.money.transfer.dao;

import java.math.BigDecimal;

import org.json.JSONObject;

import com.app.money.transfer.exception.AccountBalanceException;
import com.app.money.transfer.exception.InvalidUserIdException;
import com.app.money.transfer.exception.UncaughtException;
import com.app.money.transfer.model.AccountBalance;
import com.app.money.transfer.model.BankDetails;

public class AccountBalanceDAO {

	static String LOGPREFIX = "AccountBalanceDAO |";

	public AccountBalance getAccountBalance(String userid) {

		LOGPREFIX = LOGPREFIX + " getAccountBalance";
		AccountBalance accountBalanceObj = new AccountBalance();

		try {

			if (TransactionDAO.userIdChashMap.get(userid) == null) {

				throw new InvalidUserIdException("INVALID_USER_ID");

			} else {

				// Fetch the account id for the given user
				BankDetails bankDetails = TransactionDAO.userIdChashMap.get(userid);
				String accountId = bankDetails.getAccountId();
				System.out.println(LOGPREFIX + " Account Id::" + accountId);

				if (TransactionDAO.bankBalance.get(accountId) == null) {

					throw new AccountBalanceException("BALANCE_RETRIEVAL_FAILURE");
					
				} else {
					// Once account ID is there , fetch the account balance
					BigDecimal accountBalance = TransactionDAO.bankBalance.get(accountId);
					System.out.println(LOGPREFIX + " Account Balance::" + accountBalance);

					// Prepare the response
					accountBalanceObj.setAccountId(accountId);
					accountBalanceObj.setAccountbalance(accountBalance.toString());

					// print the response before return
					JSONObject accBalanceRespObj = new JSONObject(accountBalanceObj);
					System.out.println(LOGPREFIX + " |Account Balance response::" + accBalanceRespObj);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(LOGPREFIX + "Exception::" + e);

			if (!(e instanceof UncaughtException))
				throw e;
			else
				throw new UncaughtException(e.toString());
		}
		return accountBalanceObj;
	}
}
