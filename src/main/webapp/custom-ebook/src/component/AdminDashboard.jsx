import React,{Component} from "react"
import productsInfo from "./Data/productInfo";
import axios from 'axios';
import Dashboard from "./Dashboard";

class AdminDashboard extends Component{
      
        state = {
     
          // Initially, no file is selected
          selectedFile: null
         
        };
        
        // On file select (from the pop up)
        onFileChange = event => {
        
          // Update the state
          this.setState({ selectedFile: event.target.files[0] });
        
        };
        
        // On file upload (click the upload button)
        onFileUpload = () => {
        
          // Create an object of formData
          const formData = new FormData();
        
          // Update the formData object
          formData.append(
            "myFile",
            this.state.selectedFile,
            this.state.selectedFile.name,
            
          );
        
          // Details of the uploaded file
          console.log(this.state.selectedFile);
        
          // Request made to the backend api
          // Send formData object
          axios.post("api/uploadfile", formData);     //update the api path in future
        };
        
        // File content to be displayed after
        // file upload is complete
        fileData = () => {
        
          if (this.state.selectedFile) {
             
            return (
              <div>
                <h2>File Details:</h2>
                 
    <p>File Name: {this.state.selectedFile.name}</p>
     
                 
    <p>File Type: {this.state.selectedFile.type}</p>
     
                 
    <p>
                  Last Modified:{" "}
                  {this.state.selectedFile.lastModifiedDate.toDateString()}
                </p>
     
              </div>
            );
          } else {
            return (
              <div>
                <br />
                <h4>Choose the file before Pressing the Upload button</h4>
              </div>
            );
          }
        };
        
        render() {
        
          return (
              <div>
            <div className="bookUpload">
               
                <h2>
                 Upload the Ebook here
                </h2>
                <div>
                    <input type="name"  name="email"    //need to add change handler function for name
                            required="True" placeholder="Book Name" onChange={this.handleChange}></input>
                    <input type="file" onChange={this.onFileChange} />   
                    <button onClick={this.onFileUpload}>
                      Upload!
                    </button>
                </div>
              {this.fileData()}
            </div>
            </div>
          );
        }
    }
export default AdminDashboard