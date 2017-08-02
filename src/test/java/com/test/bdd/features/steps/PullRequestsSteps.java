package com.test.bdd.features.steps;

import com.test.model.pullRequest.PullRequestsResponse;
import com.test.steps.GitHubSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Steps;

import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class PullRequestsSteps {
    @Steps
    private GitHubSteps gitHubSteps;

    @Given("invoke list pull requests service")
    public void givenInvokePullRequestsService(){
        gitHubSteps.executeListPullRequests();
    }

    @Then("pull request #(.*) contains title - (.*)")
    public void thenPullRequestTitleIs(Integer index, String title){
        gitHubSteps.verifyPullRequestResponseObject((Consumer<PullRequestsResponse[]>)(resp)->{
            assertEquals(resp[index - 1].getTitle(), title);
        });
    }

    @Then("pull request #(.*) has state (.*)")
    public void thenPullRequestHasState(Integer index, String state){
        gitHubSteps.verifyPullRequestResponseObject((Consumer<PullRequestsResponse[]>)(resp)->{
            assertEquals(resp[index - 1].getState(), state);
        });
    }

    @Then("pull request #(.*) contains user login - (.*)")
    public void thenPullRequestContainsUserLogin(Integer index, String content){
        gitHubSteps.verifyPullRequestResponseObject((Consumer<PullRequestsResponse[]>)(resp)->{
            assertEquals(resp[0].getUser().getLogin(), content);
        });
    }
}
