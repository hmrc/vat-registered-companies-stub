/*
 * Copyright 2019 HM Revenue & Customs
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

package uk.gov.hmrc.vatregisteredcompaniesstub.connectors

import javax.inject.{Inject, Singleton}
import play.api.Mode.Mode
import play.api.libs.json.Writes
import play.api.{Configuration, Environment}
import uk.gov.hmrc.http.logging.Authorization
import uk.gov.hmrc.http.{HeaderCarrier, HttpReads}
import uk.gov.hmrc.play.bootstrap.http.HttpClient
import uk.gov.hmrc.play.config.ServicesConfig

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BackendConnector @Inject()(
  http: HttpClient,
  environment: Environment,
  configuration: Configuration
) extends ServicesConfig {

  val serviceURL: String = baseUrl("vat-registered-companies")

  def bePost[I, O](url: String, body: I)(implicit wts: Writes[I], rds: HttpReads[O], hc: HeaderCarrier, ec: ExecutionContext): Future[O] =
    http.POST[I, O](s"$serviceURL$url", body)(wts, rds, addHeaders, ec)

  def addHeaders(implicit hc: HeaderCarrier): HeaderCarrier = {
    hc.copy(authorization = Some(Authorization(s"Bearer ${getConfString("vat-registered-companies.token", "")}")))
  }

  override protected def mode: Mode = environment.mode

  override protected def runModeConfiguration: Configuration = configuration
}




