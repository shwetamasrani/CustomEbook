import React, {Component} from "react"
import {Link} from "react-router-dom"
import history from './history';
import BookDetails from "./BookDetails"
import Cart from "./Cart"

class Chapters extends Component {
    constructor(props) {
        super(props);
        this.state={
            name: this.props.name,
            cost:this.props.cost,
        }
    }
    // handleClick(){
    //     <BookDetails/>
    // }

    render() {
        return (
            <div className="Chapters" href="" style={{ width: '18rem' }}>

                <p>NAME:{this.state.name}</p>
                <p>Price:Rs.{this.state.cost}</p>
                <Link to={'/Cart'} >
                    <button className="Checkout" > Checkout </button>
                </Link>
            </div>

        )
    }
}

export default Chapters;
