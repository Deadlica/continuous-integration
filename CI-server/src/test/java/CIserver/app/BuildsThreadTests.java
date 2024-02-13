package CIserver.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.Mockito;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
class BuildsThreadTests {
/*
    @Test
    void testBuildThread() {
        String validRequestBody = "{\n" +
                "  \"ref\": \"refs/heads/8-compilation\",\n" +
                "  \"before\": \"0000000000000000000000000000000000000000\",\n" +
                "  \"after\": \"4ed227fa48fc9106d176ed74d797484d5fa24790\",\n" +
                "  \"repository\": {\n" +
                "    \"id\": 753136494,\n" +
                "    \"node_id\": \"R_kgDOLOPzbg\",\n" +
                "    \"name\": \"continuous-integration\",\n" +
                "    \"full_name\": \"Deadlica/continuous-integration\",\n" +
                "    \"private\": false,\n" +
                "    \"owner\": {\n" +
                "      \"name\": \"Deadlica\",\n" +
                "      \"email\": \"78732361+Deadlica@users.noreply.github.com\",\n" +
                "      \"login\": \"Deadlica\",\n" +
                "      \"id\": 78732361,\n" +
                "      \"node_id\": \"MDQ6VXNlcjc4NzMyMzYx\",\n" +
                "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/78732361?v=4\",\n" +
                "      \"gravatar_id\": \"\",\n" +
                "      \"url\": \"https://api.github.com/users/Deadlica\",\n" +
                "      \"html_url\": \"https://github.com/Deadlica\",\n" +
                "      \"followers_url\": \"https://api.github.com/users/Deadlica/followers\",\n" +
                "      \"following_url\": \"https://api.github.com/users/Deadlica/following{/other_user}\",\n" +
                "      \"gists_url\": \"https://api.github.com/users/Deadlica/gists{/gist_id}\",\n" +
                "      \"starred_url\": \"https://api.github.com/users/Deadlica/starred{/owner}{/repo}\",\n" +
                "      \"subscriptions_url\": \"https://api.github.com/users/Deadlica/subscriptions\",\n" +
                "      \"organizations_url\": \"https://api.github.com/users/Deadlica/orgs\",\n" +
                "      \"repos_url\": \"https://api.github.com/users/Deadlica/repos\",\n" +
                "      \"events_url\": \"https://api.github.com/users/Deadlica/events{/privacy}\",\n" +
                "      \"received_events_url\": \"https://api.github.com/users/Deadlica/received_events\",\n" +
                "      \"type\": \"User\",\n" +
                "      \"site_admin\": false\n" +
                "    },\n" +
                "    \"html_url\": \"https://github.com/Deadlica/continuous-integration\",\n" +
                "    \"description\": null,\n" +
                "    \"fork\": false,\n" +
                "    \"url\": \"https://github.com/Deadlica/continuous-integration\",\n" +
                "    \"forks_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/forks\",\n" +
                "    \"keys_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/keys{/key_id}\",\n" +
                "    \"collaborators_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/collaborators{/collaborator}\",\n" +
                "    \"teams_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/teams\",\n" +
                "    \"hooks_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/hooks\",\n" +
                "    \"issue_events_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/issues/events{/number}\",\n" +
                "    \"events_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/events\",\n" +
                "    \"assignees_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/assignees{/user}\",\n" +
                "    \"branches_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/branches{/branch}\",\n" +
                "    \"tags_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/tags\",\n" +
                "    \"blobs_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/git/blobs{/sha}\",\n" +
                "    \"git_tags_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/git/tags{/sha}\",\n" +
                "    \"git_refs_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/git/refs{/sha}\",\n" +
                "    \"trees_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/git/trees{/sha}\",\n" +
                "    \"statuses_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/statuses/{sha}\",\n" +
                "    \"languages_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/languages\",\n" +
                "    \"stargazers_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/stargazers\",\n" +
                "    \"contributors_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/contributors\",\n" +
                "    \"subscribers_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/subscribers\",\n" +
                "    \"subscription_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/subscription\",\n" +
                "    \"commits_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/commits{/sha}\",\n" +
                "    \"git_commits_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/git/commits{/sha}\",\n" +
                "    \"comments_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/comments{/number}\",\n" +
                "    \"issue_comment_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/issues/comments{/number}\",\n" +
                "    \"contents_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/contents/{+path}\",\n" +
                "    \"compare_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/compare/{base}...{head}\",\n" +
                "    \"merges_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/merges\",\n" +
                "    \"archive_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/{archive_format}{/ref}\",\n" +
                "    \"downloads_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/downloads\",\n" +
                "    \"issues_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/issues{/number}\",\n" +
                "    \"pulls_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/pulls{/number}\",\n" +
                "    \"milestones_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/milestones{/number}\",\n" +
                "    \"notifications_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/notifications{?since,all,participating}\",\n" +
                "    \"labels_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/labels{/name}\",\n" +
                "    \"releases_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/releases{/id}\",\n" +
                "    \"deployments_url\": \"https://api.github.com/repos/Deadlica/continuous-integration/deployments\",\n" +
                "    \"created_at\": 1707144106,\n" +
                "    \"updated_at\": \"2024-02-08T00:22:11Z\",\n" +
                "    \"pushed_at\": 1707406243,\n" +
                "    \"git_url\": \"git://github.com/Deadlica/continuous-integration.git\",\n" +
                "    \"ssh_url\": \"git@github.com:Deadlica/continuous-integration.git\",\n" +
                "    \"clone_url\": \"https://github.com/Deadlica/continuous-integration.git\",\n" +
                "    \"svn_url\": \"https://github.com/Deadlica/continuous-integration\",\n" +
                "    \"homepage\": \"https://ci-server.samflix.se/test\",\n" +
                "    \"size\": 70,\n" +
                "    \"stargazers_count\": 1,\n" +
                "    \"watchers_count\": 1,\n" +
                "    \"language\": \"Java\",\n" +
                "    \"has_issues\": true,\n" +
                "    \"has_projects\": true,\n" +
                "    \"has_downloads\": true,\n" +
                "    \"has_wiki\": true,\n" +
                "    \"has_pages\": false,\n" +
                "    \"has_discussions\": false,\n" +
                "    \"forks_count\": 0,\n" +
                "    \"mirror_url\": null,\n" +
                "    \"archived\": false,\n" +
                "    \"disabled\": false,\n" +
                "    \"open_issues_count\": 7,\n" +
                "    \"license\": null,\n" +
                "    \"allow_forking\": true,\n" +
                "    \"is_template\": false,\n" +
                "    \"web_commit_signoff_required\": false,\n" +
                "    \"topics\": [\n" +
                "\n" +
                "    ],\n" +
                "    \"visibility\": \"public\",\n" +
                "    \"forks\": 0,\n" +
                "    \"open_issues\": 7,\n" +
                "    \"watchers\": 1,\n" +
                "    \"default_branch\": \"main\",\n" +
                "    \"stargazers\": 1,\n" +
                "    \"master_branch\": \"main\"\n" +
                "  },\n" +
                "  \"pusher\": {\n" +
                "    \"name\": \"Deadlica\",\n" +
                "    \"email\": \"78732361+Deadlica@users.noreply.github.com\"\n" +
                "  },\n" +
                "  \"sender\": {\n" +
                "    \"login\": \"Deadlica\",\n" +
                "    \"id\": 78732361,\n" +
                "    \"node_id\": \"MDQ6VXNlcjc4NzMyMzYx\",\n" +
                "    \"avatar_url\": \"https://avatars.githubusercontent.com/u/78732361?v=4\",\n" +
                "    \"gravatar_id\": \"\",\n" +
                "    \"url\": \"https://api.github.com/users/Deadlica\",\n" +
                "    \"html_url\": \"https://github.com/Deadlica\",\n" +
                "    \"followers_url\": \"https://api.github.com/users/Deadlica/followers\",\n" +
                "    \"following_url\": \"https://api.github.com/users/Deadlica/following{/other_user}\",\n" +
                "    \"gists_url\": \"https://api.github.com/users/Deadlica/gists{/gist_id}\",\n" +
                "    \"starred_url\": \"https://api.github.com/users/Deadlica/starred{/owner}{/repo}\",\n" +
                "    \"subscriptions_url\": \"https://api.github.com/users/Deadlica/subscriptions\",\n" +
                "    \"organizations_url\": \"https://api.github.com/users/Deadlica/orgs\",\n" +
                "    \"repos_url\": \"https://api.github.com/users/Deadlica/repos\",\n" +
                "    \"events_url\": \"https://api.github.com/users/Deadlica/events{/privacy}\",\n" +
                "    \"received_events_url\": \"https://api.github.com/users/Deadlica/received_events\",\n" +
                "    \"type\": \"User\",\n" +
                "    \"site_admin\": false\n" +
                "  },\n" +
                "  \"created\": true,\n" +
                "  \"deleted\": false,\n" +
                "  \"forced\": false,\n" +
                "  \"base_ref\": \"refs/heads/5-maven-build-system\",\n" +
                "  \"compare\": \"https://github.com/Deadlica/continuous-integration/compare/8-compilation\",\n" +
                "  \"commits\": [\n" +
                "\n" +
                "  ],\n" +
                "  \"head_commit\": {\n" +
                "    \"id\": \"4ed227fa48fc9106d176ed74d797484d5fa24790\",\n" +
                "    \"tree_id\": \"d4bef11d39392e3ebbda68095bfc9c2e9134c3a4\",\n" +
                "    \"distinct\": true,\n" +
                "    \"message\": \"Merge pull request #7 from Deadlica/3-github-actions\\n\\n#3 feat: added github action to build server with maven\",\n" +
                "    \"timestamp\": \"2024-02-08T14:09:40+01:00\",\n" +
                "    \"url\": \"https://github.com/Deadlica/continuous-integration/commit/4ed227fa48fc9106d176ed74d797484d5fa24790\",\n" +
                "    \"author\": {\n" +
                "      \"name\": \"Samuel Greenberg\",\n" +
                "      \"email\": \"78732361+Deadlica@users.noreply.github.com\",\n" +
                "      \"username\": \"Deadlica\"\n" +
                "    },\n" +
                "    \"committer\": {\n" +
                "      \"name\": \"GitHub\",\n" +
                "      \"email\": \"noreply@github.com\",\n" +
                "      \"username\": \"web-flow\"\n" +
                "    },\n" +
                "    \"added\": [\n" +
                "      \".github/workflows/build-maven.yml\"\n" +
                "    ],\n" +
                "    \"removed\": [\n" +
                "\n" +
                "    ],\n" +
                "    \"modified\": [\n" +
                "\n" +
                "    ]\n" +
                "  }\n" +
                "}\n";

        // Set up mocks for a successful build
        // Create BuildThread instance
        BuildThread buildThread = new BuildThread(validRequestBody);
        // Run the build thread
        assertDoesNotThrow(buildThread::run);
    }
     */
}
