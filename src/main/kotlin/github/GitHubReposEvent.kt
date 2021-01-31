package github

import github.GitHubReposEffect.FetchGitHubRepos
import io.redgreen.kstate.Event
import io.redgreen.kstate.annotations.EffectEvent
import io.redgreen.kstate.annotations.UiEvent

sealed class GitHubReposEvent : Event {
  @EffectEvent(FetchGitHubRepos::class)
  data class Resolve(val repos: List<GitHubRepo>) : GitHubReposEvent()

  @EffectEvent(FetchGitHubRepos::class)
  object Reject : GitHubReposEvent()

  @UiEvent
  object Retry : GitHubReposEvent()
}
