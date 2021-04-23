import React,{Component} from 'react';
import ChaptersInfo from "./Data/ChaptersInfo";

class Cart extends Component{
    constructor(props) {
        super(props);
        this.state={
            cart:[],
            totalSum:0,
            newCart:this.props.location.cart,
            userId:this.props.location.userId
        }
        this.removeFromCart=this.removeFromCart.bind(this);
        this.clearCart=this.clearCart.bind(this);
        this.getTotalSum=this.getTotalSum.bind(this);
        this.getChapterDetails=this.getChapterDetails.bind(this);
    }

    async getChapterDetails(bookId,chapterNumber){
        let response = await fetch('http://localhost:8081/api/books/'+bookId, {
            method: 'GET',
            headers: {
                'Accept': '*/*'
            }
        })
        let status = response.status;
        if (status === 200) {
            console.log("successful")
        }
        let bookDetails=await response.json();
        let bookName=bookDetails["bookName"]
        let chapters=bookDetails["bookChapters"]
        console.log("chapters",chapters)
        let chapterName,price;
        for(let i=0;i<chapters.length;i++)
        {
            if(chapterNumber===chapters[i]["chapterNumber"])
            {
                chapterName=chapters[i]["chapterName"]
                price=chapters[i]["price"]
                break;
            }
        }
        let chapterList={
            bookName:bookName,
            chapterName:chapterName,
            price:price
        }
        let chapterLists=[bookName,chapterName,price]
        console.log("chapterList",chapterList)
        console.log("chaptersList",chapterLists)
        return chapterList
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
        // console.log("componentDidMount",this.state.newCart)
        // console.log("cartinfo1:",localStorage.getItem('oldCart'))
        // console.log("cartinfo2:",localStorage.getItem('oldCart').length)
        this.getTotalSum()
        await this.getChapterDetails(1,5)
        console.log(this.state.newCart)
        if(localStorage.getItem('oldCart').length===0)
        {
            // console.log("cart length 0")
            this.setState({
                cart:this.state.newCart
            },()=>console.log("callback",this.state.cart))
        }
        else
        {
            // console.log("cart length >0")
            let oldCart=JSON.parse(localStorage.getItem('oldCart'))
            console.log(oldCart)
            this.state.newCart.forEach(cartItem => {
                oldCart.push(cartItem)})
            this.setState({
                cart:oldCart
            })
        }
        localStorage.setItem('oldCart', JSON.stringify(this.state.cart));

        // let tempCart=[]
        // for (let i = 0; i < ChaptersInfo.length; i++) {
        //     tempCart.push(ChaptersInfo[i]);
        // }
        // this.setState({
        //     cart:tempCart
        // },()=>this.getTotalSum())
    }

    getTotalSum (){
        let sum=0
        for(let i=0;i<this.state.newCart.length;i++)
        {
            console.log(this.state.newCart[i])
            sum=sum+this.state.newCart[i].chapterNum
        }
        this.setState({
            totalSum:sum
        })

    }


    render(){
        let cartItems=this.state.newCart.map((chapter, idx) => {
            let chapterDetails=this.getChapterDetails(chapter.bookId,chapter.chapterNum)
            return  (

                <div  key={idx}>
                    <h3>BookId:{chapter.bookId}</h3>
                    <h4>Chapter number:{chapter.chapterNum}</h4>
                    <button onClick={() => this.removeFromCart(chapter)}>
                        Remove
                    </button>
                </div>
            )})
        return (
            <div>
                <h1>Cart</h1>
                <div className="Chapters">
                    {this.state.newCart.length===0 && (
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

