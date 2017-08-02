package com.test.service;

import io.restassured.response.ResponseBody;

public interface IResponseAction {
    void doAction(ResponseBody body);
}
