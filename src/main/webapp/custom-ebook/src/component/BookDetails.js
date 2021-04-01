import React,{Component} from "react"
import {Link} from "react-router-dom";
import productsInfo from "./Data/productInfo";

function BookDetails(props){
const bookComponent=productsInfo[0]

    return(
        <div>
            <h1 style={{color:"white"}}>Book Details</h1>
           
            <div className="BookDetails_Image" href="" style={{ width: '20rem' }}>
                <img src={productsInfo[0].img}   style={{ width: '20rem' }}/>
        </div>
<div className="Preview">
</div>
<div className="BookDetails_Info"  style={{ width: '20rem' }}>
    <p>Name:{bookComponent.bookTitle}</p>
    <p>Description:{bookComponent.description}</p>
    <p>Price:{bookComponent.price}</p>
    <p>Genre:{bookComponent.type}</p>
    
  <ol title="Chapters" >
  <li><input type="checkbox"/> The Boy Who Lived </li><br/>
    <li> <input type="checkbox"/>The Vanishing Glass</li> <br/>
    <li> <input type="checkbox"/>The Letters from No One</li><br/>
    <li> <input type="checkbox"/>The Keeper of Keys</li><br/>
    <li> <input type="checkbox"/>Diagon Alley</li><br/>
    <br/>
    <button className="addToCart" > Add To Cart </button>
    <button className="buyNow" > Buy Now </button>
    </ol>
</div>
        </div>
    )
}

export default BookDetails