package com.leosnumismatics.leosnumismaticsscala2http4s023

import cats.Applicative
import cats.implicits._
import io.circe.{Encoder, Json}
import org.http4s.EntityEncoder
import org.http4s.circe._
// import com.snowplowanalytics.iglu.core.{SchemaKey, SchemaVer}
// import scala.concurrent.ExecutionContext.Implicits.global

// import com.snowplowanalytics.snowplow.scalatracker.Tracker
// import com.snowplowanalytics.snowplow.scalatracker.Emitter._
// import com.snowplowanalytics.snowplow.scalatracker._
// import com.snowplowanalytics.snowplow.scalatracker
import cats.data.NonEmptyList

trait HelloWorld[F[_]]{
  def hello(n: HelloWorld.Name): F[HelloWorld.Greeting]
}

object HelloWorld {
  final case class Name(name: String) extends AnyVal
  /**
    * More generally you will want to decouple your edge representations from
    * your internal data structures, however this shows how you can
    * create encoders for your data.
    **/
  final case class Greeting(greeting: String) extends AnyVal
  object Greeting {
    implicit val greetingEncoder: Encoder[Greeting] = new Encoder[Greeting] {
      final def apply(a: Greeting): Json = Json.obj(
        ("message", Json.fromString(a.greeting)),
      )
    }
    implicit def greetingEntityEncoder[F[_]]: EntityEncoder[F, Greeting] =
      jsonEncoderOf[F, Greeting]
  }

  def impl[F[_]: Applicative]: HelloWorld[F] = new HelloWorld[F]{
    def hello(n: HelloWorld.Name): F[HelloWorld.Greeting] = {
      val emitter1 = com.snowplowanalytics.snowplow.scalatracker.emitters.id.AsyncEmitter.createAndStart(
        com.snowplowanalytics.snowplow.scalatracker.Emitter.EndpointParams("mycollector.com")
      )
      // val emitter2 = com.snowplowanalytics.snowplow.scalatracker.emitters.SyncEmitter(com.snowplowanalytics.snowplow.scalatracker.Emitter.EndpointParams("myothercollector.com", port = Some(8080)))
      val tracker = new com.snowplowanalytics.snowplow.scalatracker.Tracker(NonEmptyList.of(emitter1), "leos-numismatics-scala", "leos-numismatics-scala")
      tracker.trackPageView("http://example.com") // returns Unit
      tracker.trackTransaction("order1234", 42.0) // returns Unit

      Greeting("Hello, " + n.name).pure[F]
    }
  }
}
