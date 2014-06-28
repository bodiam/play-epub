curl -XGET 'http://localhost:9200/books/_search?q=title:Alice%20in%20Wonderland'

curl -XGET 'http://localhost:9200/books/_search?q=language:en'

curl -XGET 'http://localhost:9200/books/_search?pretty=true' -d '
{
    "query" : {
        "range" : {
            "pages" : {
                "from" : "10",
                "to" : "200",
                "include_lower" : true,
                "include_upper": true
            }
        }
    }
}
'

curl -XGET --globoff 'localhost:9200/books/_search?q=pages:["100"+TO+"200"]&pretty=true'