import React from "react"
import {Link} from 'react-router-dom'


function Navbar(){
    return(
        <div>
        <nav>
            <ul>
                <li ><Link to="/">Home</Link></li>
                <li><Link to="/SignUp">About</Link></li>
                <li><Link to="/SignUp">Services</Link></li>
                <li><Link to="/SignUp">Contact</Link></li>
                <li><Link to="/SignUp">Sign in</Link></li>
                <li><Link to="/Dashboard">Dashboard</Link></li>
                <li><Link to="/SignUp">Create Account</Link></li>
            </ul>
        </nav>
    </div>
    )
}

export default Navbar