package it.unibo.dcs.commons

import java.text.SimpleDateFormat
import java.util.Date

import scala.language.implicitConversions

package object dataaccess {
  private val mySqlFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

  object Implicits {

    implicit def dateToString(date: Date): String = mySqlFormat.format(date)

    implicit def stringToDate(date: String): Date = mySqlFormat.parse(date)

    implicit def booleanToString(value: Boolean): String = if (value) "1" else "0"

    implicit def stringToBoolean(value: String): Boolean =
      if (value == "0") false
      else if (value == "1") true
      else throw new IllegalArgumentException()

  }

}
