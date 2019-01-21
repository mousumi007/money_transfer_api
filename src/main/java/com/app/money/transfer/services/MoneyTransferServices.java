package com.app.money.transfer.services;

import org.json.JSONObject;

import com.app.money.transfer.dao.TransactionDAO;
import com.app.money.transfer.exception.TransactionDeniedException;
import com.app.money.transfer.exception.UncaughtException;
import com.app.money.transfer.model.TransactionDetails;
import com.app.money.transfer.model.UserTransaction;

public class MoneyTransferServices {

	static String LOGPREFIX = "MoneyTransferServices |";

	public TransactionDetails moneyTransferService(UserTransaction transactionObj) {
		TransactionDetails tDetailsObj = new TransactionDetails();
		try {

			JSONObject requestObj = new JSONObject(transactionObj);
			System.out.println(LOGPREFIX + " |Transaction request details::" + requestObj);

			// check if accountId & beneficiaryAccountId is same or not
			if (transactionObj.getAccountId().equalsIgnoreCase(transactionObj.getBeneficiaryAccountId())) {
				
				throw new TransactionDeniedException("ACCOUNT_ID_SAME");
				
			} else {
				TransactionDAO moneyTransactionObj = new TransactionDAO();
				tDetailsObj = moneyTransactionObj.initiateTransfer(transactionObj);

				JSONObject transactionRespObj = new JSONObject(tDetailsObj);
				System.out.println(LOGPREFIX + " |Transaction response::" + transactionRespObj);
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
