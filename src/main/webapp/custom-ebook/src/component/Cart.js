import React,{Component} from 'react';
import {useState} from 'react';
import ChaptersInfo from "./Data/ChaptersInfo";
import {render} from "@testing-library/react";

class Cart extends Component{
    constructor(props) {
        super(props);
        this.state={
            cart:[],
            totalSum:0
        }
        this.removeFromCart=this.removeFromCart.bind(this);
        this.clearCart=this.clearCart.bind(this);
        this.getTotalSum=this.getTotalSum.bind(this);
    }

    removeFromCart(chapterToRemove){
        console.log("removed");
        let tempCart=this.state.cart.filter((chapter) => chapter !== chapterToRemove)
        this.setState({
            cart: tempCart
        },()=>this.getTotalSum())
    }
    clearCart()
    {
        this.setState({
            cart:[]
        },()=>this.getTotalSum())
    }
    async componentDidMount() {
        let tempCart=[]
        for (let i = 0; i < ChaptersInfo.length; i++) {
            tempCart.push(ChaptersInfo[i]);
        }
        this.setState({
            cart:tempCart
        },()=>this.getTotalSum())
    }

    getTotalSum (){
        console.log("inside totalSum:",this.state.cart)
        let sum=0
        for(let i=0;i<this.state.cart.length;i++)
        {
            sum=sum+this.state.cart[i].cost
        }
        this.setState({
            totalSum:sum
        })

    }


    render(){
        let cartItems=this.state.cart.map((chapter, idx) => (
                <div className="chapter" key={idx}>
                    <h3>{chapter.name}</h3>
                    <h4>${chapter.cost}</h4>
                    <button onClick={() => this.removeFromCart(chapter)}>
                        Remove
                    </button>
                </div>
            ))
        return (

            <div>
                <h1>Cart</h1>
                <div className="Chapters">
                    {this.state.cart.length===0 && (
                        <h3> Your cart is empty </h3>
                    )}
                    {cartItems}
                </div>
                <div>Total Cost: ${this.state.totalSum}</div>

                {this.state.cart.length > 0 && (
                    <button onClick={this.clearCart}>Clear Cart</button>
                )}
            </div>

        )
    }

}

export default Cart;

