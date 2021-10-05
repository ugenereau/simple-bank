package fr.fpe.school
package database

import database.error.AccountRepositoryError
import model.Account

trait AccountRepository[F[_]] {
  def insert(name: String): F[Either[AccountRepositoryError, Account]]
}
