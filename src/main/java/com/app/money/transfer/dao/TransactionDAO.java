package com.app.money.transfer.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.json.JSONObject;

import com.app.money.transfer.exception.InsufficientAccountBalanceException;
import com.app.money.transfer.exception.InvalidAccountIdException;
import com.app.money.transfer.exception.InvalidUserIdException;
import com.app.money.transfer.exception.UncaughtException;
import com.app.money.transfer.model.BankDetails;
import com.app.money.transfer.model.ErrorMessage;
import com.app.money.transfer.model.TransactionDetails;
import com.app.money.transfer.model.UserTransaction;

public class TransactionDAO {

	static String LOGPREFIX = "TransactionDAO |";

	static BankDetails bDetails = new BankDetails("800120678", "800");
	static BankDetails bDetails1 = new BankDetails("800121679", "800");
	static BankDetails bDetails2 = new BankDetails("900122680", "900");

	static ConcurrentHashMap<String, BankDetails> userIdChashMap = new ConcurrentHashMap<>();
	static ConcurrentHashMap<String, BigDecimal> bankBalance = new ConcurrentHashMap<>();
	static MultiValuedMap<String, TransactionDetails> transactionMap = new HashSetValuedHashMap<>();

	TransactionDetails tDetailsObj = null;

	public static String initializeDAO() {

		LOGPREFIX = LOGPREFIX + " initializeDAO";

		try {
			// Load users userIdChashMap<userid, BankDetails>
			userIdChashMap.put("800120", bDetails);
			userIdChashMap.put("800121", bDetails1);
			userIdChashMap.put("900122", bDetails2);

			// Load bank balance into users accounts amountChashMap<accountid, amount>
			bankBalance.put("800120678", new BigDecimal(5000.00));
			bankBalance.put("800121679", new BigDecimal(15000.00));
			bankBalance.put("900122680", new BigDecimal(25000.00));

			System.out.println(LOGPREFIX + " |userIdChashMap::" + userIdChashMap.size());
			System.out.println(LOGPREFIX + " |amountChashMap::" + bankBalance.size());

		} catch (Exception e) {
			System.out.println(LOGPREFIX + "Exception::" + e);
		}
		return "DB Initialized";
	}

	public TransactionDetails initiateTransfer(UserTransaction userTransactionDetails) {

		LOGPREFIX = LOGPREFIX + " initiateTransfer";
		String transactionStatus = "Failure";
		try {

			JSONObject requestObj = new JSONObject(userTransactionDetails);
			System.out.println(LOGPREFIX + " |Transaction request details::" + requestObj);

			String beneficiaryAccountId = userTransactionDetails.getBeneficiaryAccountId();
			String accountId = userTransactionDetails.getAccountId();
			BigDecimal transferAmt = new BigDecimal(userTransactionDetails.getAmount());

			if(userIdChashMap.get(userTransactionDetails.getUserId()) == null) {
				throw new InvalidUserIdException("INVALID_USER_ID");
			}
			
			if (bankBalance.containsKey(beneficiaryAccountId)) {

				if (bankBalance.get(accountId).compareTo(transferAmt) == 1) {

					// Debit money from payee account
					BigDecimal balance = bankBalance.get(accountId);
					System.out.println(LOGPREFIX + " |currentBalance::" + balance);

					BigDecimal currentBalance = balance.subtract(transferAmt);
					System.out.println(LOGPREFIX + " |updated currentBalance::" + currentBalance);

					bankBalance.put(accountId, currentBalance);

					// Credit money to beneficiary account
					BigDecimal beneficiaryAccBalance = bankBalance.get(beneficiaryAccountId);
					System.out.println(LOGPREFIX + " |beneficiaryAccBalance::" + beneficiaryAccBalance);

					BigDecimal updatedBalance = beneficiaryAccBalance.add(transferAmt);
					System.out.println(LOGPREFIX + " |updated beneficiaryAccBalance::" + updatedBalance);

					bankBalance.put(beneficiaryAccountId, updatedBalance);

					String transactionId = String.valueOf(transactionMap.size() + 1);
					String transactionDate = new Date().toString();
					String transactionReason = userTransactionDetails.getReason();
					transactionStatus = "Success";

					tDetailsObj = new TransactionDetails(accountId, beneficiaryAccountId, transactionId,
							transferAmt.toString(), transactionDate, transactionReason, transactionStatus,
							currentBalance.toString());

					transactionMap.put(accountId, tDetailsObj);
					JSONObject transactionRespObj = new JSONObject(tDetailsObj);
					System.out.println(LOGPREFIX + " |Transaction success response::" + transactionRespObj);

				} else {

					// construct transaction failure response
					String transactionId = String.valueOf(transactionMap.size() + 1);
					String transactionDate = new Date().toString();
					String transactionReason = userTransactionDetails.getReason();
					String balance = bankBalance.get(accountId).toString();
					transactionStatus = "INSUFFICIENT_BALANCE";

					tDetailsObj = new TransactionDetails(accountId, beneficiaryAccountId, transactionId,
							transferAmt.toString(), transactionDate, transactionReason, transactionStatus, balance);

					transactionMap.put(accountId, tDetailsObj);
					JSONObject transactionRespObj = new JSONObject(tDetailsObj);

					System.out.println(LOGPREFIX + " |Transaction failure response::" + transactionRespObj);

					// throw error
					throw new InsufficientAccountBalanceException("INSUFFICIENT_BALANCE");
				}

			} else {
				// throw invalid accountid & store transaction logs

				// construct transaction failure response
				String transactionId = String.valueOf(transactionMap.size() + 1);
				String transactionDate = new Date().toString();
				String transactionReason = userTransactionDetails.getReason();
				String balance = "0";

				if (bankBalance.get(accountId) == null) {
					throw new InvalidAccountIdException("INVALID_ACCOUNT_ID");
				} else {
					balance = bankBalance.get(accountId).toString();
				}
				transactionStatus = "INVALID_BENEFICIARY_ACCOUNT_ID";

				tDetailsObj = new TransactionDetails(accountId, beneficiaryAccountId, transactionId,
						transferAmt.toString(), transactionDate, transactionReason, transactionStatus, balance);

				transactionMap.put(accountId, tDetailsObj);
				JSONObject transactionRespObj = new JSONObject(tDetailsObj);

				System.out.println(LOGPREFIX + " |Transaction failure response::" + transactionRespObj);

				// throw error
				throw new InvalidAccountIdException("INVALID_BENEFICIARY_ACCOUNT_ID");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(LOGPREFIX + "Exception::" + e);
			
			if(!(e instanceof UncaughtException))
				throw e;
			else
				throw new UncaughtException(e.toString());
		}
		return tDetailsObj;
	}
}
