package com.seanshubin.iteration.tracker.core.json

import java.io.StringWriter

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.{JsonFactory, JsonParseException}
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, SerializationFeature}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.seanshubin.http.values.domain.StringUtil

import scala.collection.JavaConverters._

class JsonMarshallerImpl extends JsonMarshaller {
  private val mapper = new ObjectMapper()
  mapper.registerModule(DefaultScalaModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
  mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
  mapper.configure(SerializationFeature.INDENT_OUTPUT, true)

  override def toJson[T](theObject: T): String = {
    val stringWriter = new StringWriter
    mapper.writeValue(stringWriter, theObject)
    val json = stringWriter.toString
    json
  }

  override def fromJson[T](json: String, theClass: Class[T]): T = {
    try {
      val parsed = mapper.readValue(json, theClass)
      parsed
    } catch {
      case ex: JsonParseException =>
        val escapedJson = StringUtil.doubleQuote(json)
        throw new RuntimeException(s"Error while attempting to parse $escapedJson: ${ex.getMessage}", ex)
    }
  }

  override def fromJsonArray[T](json: String, theElementClass: Class[T]): Seq[T] = {
    val collectionType = mapper.getTypeFactory.constructCollectionType(classOf[java.util.List[T]], theElementClass)
    val myObjects: java.util.List[T] = mapper.readValue(json, collectionType)
    myObjects.asScala
  }

  override def normalize(json: String): String = {
    val asObject: AnyRef = fromJson(json, classOf[AnyRef])
    val normalized = toJson(asObject)
    normalized
  }

  override def merge(aObject: AnyRef, bObject: AnyRef): AnyRef = {
    val merged = (aObject, bObject) match {
      case (mapA: Map[_, _], mapB: Map[_, _]) =>
        val checkedMapA = mapA.asInstanceOf[Map[AnyRef, AnyRef]]
        val checkedMapB = mapB.asInstanceOf[Map[AnyRef, AnyRef]]
        checkedMapB.foldLeft(checkedMapA)(mergeEntry)
      case (a, b) => b
    }
    merged
  }

  def toDynamicJsonObject(rawJson: String): DynamicJson = {
    val jsonFactory = new JsonFactory
    val parser = jsonFactory.createParser(rawJson)
    val abstractSyntaxTree = new JsonAbstractSyntaxTree(parser)
    val value = abstractSyntaxTree.parseValue()
    val dynamic: DynamicJson = DynamicJson(value)
    dynamic
  }

  private def mergeEntry(aMap: Map[AnyRef, AnyRef], bEntry: (AnyRef, AnyRef)): Map[AnyRef, AnyRef] = {
    val (bKey, bValue) = bEntry
    val merged = if (bValue == null) {
      aMap - bKey
    } else {
      val cValue = aMap.get(bKey) match {
        case Some(aValue) => merge(aValue, bValue)
        case None => bValue
      }
      aMap.updated(bKey, cValue)
    }
    merged
  }
}
