Feature: Test commits

Scenario: Commits service returns consistent data
Given get commits for master
Then first commit title is - firstCommit
Then number if files is - 1
Then file #1 in commit contains Testcommit