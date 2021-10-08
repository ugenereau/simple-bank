package fr.fpe.school
package database

import model.Account

trait AccountRepository[F[_]] {
  def insert(name: String): F[Account]
}
