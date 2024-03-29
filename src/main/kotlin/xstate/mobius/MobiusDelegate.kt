package xstate.mobius

import com.spotify.mobius.Connectable
import com.spotify.mobius.Connection
import com.spotify.mobius.Init
import com.spotify.mobius.Mobius
import com.spotify.mobius.Update
import com.spotify.mobius.extras.Connectables
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.ObservableTransformer
import xstate.fastLazy
import xstate.mobius.view.ViewRenderer

class MobiusDelegate<M, E, F>(
  private val initialModel: M,
  private val init: Init<M, F>?,
  private val update: Update<M, E, F>,
  private val effectHandler: ObservableTransformer<F, E>,
  private val viewRenderer: ViewRenderer<M>
) {
  private val eventSource = DeferredEventSource<E>()

  private val loopController by fastLazy {
    val loop = RxMobius
      .loop(update, effectHandler)
      .eventSource(eventSource)

    if (init != null) {
      Mobius.controller(loop, initialModel, init)
    } else {
      Mobius.controller(loop, initialModel)
    }
  }

  private val connectable by fastLazy {
    Connectable<M, E> {
      object : Connection<M> {
        override fun accept(model: M) {
          viewRenderer.render(model)
        }

        override fun dispose() {
          // No-op
        }
      }
    }
  }

  @Suppress("NULLABLE_TYPE_PARAMETER_AGAINST_NOT_NULL_TYPE_PARAMETER")
  fun start() {
    with(loopController) {
      connect(Connectables.contramap({ it }, connectable))
      start()
    }
  }

  fun notify(event: E) {
    eventSource.notify(event)
  }
}
