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

//import uk.gov.hmrc.play.bootstrap.http.HttpClient

import com.typesafe.config.Config
import javax.inject.Singleton
import play.api.libs.json.Writes
import uk.gov.hmrc.http.hooks.HttpHook
import uk.gov.hmrc.http.logging.Authorization
import uk.gov.hmrc.http.{HeaderCarrier, HttpPost, HttpReads, HttpResponse}
import uk.gov.hmrc.play.config.ServicesConfig
import uk.gov.hmrc.play.http.ws.WSPost

import scala.concurrent.{ExecutionContext, Future}

@Singleton
trait BackendConnector extends ServicesConfig {

  val http: HttpPost = new HttpPost with WSPost {
    override val hooks: Seq[HttpHook] = NoneRequired
  }

  val serviceURL: String = baseUrl("vat-registered-companies")

  def bePost[I, O](url: String, body: I)(implicit wts: Writes[I], rds: HttpReads[O], hc: HeaderCarrier, ec: ExecutionContext): Future[O] =
    http.POST[I, O](s"$serviceURL$url", body)(wts, rds, addHeaders, ec)

  def addHeaders(implicit hc: HeaderCarrier): HeaderCarrier = {
//    hc.withExtraHeaders(
//      "Environment" -> getConfString("des.environment", "")
//    ).copy(authorization = Some(Authorization(s"Bearer ${getConfString("des.token", "")}")))
    hc // TODO we will need to pass a token to the BE to authenticate - see soft-drinks-industry-levy DesHelpers & application.conf
  }

}



