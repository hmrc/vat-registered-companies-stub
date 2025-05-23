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

package uk.gov.hmrc.vatregisteredcompaniesstub.connectors

import play.api.libs.json._
import uk.gov.hmrc.http.HttpReads.Implicits._
import uk.gov.hmrc.http.client.HttpClientV2
import uk.gov.hmrc.http.{HeaderCarrier, HeaderNames, HttpResponse, StringContextOps}
import uk.gov.hmrc.play.bootstrap.config.ServicesConfig
import play.api.libs.ws.JsonBodyWritables.writeableOf_JsValue

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class BackendConnector @Inject()(
  http: HttpClientV2,
  servicesConfig: ServicesConfig
) {

  val serviceURL: String = servicesConfig.baseUrl("vat-registered-companies")

  private def authHeader: (String, String) =
    HeaderNames.authorisation -> s"Bearer ${servicesConfig.getConfString("vat-registered-companies.token", "")}"

  def bePost[I: Writes, O: Reads](url: String, body: I)(implicit hc: HeaderCarrier, ec: ExecutionContext): Future[O] = {
    http
      .post(url"$serviceURL$url")
      .withBody(Json.toJson(body))
      .setHeader(authHeader)
      .execute[HttpResponse]
      .map(_.json.as[O])
  }
}




