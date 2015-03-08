package net.justdoit.dexter.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

	public String transactionId;
	public String accountId;
	public String rawMerchant;
	public String merchant;
	public String isPending;
	
	public Date transactionTime;
	public double amount;
	public String categorization;
	
	public void setTransactionTime(String s) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		//2015-02-15T17:19:00.000
		//"yyyy-MM-dd'T'HH:mm:ss.SSSZ"	2001-07-04T12:08:56.235-0700
		/*try {
			transactionTime = df.parse(s);
		} catch (ParseException e) {
			String s2 = s.substring(0,s.length()-1);
			try {
				transactionTime = df.parse(s2);
			} catch (ParseException e2) {
				e2.printStackTrace();
			}
		}*/
		
		transactionTime = new Date();
	}

	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("TransactionTime " + transactionTime);
		sb.append("\tAmount " + amount);
		sb.append("\tCategorization " +categorization);
		
		sb.append("\tTransactionID " + transactionId);
		
		return sb.toString();
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public Date getTransactionTime() {
		return transactionTime;
	}

	public void setTransactionTime(Date transactionTime) {
		this.transactionTime = transactionTime;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getCategory() {
		return categorization;
	}

	public void setCategory(String categorization) {
		this.categorization = categorization;
	}
	
	
	
}
