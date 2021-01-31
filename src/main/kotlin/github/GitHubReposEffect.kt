package github

import io.redgreen.kstate.contract.Effect

sealed class GitHubReposEffect : Effect {
  object FetchGitHubRepos : GitHubReposEffect()
}
