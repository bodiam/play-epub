package controllers

import javax.inject.Singleton

import org.slf4j.{Logger, LoggerFactory}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.mvc._
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.Cursor

import scala.concurrent.Future

/**
 * The Books controllers encapsulates the Rest endpoints and the interaction with the MongoDB, via ReactiveMongo
 * play plugin. This provides a non-blocking driver for mongoDB as well as some useful additions for handling JSon.
 * @see https://github.com/ReactiveMongo/Play-ReactiveMongo
 */
@Singleton
class Books extends Controller with MongoController {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[Books])

  /*
   * Get a JSONCollection (a Collection implementation that is designed to work
   * with JsObject, Reads and Writes.)
   * Note that the `collection` is not a `val`, but a `def`. We do _not_ store
   * the collection reference to avoid potential problems in development with
   * Play hot-reloading.
   */
  def collection: JSONCollection = db.collection[JSONCollection]("books")

  // ------------------------------------------ //
  // Using case classes + Json Writes and Reads //
  // ------------------------------------------ //

  import models.JsonFormats._
  import models._

  def createBook = Action.async(parse.json) {
    request =>
      /*
       * request.body is a JsValue.
       * There is an implicit Writes that turns this JsValue as a JsObject,
       * so you can call insert() with this JsValue.
       * (insert() takes a JsObject as parameter, or anything that can be
       * turned into a JsObject using a Writes.)
       */
      request.body.validate[Book].map { book =>
        collection.insert(book).map {
          lastError =>
            logger.debug(s"Successfully inserted with LastError: $lastError")
            Created(s"Book Created")
        }
      }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  // {"summary":"dd","isbn13":"ddd","active":true,"title":"sss","author":{"name":"sss"},"language":"sss","isbn10":"sss"}

  def findBooks = Action.async {
    // let's do our query
    val cursor: Cursor[Book] =
      collection.find(Json.obj("active" -> true))
        .sort(Json.obj("created" -> -1))
        .cursor[Book]

    // gather all the JsObjects in a list
    val futureBooksList: Future[List[Book]] = cursor.collect[List]()

    // transform the list into a JsArray
    val futureBookJsonArray: Future[JsArray] = futureBooksList.map { books =>
      Json.arr(books)
    }
    // everything's ok! Let's reply with the array
    futureBookJsonArray.map {
      books =>
        Ok(books(0))
    }
  }
}
