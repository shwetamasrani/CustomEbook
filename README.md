# CustomEbook

APIs:
1. To add a new book, it also checks whether the ISBN number exists or not.
POST: localhost:8081/api/books
Request Body:
{
"bookName":"Ayushi",
"isbnNumber":"ryikn7456981230",
"author":"Neha Kothari",
"publisher":"Onkita",
"yearOfRelease":"2018",
"price":"500",
"description":"BFFS"    
}


2. To retrieve a book for a specific ISBN
GET: localhost:8081/api/books/isbn/712

Response Body(if book exists):

{
"bookId": 1,
"bookName": "Ayushi",
"isbnNumber": "ryikn7456981230",
"author": "Neha Kothari",
"publisher": null,
"yearOfRelease": 2018,
"price": 500.0,
"imageLocation": null,
"description": null,
"pdfFileLocation": null,
"bookChapters": null
}
 
3. If the seller wants to split a book
POST: localhost:8081/api/books/split
   
Request Body:
{
"bookId": "1",
"bookChapters": [
{
"chapterNumber": "1",
"chapterName": "Hello",
"price": "85",
"startPage": "10",
"endPage": "20",
"description": "Tata World!"
},
{
"chapterNumber": "5",
"chapterName": "Tata",
"price": "8650",
"startPage": "35",
"endPage": "50",
"description": "BABABABABABABABABABA"
}
]
}
