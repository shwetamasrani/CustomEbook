import React, {Component} from "react"
import productsInfo from "./Data/productInfo";
import axios from 'axios';
import Dashboard from "./Dashboard";
import BookService from "../services/BookService"
import AddChapterDetails from "./AddChapterDetails";

class AdminDashboard extends Component {

    constructor(props) {
        super(props);
        this.state = {
            bookName: "",
            isbnNumber: "",
            author: "",
            yearOfRelease:"",
            publisher: "",
            price: "",
            noOfChapters: "",
            description:"",
            imageLocation:"",
            pdfFileLocation:""
        
        }
        this.handleChange = this.handleChange.bind(this)
    }
    handleChange(event) {
      const {name, value} = event.target;
      this.setState({
          [name]: value
      })
  }

  onFileUpload = (e) => {
    e.preventDefault();
    let book = {
      bookName: this.state.bookName, isbnNumber: this.state.isbnNumber, author: this.state.author,
      publisher: this.state.publisher, price: this.state.price, noOfChapters: this.state.noOfChapters,
      description: this.state.description ,imageLocation: this.state.imageLocation,
      pdfFileLocation: this.state.pdfFileLocation,yearOfRelease:this.state.yearOfRelease
    }

    console.log('Book =>' + JSON.stringify(book));

    BookService.createBook(book).then((res) => {
       
       
        console.log("admindash-data.bookid",res.bookId)
        this.props.history.push({pathname:'/AddChapterDetails',state: {
          bookId: res.bookId}});
    });

    // const book_result= BookService.createBook(book)
    // console.log("admin-dashboard",book_result)
}

    // state = {
    //     // Initially, no file is selected
    //     selectedFile: null
    // };

    // // On file select (from the pop up)
    // onFileChange = event => {
    //     // Update the state
    //     this.setState({selectedFile: event.target.files[0]});
    // };


    // // On file upload (click the upload button)
    // onFileUpload = () => {

    //     // Create an object of formData
    //     const formData = new FormData();

    //     // Update the formData object
    //     formData.append(
    //         "myFile",
    //         this.state.selectedFile,
    //         this.state.selectedFile.name,
    //     );

    //     // Details of the uploaded file
    //     console.log(this.state.selectedFile);

    //     // Request made to the backend api
    //     // Send formData object
    //     axios.post("api/uploadfile", formData);     //update the api path in future
    // };

    // // File content to be displayed after
    // // file upload is complete
    // fileData = () => {

    //     if (this.state.selectedFile) {
    //         return (
    //             <div>
    //                 <h2>File Details:</h2>
    //                 <p>File Name: {this.state.selectedFile.name}</p>
    //                 <p>File Type: {this.state.selectedFile.type}</p>
    //                 <p>File Location:{this.state.selectedFile}</p>
    //                 <p>
    //                     Last Modified:{" "}
    //                     {this.state.selectedFile.lastModifiedDate.toDateString()}
    //                 </p>
    //             </div>
    //         );
    //     } else {
    //         return (
    //             <div>
    //                 <br/>
    //                 <h4>Choose the file before Pressing the Upload button</h4>
    //             </div>
    //         );
    //     }
    // };

    render() {
        return (
            <div>
                <div className="bookUpload">
                    <h2>
                        Upload the Ebook here
                    </h2>
                    <div>
                        <form>
                             <input
                                type="number"
                                name="isbnNumber"
                                required="True"
                                placeholder="ISBN Number"
                                value={this.state.isbnNumber}
                                onChange={this.handleChange}/>

                            <input type="text"
                                   name="bookName"    //need to add change handler function for name
                                   required="True"
                                   placeholder="Book Name"
                                   value={this.state.bookName}
                                   onChange={this.handleChange}/>
                           
                            <input
                                type="text"
                                name="author"
                                required="True"
                                placeholder="Author name"
                                value={this.state.author}
                                onChange={this.handleChange}/>
                            <input
                                type="text"
                                name="publisher"
                                required="True"
                                placeholder="Publisher"
                                value={this.state.publisher}
                                onChange={this.handleChange}/>
                            <input
                                type="number"
                                name="yearOfRelease"
                                required="True"
                                placeholder="Year of Release"
                                value={this.state.yearOfRelease}
                                onChange={this.handleChange}/>
                            <input
                                type="number"
                                name="price"
                                required="True"
                                placeholder="Price"
                                value={this.state.price}
                                onChange={this.handleChange}/>

                            <input
                                type="number"
                                name="noOfChapters"
                                required="True"
                                placeholder="Number of Chapters"
                                value={this.state.noOfChapters}
                                onChange={this.handleChange}/> 

                            <input
                                type="text"
                                name="description"
                                required="True"
                                placeholder="Description"
                                value={this.state.description}
                                onChange={this.handleChange}/>

                            <input
                                type="text"
                                name="imageLocation"
                                required="True"
                                placeholder="Image Location"
                                value={this.state.imageLocation}
                                onChange={this.handleChange}/>

                            <input
                                type="text"
                                name="pdfFileLocation"
                                required="True"
                                placeholder="PDF Location"
                                value={this.state.pdfFileLocation}
                                onChange={this.handleChange}/>




                            {/* <h3>Upload Book Cover:</h3>
                            <input type="file" onChange={this.onFileChange}/> */}
                            <button onClick={this.onFileUpload}>
                                Upload!
                            </button>
                        </form>
                    </div>
                    {/* {this.fileData()} */}
                </div>

                <div className="box hide">

                </div>
            </div>
        );
    }
}

export default AdminDashboard