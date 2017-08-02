Feature: Pull requests
    
Scenario: Pull request returns consistent data
 Given invoke list pull requests service
 Then pull request #1 contains title - testPullRequest
 Then pull request #1 has state open
 Then pull request #1 contains user login - denis-sklyarov