import { ClubsIndex } from "../../clubs/ClubsIndex"
import { Navbar } from "../components/Navbar"
import { SideNavbar } from "../components/SideNavbar"

export const LayoutClubsIndex = ({ isLoggedIn, userLogged }) => {
    return (
        <>
            <Navbar isLoggedIn={isLoggedIn} userLogged={userLogged}/>
            <div id="layoutSidenav">
                <div id="layoutSidenav_nav">
                    <SideNavbar isLoggedIn={isLoggedIn} userLogged={userLogged}/>
                </div>
                <div id="layoutSidenav_content" className="bg-white-to-green">
                    <main className="mb-0">
                        <ClubsIndex />
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