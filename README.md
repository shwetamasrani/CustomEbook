# CustomEbook

APIs:
### 1. To add a new book, it also checks whether the ISBN number exists or not.
POST: localhost:8081/api/books
#### Request Body:
```
{
 "bookName":"Ayushi",
 "isbnNumber":"ryikn7456981230",
 "author":"Neha Kothari",
 "publisher":"Onkita",
 "yearOfRelease":"2018",
 "price":"500",
 "description":"BFFS"    
}
```


### 2. To retrieve a book for a specific ISBN
GET: localhost:8081/api/books/isbn/712

#### Response Body(if book exists & is not split):

```{
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
```
 
### 3. If the seller wants to split a book
POST: localhost:8081/api/books/split
   
#### Request Body:

```{
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
```
### 4. Retrieve book details using BookId(the response is when the book is split) :
GET: localhost:8081/api/books/1

#### Response Body:
```
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
   "bookChapters": [
   {
   "chapterNumber": 1,
   "chapterName": "Hello",
   "price": 85.0,
   "startPage": 10,
   "endPage": 20,
   "description": "Tata World!"
   },
   {
   "chapterNumber": 5,
   "chapterName": "Tata",
   "price": 8650.0,
   "startPage": 35,
   "endPage": 50,
   "description": "BABABABABABABABABABA"
   }
   ]
   }
``` 

### 5. Adding an item to cart
POST: localhost:8081/api/cart/add
   #### Request Body:
   ```
   {
   "userId":"2",
   "itemDetails": {
   "bookId":"2",
   "chapterNumber":"3",
   "price":"50"
   }
   }
   ```
   
  #### Response Body:
  ```
   {
   "orderId": 7,
   "userId": 2,
   "totalNumberOfItems": 2
   }
   ```
### 6. Getting all the details of the item in cart
GET: localhost:8081/api/cart/orders/7
   #### Response Body:
```{
"orderId": 7,
"userId": 2,
"customEBookName": null,
"orderItems": [
{
"bookId": 2,
"bookName": "DevOps For Dummies",
"bookLocation": "/home/nehakothari/Desktop/Semester2/CS-605DataModeling/Project/Books/2.pdf",
"chapterNumber": 1,
"price": 85.0,
"startPage": 10,
"endPage": 20,
"chapterDescription": "DevOps Introduction!"
},
{
"bookId": 2,
"bookName": "DevOps For Dummies",
"bookLocation": "/home/nehakothari/Desktop/Semester2/CS-605DataModeling/Project/Books/2.pdf",
"chapterNumber": 3,
"price": 8650.0,
"startPage": 35,
"endPage": 50,
"chapterDescription": "Why DevOps was Introdcued?"
}
]
}
 ```
### 7. Checkout
POST: localhost:8081/api/cart/checkout
   #### RequestBody:
   ```
   {
   "orderId":"6",
   "userId":"1",
   "customEBookName":"Sample stuff 3",
   "orderItems": [
   {
   "bookId":"2",
   "chapterNumber": 2,
   "price": 8650.0,
   "startPage": 35,
   "endPage": 50,
   "bookLocation":"/home/nehakothari/Desktop/Semester2/CS-605DataModeling/Project/Books/1.pdf"
   },
   {
   "bookId":"1",
   "chapterNumber": 1,
   "chapterName": "Introduction",
   "price": 85.0,
   "startPage": 10,
   "endPage": 20,
   "bookLocation":"/home/nehakothari/Desktop/Semester2/CS-605DataModeling/Project/Books/1.pdf"
   }
   ]
}
```
   #### Response Body:
   ```
   {
   "orderId": 6,
   "mergedPdfLocation": "/home/nehakothari/Desktop/Semester2/CS-605DataModeling/Project/Books/Order-6.pdf",
   "totalPrice": 50.0
   }
```

