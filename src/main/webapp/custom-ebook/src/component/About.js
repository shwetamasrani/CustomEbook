import React, {Component} from "react"
import Navbar from "./Navbar";

class About extends Component {

    render() {
        return (
            <div>
                <Navbar/>
                <div className="about">
                    <h2> Ours is a platform which allows you to choose chapters in various books and merge them
                        together to create your own customized book. Now it is not necessary to buy an entire book! Just
                        get the
                        parts you need.</h2>
                </div>
            </div>
        )
    }
}

export default About
