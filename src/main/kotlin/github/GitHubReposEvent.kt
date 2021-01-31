package github

import github.GitHubReposEffect.FetchGitHubRepos
import io.redgreen.kstate.annotation.EffectEvent
import io.redgreen.kstate.annotation.UiEvent
import io.redgreen.kstate.contract.Event

sealed class GitHubReposEvent : Event {
  @EffectEvent(FetchGitHubRepos::class)
  data class Resolve(val repos: List<GitHubRepo>) : GitHubReposEvent()

  @EffectEvent(FetchGitHubRepos::class)
  object Reject : GitHubReposEvent()

  @UiEvent
  object Retry : GitHubReposEvent()
}
