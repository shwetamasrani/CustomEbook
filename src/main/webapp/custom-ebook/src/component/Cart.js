import React,{Component} from 'react';

class Cart extends Component{
    constructor(props) {
        super(props);
        this.state={
            cart:[],
            totalSum:0,
            newCart:this.props.location.cart,   //cart passed from bookDetails component
            userId:this.props.location.userId,
            chapterDetails:[],
        }
        this.removeFromCart=this.removeFromCart.bind(this);
        this.clearCart=this.clearCart.bind(this);
        this.getTotalSum=this.getTotalSum.bind(this);
        this.buyBook=this.buyBook.bind(this);
    }

    buyBook()
    {
        alert("book bought")
    }

    removeFromCart(chapterToRemove){
        console.log("removed");
        let tempCart=this.state.newCart.filter((chapter) => chapter !== chapterToRemove)
        this.setState({
            newCart: tempCart
        },()=>this.getTotalSum())
    }
    clearCart()
    {
        this.setState({
            cart:[]
        },()=>this.getTotalSum())
    }
    async componentDidMount() {
        this.getTotalSum()
        console.log(this.state.newCart)

        if (this.state.newCart === undefined) {


            this.setState({
                newCart: JSON.parse(localStorage.getItem('newCart')),
                userId: JSON.parse(localStorage.getItem('userId')),
            })
        } else {
            localStorage.setItem('newCart', JSON.stringify(this.state.newCart));
            localStorage.setItem('userId', JSON.stringify(this.state.userId));

        }
        // if(localStorage.getItem('oldCart').length===0)
        // {
        //     this.setState({
        //         cart:this.state.newCart
        //     },()=>console.log("callback",this.state.cart))
        // }
        // else
        // {
        //     let oldCart=JSON.parse(localStorage.getItem('oldCart'))
        //     console.log(oldCart)
        //     this.state.newCart.forEach(cartItem => {
        //         oldCart.push(cartItem)})
        //     this.setState({
        //         cart:oldCart
        //     })
        // }
        // localStorage.setItem('oldCart', JSON.stringify(this.state.cart));
    }

    getTotalSum (){
        let sum=0
        for(let i=0;i<this.state.newCart.length;i++)
        {
            sum=sum+parseInt(this.state.newCart[i].chapterData[0].price)
        }
        this.setState({
            totalSum:sum
        })

    }


      render(){
        let cartItems= this.state.newCart.map( (chapter, idx) => {
            return  (
                <div  key={idx}>
                    <h3>BookId:{chapter.bookId}</h3>
                    <h4>BookName:{chapter.bookName}</h4>
                    <h4>Chapter number:{chapter.chapterNum}</h4>
                    <h4>Chapter name:{chapter.chapterData[0].chapterName}</h4>
                    <h4>Price: ${chapter.chapterData[0].price}</h4>
                    <button onClick={() => this.removeFromCart(chapter)}>
                        Remove
                    </button>
                </div>
            )})


        return (
            <div>
                <h1 style={{color:"white"}}>Cart</h1>
                <div className="Chapters">
                    {this.state.newCart.length===0 && (
                        <h3> Your cart is empty </h3>
                    )}
                    {cartItems}
                </div>
                <div style={{color:"white"}}><h2>Total Cost: ${this.state.totalSum}</h2></div>
                <button name="buy" onClick={this.buyBook}>Buy Book</button>

                {this.state.cart.length > 0 && (
                    <button onClick={this.clearCart}>Clear Cart</button>
                )}
            </div>

        )
    }

}

export default Cart;

