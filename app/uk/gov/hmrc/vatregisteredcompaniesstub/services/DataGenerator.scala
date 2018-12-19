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

package uk.gov.hmrc.vatregisteredcompaniesstub.services

import cats.implicits._
import org.scalacheck.Gen
import uk.gov.hmrc.smartstub._
import uk.gov.hmrc.vatregisteredcompaniesstub.models.{Address, Payload, VatRegisteredCompany}


object DataGenerator {

  private val minElements = 0
  private val maxElements = 1000

  private def companyName: Gen[String] = Gen.company

  private def vatNumber: Gen[String] = for {
    short <- pattern"999999999"
    long <- pattern"999999999999"
    vatNumber <- Gen.oneOf(short, long)
  } yield vatNumber

  private def address: Gen[Address] = Gen.ukAddress.map{ x =>
    val lines = x.dropRight(2)
    Address(
      lines.head,
      lines.get(1),
      lines.get(2),
      lines.get(3),
      lines.get(4),
      x.last.some,
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
    b <- Gen.choose(minElements, maxElements).flatMap(Gen.listOfN(_, vatNumber))
  } yield Payload(a, b)

  def generateData: Payload = payload.seeded(1L).get // TODO figure out what the seed should be

}