import React, {Component, useState} from "react"
import {Link} from "react-router-dom";
import productsInfo from "./Data/productInfo";
import ChaptersInfo from "./Data/ChaptersInfo";

class BookDetails extends Component {
    constructor(props) {
        super(props);
        this.state = {
            chapterData: [],
            isChecked:true,
            cartChapters:[]
            // chapterNames:[],
            // chapterPrices:[]
        }
        this.handleChange=this.handleChange.bind(this)
    }

    handleChange(event) {
        const {name, value, type, checked} = event.target
        if(type==="checkbox")
        {
            let tempCart=this.state.cartChapters
            if(checked)
            {
                tempCart.push(name)
                console.log("checked",tempCart)
            }
            else
            {
                tempCart=this.state.cartChapters.filter((chapter) => chapter !== name)
                console.log("unchecked",tempCart)

            }
            this.setState(
                {[name]: checked, cartChapters:tempCart}
                )
        }
        else
        {
            this.setState({[name]: value})
        }
    }

    async componentDidMount() {
        this.setState({
            chapterData: await require('./Data/chapter.json')["chapters"],
            // chapterNames: await require('./Data/chapter.json')["chapters"]["name"],
            // chapterPrices:await require('./Data/chapter.json')["chapters"]["cost"]
        }, () => console.log(this.state.chapterData))

    }

    render() {
        const bookComponent = productsInfo[0];
        return (
            <div>
                <h1 style={{color: "white"}}>Book Details</h1>

                <div className="BookDetails_Info">
                    <img src={productsInfo[0].img} style={{width: '20rem'}}/>
                    <div className="Preview">
                        <Link to={'/Cart'}>
                            <button>Go to Cart</button>
                        </Link>
                    </div>
                    <p>Name:{bookComponent.bookTitle}</p>
                    <p>Description:{bookComponent.description}</p>
                    <p>Price:{bookComponent.price}</p>
                    <p>Genre:{bookComponent.type}</p>

                    <ol title="Chapters">
                        {
                            this.state.chapterData.map((chap) => {
                                return (
                                    <div>
                                        <li>{chap.name}: ${chap.cost}
                                        <input type="checkbox"
                                               name="isChecked"
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
                        <button className="addToCart"> Add To Cart</button>
                        <button className="buyNow"> Buy Now</button>
                    </ol>

                </div>

            </div>


        )
    }


}

export default BookDetails
