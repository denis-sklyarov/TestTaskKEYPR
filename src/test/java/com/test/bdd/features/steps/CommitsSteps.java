package com.test.bdd.features.steps;


import com.test.model.commits.CommitsResponse;
import com.test.steps.GitHubSteps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Steps;

import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class CommitsSteps {

    @Steps
    private GitHubSteps gitHubSteps;

    @Given("get commits for (.*)")
    public void givenInvokeCommitsService(String branch){
        gitHubSteps.executeListCommits(branch);
    }

    @Then("first commit title is - (.*)")
    public void thenFirstCommitTitleIs(String title){
        gitHubSteps.verifyCommitResponseObject((Consumer<CommitsResponse>)(resp)->{
            assertEquals(resp.getCommit().getMessage(), title);
        });
    }

    @Then("number if files is - (.*)")
    public void thenNumberOfFilesIs(Integer number){
        gitHubSteps.verifyCommitResponseObject((Consumer<CommitsResponse>)(resp)->{
            assertEquals(number, new Integer(resp.getFiles().size()));
        });
    }

    @Then("file #(.*) in commit contains (.*)")
    public void thenFileContentIs(Integer index, String content){
        gitHubSteps.verifyCommitResponseObject((Consumer<CommitsResponse>)(resp)->{
            assertEquals(content, gitHubSteps.getFileContent(index - 1));
        });
    }


}
