package github

import github.GitHubReposEvent.Fetch
import github.GitHubReposEvent.Reject
import github.GitHubReposEvent.Resolve
import github.GitHubReposEvent.Retry
import io.redgreen.kstate.State
import io.redgreen.kstate.annotations.Final
import io.redgreen.kstate.annotations.Initial
import io.redgreen.kstate.annotations.On

sealed class GitHubReposState : State {
  @Initial
  @On(Fetch::class, goto = FetchingGitHubRepos::class)
  object Idle : GitHubReposState()

  @On(Resolve::class, goto = Success::class)
  @On(Reject::class, goto = Failure::class)
  object FetchingGitHubRepos : GitHubReposState()

  @Final
  data class Success(
    val repos: List<GitHubRepo>
  ) : GitHubReposState()

  @On(Retry::class, goto = FetchingGitHubRepos::class)
  object Failure : GitHubReposState()
}
