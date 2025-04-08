package com.leosnumismatics.leosnumismaticsscala2http4s023

import cats.effect.{IO, IOApp}

object Main extends IOApp.Simple {
  val run = Leosnumismaticsscala2http4s023Server.run[IO]
}
