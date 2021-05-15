import React, {Component} from 'react';
import {Link} from "react-router-dom";

class Cart extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cart: [],
            totalSum: 0,
            newCart: this.props.location.cart,   //cart passed from bookDetails component
            userId: this.props.location.userId,
            orderId: this.props.location.orderId,
            chapterDetails: [],
            customBookName: ""
        }
        /*
        Contents of newCart:
                "bookId"
                "bookName"
                "bookLocation"
                "chapterNum"
                "chapterData"(entire chapterdata in json)
        */
        this.removeFromCart = this.removeFromCart.bind(this);
        this.clearCart = this.clearCart.bind(this);
        this.getTotalSum = this.getTotalSum.bind(this);
        this.buyBook = this.buyBook.bind(this);
        this.updateCartData = this.updateCartData.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.getOldCartItems = this.getOldCartItems.bind(this)
    }

    handleChange(event) {
        const {name, value} = event.target;
        this.setState({
            [name]: value
        })
    }

    async buyBook(e) {
        e.preventDefault();
        if (this.state.customBookName === "")
            alert("Please enter your book name")
        else {
            let chapterList = []
            for (let i = 0; i < this.state.newCart.length; i++) {
                let chapter = {
                    bookId: this.state.newCart[i].bookId,
                    chapterNumber: this.state.newCart[i].chapterNum,
                    price: this.state.newCart[i].chapterData[0].price,
                    chapterName: this.state.newCart[i].chapterData[0].chapterName,
                    startPage: this.state.newCart[i].chapterData[0].startPage,
                    endPage: this.state.newCart[i].chapterData[0].endPage,
                    bookLocation: this.state.newCart[i].bookLocation
                }
                chapterList.push(chapter)
            }
            let completeCart = {
                userId: this.state.userId,
                orderItems: chapterList,
                customEBookName: this.state.customBookName,
                orderId: this.state.orderId
            }
            let response = await fetch('http://localhost:8081/api/cart/checkout', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': '*/*'
                },
                body: JSON.stringify(
                    completeCart
                )
            })
            let status = response.status;
            if (status === 201) {
                this.clearCart()
            }
            let bookDetails = await response.json()
            console.log(JSON.stringify(completeCart))
            alert("Book Bought")
        }
    }

    removeFromCart(chapterToRemove) {
        console.log("removed");
        let tempCart = this.state.newCart.filter((chapter) => chapter !== chapterToRemove)
        this.setState({
            newCart: tempCart
        }, () => this.updateCartData())
    }

    updateCartData() {
        this.getTotalSum()
        localStorage.setItem('newCart', JSON.stringify(this.state.newCart));
    }

    clearCart() {
        this.setState({
            newCart: [],
            customBookName: ""
        }, () => this.updateCartData())
    }

    async getOldCartItems() {
        let response = await fetch('http://localhost:8081/api/users/'+this.state.userId+'/cart', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': '*/*'
            },
        })
        let status = response.status;
        if (status === 200) {
            console.log("successful")
        }

        let oldCart = await response.json()
        console.log("oldCart--->", oldCart)
        let formattedOldCart = []
        for (const orderItem of oldCart.orderItems) {
            let cartItem = {
                bookId: orderItem.bookId,
                bookName: orderItem.bookName,
                bookLocation: orderItem.bookLocation,
                chapterNum: orderItem.chapterNumber,
                chapterData: [
                    {
                        chapterNumber: orderItem.chapterNumber,
                        chapterName: orderItem.chapterName,
                        price: orderItem.price,
                        startPage: orderItem.startPage,
                        endPage: orderItem.endPage,
                        description: orderItem.chapterDescription
                    }
                ]
            }
            formattedOldCart.push(cartItem)
        }
        console.log(formattedOldCart)
        localStorage.setItem('newCart', JSON.stringify(formattedOldCart));
        this.setState({
            newCart: formattedOldCart
        }, () => this.getTotalSum())


        //this.getTotalSum()
    }

    async componentDidMount() {

        console.log("NewCart:--->", this.state.newCart)

        if (this.state.userId === undefined) {
            this.setState({
                    newCart: JSON.parse(localStorage.getItem('newCart')),
                    userId: JSON.parse(localStorage.getItem('userId')),
                    //orderId: JSON.parse(localStorage.getItem('orderId'))
                }, () => this.getOldCartItems()
            )
        } else {
            localStorage.setItem('newCart', JSON.stringify(this.state.newCart));
            localStorage.setItem('userId', JSON.stringify(this.state.userId));
            //localStorage.setItem('orderId', JSON.stringify(this.state.orderId));

            await this.getOldCartItems()
        }
    }


    getTotalSum() {
        let sum = 0
        for (let i = 0; i < this.state.newCart.length; i++) {
            sum = sum + parseInt(this.state.newCart[i].chapterData[0].price)
        }
        this.setState({
            totalSum: sum
        })

    }


    render() {
        if (this.state.newCart === undefined)
            return (
                <div>
                    Loading...
                </div>
            )

        let cartItems = this.state.newCart.map((chapter, idx) => {
            return (
                <div key={idx}>
                    <h4>BookName:{chapter.bookName}</h4>
                    <h4>Chapter number:{chapter.chapterNum}</h4>
                    <h4>Chapter name:{chapter.chapterData[0].chapterName}</h4>
                    <h4>Price: Rs.{chapter.chapterData[0].price}</h4>
                    <button onClick={() => this.removeFromCart(chapter)}>
                        Remove
                    </button>
                </div>
            )
        })
        return (
            <div>
                <nav>
                    <ul>
                        <li><Link to="/">Home</Link></li>
                        <li><Link to="/SignUp">About</Link></li>
                        <li><Link to="/Dashboard">Dashboard</Link></li>
                        <li><Link to="/" onClick={this.logout}>Logout</Link></li>
                    </ul>
                </nav>
                <div className="Cart" style={{display: "inline-grid"}}>
                    <h1 style={{color: "white"}}>Cart</h1>
                    <div className="Chapters" style={{width: '100%'}}>
                        {this.state.newCart.length === 0 && (
                            <h3> Your cart is empty </h3>
                        )}
                        {cartItems}
                    </div>
                    <div className="bookName">
                        <h2>Total Cost: Rs.{this.state.totalSum}</h2>
                        {this.state.newCart.length > 0 && (
                            <div>
                                <h3>Enter a book Name:</h3>
                                <input type="text" name="customBookName" required="true"
                                       value={this.state.customBookName}
                                       onChange={this.handleChange}/>
                                <button name="buy" onClick={this.buyBook}>Buy Book</button>
                            </div>)
                        }
                        {this.state.newCart.length > 0 && (
                            <button onClick={this.clearCart}>Clear Cart</button>
                        )}
                    </div>
                </div>
            </div>


        )
    }

}

export default Cart;

