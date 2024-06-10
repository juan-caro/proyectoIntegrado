import { Navigate, Route, Routes } from "react-router-dom"
import { LayoutDashboard } from "../components/layouts/LayoutDashboard"
import { LayoutTorneosIndex } from "../components/layouts/torneos/LayoutTorneosIndex"
import { LayoutTorneosForm } from "../components/layouts/torneos/LayoutTorneosForm"
import { LayoutRegisterUser } from "../components/layouts/users/LayoutRegisterUser"
import { LayoutLoginUser } from "../components/layouts/users/LayoutLoginUser"
import { LogoutUser } from "../components/users/LogoutUser"
import { LayoutTorneosDetails } from "../components/layouts/torneos/LayoutTorneosDetails"
import { LayoutTorneosUser } from "../components/layouts/users/LayoutTorneosUser"
import { LayoutUserProfile } from "../components/layouts/users/LayoutUserProfile"
import { LayoutClubsIndex } from "../components/layouts/clubs/LayoutClubsIndex"
import { LayoutClubsForm } from "../components/layouts/clubs/LayoutClubsForm"
import { LayoutClubsDetails } from "../components/layouts/clubs/LayoutClubsDetails"
import { LayoutTorneosCreator } from "../components/layouts/users/LayoutTorneosCreator"
import { LayoutPlatformIndex } from "../components/layouts/plataformas/LayoutPlatformIndex"
import { LayoutPlatformForm } from "../components/layouts/plataformas/LayoutPlatformForm"
import { LayoutUserProfileForm } from "../components/layouts/users/LayoutUserProfileForm"

export const RoutesApp = ({ isLoggedIn, handleLogin, handleLogout, userLogged, setUserLogged}) => {
    
    return (
        <>
            <Routes>
                <Route path="principal" element={ <LayoutDashboard isLoggedIn={isLoggedIn} userLogged={userLogged}/>} />
                <Route path="/torneos" element={ <LayoutTorneosIndex isLoggedIn={isLoggedIn} userLogged={userLogged}/>} />
                <Route path="/torneos/create" element={ <LayoutTorneosForm isLoggedIn={isLoggedIn} userLogged={userLogged}/>} />
                <Route path="/" element={ <Navigate to={'principal'} isLoggedIn={isLoggedIn} userLogged={userLogged}/>} />
                <Route path="/register" element={ <LayoutRegisterUser isLoggedIn={isLoggedIn} userLogged={userLogged} handleLogin={handleLogin} setUserLogged={setUserLogged}/> } />
                <Route path="/login" element={<LayoutLoginUser handleLogin={handleLogin} setUserLogged={setUserLogged} />} />
                <Route path="/logout" element={<LogoutUser handleLogout={handleLogout}/>} />
                <Route path="/torneos/details" element={<LayoutTorneosDetails isLoggedIn={isLoggedIn} userLogged={userLogged}/>} />
                <Route path="/torneos_participados" element={<LayoutTorneosUser isLoggedIn={isLoggedIn} userLogged={userLogged}/>} />
                <Route path="/miperfil" element={<LayoutUserProfile isLoggedIn={isLoggedIn} userLogged={userLogged} handleLogin={handleLogin}/>} />
                <Route path="/clubs" element={ <LayoutClubsIndex isLoggedIn={isLoggedIn} userLogged={userLogged}/>} />
                <Route path="/clubs/create" element={ <LayoutClubsForm isLoggedIn={isLoggedIn} userLogged={userLogged}/>} />
                <Route path="/clubs/details" element={ <LayoutClubsDetails isLoggedIn={isLoggedIn} userLogged={userLogged}/>} />
                <Route path="/torneos_organizados" element={<LayoutTorneosCreator isLoggedIn={isLoggedIn} userLogged={userLogged}/>} />
                <Route path="/platforms" element={ <LayoutPlatformIndex isLoggedIn={isLoggedIn} userLogged={userLogged}/>} />
                <Route path="/platforms/create" element={ <LayoutPlatformForm isLoggedIn={isLoggedIn} userLogged={userLogged}/>} />
                <Route path="/miperfil/edit" element={<LayoutUserProfileForm isLoggedIn={isLoggedIn} userLogged={userLogged} setUserLogged={setUserLogged} handleLogin={handleLogin} />} />
            </Routes>
        </>
    )
}
