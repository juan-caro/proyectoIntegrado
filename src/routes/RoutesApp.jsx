import { Navigate, Route, Routes } from "react-router-dom"
import { LayoutDashboard } from "../components/layouts/LayoutDashboard"
import { LayoutTorneosIndex } from "../components/layouts/torneos/LayoutTorneosIndex"
import { LayoutTorneosForm } from "../components/layouts/torneos/LayoutTorneosForm"
import { LayoutRegisterUser } from "../components/layouts/users/LayoutRegisterUser"
import { LayoutLoginUser } from "../components/layouts/users/LayoutLoginUser"
import { LogoutUser } from "../components/users/LogoutUser"
export const RoutesApp = ({ isLoggedIn, handleLogin, handleLogout, userLogged, setUserLogged }) => {
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
            </Routes>
        </>
    )
}
