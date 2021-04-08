import React, {Component} from "react";
import {BrowserRouter as Router, Route,Switch} from 'react-router-dom'
import './App.css';
import SignUp from "./component/SignUp"
import Welcome from "./component/Welcome";
import SignIn from "./component/SignIn";
import Dashboard from "./component/Dashboard";
import BookDetails from "./component/BookDetails"
import Navbar from './component/Navbar'


class App extends Component{
    render(){
        return(
            <div>
                <Router>
                    <div>
                        <Navbar/>
                    </div>
                    <div className="container">
                        <Switch>

                            <Route exact path='/' component={Welcome}/>
                            <Route exact path='/SignIn' component={SignIn}/>
                            <Route exact path='/SignUp' component={SignUp}/>
                            <Route exact path='/Dashboard' component={Dashboard}/>
                            <Route exact path='/BookDetails' component={BookDetails}/>

                        </Switch>
                    </div>
                </Router>
            </div>
        )
    }

}

export default App;