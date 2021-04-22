import axios from 'axios';

const BOOK_API_BASE_URL="http://localhost:8081/api/books"

class BookService{

    getBook()
    {
        return axios.get(BOOK_API_BASE_URL);   //get the data from the API mentioned
    }

    createBook(book)
    {
        console.log("Creating book", book);
        return axios.post(BOOK_API_BASE_URL,book);

    }
}
export default new BookService()   //exporting the object of this class