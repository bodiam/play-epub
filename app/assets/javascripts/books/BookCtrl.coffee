
class BookCtrl

    constructor: (@$log, @BookService) ->
        @$log.debug "constructing BookController"
        @books = []
        @getAllBooks()

    getAllBooks: () ->
        @$log.debug "getAllBooks()"

        @BookService.listBooks()
        .then(
            (data) =>
                @$log.debug "Promise returned #{data.length} Books"
                @books = data
            ,
            (error) =>
                @$log.error "Unable to get Books: #{error}"
            )


controllersModule.controller('BookCtrl', BookCtrl)