package github

import github.GitHubReposEffect.FetchGitHubRepos
import github.GitHubReposEvent.Reject
import github.GitHubReposEvent.Resolve
import github.GitHubReposEvent.Retry
import github.GitHubReposState.FetchingGitHubRepos
import io.redgreen.kstate.State
import io.redgreen.kstate.annotations.Final
import io.redgreen.kstate.annotations.Initial
import io.redgreen.kstate.annotations.Next
import io.redgreen.kstate.annotations.On
import io.redgreen.kstate.annotations.StateMachine

@StateMachine
@Initial(FetchingGitHubRepos::class, FetchGitHubRepos::class)
object GitHubReposStateMachine

sealed class GitHubReposState : State {
  @On(Resolve::class, Next(Success::class))
  @On(Reject::class, Next(Failure::class))
  object FetchingGitHubRepos : GitHubReposState()

  @Final
  data class Success(
    val repos: List<GitHubRepo>
  ) : GitHubReposState()

  @On(Retry::class, next = Next(FetchingGitHubRepos::class, FetchGitHubRepos::class))
  object Failure : GitHubReposState()
}
