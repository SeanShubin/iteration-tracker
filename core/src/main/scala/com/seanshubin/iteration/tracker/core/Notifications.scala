package com.seanshubin.iteration.tracker.core

import com.seanshubin.http.values.domain.{RequestValue, ResponseValue}

trait Notifications {
  def request(request: RequestValue)

  def response(request: RequestValue, response: ResponseValue)

  def exception(runtimeException: RuntimeException)
}
