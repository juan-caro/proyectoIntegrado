import React, { useEffect } from 'react'
import { Navbar } from '../components/Navbar';
import { SideNavbar } from '../components/SideNavbar';
import { useNavigate } from 'react-router-dom';
import { TorneosCreator } from '../../users/TorneosCreator';

export const LayoutTorneosCreator = ({ isLoggedIn, userLogged }) => {

    const navigate = useNavigate();

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
                    <SideNavbar/>
                </div>
                <div id="layoutSidenav_content" className="bg-white-to-green">
                    <main className="mb-0">
                        <TorneosCreator userLogged={userLogged} isLoggedIn={isLoggedIn} />
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