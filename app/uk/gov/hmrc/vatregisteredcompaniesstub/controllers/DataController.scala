package uk.gov.hmrc.vatregisteredcompaniesstub.controllers

import play.api.mvc.Result
import uk.gov.hmrc.play.microservice.controller.BaseController
import uk.gov.hmrc.vatregisteredcompaniesstub.models.Payload
import uk.gov.hmrc.vatregisteredcompaniesstub.services.{DataGenerator, JsonSchemaChecker}

import scala.concurrent.Future

class DataController extends BaseController {

  def triggerPost: Future[Result] = {
    // gen some data - make sure it conforms to schema - make sure it's json < 10mb - send to backend
    val payload = DataGenerator.generateData
    // validate payload against accepted schema see:
    // soft-drinks-industry-levy/app/uk/gov/hmrc/softdrinksindustrylevy/services/JsonSchemaChecker.scala
    JsonSchemaChecker[Payload](payload, "mdg-payload")
    Future.successful(Ok("triggered!"))
  }

}
