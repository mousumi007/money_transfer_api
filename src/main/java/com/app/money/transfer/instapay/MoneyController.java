package com.app.money.transfer.instapay;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import com.app.money.transfer.exception.UncaughtException;
import com.app.money.transfer.model.AccountBalance;
import com.app.money.transfer.model.TransactionDetails;
import com.app.money.transfer.model.UserTransaction;
import com.app.money.transfer.services.AccountDetailsServices;
import com.app.money.transfer.services.MoneyTransferServices;

/**
 * Root resource (exposed at "accounts" path)
 */
@Path("/accounts")
public class MoneyController {

	static String LOGPREFIX = "MoneyController |";

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/{userid}")
	public AccountBalance userAccountBalanceDetails(@PathParam("userid") String userid) {

		LOGPREFIX = LOGPREFIX + " userAccountBalanceDetails";
		
		AccountBalance accountBalanceObj = new AccountBalance();
		
		try {
			System.out.println(LOGPREFIX + " |userid::" + userid);
			
			AccountDetailsServices serviceObj = new AccountDetailsServices();
			
			accountBalanceObj = serviceObj.accountDetailsServices(userid);
			
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

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/transfer")
	public TransactionDetails moneyTransfer(@Valid UserTransaction userTransactionDetails) {

		LOGPREFIX = LOGPREFIX + " moneyTransfer";
		TransactionDetails transactionResponse = new TransactionDetails();

		try {
			
			JSONObject requestObj = new JSONObject(userTransactionDetails);
			System.out.println(LOGPREFIX + " |request body::" + requestObj);

			MoneyTransferServices mtSrvObj = new MoneyTransferServices();
			transactionResponse = mtSrvObj.moneyTransferService(userTransactionDetails);

			JSONObject transactionRespObj = new JSONObject(transactionResponse);
			System.out.println(LOGPREFIX + " |Transaction response::" + transactionRespObj);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(LOGPREFIX + "Exception::" + e);

			if (!(e instanceof UncaughtException))
				throw e;
			else
				throw new UncaughtException(e.toString());
		}

		return transactionResponse;
	}

}
