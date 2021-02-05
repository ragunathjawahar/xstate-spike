package xstate.examples.openproject

import xstate.Machine
import xstate.examples.openproject.OpenProjectEffect.GetGitUsername
import xstate.examples.openproject.OpenProjectEvent.GitUsernameFound
import xstate.examples.openproject.OpenProjectEvent.GitUsernameNotFound
import xstate.examples.openproject.OpenProjectEvent.UnableToGetGitUsername
import xstate.examples.openproject.OpenProjectState.GenericGreeting
import xstate.examples.openproject.OpenProjectState.GettingGitUsername
import xstate.examples.openproject.OpenProjectState.PersonalizedGreeting
import xstate.runVisitor
import xstate.visitors.XStateJsonVisitor

fun main() {
  val visitor = runVisitor(greetUserDsl, XStateJsonVisitor())
  println(visitor.json)
}

val greetUserDsl = Machine
  .create<OpenProjectState, OpenProjectEvent, OpenProjectEffect>("Open Project") {
    initial(GettingGitUsername::class, setOf(GetGitUsername::class))

    state(GettingGitUsername::class) {
      on(GitUsernameFound::class, next = PersonalizedGreeting::class)
      on(GitUsernameNotFound::class, next = GenericGreeting::class)
      on(UnableToGetGitUsername::class, next = GenericGreeting::class)
    }

    state(PersonalizedGreeting::class)
    state(GenericGreeting::class)
  }

sealed class OpenProjectState {
  object GettingGitUsername : OpenProjectState()
  object PersonalizedGreeting : OpenProjectState()
  object GenericGreeting : OpenProjectState()
}

sealed class OpenProjectEvent {
  object GitUsernameFound : OpenProjectEvent()
  object GitUsernameNotFound : OpenProjectEvent()
  object UnableToGetGitUsername : OpenProjectEvent()
  object ChooseGitRepository : OpenProjectEvent()
  object GitRepositoryChosen : OpenProjectEvent()
  object GitRepositoryDetected : OpenProjectEvent()
  object GitRepositoryNotDetected : OpenProjectEvent()
  object NoRecentRepositories : OpenProjectEvent()
  object HasRecentRepositories : OpenProjectEvent()
  object UnableToGetRecentRepositories : OpenProjectEvent()
  object OpenRecentRepository : OpenProjectEvent()
}

sealed class OpenProjectEffect {
  object GetGitUsername : OpenProjectEffect()
  object ShowFileChooser : OpenProjectEffect()
  object DetectGitRepository : OpenProjectEffect()
  object ShowNotAGitRepositoryError : OpenProjectEffect()
  object OpenGitRepository : OpenProjectEffect()
  object GetRecentRepositories : OpenProjectEffect()
  object GetRecentRepositoriesFailed : OpenProjectEffect()
  object UpdateRecentRepositories : OpenProjectEffect()
}
