import React, {Component} from "react"
import {Link} from "react-router-dom";
import UserService from '../services/UserService';
import Navbar from "./Navbar";

class SignUp extends Component {
    constructor(props) {
        super(props);

        this.state = {
            firstName: "",
            lastName: "",
            email: "",
            contactNumber: "",
            password: "",
            companyName: "",
            isPublisher: "No"
        }
        this.handleChange = this.handleChange.bind(this)
        this.saveUser = this.saveUser.bind(this);
    }

    handleChange(event) {
        const {name, value} = event.target;
        this.setState({
            [name]: value
        })
    }

    saveUser = (e) => {
        e.preventDefault();
        let user = {
            firstName: this.state.firstName, lastName: this.state.lastName, email: this.state.email,
            contactNumber: this.state.contactNumber, password: this.state.password, companyName: this.state.companyName,
            publisherFlag: this.state.isPublisher === "Yes"
        }

        console.log('User =>' + JSON.stringify(user));

        UserService.createUser(user).then(res => {
            this.props.history.push('/Signin');
        });
    }

    render() {
        return (
            <div>
                <Navbar/>
                <div className="Welcome">
                <div className="content">
                    <label className="logo">EBook Project</label>
                    <h4>A platform for all your bookish needs. Choose the books and chapters you need and create your
                        own customized books </h4>
                </div>
                </div>
            <div className="SignUp">
                <div className="register">
                    <h1>Create an account</h1>
                    <p>Already have an account?<Link to="/SignIn"> Sign in</Link></p>
                    
                </div>
                <div className="main">
                    <form onSubmit={this.handleSubmit}>
                        {/*<h2 className="type">Account Type</h2>*/}
                        <label className="firstLabel">Do you want a publisher account?  </label>
                        <select
                            name="isPublisher"
                            id="isPublisher"
                            onChange={this.handleChange}
                        >
                            <option value="No">No</option>
                            <option value="Yes">Yes</option>
                        </select>
                        <br/>
                        <br></br>
                        <div style={{display: this.state.isPublisher === "Yes" ? "block" : "none"}}>
                            {/*<h2 className="name">Company Name</h2>*/}
                            <label>Company Name:</label>
                            <input
                                type="text"
                                name="companyName"
                                className="companyName"
                                placeholder="Company Name"
                                value={this.state.companyName}
                                onChange={this.handleChange}
                            />
                            
                        </div>
                        <br/>
                        <div style={{display: this.state.isPublisher === "No" ? "block" : "none"}} id="name">
                            {/*<h2 className="name">Name</h2>*/}
                            {/*<label className="firstLabel">First Name: </label>*/}
                            <label>First Name:</label>
                            <input
                                type="text"
                                name="firstName"
                                className="firstName"
                                placeholder="First Name"
                                value={this.state.firstName}
                                onChange={this.handleChange}
                            />
                            <br/><br/>
                            {/*<br/>*/}
                            {/*<label className="lastLabel">Last Name: </label>*/}
                            <label>Last Name:</label>
                            <input
                                type="text"
                                name="lastName"
                                className="lastName"
                                placeholder="Last Name"
                                value={this.state.lastName}
                                onChange={this.handleChange}
                            />
                        </div>
                        <br/><br/>
                        {/*<h2 className="name">Email Address</h2>*/}
                        <label>Email Address:</label>
                        <input
                            type="email"
                            name="email"
                            required="True"
                            className="email"
                            placeholder="Email address"
                            value={this.state.email}
                            onChange={this.handleChange}
                        />
                        <br/><br/>
                        {/*<br/>*/}
                        {/*<h2 className="name">Phone</h2>*/}
                        <label>Contact Number:</label>
                        <input
                            type="number"
                            name="contactNumber"
                            required="True"
                            className="contactNumber"
                            placeholder="Contact Number"
                            value={this.state.contactNumber}
                            onChange={this.handleChange}
                        />
                        <br/><br/>
                        {/*<br/>*/}
                        {/*<h2 className="name">Password</h2>*/}
                        <label>Password:</label>
                        <input
                            type="password"
                            name="password"
                            required="True"
                            className="password"
                            placeholder="Password"
                            value={this.state.password}
                            onChange={this.handleChange}
                        />
                        
                        <button className="registerButton" onClick={this.saveUser}>Register</button>
                    </form>
                </div>
            </div>
            </div>
        )
    }
}

export default SignUp;
