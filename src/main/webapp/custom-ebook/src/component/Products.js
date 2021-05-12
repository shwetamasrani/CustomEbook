import React, {Component} from "react"
import {Link} from "react-router-dom"
import history from './history';
import BookDetails from "./BookDetails"

class Products extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: this.props.id,
            bookTitle: this.props.bookTitle,
            img: this.props.img,
            authorName: this.props.authorName,
            price: this.props.price,
            publisher: this.props.publisher,
            userId: this.props.userId
        }
    }


    render() {
        return (
            <div className="Products" href="">
                <div>
                    <img src={this.state.img}/>
                </div>
                <div>
                    <h3>{this.state.bookTitle}</h3>
                    <p>Author:{this.state.authorName}</p>
                    <p>Price:Rs.{this.state.price}</p>
                    <p>Publisher:{this.state.publisher} </p>
                    <Link to={{pathname: '/BookDetails', bookId: this.state.id, userId: this.state.userId}}>
                        <button className="viewDetails"> View Details</button>
                    </Link>
                </div>

            </div>

        )
    }
}

export default Products;
