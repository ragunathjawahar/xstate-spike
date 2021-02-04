package xstate.examples.trafficlights

import com.spotify.mobius.rx2.RxMobius
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import java.util.concurrent.TimeUnit.MILLISECONDS
import trafficlights.TrafficLightsEffect
import trafficlights.TrafficLightsEffect.BeginCountDown
import trafficlights.TrafficLightsEvent
import trafficlights.TrafficLightsEvent.CountDownElapsed

object TrafficLightsEffectHandler {
  fun create(): ObservableTransformer<TrafficLightsEffect, TrafficLightsEvent> {
    return RxMobius
      .subtypeEffectHandler<TrafficLightsEffect, TrafficLightsEvent>()
      .addTransformer(BeginCountDown::class.java) { beginCountDownEvents ->
        beginCountDownEvents
          .flatMap { Observable.timer(it.duration, MILLISECONDS) }
          .map { CountDownElapsed }
      }
      .build()
  }
}
