import React, {Component} from "react"
import {Link} from "react-router-dom";
import productsInfo from "./Data/productInfo";

class BookDetails extends Component {
    constructor(props) {
        super(props);
        this.state = {
            userId:1,
            bookId: 1,
            chapterData: [],
            isChecked: true,
            cartChapters: [],
            finalCart: []
        }
        this.handleChange = this.handleChange.bind(this)
        this.addToCart = this.addToCart.bind(this)
        this.goToCart=this.goToCart.bind(this)
        this.getBookDetails=this.getBookDetails.bind(this)
    }
    async getBookDetails(){
        let response = await fetch('http://localhost:8081/api/books/'+this.state.bookId, {
            method: 'GET',
            headers: {
                'Accept': '*/*'
            }
        })
        let status = response.status;
        if (status === 200) {
            console.log("successful")
        }
        return await response.json()
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

    addToCart() {
        let tempCart = [];
        this.state.cartChapters.forEach(cNum => {
            tempCart.push({
                "bookId": this.state.bookId,
                "chapterNum": cNum
            })
        })

        this.setState({
            finalCart: tempCart
        })
    }

    goToCart() {
        this.props.history.push({
            pathname: "/Cart",
            cart: this.state.finalCart,
            userId: this.state.userId
        })
    }

    async componentDidMount() {
        let bookDetails=await this.getBookDetails()
        this.setState({
            chapterData: bookDetails["bookChapters"],
            // chapterNames: await require('./Data/chapter.json')["chapters"]["name"],
            // chapterPrices:await require('./Data/chapter.json')["chapters"]["cost"]
        })
    }

    render() {
        const bookComponent = productsInfo[0];
        return (
            <div>
                <h1 style={{color: "white"}}>Book Details</h1>

                <div className="BookDetails_Info">
                    <img src={productsInfo[0].img} style={{width: '20rem'}}/>
                    {this.state.finalCart.length>0 &&(<div className="Preview">
                        <button onClick={this.goToCart}>Go to Cart</button>
                    </div>)}

                    <p>Name:{bookComponent.bookTitle}</p>
                    <p>Description:{bookComponent.description}</p>
                    <p>Price:{bookComponent.price}</p>
                    <p>Genre:{bookComponent.type}</p>

                    <ol title="Chapters">
                        {
                            this.state.chapterData.map((chap) => {
                                return (
                                    <div>
                                        <li>{chap.chapterName}: ${chap.price}
                                            <input type="checkbox"
                                                   name={chap.chapterNumber}
                                                   onChange={this.handleChange}
                                                   value={this.state.isChecked}
                                            />
                                        </li>
                                        <br/>
                                    </div>
                                )
                            })
                        }
                        <br/>
                        <button className="addToCart" onClick={this.addToCart}> Add To Cart</button>
                        <button className="buyNow"> Buy Now</button>
                    </ol>

                </div>

            </div>


        )
    }


}

export default BookDetails