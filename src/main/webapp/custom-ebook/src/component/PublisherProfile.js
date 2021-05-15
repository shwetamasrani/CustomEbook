import React, {Component} from "react"
import {Link} from "react-router-dom";
import Products from "./Products";

class PublisherProfile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoading: true,
            userInfo: [],
            userId:2
            //userId:this.props.location.userId

        }
        this.getUserInfo = this.getUserInfo.bind(this)
        this.sendMail=this.sendMail.bind(this)
        this.logout=this.logout.bind(this)
    }
    logout(){
        localStorage.removeItem('userId');
    }

    sendMail()
    {
        alert("Mail Sent")
    }

    async getUserInfo() {
        console.log("UProfile userId:", this.state.userId)
        let response = await fetch('http://localhost:8081/api/users/' + this.state.userId + '/uploadedBooks', {
            method: 'GET',
            headers: {
                'Accept': '*/*'
            }
        })
        let status = response.status;
        if (status === 200) {
            console.log("UProfile: User details fetched")
        }
        let userInfo = await response.json()
        console.log("UserInfo:", userInfo)

        this.setState({
                userInfo: userInfo,
                isLoading: false
            }
        )
    }

    async componentDidMount() {

        if (this.state.userId === undefined) {
            console.log("UProfile:getting UserId")
            this.setState({
                userId: JSON.parse(localStorage.getItem('userId')),
            }, () => this.getUserInfo())

        } else {
            console.log("UProfile: setting UserId:", this.state.userId)
            localStorage.setItem('userId', JSON.stringify(this.state.userId));
            await this.getUserInfo();
        }
    }

    render() {
        if (this.state.isLoading)
            return (
                <div>
                    <h2>Loading.....</h2>
                </div>
            )

        // noOfChapters: null

        else {
            let orders = this.state.userInfo.uploadedBooks.map((order) => {
                return (
                    <div>
                        <div className="PublisherUploads">
                            <img src={order.imageLocation} style={{height:"70px"}}/>
                            <h4>#{order.isbnNumber}</h4>
                            <h4>{order.bookName}</h4>
                            <h4>{order.author}</h4>
                            <h4>${order.price}</h4>
                            <h4>{order.yearOfRelease}</h4>
                        </div>
                        <hr/>
                    </div>
                )
            })
            return (
                <div>
                    <div className="Navbar">
                        <nav>
                            <ul>
                                <li><Link to="/">Home</Link></li>
                                <li><Link to="/SignUp">About</Link></li>
                                <li><Link to="/Dashboard">Dashboard</Link></li>
                                <li><Link to="/" onClick={this.logout}>Logout</Link></li>
                            </ul>
                        </nav>
                        <h1 style={{color: 'white'}}>Profile</h1>
                    </div>
                    <br/>
                    <div className="UserProfile">
                        <h2>Hello !</h2>
                        <div className="data">
                            <h3>Company: </h3>
                            <h3>{this.state.userInfo.companyName} </h3>
                            <h3>Email id: </h3>
                            <h3>{this.state.userInfo.emailAddress}</h3>
                        </div>
                        <h3>Your Books:</h3>
                        {this.state.userInfo.uploadedBooks.length===0?(
                            <div className="UserOrdersContainer">
                                <h3 style={{padding:'10px'}}>You have not uploaded any books</h3>
                            </div>

                        ):(
                            <div className="UserOrdersContainer">
                                <div className="PublisherUploads">
                                    <h3>Image</h3>
                                    <h3>ISBN</h3>
                                    <h3>BookName</h3>
                                    <h3>Author</h3>
                                    <h3>Price</h3>
                                    <h3>Year of Release</h3>
                                </div>
                                <hr/>
                                {orders}
                            </div>
                        )}

                    </div>

                    <div className="OrderDetails">
                    </div>
                </div>
            )
        }
    }
}

export default PublisherProfile;
