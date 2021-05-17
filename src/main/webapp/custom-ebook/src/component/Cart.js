import React, {Component} from 'react';
import {Link} from "react-router-dom";
import UserService from "../services/UserService";

class Cart extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cart: [],
            totalSum: 0,
            newCart: this.props.location.cart,   //cart passed from bookDetails component
            userId: this.props.location.userId,
            chapterDetails: [],
            customBookName: ""
        }
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
            alert("Thank you for buying the book! Your Custom Ebook will be mailed to you shortly.")
            this.clearCart()

            let response = await fetch('http://localhost:8081/api/users/' + this.state.userId + '/cart/checkout/' + this.state.customBookName, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': '*/*'
                },
            })
            let status = response.status;
            if (status === 200) {
            }


        }
    }

    async removeFromCart(chapterToRemove) {

        console.log("removed");
        console.log(chapterToRemove)
        let response = await fetch("http://localhost:8081/api/users/" + this.state.userId + "/cart", {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Accept': '*/*'
            },
            body: JSON.stringify({
                bookId: chapterToRemove.bookId,
                chapterNumber: chapterToRemove.chapterData[0].chapterNumber
            })
        })
        let status = response.status;
        if (status === 200) {
            console.log("delete: Success")
        } else {
            console.log("delete: Failed")
        }
        console.log("Newcart-->", this.state.newCart)
        // let chapter={
        //     bookId:"1",
        //     chapterNumber:"1"
        //     // bookId:chapterToRemove.bookId,
        //     //  chapterNumber:chapterToRemove.chapterData[0].chapterNumber
        // }
        // console.log(chapter)
        // UserService.deleteCartItem(chapter,this.state.userId).then()
        let tempCart = this.state.newCart.filter((chapter) => chapter !== chapterToRemove)

        this.setState({
            newCart: tempCart
        }, () => this.updateCartData())
    }

    updateCartData() {
        console.log("IN update cart", this.state.newCart)
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
        let response = await fetch('http://localhost:8081/api/users/' + this.state.userId + '/cart', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Accept': '*/*'
            },
        })
        let status = response.status;
        if(status===201)
        {
            this.clearCart()
        }
        if (status === 200) {
            console.log("successful")
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
    }

    async componentDidMount() {

        console.log("NewCart:", this.state.newCart," UID:",this.state.userId)

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
                <div key={idx} className="CartItem">
                    <h4>{chapter.bookName}</h4>
                    <h4>{chapter.chapterNum}</h4>
                    <h4>{chapter.chapterData[0].chapterName}</h4>
                    <h4>Rs.{chapter.chapterData[0].price}</h4>
                    <button onClick={() => this.removeFromCart(chapter)}>
                        Remove
                    </button>
                    <hr/>
                </div>
            )
        })
        return (
            <div>
                <nav>
                    <ul>
                        <li><Link to="/Dashboard">Dashboard</Link></li>
                        <li><Link to="/User">Profile</Link></li>
                        <li><Link to="/" onClick={this.logout}>Logout</Link></li>
                    </ul>
                </nav>
                <h1 style={{color: "white"}}>Cart</h1>
                <br/>
                <div className="Cart">
                    <div className="Chapters" style={{width: '100%'}}>
                        <div className="CartItem">
                            <h4>BookName</h4>
                            <h4>Chapter number</h4>
                            <h4>Chapter name</h4>
                            <h4>Price</h4>
                            <h4>Remove</h4>
                        </div>
                        <hr/>
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

                    </div>
                </div>
            </div>


        )
    }

}

export default Cart;

