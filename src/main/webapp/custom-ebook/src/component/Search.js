import React, {Component} from "react"
import Products from "./Products";
import productsInfo from "./Data/productInfo";
import {Link} from "react-router-dom";
import axios from "axios";

class Search extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: false,
            productInfo: [],
            bookName : '',
            searchBy: 'isbn',
            userId:this.props.location.userId
        }
        this.setStateToFalse = this.setStateToFalse.bind(this)
        this.setBookName = this.setBookName.bind(this);
        this.setSearchBy = this.setSearchBy.bind(this);
        this.logout=this.logout.bind(this)
        this.HandleClick = this.HandleClick.bind(this);

    }
    logout(){
        localStorage.removeItem('userId');
    }

    setStateToFalse() {
        this.state({
            isLoading: false
        })
    }
    setBookName(event){
        this.setState({bookName: event.target.value});
    }
    setSearchBy(event){
        this.setState({searchBY : event.target.value});
    }


    HandleClick(event) {
        event.preventDefault();
        console.log(this.state.searchBy);
        if(this.state.searchBy === "bookName"){
            axios.get('http://localhost:8081/api/books/bookName/' + this.state.bookName)
                .then(response =>{
                    // console.log(response);
                    console.log(response.data);
                    let status = response.status;
                    if (status === 200) {
                        console.log("successful")
                        console.log("isbn");
                    }
                    let tempProductInfo =  response.data;

                    /* if (this.state.userId === undefined) {
                         this.setState({
                             userId: (localStorage.getItem('userId')),
                         })
                     } else {
                         console.log("hi")
                         localStorage.setItem('userId', JSON.stringify(this.state.userId));
                     }
     */
                    this.setState({
                        productInfo: tempProductInfo,
                        isLoading: false
                    });
                    //this.props.history.push(`/location/${this.state.location}`);
                })
                .catch(error =>{
                    console.log(error)
                })}
        else if(this.state.searchBy === "publisher"){
            axios.get('http://localhost:8081/api/books/publisher/' + this.state.bookName)
                .then(response =>{
                    // console.log(response);
                    console.log(response.data);
                    let status = response.status;
                    if (status === 200) {
                        console.log("successful")
                        console.log("bookName");
                    }
                    let tempProductInfo =  response.data;

                    /*if (this.state.userId === undefined) {
                        this.setState({
                            userId: (localStorage.getItem('userId')),
                        })
                    } else {
                        console.log("hi")
                        localStorage.setItem('userId', JSON.stringify(this.state.userId));
                    }*/

                    this.setState({
                        productInfo: tempProductInfo,
                        isLoading: false
                    });
                    //this.props.history.push(`/location/${this.state.location}`);
                })
                .catch(error =>{
                    console.log(error)
                })
        }
        else if(this.state.searchBy === "isbn"){
            axios.get('http://localhost:8081/api/books/isbn/' + this.state.bookName)
                .then(response =>{
                    // console.log(response);
                    console.log(response.data);
                    let status = response.status;
                    if (status === 200) {
                        console.log("successful");
                        console.log("publisher");

                    }
                    let tempProductInfo =  response.data;

                    /*if (this.state.userId === undefined) {
                        this.setState({
                            userId: (localStorage.getItem('userId')),
                        })
                    } else {
                        console.log("hi")
                        localStorage.setItem('userId', JSON.stringify(this.state.userId));
                    }*/

                    this.setState({
                        productInfo: tempProductInfo,
                        isLoading: false
                    });
                    //this.props.history.push(`/location/${this.state.location}`);
                })
                .catch(error =>{
                    console.log(error)
                })
        }
        else{
            axios.get('http://localhost:8081/api/books/author/' + this.state.bookName)
                .then(response =>{
                    // console.log(response);
                    console.log(response.data);
                    let status = response.status;
                    if (status === 200) {
                        console.log("successful");
                        console.log("author");
                    }
                    let tempProductInfo =  response.data;

                    /* if (this.state.userId === undefined) {
                         this.setState({
                             userId: localStorage.getItem('userId'),
                         })
                     } else {
                         console.log("hi")
                         localStorage.setItem('userId', JSON.stringify(this.state.userId));
                     }*/

                    this.setState({
                        productInfo: tempProductInfo,
                        isLoading: false
                    });
                    //this.props.history.push(`/location/${this.state.location}`);
                })
                .catch(error =>{
                    console.log(error)
                })
        }
        event.preventDefault();



    }



    render() {

        if (this.state.isLoading)
            return (
                <div>
                    <div className="Navbar">
                        <nav>
                            <ul>
                                <li ><Link to="/">Home</Link></li>
                                <li><Link to="/SignUp">About</Link></li>
                                <li><Link to="/Dashboard">Dashboard</Link></li>
                                <li><Link to ="/Search">Search</Link></li>
                                <li><Link to="/" onClick={this.logout}>Logout</Link></li>
                            </ul>
                        </nav>
                    </div>
                    <h3>loading...</h3>

                </div>
            )
        const productComponents = this.state.productInfo.map((product) => {
            return (

                <Products
                    id={product.bookId}
                    bookTitle={product.bookName}
                    authorName={product.author}
                    price={product.price}
                    img={product.imageLocation}
                    publisher={product.publisher}
                    userId={this.props.location.userId}
                />
            )
        })
        return (
            <div className="Dashboard">
                {this.state.isLoading ? this.setStateToFalse :
                    <div>
                        <div className="Navbar">
                            <nav>
                                <ul>
                                    <li ><Link to="/">Home</Link></li>
                                    <li><Link to="/SignUp">About</Link></li>
                                    <li><Link to="/Dashboard">Dashboard</Link></li>
                                    <li><Link to="/" onClick={this.logout}>Logout</Link></li>
                                </ul>
                            </nav>
                        </div>
                        <h1 style={{border:'10px solid rgba(255,255,255,0)',width:'fit-content'}}>Dashboard</h1>
                        <div className="box">
                            <div>
                                <div>
                                    <form >
                                        <label htmlFor="Books"><h4> Search by : </h4></label>
                                        <select  onChange={this.setSearchBy} >
                                            <option value="isbn">ISBN</option>
                                            <option value="bookName">Book Name</option>
                                            <option value="publisher">Publisher</option>
                                            <option value="author">Author</option>
                                        </select>
                                    </form>
                                </div>
                                <div>
                                    <form>
                                        <input  type="search" placeholder="Enter book name" value={this.state.bookName}
                                                onChange={this.setBookName}/>
                                        <button  onClick={this.HandleClick} >Search</button>
                                    </form>
                                </div>
                            </div>
                            {this.state.productInfo.length>0?this.state.productInfo.map((product) => {
                                return (

                                    <Products
                                        id={product.bookId}
                                        bookTitle={product.bookName}
                                        authorName={product.author}
                                        price={product.price}
                                        img={product.imageLocation}
                                        publisher={product.publisher}
                                        userId={this.state.userId}
                                    />
                                )
                            }): "No data Exists"}
                        </div>

                    </div>}
            </div>
        )
    }
}

export default Search;
