package com.paad.amconsoft.remote;

public interface ApiResult<T> {

    void onSuccess(T result);
    void onFail();

}
