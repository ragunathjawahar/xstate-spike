package github

import io.redgreen.kstate.Effect

sealed class GitHubReposEffect : Effect {
  object FetchGitHubRepos : GitHubReposEffect()
}
