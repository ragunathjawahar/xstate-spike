package github

import github.GitHubReposEffect.FetchGitHubRepos
import github.GitHubReposEvent.Reject
import github.GitHubReposEvent.Resolve
import github.GitHubReposEvent.Retry
import github.GitHubReposState.FetchingGitHubRepos
import io.redgreen.kstate.annotation.Final
import io.redgreen.kstate.annotation.Initial
import io.redgreen.kstate.annotation.Next
import io.redgreen.kstate.annotation.On
import io.redgreen.kstate.annotation.StateMachine
import io.redgreen.kstate.contract.State

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
