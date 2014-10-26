package com.seanshubin.iteration.tracker.core

trait ErrorHandler {
  def handleConfigurationError(lines: Seq[String])
}
