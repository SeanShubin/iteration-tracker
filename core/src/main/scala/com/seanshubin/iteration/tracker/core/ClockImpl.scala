package com.seanshubin.iteration.tracker.core

class ClockImpl extends Clock {
  override def currentTimeMillis(): Long = System.currentTimeMillis()
}
