import React from "react"
import {Link} from 'react-router-dom'


function Navbar(){
    return(
        <div className="Navbar">
            <nav>
                <ul>
                    <li ><Link to="/">Home</Link></li>
                    <li><Link to="/AboutUs">About</Link></li>
                    <li><Link to="/SignUp">Create Account</Link></li>
                </ul>
            </nav>
        </div>
    )
}

export default Navbar
