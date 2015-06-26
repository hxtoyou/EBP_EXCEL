/*
 * Created on 2005-10-20
 */
package com.ebills.product.dg.commons.utils;

import java.io.PrintStream;
import java.io.PrintWriter;


public class ApplicationException extends Exception {
	protected Throwable nestedThrowable = null;

	protected String message = "";

	protected int errorCode = 0;


	public ApplicationException() {
		super();
	}

	
	public ApplicationException(String msg) {
		super(msg);
		this.message = msg;
	}


	public ApplicationException(int code, String msg) {
		super(msg);
		this.message = msg;
		errorCode = code;
	}


	public ApplicationException(int code, Throwable nestedThrowable) {
		this.nestedThrowable = nestedThrowable;
		this.message = nestedThrowable.getMessage();
		errorCode = code;
	}


	public ApplicationException(Throwable nestedThrowable) {
		this.nestedThrowable = nestedThrowable;
		this.message = nestedThrowable.getMessage();
		errorCode = 0;
	}


	public ApplicationException(String msg, Throwable nestedThrowable) {
		super(msg);
		if (!this.message.equals(nestedThrowable.getMessage()))
			this.message = msg + " " + nestedThrowable.getMessage();
		else
			this.message = msg;
		this.nestedThrowable = nestedThrowable;
	}


	public String getMessage() {
		String msg;
		if (errorCode != 0)
			msg = message + " ������:" + errorCode;
		else
			msg = message;
		return msg;
	}


	public int getErrorCode() {
		return errorCode;
	}


	public void printStackTrace() {
		super.printStackTrace();
		if (this.nestedThrowable != null) {
			this.nestedThrowable.printStackTrace();
		}
	}

	public void printStackTrace(PrintStream inPrintStream) {
		super.printStackTrace(inPrintStream);
		if (this.nestedThrowable != null) {
			this.nestedThrowable.printStackTrace(inPrintStream);
		}
	}

	public void printStackTrace(PrintWriter inPrintWriter) {
		super.printStackTrace(inPrintWriter);
		if (this.nestedThrowable != null) {
			this.nestedThrowable.printStackTrace(inPrintWriter);
		}
	}

	public String toString() {
		String s = getClass().getName();
		String message = this.getMessage();
		return (message != null) ? (s + ": " + message) : s;
	}
}