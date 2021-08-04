/*
 * Copyright 2021 HM Revenue & Customs
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
import play.api.mvc._
import uk.gov.hmrc.play.bootstrap.backend.controller.BackendController
import uk.gov.hmrc.vatregisteredcompaniesstub.connectors.BackendConnector
import uk.gov.hmrc.vatregisteredcompaniesstub.models.{Payload, PayloadSubmissionResponse}
import uk.gov.hmrc.vatregisteredcompaniesstub.services.{DataGenerator, JsonSchemaChecker}
import uk.gov.hmrc.http.HttpReads.Implicits._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class DataController @Inject()
(
  backendConnector: BackendConnector,
  cc: ControllerComponents
)(
  implicit executionContext: ExecutionContext
) extends BackendController(cc) {

  private def send(payload: Payload)(implicit request: Request[AnyContent]): Future[Result] =
    backendConnector.bePost[Payload, PayloadSubmissionResponse]("/vat-registered-companies/vatregistrations", payload).map{ res =>
      JsonSchemaChecker[PayloadSubmissionResponse](res, "be-response")
      Ok(Json.toJson(res).toString())
    }

  /**
    * Sends an initial data import to the BE service - can be run many times.
    * @return
    */
  def triggerPost(seed: String): Action[AnyContent] = Action.async { implicit request =>
    val payload: Payload = DataGenerator.generateData(seed.toLong)
    JsonSchemaChecker[Payload](payload, "mdg-payload")
    send(payload)
  }

  /**
    * Sends an update to the initial import; some changes, some deletes.
    */
  def triggerUpdate: Action[AnyContent] = Action.async { implicit request =>
    send(DataGenerator.updatedPayload)
  }

}
