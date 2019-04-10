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

package uk.gov.hmrc.vatregisteredcompaniesstub.services

import cats.implicits._
import org.scalacheck.Gen
import uk.gov.hmrc.smartstub._
import uk.gov.hmrc.vatregisteredcompaniesstub.models.{Address, Payload, VatRegisteredCompany}

object DataGenerator {

  private val minElements = 39000
  private val maxElements = 40000

  private def companyName: Gen[String] =
    Gen.company.retryUntil(_.length > 1).map(_.take(105))

  private def vatNumber: Gen[String] = for {
    short <- pattern"999999999"
    long <- pattern"999999999999"
    vatNumber <- Gen.oneOf(short, long)
  } yield vatNumber

  private def address: Gen[Address] = Gen.ukAddress.map { lines =>
    Address(
      lines.head.take(35),
      lines.get(1).map(_.take(35)),
      lines.get(2).map(_.take(35)),
      lines.get(3).map(_.take(35)),
      lines.get(4).map(_.take(35)),
      lines.last.some,
      "GB"
    )
  }

  private def vatRegisteredCompany: Gen[VatRegisteredCompany] = for {
    a <- companyName
    b <- vatNumber
    c <- address
  } yield VatRegisteredCompany(a, b, c)

  private def payload: Gen[Payload] = for {
    a <- Gen.choose(minElements, maxElements).flatMap(Gen.listOfN(_, vatRegisteredCompany))
    b <- Gen.choose(0, 0).flatMap(Gen.listOfN(_, vatNumber))
  } yield Payload(a, b)

  def generateData: Payload = payload.seeded(1L).get

  def updatedPayload: Payload = {
    val payload: Payload = DataGenerator.generateData
    Payload(
      payload.createsAndUpdates.take(10000).map(_.copy(name = "foo")),
      payload.createsAndUpdates.takeRight(20000).map(_.vatNumber)
    )
  }

}
