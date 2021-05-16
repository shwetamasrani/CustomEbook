import React, {Component} from 'react'
import BookService from "../services/BookService"
import {Link} from "react-router-dom";

class AddChapterDetails extends Component {

    constructor(props) {
        super(props);
        this.state = {

            bookId: this.props.location.bookId,
            totalChapter: this.props.location.totalChapter,
            chapterNumber: 1,
            chapterName: "",
            price: "",
            startPage: "",
            endPage: "",
            description: "",
            chapterArray: [],
            isSubmit: false,
            isAdd: true
        }
        this.handleChange = this.handleChange.bind(this)
        this.onSubmit = this.onSubmit.bind(this)
        this.onAddChapter = this.onAddChapter.bind(this)
        this.logout=this.logout.bind(this)
    }
    logout(){
        alert("...Logging out")
    }

    handleChange(event) {
        const {name, value} = event.target;
        this.setState({
            [name]: value
        })
    }

    onAddChapter = (c) => {
        c.preventDefault()
        
        // let chapter={chapterNumber:this.state.chapterNumber,chapterName:this.state.chapterName,
        //         price:this.state.price ,startPage:this.state.startPage,
        //         endPage:this.state.endPage, description:this.state.description};

        // console.log('chapter => ' + JSON.stringify(chapter));

        // this.state.chapterArray.push({chapter});
        console.log("total",this.state.totalChapter);
        console.log(this.state.chapterNumber)
        this.state.chapterArray.push({
            chapterNumber: this.state.chapterNumber, 
            chapterName: this.state.chapterName,
            price: this.state.price, 
            startPage: this.state.startPage,
            endPage: this.state.endPage, 
            description: this.state.description
        });

        Array.from(document.querySelectorAll("input")).forEach(
            input => (input.value = "")
          );

        if(this.state.isSubmit  )
        {
              this.onSubmit();
        }
        //console.log("chapter-array", this.state.chapterArray)
        this.setState({
            chapterNumber: this.state.chapterNumber+1,
        })
        // console.log("total afer push",this.state.totalChapter);
        // console.log(this.state.chapterNumber)
        // console.log("Length",this.state.chapterArray.length)
        if(this.state.chapterNumber == this.state.totalChapter-1)
        {
            this.setState({
                isSubmit: true,
                isAdd: false
            })
        }

    }


    async onSubmit(){
        // b.preventDefault();

        console.log(this.state.bookId)
        let book_split = {
            bookId: this.state.bookId, 
            bookChapters: this.state.chapterArray
        };
        console.log('book_split => ' + JSON.stringify(book_split));

        // console.log('User =>' + JSON.stringify(user));

       await BookService.splitBookChapters(book_split).then((res) => {
            // this.props.history.push('/Signin');
            console.log(res.data)
            alert("Submitted Successfully!");
            this.props.history.push({pathname:'/AdminDashboard'});
        }).catch((err)=>{
            alert(err)
        });


    }


    componentDidMount() {


        console.log("in add chapter", this.props.location.bookId)
        console.log(this.state.totalChapter)
    }

    render() {

        return (

            <div>
                <div className="Navbar">
                    <nav>
                        <ul>
                            <li ><Link to="/">Home</Link></li>
                            <li><Link to="/SignUp">About</Link></li>
                            <li><Link to="/AdminDashboard">Dashboard</Link></li>
                            <li><Link to="/" onClick={this.logout}>Logout</Link></li>
                        </ul>
                    </nav>
                </div>

                <div className="chapter">
                    <h2>
                        Add Chapter details here
                    </h2>
                    <form>

                        <div>
                            <form>
                                <label>Chapter Number:</label>
                                <input
                                    type="number"
                                    name="chapterNumber"
                                    required="True"
                                    placeholder="Chapter Number"
                                    value={this.state.chapterNumber}
                                    // onChange={this.handleChange}/>
                                    readOnly
                                    />
                                
                                <label>Chapter Name:</label>
                                <input
                                    type="text"
                                    name="chapterName"
                                    required="True"
                                    placeholder="Chapter Name"
                                    //value={this.state.bookName}
                                    onChange={this.handleChange}/>

                                <label>Price:</label>
                                <input
                                    type="number"
                                    name="price"
                                    required="True"
                                    placeholder="Chapter Price"
                                    //value={this.state.chapterPrice}
                                    onChange={this.handleChange}/>

                                <label>Start Page:</label>
                                <input
                                    type="number"
                                    name="startPage"
                                    required="True"
                                    placeholder="Start Page"
                                    //value={this.state.chapterPrice}
                                    onChange={this.handleChange}/>

                                <label>End Page:</label>
                                <input
                                    type="number"
                                    name="endPage"
                                    required="True"
                                    placeholder="End Page"
                                    //value={this.state.chapterPrice}
                                    onChange={this.handleChange}/>

                                <label>Description: </label>
                                <input
                                    type="text"
                                    name="description"
                                    required="True"
                                    placeholder="Description"
                                    //value={this.state.chapterPrice}
                                    onChange={this.handleChange}/>

                                {this.state.isAdd && (
                                    <button type="submit" onClick={this.onAddChapter}>
                                        Add Next Chapter!
                                    </button>
                                )}    
                                
                            </form>
                        </div>

                        <div>
                        {this.state.isSubmit && (
                            <button type="submit" onClick={this.onAddChapter}>
                                Add last Chapter!
                            </button>
                        )}  
                        </div>
                    </form>
                </div>
            </div>
        );
    }
}

export default AddChapterDetails
