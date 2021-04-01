import React,{Component} from "react"
import Products from "./Products";
import productsInfo from "./Data/productInfo";


class Dashboard extends Component{
    constructor(props) {
        super(props);
        this.state= {
            isLoading: false,
            productInfo: []
        }

    }
    async componentDidMount() {
        this.setState({
            isLoading: true
        })

        this.setState({
                productInfo: await require('./Data/productInfo'),
                isLoading: false
            }
        )
    }
    

    render(){
        const productComponents=productsInfo.map((product)=>{
            return (
                <Products
                    id={product.id}
                    bookTitle={product.bookTitle}
                    authorName={product.description}
                    price={product.price}
                    img={product.img}
                    genre={product.type}
                />
            )
        })
        return(
            <div className="Dashboard">
                <h1>Dashboard</h1>
                {productComponents}
            </div>
        )
    }
}
export default Dashboard