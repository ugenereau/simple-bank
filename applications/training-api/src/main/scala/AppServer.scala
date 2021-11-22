package fr.fpe.school

import config.Config
import routes.BankRoutes

import cats.effect.{Blocker, ExitCode, IO, IOApp}
import fr.fpe.school.api.AccountAPI
import fs2.Stream
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits._

import scala.concurrent.ExecutionContext.global

object AppServer extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    (for {
      blocker <- Stream.resource(Blocker[IO])
      config  <- Stream.resource(Config.load(blocker))
      exitCode <- BlazeServerBuilder[IO](global)
        .bindHttp(config.server.port, config.server.host)
        .withHttpApp(buildBankRoutes().orNotFound)
        .serve
    } yield exitCode).compile.lastOrError

  private def buildBankRoutes() = {

    val api = new AccountAPI()
    val bankRoutes = new BankRoutes(api)

    bankRoutes.routes

  }
}
