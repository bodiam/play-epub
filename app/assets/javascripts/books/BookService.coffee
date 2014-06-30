
class BookService

    @headers = {'Accept': 'application/json', 'Content-Type': 'application/json'}
    @defaultConfig = { headers: @headers }

    constructor: (@$log, @$http, @$q) ->
        @$log.debug "constructing BookService"

    listBooks: () ->
        @$log.debug "listBooks()"
        deferred = @$q.defer()

        @$http.get("/books")
        .success((data, status, headers) =>
                @$log.info("Successfully listed Books - status #{status}")
                deferred.resolve(data)
            )
        .error((data, status, headers) =>
                @$log.error("Failed to list Books - status #{status}")
                deferred.reject(data);
            )
        deferred.promise

    createBook: (book) ->
        @$log.debug "createBook #{angular.toJson(book, true)}"
        deferred = @$q.defer()

        @$http.post('/book', book)
        .success((data, status, headers) =>
                @$log.info("Successfully created Book - status #{status}")
                deferred.resolve(data)
            )
        .error((data, status, headers) =>
                @$log.error("Failed to create book - status #{status}")
                deferred.reject(data);
            )
        deferred.promise

servicesModule.service('BookService', BookService)