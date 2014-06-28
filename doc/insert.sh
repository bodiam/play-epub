curl -XPUT 'http://localhost:9200/books/book/1' -d '{
    "title": "Alice in Wonderland",
    "author": "Lewis Carol",
    "publication_date": "1862-01-01",
    "language": "en"
}'

curl -XPUT 'http://localhost:9200/books/book/2' -d '{
    "title" : "Peter Pan",
    "author" : "J. M. Barrie",
    "publication_date" : "1862-01-01",
    "pages": 160
}'

curl -XPUT 'http://localhost:9200/books/book/3' -d '{
    "title" : "Mary Poppins",
    "author" : "Dr. P. L. Travers",
    "pages" : 224,
    "language": "en"
}'

curl -XPUT 'http://localhost:9200/books/book/4' -d '{
    "title" : "The Borrowers",
    "author" : "Mary Norton",
    "pages" : 192,
    "publication_date" : "1953-01-01",
    "language": "en"
}'



#curl -XPUT 'http://localhost:9200/twitter/tweet/1' -d '{
#    "user" : "kimchy",
#    "post_date" : "2009-11-15T14:12:12",
#    "message" : "trying out Elasticsearch"
#}'