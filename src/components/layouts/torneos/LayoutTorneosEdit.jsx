import React from 'react'
import { useNavigate } from "react-router-dom";
import { Navbar } from "../components/Navbar"
import { SideNavbar } from "../components/SideNavbar"
import { useEffect } from "react";
import { TorneosEdit } from '../../torneos/TorneosEdit';

export const LayoutTorneosEdit = ({ isLoggedIn, userLogged }) => {
      
    const navigate = useNavigate();
    console.log("loggedin" + isLoggedIn);
    useEffect(() => {
        if (!isLoggedIn) {
          navigate('/login');
        }
      }, [isLoggedIn, navigate]);

    return (
        <>
            <Navbar isLoggedIn={isLoggedIn} userLogged={userLogged}/>
            <div id="layoutSidenav">
                <div id="layoutSidenav_nav">
                    <SideNavbar isLoggedIn={isLoggedIn} userLogged={userLogged}/>
                </div>
                <div id="layoutSidenav_content" className="bg-white-to-green">
                    <main className="mb-0">
                     
                        <TorneosEdit userLogged={userLogged}/>
                    </main>
                    <footer className="custom-bg-dark text-white">
                        <div className="custom-bg-dark">
                            <div className="custom-bg-dark">
                                <div className="">Copyright &copy; Chess Tournaments 2024</div>
                            </div>
                        </div>
                    </footer>
                </div>
            </div>
            
    
        </>
      )

}
