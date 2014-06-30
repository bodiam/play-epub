
class CreateBookCtrl

    constructor: (@$log, @$location, @BookService) ->
        @$log.debug "constructing CreateBookController"
        @book = {}

    createBook: () ->
        @$log.debug "createBook()"
        @book.active = true
        @BookService.createBook(@book)
        .then(
            (data) =>
                @$log.debug "Promise returned #{data} Book"
                @book = data
                @$location.path("/")
            ,
            (error) =>
                @$log.error "Unable to create Book: #{error}"
            )


controllersModule.controller('CreateBookCtrl', CreateBookCtrl)