package com.xueyun.www.exception;

/**
 * @author 86135
 * 自定义异常
 */
public class CustomerErrorMsgException extends Exception {

    public CustomerErrorMsgException(){

    }

    public CustomerErrorMsgException(String errorMsg){
        super(errorMsg);
    }
}
