package it.unibo.dcs.commons.validation

import it.unibo.dcs.commons.validation.ValidatorBuilder._

import scala.collection.mutable

final case class Rule[T](private[validation] val predicate: T => Boolean,
                         private[validation] val exception: T => Throwable)

final class ValidatorBuilder[T] private[validation]() {

  private[this] var _rules = Option(mutable.Buffer[Rule[T]]())

  def addRule(rule: Rule[T]): ValidatorBuilder[T] = {
    rules += rule
    this
  }

  def addRule(predicate: T => Boolean,
              exception: T => Throwable = _ => new RuntimeException()): ValidatorBuilder[T] = {
    addRule(Rule[T](predicate, exception))
  }

  private[validation] def build: Validator[T] = {
    val validator = new ValidatorImpl[T](rules.toList)
    rules.clear()
    _rules = None
    validator
  }

  private[this] def rules = _rules.getOrElse(throw EXCEPTION)

}

object ValidatorBuilder {

  private[validation] val EXCEPTION = new IllegalStateException("builder has already been built")

}
