import { Link } from "react-router-dom"

export const Navbar = ({isLoggedIn, userLogged}) => {
  return (
    <>
    <nav className="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            {/* <!-- Navbar Brand--> */}
            <Link className="navbar-brand ps-3" to={{ pathname: '/'}}>Chess Tournaments</Link>
            
            {/* <!-- Navbar Search--> */}
            
            {/* <!-- Navbar--> */}
            <ul className="navbar-nav ms-auto">
                <li className="nav-item dropdown">
                    <a className="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i className="fas fa-user fa-fw"></i></a>
                    <ul className="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">

                        {isLoggedIn ?
                        <>
                            <li><h5 className="dropdown-item">{userLogged.username}</h5></li>
                            <li><a className="dropdown-item" href="#!">Perfil</a></li>
                            <li><hr className="dropdown-divider" /></li>
                            <li><Link className="dropdown-item" to={{ pathname: '/logout'}}>Cerrar Sesión</Link></li>
                        </>
                        : 
                        <>
                            <li><Link className="dropdown-item" to={{ pathname: '/login'}}>Iniciar Sesión</Link></li>
                            <li><hr className="dropdown-divider" /></li>
                            <li><Link className="dropdown-item" to={{ pathname: '/register'}}>Registrarse</Link></li>
                        </>
                        }
                    </ul>
                </li>
            </ul>
        </nav>
        </>
  )
}
