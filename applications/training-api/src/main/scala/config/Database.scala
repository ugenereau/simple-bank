package fr.fpe.school
package config

import cats.effect.{Blocker, ContextShift, IO, Resource}
import doobie.hikari.HikariTransactor
import doobie.util.ExecutionContexts
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.output.MigrateResult

object Database {
  def transactor(config: DatabaseConfig, blocker: Blocker)(implicit
      contextShift: ContextShift[IO]
  ): Resource[IO, HikariTransactor[IO]] =
    ExecutionContexts
      .fixedThreadPool[IO](config.threadPoolSize)
      .flatMap(executionContext =>
        HikariTransactor.newHikariTransactor[IO](
          config.driver,
          config.url,
          config.user,
          config.password,
          executionContext,
          blocker
        )
      )

  def initialize(transactor: HikariTransactor[IO]): IO[MigrateResult] =
    transactor.configure { dataSource =>
      IO {
        val flyWay = Flyway.configure().dataSource(dataSource).load()
        flyWay.migrate()
      }
    }
}
