import github.GitHubReposEffect.FetchGitHubRepos.Failure
import github.GitHubReposStateMachine

fun main() {
  println(GitHubReposStateMachine::class.java)
  println(Failure.NoReposFound::class.java)
  println(Failure.SessionExpired::class.java)
  println(Failure.BadGateway::class.java)
}
