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
import uk.gov.hmrc.play.microservice.controller.BaseController
import uk.gov.hmrc.vatregisteredcompaniesstub.connectors.BackendConnector
import uk.gov.hmrc.vatregisteredcompaniesstub.models.{Payload, PayloadSubmissionResponse}
import uk.gov.hmrc.vatregisteredcompaniesstub.services.{DataGenerator, JsonSchemaChecker}

import scala.concurrent.ExecutionContext

@Singleton
class DataController @Inject()(implicit executionContext: ExecutionContext)
  extends BaseController with BackendConnector {

  // TODO create more fine grained posts... those that update existing, those that delete etc.

  def triggerPost(): Action[AnyContent] = Action.async { implicit request =>
    val payload: Payload = DataGenerator.generateData
    JsonSchemaChecker[Payload](payload, "mdg-payload")
    bePost[Payload, PayloadSubmissionResponse]("/vat-registered-companies/vatregistrations", payload).map{ res =>
      JsonSchemaChecker[PayloadSubmissionResponse](res, "be-response")
      Ok(Json.toJson(res).toString())
    }
  }

}
