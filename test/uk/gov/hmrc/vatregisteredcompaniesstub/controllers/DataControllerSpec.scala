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

package uk.gov.hmrc.vatregisteredcompaniesstub.controllers

import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.http.Status
import play.api.libs.json.{Reads, Writes}
import play.api.test.FakeRequest
import play.api.test.Helpers._
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.vatregisteredcompaniesstub.connectors.BackendConnector
import uk.gov.hmrc.vatregisteredcompaniesstub.models.{Payload, PayloadSubmissionResponse}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

class DataControllerSpec extends AnyWordSpec with Matchers with GuiceOneAppPerSuite with MockitoSugar {

  val fakeRequest = FakeRequest("GET", "/")
  val mockBackendConnector = mock[BackendConnector]
  val cc = play.api.test.Helpers.stubControllerComponents()

  "GET /" should {
    "return 200" in {
      when(mockBackendConnector.bePost[Payload, PayloadSubmissionResponse](
        anyString(), any[Payload]()
      )(
        any[Writes[Payload]](), any[Reads[PayloadSubmissionResponse]](), any[HeaderCarrier](), any[ExecutionContext]()
      )).thenReturn(
        Future.successful(
          PayloadSubmissionResponse(
            PayloadSubmissionResponse.Outcome.SUCCESS,
            None
          )
        )
      )

      val controller = new DataController(mockBackendConnector, cc)
      val result = controller.triggerUpdate(fakeRequest)
      status(result) shouldBe Status.OK
    }
  }
}