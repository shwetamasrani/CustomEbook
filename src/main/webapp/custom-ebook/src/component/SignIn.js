import React,{Component} from "react"
import {Link} from 'react-router-dom';
import UserService from "../services/UserService";
import history from './history';

class SignIn extends Component{
    constructor(props) {
        super(props);

        this.state = {
            email: "",
            password: "",
        }

        this.handleChange = this.handleChange.bind(this)
        this.handleClick = this.handleClick.bind(this)
    }

    handleChange(event) {
        console.log("Handle change callled")
        const {name, value} = event.target;
        this.setState({
            [name]: value
        })
        console.log(this.state)
    }
    handleClick(e){
        e.preventDefault();
        let user = {
            email: this.state.email,
            password: this.state.password
        }
        console.log("HandleClick")
        console.log(user);
        UserService.getUser(user).then(res => {
            this.props.history.push('/Dashboard');
            console.log("LoggedIn");
        });
        alert("Login")
    }
    render() {
        return (
            <div className="SignIn">
                <div className="register">
                    <h1>Sign In</h1>
                </div>
                <div className="main">
                    <form>
                        {/*<h4 className="name">Email Address</h4>*/}
                        <input
                            type="email"
                            name="email"
                            required="True"
                            className="email"
                            placeholder="Email address"
                            value={this.state.email}
                            onChange={this.handleChange}
                        />
                        <br/>
                        {/*<h4 className="name">Password</h4>*/}
                        <input
                            type="password"
                            name="password"
                            required="True"
                            className="password"
                            placeholder="Password"
                            value={this.state.password}
                            onChange={this.handleChange}
                        />
                        <h3 style={{display: this.state.errorMessage ? "block" : "none"}}>Incorrect
                            Username/Password</h3>
                        <br/>
                        <button className="registerButton" onClick={this.handleClick} >Login</button>
                        <p>New at the portal?<Link to="/SignUp"> Sign Up</Link></p>

                    </form>
                </div>
            </div>
        );
    }

}

export default SignIn;

