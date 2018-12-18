package uk.gov.hmrc.vatregisteredcompaniesstub.services

import org.scalacheck.Gen
import uk.gov.hmrc.smartstub._
import uk.gov.hmrc.vatregisteredcompaniesstub.models.{Address, Payload}


object DataGenerator {

  private def companyName: Gen[String] = Gen.company
  private def postcode: Gen[String] = Gen.postcode
  private def vatNumber: Gen[String] = ??? // see sdilNumber gen
  private def address: Gen[Address] = ???
  //  val address: Gen[List[String]] = Gen.ukAddress // can't do this, need to create our own

  private def payload: Gen[Payload] = ???
  def generateData: Payload = payload.seeded(1L).get // TODO figure out what the seed should be

}