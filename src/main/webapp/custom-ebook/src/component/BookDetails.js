import React, {Component} from "react"
import {Link} from "react-router-dom";
import productsInfo from "./Data/productInfo";
import BookService from "../services/BookService";

class BookDetails extends Component {
    constructor(props) {
        super(props);
        this.state = {
            userId: this.props.location.userId,
            bookId: this.props.location.bookId,
            isChecked: true,
            orderId: undefined,
            bookData: [],         //contains everything returned by api
            chapterData: [],    //contains everything in "bookChapter" part of api result
            cartChapters: [],   //cart with current {chapterNum, bookId}
            finalCart: []       //cart with chapters {chapterNum, bookId} that are sent to the next component
        }
        this.handleChange = this.handleChange.bind(this)
        this.addToCart = this.addToCart.bind(this)
        this.goToCart = this.goToCart.bind(this)
        this.getBookDetails = this.getBookDetails.bind(this)
        this.logout = this.logout.bind(this)
    }

    logout() {
        localStorage.removeItem('userId');
    }

    async getBookDetails() {
        console.log("BookDetails:UID", this.state.userId, " BID:", this.state.bookId);
        let response = await fetch('http://localhost:8081/api/books/' + this.state.bookId, {
            method: 'GET',
            headers: {
                'Accept': '*/*'
            }
        })
        let status = response.status;
        if (status === 200) {
            console.log("BookDetails: fetch successful")
        }
        let bookDetails = await response.json()
        this.setState({
            chapterData: bookDetails["bookChapters"],
            bookData: bookDetails
        })
    }

    handleChange(event) {
        const {name, value, type, checked} = event.target
        if (type === "checkbox") {
            let tempCart = this.state.cartChapters
            if (checked) {
                tempCart.push(name)
            } else {
                tempCart = this.state.cartChapters.filter((chapter) => chapter !== name)

            }
            this.setState(
                {[name]: checked, cartChapters: tempCart}
            )
        } else {
            this.setState({[name]: value})
        }
    }

    async addToCart() {
        let tempCart = [];
        for (const cNum of this.state.cartChapters) {
            let chapterData = []
            for (let i = 0; i < this.state.chapterData.length; i++) {
                if (this.state.chapterData[i].chapterNumber == cNum)
                    chapterData.push(this.state.chapterData[i])

            }
            tempCart.push({
                "bookId": this.state.bookId,
                "bookName": this.state.bookData["bookName"],
                "bookLocation": this.state.bookData["pdfFileLocation"],
                "chapterNum": cNum,
                "chapterData": chapterData,
            })
            console.log(JSON.stringify({
                userId: this.state.userId,
                itemDetails: {
                    bookId: this.state.bookId,
                    chapterNumber: cNum,
                    price: chapterData[0].price
                }
            }))
            let response = await fetch('http://localhost:8081/api/users/'+this.state.userId+'/cart', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': '*/*'
                },
                body: JSON.stringify({
                    // userId: this.state.userId,
                    // itemDetails: {
                        bookId: this.state.bookId,
                        chapterNumber: cNum,
                        // price: chapterData[0].price
                    // }
                })
            })
            let status = response.status;
            if (status === 200) {
                console.log("BookDetails: Success")
            }
            else
            {
                console.log("BookDetails: Failed")
            }
            //----Probably not needed----
            let bookDetails = await response.json()
            console.log(bookDetails.orderId)
            this.setState({
                orderId: bookDetails.orderId
            })
            //----till here
        }
        this.setState({
            finalCart: tempCart
        }, () => console.log(this.state.finalCart))

    }

    goToCart() {
        console.log("BookDetails UID:", this.state.userId, " OID:", this.state.orderId, " cart:", this.state.cart)
        this.props.history.push({
            pathname: "/Cart",
            cart: this.state.finalCart,
            userId: this.state.userId,
            orderId: this.state.orderId
        })
    }

    async componentDidMount() {
        if (this.state.bookId === undefined) {
            console.log("BookDetails: Getting UID and BID")
            this.setState({
                bookId: JSON.parse(localStorage.getItem('bookId')),
                userId: JSON.parse(localStorage.getItem('userId'))
            }, () => this.getBookDetails())
        } else {
            console.log("BookDetails: Setting UID and BID")
            localStorage.setItem('bookId', JSON.stringify(this.state.bookId));
            localStorage.setItem('userId', JSON.stringify(this.state.userId));
            await this.getBookDetails()
        }
    }

    render() {
        const bookComponent = this.state.bookData;
        return (
            <div>
                <div className="Navbar">
                    <nav>
                        <ul>
                            <li><Link to="/Dashboard">Dashboard</Link></li>
                            <li><Link to="/Cart">Cart</Link></li>
                            <li><Link to="/User">Profile</Link></li>
                            <li><Link to="/" onClick={this.logout}>Logout</Link></li>
                        </ul>
                    </nav>
                </div>
                <h1 style={{color: "white"}}>Book Details</h1>
                <div className="BookDetailsContainer">
                    <div className="BookDetails_Info">
                        <img src={bookComponent.imageLocation} style={{width: '20rem'}} alt="Image could not be fetched!"/>
                        <div>
                            
                            <p>Name: {bookComponent.bookName}</p>
                            <p>Description: {bookComponent.description}</p>
                            <p>Price: Rs.{bookComponent.price}</p>
                            <p>Author: {bookComponent.author}</p>
                            {this.state.chapterData == null ? <h3>No Chapters</h3> :
                                <div>
                                    <ol title="Chapters">
                                        {this.state.chapterData.map((chap) => {
                                            return (
                                                <div>
                                                    <li>{chap.chapterName}: Rs.{chap.price}
                                                        <input type="checkbox"
                                                               name={chap.chapterNumber}
                                                               onChange={this.handleChange}
                                                               value={this.state.isChecked}/>
                                                    </li>
                                                    <br/>
                                                </div>)
                                        })
                                        }
                                    </ol>
                                    <br/>
                                    <div className="bookButtons">
                                        <button className="addToCart" onClick={this.addToCart}> Add To Cart</button>
                                    </div>
                                    {this.state.finalCart.length > 0 && (<div className="bookButtons">
                                        <button onClick={this.goToCart}>Go to Cart</button>
                                    </div>)}
                                </div>}
                        </div>
                    </div>
                </div>

            </div>


        )
    }


}

export default BookDetails
