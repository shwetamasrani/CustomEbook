import React, { Component } from 'react'
import BookService from "../services/BookService"

class AddChapterDetails extends Component {

    constructor(props) {
        super(props);
        this.state = {

            bookId:this.props.location.state.bookId,
            chapterNumber:"",
            chapterName:"",
            price:"",
            startPage:"",
            endPage:"",
            description:"",
            chapterArray:[]
        }
        this.handleChange = this.handleChange.bind(this)
        this.onSubmit=this.onSubmit.bind(this)
        this.onAddChapter=this.onAddChapter.bind(this)
    }

    handleChange(event) {
        const {name, value} = event.target;
        this.setState({
            [name]: value
        })
    }

    onAddChapter=(c)=>{
        c.preventDefault()
    // let chapter={chapterNumber:this.state.chapterNumber,chapterName:this.state.chapterName,
    //         price:this.state.price ,startPage:this.state.startPage, 
    //         endPage:this.state.endPage, description:this.state.description};

            // console.log('chapter => ' + JSON.stringify(chapter));

            // this.state.chapterArray.push({chapter}); 

            this.state.chapterArray.push({chapterNumber:this.state.chapterNumber,chapterName:this.state.chapterName,
                price:this.state.price ,startPage:this.state.startPage, 
                endPage:this.state.endPage, description:this.state.description}); 

         console.log("chapter-array",this.state.chapterArray)

    }


    onSubmit = (b) => {
        b.preventDefault();

        let book_split = {
          bookId:this.state.bookId,bookChapters:this.state.chapterArray
        };
        console.log('book_split => ' + JSON.stringify(book_split));

        // console.log('User =>' + JSON.stringify(user));

        BookService.splitBookChapters(book_split).then(res => {
            // this.props.history.push('/Signin');
        });
    }


    componentDidMount(){
       
    
      console.log("in add chapter",this.props.location.state.bookId)
    }
    render() {

        return (
        
            <div>
                
                <div className="chapter">
                     <h2>
                       Add Chapter details here
                    </h2>
                    <form>
                    <input
                            type="number"
                            name="bookId"
                            required="True"
                            placeholder="Book Id"
                            value={this.state.bookId}
                            onChange={this.handleChange}/>
                            
                            <hr/>
                            <div>
                            <form>

                        <input
                            type="number"
                            name="chapterNumber"
                            required="True"
                            placeholder="Chapter Number"
                            //value={this.state.bookName}
                            onChange={this.handleChange}/>
                        <input
                            type="text"
                            name="chapterName"
                            required="True"
                            placeholder="Chapter Name"
                            //value={this.state.bookName}
                            onChange={this.handleChange}/>
                        <input
                            type="number"
                            name="price"
                            required="True"
                            placeholder="Chapter Price"
                            //value={this.state.chapterPrice}
                            onChange={this.handleChange}/>
                             <input
                            type="number"
                            name="startPage"
                            required="True"
                            placeholder="Start Page"
                            //value={this.state.chapterPrice}
                            onChange={this.handleChange}/>
                             <input
                            type="number"
                            name="endPage"
                            required="True"
                            placeholder="End Page"
                            //value={this.state.chapterPrice}
                            onChange={this.handleChange}/>
                             <input
                            type="text"
                            name="description"
                            required="True"
                            placeholder="Description"
                            //value={this.state.chapterPrice}
                            onChange={this.handleChange}/>
                             <button type="submit" onClick={this.onAddChapter}>
                            Add Chapter!
                        </button>
                            </form>
                            </div>
                     
                     <div>
                        <button type="submit" onClick={this.onSubmit}>
                            Submit! 
                        </button>
                       
                        </div>
                    </form>
                </div>
                </div>
        );
    }
}
export default AddChapterDetails