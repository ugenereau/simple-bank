package fr.fpe.school

import cats.effect.{Blocker, ContextShift, IO, Resource}
import com.typesafe.config.ConfigFactory
import pureconfig._
import pureconfig.module.catseffect.syntax._
import pureconfig.generic.auto._

package object config {
  final case class ServerConfig(host: String, port: Int)

  final case class DatabaseConfig(driver: String, url: String, user: String, password: String, threadPoolSize: Int)

  final case class Config(server: ServerConfig, database: DatabaseConfig)

  object Config {
    def load(blocker: Blocker)(implicit cs: ContextShift[IO]): Resource[IO, Config] =
      Resource.eval(ConfigSource.fromConfig(ConfigFactory.load("application.conf")).loadF[IO, Config](blocker))
  }
}
