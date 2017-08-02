package com.test;

import com.test.model.commits.CommitsResponse;
import com.test.model.pullRequest.PullRequestsResponse;
import com.test.service.GitHubService;
import com.test.service.IResponseObjectAction;

import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(SerenityRunner.class)
public class SerenityRestAssuredModelTest {

    @Steps
    GitHubService gitHubService;

    @Test
    public void testPullRequests(){
        gitHubService.executeListPullRequests()
                .verifyResponseObject("Title equals to - " + "testPullRequest",(IResponseObjectAction<PullRequestsResponse[]>)(resp)->{
                   assertEquals(resp[0].getTitle(), "testPullRequest");
                }, PullRequestsResponse[].class);


    }

    @Test
    public void testCommits(){
        gitHubService.executeListCommits("master")
                .verifyResponseObject("Commit message equals to - " + "firstCommit",(IResponseObjectAction<CommitsResponse>)(resp)->{
                    assertEquals(resp.getCommit().getMessage(), "firstCommit");
                }, CommitsResponse.class);


    }
}
