package usecases

import java.util.Date

import it.unibo.dcs.commons.interactor.executor.{PostExecutionThread, ThreadExecutor}
import it.unibo.dcs.service.webapp.model.User
import it.unibo.dcs.service.webapp.repositories.AuthenticationRepository
import it.unibo.dcs.service.webapp.usecases.LogoutUserUseCase
import org.scalamock.scalatest.MockFactory
import org.scalatest.{FlatSpec, OneInstancePerTest}
import rx.lang.scala.{Observable, Subscriber}

import scala.language.postfixOps

class LogoutUseCaseSpec extends FlatSpec with MockFactory with OneInstancePerTest {

  private val user = User("niklegend", "Nicola", "Piscaglia", "bio", visible = true, new Date)

  private val threadExecutor: ThreadExecutor = mock[ThreadExecutor]
  private val postExecutionThread: PostExecutionThread = mock[PostExecutionThread]
  private val authRepository: AuthenticationRepository = mock[AuthenticationRepository]

  private val logoutSubscriber: Subscriber[Unit] = stub[Subscriber[Unit]]

  private val logoutUseCase = new LogoutUserUseCase(threadExecutor, postExecutionThread, authRepository)


  it should "logout the user when the use case is executed" in {
    // Given
    (authRepository logoutUser _) expects user.username returns Observable.empty

    // When
    logoutUseCase(user.username) subscribe logoutSubscriber

    // Then
    (() => logoutSubscriber onCompleted) verify() once()
  }
}
