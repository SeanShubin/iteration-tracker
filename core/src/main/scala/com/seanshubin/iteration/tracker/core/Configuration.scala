package com.seanshubin.iteration.tracker.core

import com.seanshubin.http.values.domain.ContentType

case class Configuration(port: Int,
                         classLoaderPrefix: String,
                         overridePath: Option[String],
                         contentByExtension: Map[String, ContentType],
                         redirects: Map[String, String]) {
  def redirectFunction(uri: String): Option[String] = {
    redirects.get(uri)
  }
}
