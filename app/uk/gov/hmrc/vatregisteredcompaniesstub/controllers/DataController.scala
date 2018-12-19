/*
 * Copyright 2018 HM Revenue & Customs
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

package uk.gov.hmrc.vatregisteredcompaniesstub.controllers

import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent}
import uk.gov.hmrc.http.HttpResponse
import uk.gov.hmrc.vatregisteredcompaniesstub.connectors.BackendConnector
import uk.gov.hmrc.play.microservice.controller.BaseController
import uk.gov.hmrc.vatregisteredcompaniesstub.models.Payload
import uk.gov.hmrc.vatregisteredcompaniesstub.services.{DataGenerator, JsonSchemaChecker}

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DataController @Inject()(implicit executionContext: ExecutionContext)
  extends BaseController with BackendConnector {

  def triggerPost(): Action[AnyContent] = Action.async { implicit request =>
    val payload: Payload = DataGenerator.generateData
    JsonSchemaChecker[Payload](payload, "mdg-payload") // TODO consider moving schema checking to unit tests

    bePost[Payload, HttpResponse]("/vatregistrations", payload).map{ res =>
      println(res.status) // TODO set up BE endpoint to post to
      // TODO check the schema of the response against our response schema!
    }

    Future.successful(Ok(Json.toJson(payload).toString()))
  }

}
