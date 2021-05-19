import axios from 'axios';

const BOOK_API_BASE_URL="http://localhost:8081/api/books"
const BOOK_GET_API_BASE_URL="http://localhost:8081/api/books/isbn"
const BOOK_SPLIT_APT_BASE_URL="http://localhost:8081/api/books/split"
const BOOK_API_BASE_URL_FILE="http://localhost:8081/api/books/savePdfFile"
class BookService{

    // getBookByIsbn(isbn)
    // {
    //     return axios.get(BOOK_API_BASE_URL+'/'+isbn);   //get the data from the API mentioned
    // }

   
    splitBookChapters(book_split)
    {
       return axios.put(BOOK_SPLIT_APT_BASE_URL,book_split)
    }
    savePdfFile(request)
    {
        console.log("in service",request)
        console.log("in service",request)
        return axios.post(BOOK_API_BASE_URL_FILE,request)
        // axios({
        //     method: "post",
        //     url: BOOK_API_BASE_URL_FILE,
        //     data: pdfFile,
        //     headers: { "Content-Type": "multipart/form-data" },
        //   })
        //     .then(function (response) {
        //       //handle success
        //       console.log(response);
        //     })
        //     .catch(function (response) {
        //       //handle error
        //       console.log(response);
        //     });
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
         console.log("in createBook-service",book)
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
