package entity;

import java.io.Serializable;

/**
 *service层返回对象列表封装
 * @param <T>
 */
public class ServiceResult<T>  implements Serializable{

    private boolean success = false;

    private String code;

    private String message;

    private T result;

    public ServiceResult() {

    }

    public ServiceResult(boolean success, String code, String message, T result) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.result = result;
    }

    public ServiceResult(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public static <T> ServiceResult<T> success(T result) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = true;
        item.result = result;
        item.code = "0";
        item.message = "success";
        return item;
    }

    public static <T> ServiceResult<T> success(String msg,T result) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = true;
        item.result = result;
        item.code = "0";
        item.message = msg;
        return item;
    }

    public static <T> ServiceResult<T> success(String code,String msg,T result) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = true;
        item.result = result;
        item.code = code;
        item.message = msg;
        return item;
    }

    public static <T> ServiceResult<T> successMsg(String msg) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = true;
        item.code = "0";
        item.message = msg;
        return item;
    }


    public static <T> ServiceResult<T> failure(String errorCode, String errorMessage) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = false;
        item.code = errorCode;
        item.message = errorMessage;

        return item;
    }

    public static <T> ServiceResult<T> failure(String errorCode, String errorMessage, T result) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = false;
        item.code = errorCode;
        item.message = errorMessage;
        item.result = result;
        return item;
    }

    public static <T> ServiceResult<T> failureMsg(String errorMessage) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = false;
        item.code = "30000";
        item.message = errorMessage;
        return item;
    }

    public static <T> ServiceResult<T> failure(String errorCode) {
        ServiceResult<T> item = new ServiceResult<T>();
        item.success = false;
        item.code = errorCode;
        item.message = "failure";
        return item;
    }

    public boolean hasResult() {
        return result != null;
    }

    public boolean isSuccess() {
        return success;
    }

    public T getResult() {
        return result;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}


