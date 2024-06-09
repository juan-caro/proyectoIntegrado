import React from 'react'
import { Navbar } from '../components/Navbar'
import { SideNavbar } from '../components/SideNavbar'
import { PlatformsForm } from '../../plataformas/PlatformsForm';

export const LayoutPlatformForm = ({ isLoggedIn, userLogged }) => {
    return (
        <>
            <Navbar isLoggedIn={isLoggedIn} userLogged={userLogged}/>
            <div id="layoutSidenav">
                <div id="layoutSidenav_nav">
                    <SideNavbar isLoggedIn={isLoggedIn} userLogged={userLogged}/>
                </div>
                <div id="layoutSidenav_content" className="bg-white-to-green">
                    <main className="mb-0">
                        <PlatformsForm />
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