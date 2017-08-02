package com.test.steps;

import com.google.gson.Gson;


import com.test.model.commits.CommitsResponse;
import com.test.model.pullRequest.PullRequestsResponse;
import com.test.service.GitHubUrlFactory;
import com.test.util.Config;


import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class GitHubSteps {

    private ResponseBody body = null;

    @Step
    public GitHubSteps executeListCommits(String branch){
        executeGet(GitHubUrlFactory.getListCommitsUrl(Config.USER_NAME, Config.REPOSITORY_NAME, branch)).asString();

        return this;


    }
    @Step
    public GitHubSteps executeListPullRequests(){
        executeGet(GitHubUrlFactory.getListPullRequestsUrl(Config.USER_NAME, Config.REPOSITORY_NAME)).asString();

       return this;
    }

    @Step
    public void verifyBody(String message, Consumer action){
        action.accept(this.body);
    }

    public <T> void verifyResponseObject(Consumer<T> action, Class resClass){
        action.accept(getResponseBodyAsObject(resClass));
    }

    private <T> T getResponseBodyAsObject(Class resClass) {
        return (T) deserializeResponseTo(this.body.asString(), resClass);
    }

    private CommitsResponse getCommitsResponse() {
        return deserializeResponseTo(this.body.asString(), CommitsResponse.class);
    }

    @Step
    public void verifyCommitResponseObject(Consumer<CommitsResponse> action){
        verifyResponseObject((Consumer<CommitsResponse>)(resp)->{
            action.accept(resp);
        }, CommitsResponse.class);
    }

    @Step
    public void verifyPullRequestResponseObject(Consumer<PullRequestsResponse[]> action){
        verifyResponseObject((Consumer<PullRequestsResponse[]>)(resp)->{
            action.accept(resp);
        }, PullRequestsResponse[].class);
    }

    public String getFileContent(int index){
        return givenAuthenticated()
                .get(getCommitsResponse().getFiles().get(index).getRawUrl())
                .body().asString();
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
