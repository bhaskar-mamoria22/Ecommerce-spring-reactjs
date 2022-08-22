import React from 'react';
import {faInfoCircle} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";

function Contacts() {
    return (
        <div className="container mt-5">
            <h4><FontAwesomeIcon className="ml-2 mr-2" icon={faInfoCircle}/>About US</h4>
            <br/>
            <p><b>Mobile:</b> 905722688<br/>
                <b>E-mail:</b> marvelstudios0100@gmail.com</p>
            <br/>
            <h6>Contact Us</h6>
            <p>The online store is open without breaks and weekends. <br/>
                Online orders are accepted.</p>
            <br/>
            <h6>Delivery</h6>
            <p>Delivery of orders come through courier service.</p>
        </div>
    )
}

export default Contacts