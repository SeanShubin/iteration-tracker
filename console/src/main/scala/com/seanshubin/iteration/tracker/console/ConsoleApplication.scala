package com.seanshubin.iteration.tracker.console

object ConsoleApplication extends App with LauncherWiring {
  override def commandLineArguments: Seq[String] = args

  launcher.launch()
}
