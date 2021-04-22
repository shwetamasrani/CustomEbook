import React, {useState} from 'react';
import ChaptersInfo from "./Data/ChaptersInfo";
import Chapters from "./Chapters";

function Cart(props){
    const [cart,setCart]= useState([]);
    for(let i=0;i<ChaptersInfo.length;i++){
        cart.push(ChaptersInfo[i]);
    }
   /* const [cart, setCart] = useState([]);*/

    const getTotalSum = () => {
        return cart.reduce(
            (sum, { cost}) => sum + cost,0

        );
    };
    const clearCart = () => {
        console.log("clearcart")
        setCart([]);
    };
    const removeFromCart = (chapterToRemove) => {
        console.log("remove");
        setCart(cart.filter((chapter) => chapter !== chapterToRemove));

    };
    return (
            <div>
            <h1>Cart</h1>
            {cart.length > 0 && (
                <button onClick={clearCart}>Clear Cart</button>
            )}

            <div className="Chapters">
                {cart.map((chapter, idx) => (
                    <div className="chapter" key={idx}>
                        <h3>{chapter.name}</h3>
                        <h4>${chapter.cost}</h4>
                        <button onClick={() => removeFromCart(chapter)}>
                            Remove
                        </button>
                    </div>

                ))}
            </div>
            <div>Total Cost: ${getTotalSum()}</div>
            </div>

    );

}
export default Cart;

