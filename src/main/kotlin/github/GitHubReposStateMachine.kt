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
@Initial(
  state = FetchingGitHubRepos::class,
  effects = [FetchGitHubRepos::class]
)
object GitHubReposStateMachine

sealed class GitHubReposState : State {
  @On(event = Resolve::class, next = Next(state = Success::class))
  @On(event = Reject::class, next = Next(state = Failure::class))
  object FetchingGitHubRepos : GitHubReposState()

  @Final
  data class Success(
    val repos: List<GitHubRepo>
  ) : GitHubReposState()

  @On(
    event = Retry::class,
    next = Next(
      state = FetchingGitHubRepos::class,
      effects = [FetchGitHubRepos::class]
    )
  )
  object Failure : GitHubReposState()
}
