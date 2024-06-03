import { useNavigate } from "react-router-dom";
import { TorneosForm } from "../../torneos/TorneosForm"
import { Navbar } from "../components/Navbar"
import { SideNavbar } from "../components/SideNavbar"
import { useEffect } from "react";

export const LayoutTorneosForm = ({ isLoggedIn, userLogged }) => {
    
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
                     
                        <TorneosForm userLogged={userLogged}/>
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
