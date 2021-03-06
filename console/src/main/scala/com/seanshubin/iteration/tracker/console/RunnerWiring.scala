package com.seanshubin.iteration.tracker.console

import java.time.Clock

import com.seanshubin.http.values.domain._
import com.seanshubin.iteration.tracker.core._
import com.seanshubin.iteration.tracker.server.{DatabaseReceiver, DatabaseRoute, JettyHttpServer}

trait RunnerWiring {
  def configuration: Configuration

  lazy val redirectReceiver: Receiver = new RedirectReceiver(configuration.redirectFunction)
  lazy val classLoader: ClassLoader = this.getClass.getClassLoader
  lazy val classLoaderReceiver: Receiver = new ClassLoaderReceiver(
    classLoader, configuration.classLoaderPrefix, configuration.contentByExtension, configuration.overridePath)
  lazy val echoReceiver: Receiver = new EchoReceiver()
  lazy val redirectRoute: Route = new RedirectRoute("redirect", redirectReceiver, configuration.redirectFunction)
  lazy val classLoaderRoute: Route = new ClassLoaderRoute(
    "class-loader", classLoaderReceiver, configuration.contentByExtension)
  lazy val databaseReceiver: Receiver = new DatabaseReceiver
  lazy val databaseRoute: Route = new DatabaseRoute("database", new DatabaseReceiver)
  lazy val routes: Seq[Route] = Seq(redirectRoute, databaseRoute, classLoaderRoute)
  lazy val dispatcher: Receiver = new DispatchingReceiver(routes)
  lazy val clock: Clock = Clock.systemUTC()
  lazy val notifications: Notifications = new NotificationsImpl(clock)
  lazy val receiver: Receiver = new FallbackReceiver(
    dispatcher, echoReceiver, notifications.request, notifications.response, notifications.exception)
  lazy val server: HttpServer = new JettyHttpServer(configuration.port, receiver)
  lazy val runner: Runner = new RunnerImpl(server)
}
