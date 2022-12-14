import React from 'react';

import "./Footer.css";

const Footer = () => {
    return (
        <footer className="page-footer p-5 bg-black text-white">
            <div className="container">
                <div className="d-flex justify-content-between">
                    <div className="footer-left">
                        <h3>LOUIS VUITTON</h3>
                        <p></p>
                        <br/>
                        <p></p>
                    </div>
                    
                    <div className="footer-right">
                        <h3>Social networks</h3>
                        <a href="https://www.linkedin.com/in/bhaskar-mamoria-87295818b/">
                            <i className="fab fa-linkedin fa-2x mr-3" style={{color: "white"}}></i>
                        </a>
                        <a href="https://www.facebook.com/bhaskar.mamoria/"><i className="fab fa-facebook-f fa-2x mr-3" style={{color: "white"}}></i></a>
                        <a href="https://www.instagram.com/thefoodiefort/"><i className="fab fa-instagram fa-2x mr-3" style={{color: "white"}}></i></a>
                    </div>
                </div>
                <div className="mx-auto" style={{width: "200px"}}>
                    <p>Copyright BHASKAR</p>
                </div>
            </div>
        </footer>
    );
}

export default Footer