package models

import java.util.Date

case class User(age: Int,
                firstName: String,
                lastName: String,
                active: Boolean)

case class Book(source: String, title: String, language: String,
                isbn10: String, isbn13: String, summary: String, contents: String, publisher: String,
                publicationDate: Date, dateAdded: Date, numberOfPages: Int, authors: Seq[Author] /*, tags: Seq[Tag]*/)

case class Author(name: String)

case class Tag(text: String)


object JsonFormats {

  import play.api.libs.json.Json

  // Generates Writes and Reads for Feed and User thanks to Json Macros
  implicit val userFormat = Json.format[User]

  implicit val authorFormat = Json.format[models.Author]
  implicit val bookFormat = Json.format[Book]
}