package models

import java.util.Date

case class Book(source:String, title: String, language: String,
    isbn10: String, isbn13: String, summary: String, contents: String, publisher: String,
publicationDate: Date, dateAdded: Date, numberOfPages: Int, authors: Seq[Author], tags: Seq[Tag]) {

}

case class Author(name: String)

case class Tag(text: String)
