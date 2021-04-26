import React, {Component} from 'react';

class Cart extends Component {
    constructor(props) {
        super(props);
        this.state = {
            cart: [],
            totalSum: 0,
            newCart: this.props.location.cart,   //cart passed from bookDetails component
            userId: this.props.location.userId,
            orderId:this.props.location.orderId,
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
            if (status === 200) {
                console.log("successful")
            }
            let bookDetails = await response.json()
            console.log(JSON.stringify(completeCart))
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
            newCart: []
        }, () => this.updateCartData())
    }

    async componentDidMount() {

        console.log(this.state.newCart)

        if (this.state.newCart === undefined) {
            this.setState({
                    newCart: JSON.parse(localStorage.getItem('newCart')),
                    userId: JSON.parse(localStorage.getItem('userId')),
                    orderId: JSON.parse(localStorage.getItem('orderId'))
                }, () => this.getTotalSum()
            )
        } else {
            localStorage.setItem('newCart', JSON.stringify(this.state.newCart));
            localStorage.setItem('userId', JSON.stringify(this.state.userId));
            localStorage.setItem('orderId', JSON.stringify(this.state.orderId));

            this.getTotalSum()
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
                    <h4>Price: ${chapter.chapterData[0].price}</h4>
                    <button onClick={() => this.removeFromCart(chapter)}>
                        Remove
                    </button>
                </div>
            )
        })
        return (
            <div>
                <h1 style={{color: "white"}}>Cart</h1>
                <div className="Chapters" style={{width: 'fit-content'}}>
                    {this.state.newCart.length === 0 && (
                        <h3> Your cart is empty </h3>
                    )}
                    {cartItems}
                </div>
                <div style={{color: "white"}}><h2>Total Cost: ${this.state.totalSum}</h2></div>
                <form>
                    <div>
                        <h3>Enter a book Name:</h3>
                        <input type="text" name="customBookName" required="true" onChange={this.handleChange}/>
                    </div>
                    {this.state.newCart.length > 0 &&
                    <button name="buy" onClick={this.buyBook}>Buy Book</button>
                    }
                </form>

                {this.state.newCart.length > 0 && (
                    <button onClick={this.clearCart}>Clear Cart</button>
                )}
            </div>

        )
    }

}

export default Cart;

