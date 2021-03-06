package com.seanshubin.iteration.tracker.core.json

trait JsonMarshaller {
  def toJson[T](theObject: T): String

  def fromJson[T](json: String, theClass: Class[T]): T

  def fromJsonArray[T](json: String, theElementClass: Class[T]): Seq[T]

  def normalize(json: String): String

  def merge(aObject: AnyRef, bObject: AnyRef): AnyRef

  def toDynamicJsonObject(rawJson: String): DynamicJson
}
