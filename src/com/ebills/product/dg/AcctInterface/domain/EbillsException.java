package com.ebills.product.dg.AcctInterface.domain;
/**
 * 该类定义了ebills RemoteException。
 */
public class EbillsException extends Exception {

    /** 嵌套的异常 */
    private Throwable nestedThrowable = null;
    private String message;
    private int errCode=0;
    /**
     * 构造方法
     */
    public EbillsException() {
        super();
    }

    /**
     * 构造方法
     * @param msg 异常信息
     */
    public EbillsException(String msg) {
        super(msg);
        this.message=msg;
    }
    public EbillsException(int errCode,String msg) {
        super(msg);
        this.message=msg;
        this.errCode=errCode;
    }

    /**
     * 构造方法
     * @param nestedThrowable 嵌套的异常
     */
    public EbillsException(Throwable nestedThrowable) {
    	super(nestedThrowable);
        this.nestedThrowable = nestedThrowable;
        message=nestedThrowable.getMessage();
    }

    /**
     * 构造方法
     * @param msg 异常信息
     * @param nestedThrowable 嵌套的异常
     */
    public EbillsException(String msg, Throwable nestedThrowable) {
        super(msg);
        this.message=msg;
        this.nestedThrowable = nestedThrowable;
    }
    /**
     * 打获取异常信息
     */
    public String getMessage() {
        if(errCode==0){
            return message;
        }else{
            return errCode+":"+message;
        }
    }
 }
