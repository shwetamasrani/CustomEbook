import React, {Component} from "react";
import {BrowserRouter as Router, Route,Switch} from 'react-router-dom'
import './App.css';
import SignUp from "./component/SignUp"
import Welcome from "./component/Welcome";
import SignIn from "./component/SignIn";
import Dashboard from "./component/Dashboard";
import BookDetails from "./component/BookDetails"
import Navbar from './component/Navbar'
import history from './component/history';

class App extends Component{
  render(){
    return(
          <Router history={history}>
           <Navbar/>
          <Switch>
         
            <Route exact path='/' component={Welcome}/>
            <Route exact path='/SignIn' component={SignIn}/>
            <Route exact path='/SignUp' component={SignUp}/>
            <Route exact path='/Dashboard' component={Dashboard}/>
            <Route exact path='/BookDetails' component={BookDetails}/>

            </Switch>
          </Router>
    )
  }

}

export default App;
