import axios from 'axios';

const BOOK_API_BASE_URL="http://localhost:8081/api/books"
const BOOK_GET_API_BASE_URL="http://localhost:8081/api/books/isbn"
const BOOK_SPLIT_APT_BASE_URL="http://localhost:8081/api/books/split"

class BookService{

    // getBookByIsbn(isbn)
    // {
    //     return axios.get(BOOK_API_BASE_URL+'/'+isbn);   //get the data from the API mentioned
    // }

   
    splitBookChapters(book_split)
    {
       return axios.post(BOOK_SPLIT_APT_BASE_URL,book_split)
    }
   
       
    // createBook(book)
    // {
    // //     console.log("service-Creating book", book);
    //    axios.post(BOOK_API_BASE_URL,book).then( (res) => {

    //        console.log("service-result",res.data.bookId)
    //     //    console.log("service-response",response.data    )
    //     //    return response.data
    //        return res.data;
    // });

     createBook = async (book) => {
        try {
            const resp = await axios.post(BOOK_API_BASE_URL,book);
            console.log(resp.data);
            return resp.data
        } catch (err) {
            // Handle Error Here
            console.error(err);
        }
    };
   
    }
    

export default new BookService()   //exporting the object of this class