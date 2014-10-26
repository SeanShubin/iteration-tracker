package com.seanshubin.iteration.tracker.core

trait RunnerFactory {
  def createRunner(configuration: Configuration): Runner
}
