import { Navigate, Route, Routes } from "react-router-dom"
import { LayoutDashboard } from "../components/layouts/LayoutDashboard"
import { LayoutTorneosIndex } from "../components/layouts/torneos/LayoutTorneosIndex"
import { LayoutTorneosForm } from "../components/layouts/torneos/LayoutTorneosForm"
export const RoutesApp = () => {
    return (
        <>
            <Routes>
                <Route path="principal" element={ <LayoutDashboard/>} />
                <Route path="/torneos" element={ <LayoutTorneosIndex />} />
                <Route path="/torneos/create" element={ <LayoutTorneosForm />} />
                <Route path="/" element={ <Navigate to={'principal'} />} />
            </Routes>
        </>
    )
}
