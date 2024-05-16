import { TorneosIndex } from "../../torneos/TorneosIndex"
import { Navbar } from "../components/Navbar"
import { SideNavbar } from "../components/SideNavbar"

export const LayoutTorneosIndex = () => {
    return (
        <>
            <Navbar />
            <div id="layoutSidenav">
                <div id="layoutSidenav_nav">
                    <SideNavbar />
                </div>
                <div id="layoutSidenav_content" className="bg-white-to-green">
                    <main className="mb-0">
                        <TorneosIndex />
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
