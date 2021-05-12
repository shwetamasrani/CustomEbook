import React, {Component} from "react";
import {BrowserRouter as Router, Route,Switch} from 'react-router-dom'
import './App.css';
import SignUp from "./component/SignUp"
import Welcome from "./component/Welcome";
import SignIn from "./component/SignIn";
import Dashboard from "./component/Dashboard";
import BookDetails from "./component/BookDetails"
import Navbar from './component/Navbar'
import AdminDashboard from "./component/AdminDashboard";
import Cart from "./component/Cart.js";
import AddChapterDetails from "./component/AddChapterDetails";



class App extends Component{
    render(){
        return(
            <div>
                <Router>

                    <div className="container">
                        <Switch>

                            <Route exact path='/' component={Welcome}/>
                            <Route exact path='/SignIn' component={Welcome}/>
                            <Route exact path='/SignUp' component={SignUp}/>
                            <Route exact path='/Dashboard' component={Dashboard}/>
                            <Route exact path='/BookDetails' component={BookDetails}/>
                            <Route exact path='/AdminDashboard' component={AdminDashboard}/>
                            <Route exact path='/Cart' component={Cart}/>
                            <Route exact path='/AddChapterDetails' component={AddChapterDetails}/>
                        </Switch>
                    </div>
                </Router>
            </div>
        )
    }

}

export default App;
