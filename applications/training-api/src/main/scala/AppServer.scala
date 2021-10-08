package fr.fpe.school

import api.AccountAPI
import config.{Config, Database}
import h2.repository.H2AccountRepository
import routes.BankRoutes

import cats.effect.{Blocker, ExitCode, IO, IOApp}
import doobie.util.transactor.Transactor
import fs2.Stream
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits._

import scala.concurrent.ExecutionContext.global

object AppServer extends IOApp {

  override def run(args: List[String]): IO[ExitCode] =
    (for {
      blocker    <- Stream.resource(Blocker[IO])
      config     <- Stream.resource(Config.load(blocker))
      transactor <- Stream.resource(Database.transactor(config.database, blocker))
      _          <- Stream.eval(Database.initialize(transactor))
      exitCode <- BlazeServerBuilder[IO](global)
        .bindHttp(config.server.port, config.server.host)
        .withHttpApp(buildBankRoutes(transactor).orNotFound)
        .serve
    } yield exitCode).compile.lastOrError

  private def buildBankRoutes(transactor: Transactor[IO]) = {

    val accountRepository = new H2AccountRepository(transactor)
    val accountAPI        = new AccountAPI(accountRepository)

    val bankRoutes = new BankRoutes(accountAPI)

    bankRoutes.routes

  }
}
