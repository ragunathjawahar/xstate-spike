package github

import io.redgreen.kstate.Event

sealed class GitHubReposEvent : Event {
  object Fetch : GitHubReposEvent()
  data class Resolve(val repos: List<GitHubRepo>) : GitHubReposEvent()
  object Reject : GitHubReposEvent()
  object Retry : GitHubReposEvent()
}
