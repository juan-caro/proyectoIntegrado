
export const Navbar = ({isLoggedIn, userLogged}) => {
  return (
    <>
    <nav className="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            {/* <!-- Navbar Brand--> */}
            <a className="navbar-brand ps-3" href="index.html">Chess Tournaments</a>
            
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
                            <li><a className="dropdown-item" href="/logout">Cerrar Sesión</a></li>
                        </>
                        : 
                        <>
                            <li><a className="dropdown-item" href="/login">Iniciar Sesión</a></li>
                            <li><hr className="dropdown-divider" /></li>
                            <li><a className="dropdown-item" href="/register">Registrarse</a></li>
                        </>
                        }
                    </ul>
                </li>
            </ul>
        </nav>
        </>
  )
}
