package github

import io.redgreen.kstate.contract.Effect

sealed class GitHubReposEffect : Effect {
  object FetchGitHubRepos : GitHubReposEffect() {
    sealed class Failure {
      object NoReposFound : Failure()
      object SessionExpired : Failure()
      object BadGateway : Failure()
    }
  }
}
