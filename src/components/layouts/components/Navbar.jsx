
export const Navbar = () => {
  return (
    <nav className="sb-topnav navbar navbar-expand navbar-dark bg-dark">
            {/* <!-- Navbar Brand--> */}
            <a className="navbar-brand ps-3" href="index.html">Chess Tournaments</a>
            
            {/* <!-- Navbar Search--> */}
            
            {/* <!-- Navbar--> */}
            <ul className="navbar-nav ms-auto">
                <li className="nav-item dropdown">
                    <a className="nav-link dropdown-toggle" id="navbarDropdown" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i className="fas fa-user fa-fw"></i></a>
                    <ul className="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                        <li><a className="dropdown-item" href="#!">Perfil</a></li>
                        <li><hr className="dropdown-divider" /></li>
                        <li><a className="dropdown-item" href="#!">Cerrar Sesión</a></li>
                    </ul>
                </li>
            </ul>
        </nav>
  )
}
