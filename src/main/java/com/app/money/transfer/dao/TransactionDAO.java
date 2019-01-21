package com.app.money.transfer.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONObject;

import com.app.money.transfer.exception.InsufficientAccountBalanceException;
import com.app.money.transfer.exception.InvalidAccountIdException;
import com.app.money.transfer.exception.InvalidUserIdException;
import com.app.money.transfer.exception.UncaughtException;
import com.app.money.transfer.model.BankDetails;
import com.app.money.transfer.model.TransactionDetails;
import com.app.money.transfer.model.UserTransaction;

public class TransactionDAO {

	static String LOGPREFIX = "TransactionDAO |";

	public TransactionDetails initiateTransfer(UserTransaction userTransactionDetails) {

		LOGPREFIX = LOGPREFIX + " initiateTransfer";
		TransactionDetails tDetailsObj = null;
		String transactionStatus = "Failure";

		try {

			JSONObject requestObj = new JSONObject(userTransactionDetails);
			System.out.println(LOGPREFIX + " |Transaction request details::" + requestObj);

			String beneficiaryAccountId = userTransactionDetails.getBeneficiaryAccountId();
			String accountId = userTransactionDetails.getAccountId();
			BigDecimal transferAmt = new BigDecimal(userTransactionDetails.getAmount());

			if (InitializeDB.userIdChashMap.get(userTransactionDetails.getUserId()) == null) {
				throw new InvalidUserIdException("INVALID_USER_ID");
			}

			ArrayList<BankDetails> accountDetails = InitializeDB.userIdChashMap.get(userTransactionDetails.getUserId());
			
			Boolean accFlag = false;
			for (int i = 0; i < accountDetails.size(); i++) {
				if (accountDetails.get(i).getAccountId().equalsIgnoreCase(accountId))
				{
					accFlag = true;
				}
			}
			
			if (accFlag == false || InitializeDB.bankBalance.get(accountId) == null) {
				throw new InvalidAccountIdException("INVALID_ACCOUNT_ID");
			}

			if (InitializeDB.bankBalance.containsKey(beneficiaryAccountId)) {

				if (InitializeDB.bankBalance.get(accountId).compareTo(transferAmt) == 1
						|| InitializeDB.bankBalance.get(accountId).compareTo(transferAmt) == 0) {

					// Debit money from payee account
					BigDecimal balance = InitializeDB.bankBalance.get(accountId);
					System.out.println(LOGPREFIX + " |currentBalance::" + balance);

					BigDecimal currentBalance = balance.subtract(transferAmt);
					System.out.println(LOGPREFIX + " |updated currentBalance::" + currentBalance);

					InitializeDB.bankBalance.put(accountId, currentBalance);

					// Credit money to beneficiary account
					BigDecimal beneficiaryAccBalance = InitializeDB.bankBalance.get(beneficiaryAccountId);
					System.out.println(LOGPREFIX + " |beneficiaryAccBalance::" + beneficiaryAccBalance);

					BigDecimal updatedBalance = beneficiaryAccBalance.add(transferAmt);
					System.out.println(LOGPREFIX + " |updated beneficiaryAccBalance::" + updatedBalance);

					InitializeDB.bankBalance.put(beneficiaryAccountId, updatedBalance);

					String transactionId = String.valueOf(InitializeDB.transactionMap.size() + 1);
					String transactionDate = new Date().toString();
					String transactionReason = userTransactionDetails.getReason();
					transactionStatus = "Success";

					tDetailsObj = new TransactionDetails(accountId, beneficiaryAccountId, transactionId,
							transferAmt.toString(), transactionDate, transactionReason, transactionStatus,
							currentBalance.toString());

					InitializeDB.transactionMap.put(accountId, tDetailsObj);
					JSONObject transactionRespObj = new JSONObject(tDetailsObj);
					System.out.println(LOGPREFIX + " |Transaction success response::" + transactionRespObj);

				} else {

					// construct transaction failure response
					String transactionId = String.valueOf(InitializeDB.transactionMap.size() + 1);
					String transactionDate = new Date().toString();
					String transactionReason = userTransactionDetails.getReason();
					String balance = InitializeDB.bankBalance.get(accountId).toString();
					transactionStatus = "INSUFFICIENT_BALANCE";

					tDetailsObj = new TransactionDetails(accountId, beneficiaryAccountId, transactionId,
							transferAmt.toString(), transactionDate, transactionReason, transactionStatus, balance);

					InitializeDB.transactionMap.put(accountId, tDetailsObj);
					JSONObject transactionRespObj = new JSONObject(tDetailsObj);

					System.out.println(LOGPREFIX + " |Transaction failure response::" + transactionRespObj);

					// throw error
					throw new InsufficientAccountBalanceException("INSUFFICIENT_BALANCE");
				}

			} else {
				// throw invalid accountid & store transaction logs

				// construct transaction failure response
				String transactionId = String.valueOf(InitializeDB.transactionMap.size() + 1);
				String transactionDate = new Date().toString();
				String transactionReason = userTransactionDetails.getReason();
				String balance = "0";

				if (InitializeDB.bankBalance.get(accountId) == null) {
					throw new InvalidAccountIdException("INVALID_ACCOUNT_ID");
				} else {
					balance = InitializeDB.bankBalance.get(accountId).toString();
				}
				transactionStatus = "INVALID_BENEFICIARY_ACCOUNT_ID";

				tDetailsObj = new TransactionDetails(accountId, beneficiaryAccountId, transactionId,
						transferAmt.toString(), transactionDate, transactionReason, transactionStatus, balance);

				InitializeDB.transactionMap.put(accountId, tDetailsObj);
				JSONObject transactionRespObj = new JSONObject(tDetailsObj);

				System.out.println(LOGPREFIX + " |Transaction failure response::" + transactionRespObj);

				// throw error
				throw new InvalidAccountIdException("INVALID_BENEFICIARY_ACCOUNT_ID");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(LOGPREFIX + "Exception::" + e);

			if (!(e instanceof UncaughtException))
				throw e;
			else
				throw new UncaughtException(e.toString());
		}
		return tDetailsObj;
	}
}
