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
            genre: this.props.genre
        }
    }

    render() {
        return (
            <div className="Products" href="" style={{width: '18rem'}}>
                <img src={this.state.img} style={{width: '18rem'}}/>
                <h3>{this.state.bookTitle}</h3>
                <p>Author:{this.state.authorName}</p>
                <p>Price:Rs.{this.state.price}</p>
                <p>Genre:{this.state.genre} </p>
                <Link to={'/BookDetails'}>
                    <button className="viewDetails"> View Details</button>
                </Link>
            </div>

        )
    }
}

export default Products;