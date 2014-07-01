package controllers

import java.util.Date

import scala.concurrent._
import duration._
import org.specs2.mutable._

import play.api.libs.json._
import play.api.test._
import play.api.test.Helpers._
import java.util.concurrent.TimeUnit


/**
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class BooksIT extends Specification {

  val timeout: FiniteDuration = FiniteDuration(5, TimeUnit.SECONDS)

  "Books" should {

    "insert a valid json" in {
      running(FakeApplication()) {
        val request = FakeRequest.apply(POST, "/book").withJsonBody(Json.obj(
          "source" -> "Google Books",
          "title" -> "Alice in Wonderland",
          "author" -> "Lewis Caroll",
          "language" -> "en",
          "isbn10" -> "1234567890",
          "isbn13" -> "1234567890123",
          "summary" -> "Alice in Wonderland by Lewis Carroll is a fictional story about a young girl named Alice who spots an interesting white rabbit, muttering about being late, that she follows down a rabbit hole into a magical world. When she enters the unknown world, she finds a table with a tiny gold key, a small door and a bottle of potion with an attached note that says \"Drink me.\"",
          "contents" -> "lots of text here...",
          "publisher" -> "some publisher",
          "publicationDate" -> new Date(),
          "dateAdded" -> new Date(),
          "numberOfPages" -> 100
        ))
        val response = route(request)
        response.isDefined mustEqual true
        val result = Await.result(response.get, timeout)
        result.header.status must equalTo(CREATED)
      }
    }

    "fail inserting a non valid json" in {
      running(FakeApplication()) {
        val request = FakeRequest.apply(POST, "/book").withJsonBody(Json.obj(
          "source" -> 123,
          "title" -> "Alice in Wonderland"
        ))
        val response = route(request)
        response.isDefined mustEqual true
        val result = Await.result(response.get, timeout)
        contentAsString(response.get) mustEqual "invalid json"
        result.header.status mustEqual BAD_REQUEST
      }
    }

  }

}