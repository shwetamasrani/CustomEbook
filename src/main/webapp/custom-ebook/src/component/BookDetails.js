import React, {Component, useState} from "react"
import {Link} from "react-router-dom";
import productsInfo from "./Data/productInfo";

const PAGE_CART = 'Cart';

function BookDetails(props) {
    const bookComponent = productsInfo[0]
    /* const [cart, setCart] = useState([]);
   const [page, setPage] = useState([]);

    /* const navigateTo = (nextPage) => {
             setPage(nextPage);
         };

     */


    /*  const addToCart = (chapter) => {
          let newCart = [...cart];
          let itemInCart;
          itemInCart = {
              ...chapter,
          };
          newCart.push(itemInCart);
          setCart(newCart);
      };*/


    return (

        <div>
            <h1 style={{color: "white"}}>Book Details</h1>
            <div className="BookDetails_Image" style={{width: '20rem'}}>
                <img src={productsInfo[0].img} style={{width: '20rem'}}/>
            </div>
            <div className="Preview">
                <Link to={'/Cart'}>
                    <button>Go to Cart</button>
                </Link>
            </div>
            <div className="BookDetails_Info" style={{width: '20rem'}}>
                <p>Name:{bookComponent.bookTitle}</p>
                <p>Description:{bookComponent.description}</p>
                <p>Price:{bookComponent.price}</p>
                <p>Genre:{bookComponent.type}</p>

                <ol title="Chapters">
                    <li><input type="checkbox"/> The Boy Who Lived price : $2</li>
                    <br/>
                    <li><input type="checkbox"/>The Vanishing Glass price : $2</li>
                    <br/>
                    <li><input type="checkbox"/>The Letters from No One price : $2</li>
                    <br/>
                    <li><input type="checkbox"/>The Keeper of Keys price : $2</li>
                    <br/>
                    <li><input type="checkbox"/>Diagon Alley price : $2</li>
                    <br/>
                    <br/>
                    <button className="addToCart"> Add To Cart</button>
                    <button className="buyNow"> Buy Now</button>
                </ol>

            </div>

        </div>


    )


}

export default BookDetails
