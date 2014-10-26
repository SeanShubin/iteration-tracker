package com.seanshubin.iteration.tracker.console

import com.seanshubin.iteration.tracker.core._

trait LauncherWiring {
  def commandLineArguments: Seq[String]

  lazy val emitLine: String => Unit = println
  lazy val errorHandler: ErrorHandler = new ErrorHandlerImpl(emitLine)
  lazy val configurationFactory: ConfigurationFactory = new ConfigurationFactoryImpl()
  lazy val runnerFactory: RunnerFactory = new RunnerFactoryImpl()
  lazy val launcher: Launcher = new LauncherImpl(commandLineArguments, configurationFactory, errorHandler, runnerFactory)
}
