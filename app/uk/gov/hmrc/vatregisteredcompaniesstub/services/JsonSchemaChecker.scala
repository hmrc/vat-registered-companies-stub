/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.vatregisteredcompaniesstub.services

import com.fasterxml.jackson.databind.JsonNode
import com.networknt.schema.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.networknt.schema.{SchemaRegistry, SpecificationVersion}
import play.api.Logger
import play.api.libs.json.{Format, Json}

object JsonSchemaChecker {

  lazy val logger: Logger = Logger(this.getClass)
  private val objectMapper: ObjectMapper = new ObjectMapper()
  private val schemaRegistry: SchemaRegistry =
    SchemaRegistry.withDefaultDialect(SpecificationVersion.DRAFT_4)

  def retrieveSchema(file: String): Schema = schema(s"/test/$file.schema.json")

  private def schema(path: String): Schema = {
    val stream = getClass.getResourceAsStream(path)
    val schemaText = scala.io.Source.fromInputStream(stream).mkString
    stream.close()
    val schemaNode = objectMapper.readTree(schemaText)
    schemaRegistry.getSchema(schemaNode)
  }

  def apply[A](model: A, file: String)(implicit format: Format[A]): Unit = {
    val schema = retrieveSchema(file)
    val jsonStr = Json.prettyPrint(Json.toJson(model))
    val jsonNode = objectMapper.readTree(jsonStr)
    val processingReport = schema.validate(jsonNode)
    if (!processingReport.isEmpty) processingReport.forEach {
      x =>
        logger.warn(
          s"failed to validate against json schema $file, schema: ${x.getSchemaLocation}, " +
            s"instance: ${x.getInstanceNode}, problem: ${x.getKeyword}"
        )
    }
  }

}