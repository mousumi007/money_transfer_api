package com.app.money.transfer.services;

import org.json.JSONObject;

import com.app.money.transfer.dao.AccountBalanceDAO;
import com.app.money.transfer.dao.TransactionDAO;
import com.app.money.transfer.exception.UncaughtException;
import com.app.money.transfer.model.AccountBalance;

public class AccountDetailsServices {

	static String LOGPREFIX = "AccountDetailsServices |";

	public AccountBalance accountDetailsServices(String userid) {
		
		LOGPREFIX = LOGPREFIX + " accountDetailsServices";
		
		AccountBalance accountBalanceObj = new AccountBalance();
		
		try {

			System.out.println(LOGPREFIX + " |userid::" + userid);
			
			AccountBalanceDAO accountBalanceDAOObj = new AccountBalanceDAO();
			
			accountBalanceObj = accountBalanceDAOObj.getAccountBalance(userid);
			
			JSONObject accBalanceRespObj = new JSONObject(accountBalanceObj);
			System.out.println(LOGPREFIX + " |Account Balance response::" + accBalanceRespObj);
			
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
