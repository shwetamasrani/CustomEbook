import React, {Component} from "react"
import Products from "./Products";
import productsInfo from "./Data/productInfo";
import {Link} from "react-router-dom";


class Dashboard extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            productInfo: [],
            userId:this.props.location.userId
        }
        this.setStateToFalse = this.setStateToFalse.bind(this)
        this.logout=this.logout.bind(this)

    }
    logout(){
        localStorage.removeItem('userId');
    }

    setStateToFalse() {
        this.state({
            isLoading: false
        })
    }

    async componentDidMount() {

        let response = await fetch('http://localhost:8081/api/books/', {
            method: 'GET',
            headers: {
                'Accept': '*/*'
            }
        })
        let status = response.status;
        if (status === 200) {
            console.log("successful")
        }
        let tempProductInfo = await response.json()

        if (this.state.userId === undefined) {
            this.setState({
                userId: JSON.parse(localStorage.getItem('userId')),
            })
        } else {
            console.log("hi")
            localStorage.setItem('userId', JSON.stringify(this.state.userId));
        }

        this.setState({
                productInfo: tempProductInfo,
                isLoading: false
            }
        )
    }


    render() {

        if (this.state.isLoading)
            return (
                <div>
                    <div className="Navbar">
                        <nav>
                            <ul>
                                <li ><Link to="/">Home</Link></li>
                                <li><Link to="/SignUp">About</Link></li>
                                <li><Link to="/Dashboard">Dashboard</Link></li>
                                <li><Link to="/" onClick={this.logout}>Logout</Link></li>
                            </ul>
                        </nav>
                    </div>
                    <h3>loading...</h3>
                </div>
            )
        const productComponents = this.state.productInfo.map((product) => {
            return (

                <Products
                    id={product.bookId}
                    bookTitle={product.bookName}
                    authorName={product.author}
                    price={product.price}
                    img={product.imageLocation}
                    publisher={product.publisher}
                    userId={this.state.userId}
                />
            )
        })
        return (
            <div className="Dashboard">
                {this.state.isLoading ? this.setStateToFalse :
                    <div>
                        <div className="Navbar">
                            <nav>
                                <ul>
                                    <li ><Link to="/">Home</Link></li>
                                    <li><Link to="/SignUp">About</Link></li>
                                    <li><Link to="/Dashboard">Dashboard</Link></li>
                                    <li><Link to="/" onClick={this.logout}>Logout</Link></li>
                                </ul>
                            </nav>
                        </div>
                        <h1 style={{border:'10px solid rgba(255,255,255,0)',width:'fit-content'}}>Dashboard</h1>
                        <div className="box">
                        {productComponents}
                        </div>
                    </div>}
            </div>
        )
    }
}

export default Dashboard
