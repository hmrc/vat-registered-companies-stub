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

package uk.gov.hmrc.vatregisteredcompaniesstub.models

import play.api.libs.json.{Json, OFormat}


case class Address(
  line1: String,
  line2: Option[String],
  line3: Option[String],
  line4: Option[String],
  line5: Option[String],
  postcode: Option[String],
  countryCode: String
)

object Address {
  implicit val addressFormat: OFormat[Address] =
    Json.format[Address]
}

//case class VatNumber(
//  vatNumber: String
//)
//
//object VatNumber {
//  implicit val vatNumberFormat: OFormat[VatNumber] =
//    Json.format[VatNumber]
//}

case class VatRegisteredCompany(
  name: CompanyName,
  vatNumber: VatNumber,
  address: Address
)

object VatRegisteredCompany {
  implicit val vatRegisteredCompanyFormat: OFormat[VatRegisteredCompany] =
    Json.format[VatRegisteredCompany]
}

case class Payload(
  createsAndUpdates: List[VatRegisteredCompany],
  deletes: List[VatNumber]
)

object Payload {
  implicit val payloadFormat: OFormat[Payload] =
    Json.format[Payload]
}




