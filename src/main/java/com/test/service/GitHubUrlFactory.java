package com.test.service;

import com.test.util.Config;

public class GitHubUrlFactory {
    public static String getListCommitsUrl(String userName, String repoName, String branch){

        return String.format("%s%s/%s/%s/commits/%s", Config.BASE_URL,
                                                        Config.REPOS_ENDPOINT,
                                                        userName,
                                                        repoName,
                                                        branch);
    }

    public static String getListPullRequestsUrl(String userName, String repoName){

        return String.format("%s%s/%s/%s/pulls", Config.BASE_URL,
                Config.REPOS_ENDPOINT,
                userName,
                repoName);
    }
}
