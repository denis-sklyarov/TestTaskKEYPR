package com.test.junit;

import com.test.model.commits.CommitsResponse;
import com.test.model.pullRequest.PullRequestsResponse;
import com.test.steps.GitHubSteps;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

@RunWith(SerenityRunner.class)
public class SerenityRestAssuredModelTest {

    @Steps
    GitHubSteps gitHubSteps;

    @Test
    public void testPullRequests(){
        gitHubSteps.executeListPullRequests()
                .verifyPullRequestResponseObject((Consumer<PullRequestsResponse[]>)(resp)->{
                    assertEquals("Title equals to - " + "testPullRequest", resp[0].getTitle(), "testPullRequest");
                    assertEquals("Pull request status", resp[0].getState(), "open");
                    assertEquals("USer login in first pull request", resp[0].getUser().getLogin(), "denis-sklyarov");
                });


    }

    @Test
    public void testCommits(){
        String content = "Testcommit";
        Integer number = 1;
        Integer index = 1;


        gitHubSteps.executeListCommits("master")
                .verifyCommitResponseObject((Consumer<CommitsResponse>)(resp)->{
                    assertEquals("Commit message", resp.getCommit().getMessage(), "firstCommit");
                    assertEquals("number if files", number, new Integer(resp.getFiles().size()));
                    assertEquals("Content in file #1", content, gitHubSteps.getFileContent(index - 1));
                });


    }
}
