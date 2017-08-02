package com.test.service;

import com.google.gson.Gson;

import com.test.util.Config;

import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class GitHubService {

    private ResponseBody body = null;

    @Step
    public GitHubService executeListCommits(String branch){
        String res = executeGet(GitHubUrlFactory.getListCommitsUrl(Config.USER_NAME, Config.REPOSITORY_NAME, branch)).asString();

        return this;


    }
    @Step
    public GitHubService executeListPullRequests(){
        String res = executeGet(GitHubUrlFactory.getListPullRequestsUrl(Config.USER_NAME, Config.REPOSITORY_NAME)).asString();

       return this;
    }

    @Step
    public void verifyBody(String message, IResponseAction action){
        action.doAction(this.body);
    }

    @Step
    public <T> void verifyResponseObject(String message, IResponseObjectAction<T> action, Class resClass){
        action.doAction(deserializeResponseTo(this.body.asString(), resClass));
    }

    public Response getListPullRequestsQuery(){
        return givenAuthenticated()
                .get(GitHubUrlFactory.getListPullRequestsUrl(Config.USER_NAME, Config.REPOSITORY_NAME));


    }


    private RequestSpecification givenAuthenticated(){
        return SerenityRest
                .given().auth().preemptive().basic(Config.USER_NAME,
                Config.TOKEN);
    }

    private ResponseBody executeGet(String url){
        this.body = givenAuthenticated()
                .get(url)
                .body();

        return this.body;
    }

    private  <T> T deserializeResponseTo(String json, Class responseClass){
        Gson gson = new Gson();

        return (T)gson.fromJson(json, responseClass);
    }
}
