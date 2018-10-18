package it.unibo.dcs.authentication_service.repository

import java.util.Date

import it.unibo.dcs.service.authentication.data.AuthenticationDataStore
import it.unibo.dcs.service.authentication.repository.AuthenticationRepositoryImpl
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec
import rx.lang.scala.{Observable, Subscriber}

class AuthenticationRepositoryTest extends FlatSpec with MockFactory {

  val username = "ale"
  val password = "123"
  val token = "token"
  val tokenExpirationDate = new Date()
  val expectedResult: Unit = Unit
  val expectedTokenValidityResult = true
  val authDataStore: AuthenticationDataStore = mock[AuthenticationDataStore]
  val authRepository = new AuthenticationRepositoryImpl(authDataStore)
  val logoutSubscriber: Subscriber[Unit] = stub[Subscriber[Unit]]

  it should "find the user when checkUserExistence is called" in {
    val loginSubscriber: Subscriber[Unit] = stub[Subscriber[Unit]]
    (authDataStore checkUserExistence _) expects username returns (Observable just expectedResult)

    authRepository checkUserExistence(username) subscribe loginSubscriber

    (loginSubscriber onNext _) verify expectedResult once()
    (() => loginSubscriber onCompleted) verify() once()
  }

  it should "logout the user when invalidToken is called" in {
    (authDataStore invalidToken (_ ,_)) expects (token, tokenExpirationDate) returns (Observable just expectedResult)

    authRepository invalidToken(token, tokenExpirationDate) subscribe logoutSubscriber

    (logoutSubscriber onNext _) verify expectedResult once()
    (() => logoutSubscriber onCompleted) verify() once()
  }

  it should "register the user when createUser is called" in {
    val registerSubscriber: Subscriber[Unit] = stub[Subscriber[Unit]]
    (authDataStore createUser(_, _)) expects (username, password) returns (Observable just expectedResult)

    authRepository createUser(username, password) subscribe registerSubscriber

    (registerSubscriber onNext _) verify expectedResult once()
    (() => registerSubscriber onCompleted) verify() once()
  }

  it should "check the token validity when isTokenValid is called" in {
    val subscriber: Subscriber[Boolean] = stub[Subscriber[Boolean]]
    (authDataStore isTokenValid _) expects token returns Observable.just(expectedTokenValidityResult)

    authRepository isTokenValid token subscribe subscriber

    (subscriber onNext _) verify expectedTokenValidityResult once()
    (() => subscriber onCompleted) verify() once()
  }

}