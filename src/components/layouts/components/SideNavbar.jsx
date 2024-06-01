import { SideNavbarFooter } from "./SideNavbarFooter";
import { Link } from 'react-router-dom';

export const SideNavbar = ({isLoggedIn, userLogged}) => {

  return (
    <nav className="sb-sidenav accordion sb-sidenav-dark " id="sidenavAccordion">
    <div className="sb-sidenav-menu">
      <div className="nav">
        <div className="sb-sidenav-menu-heading">Chess Tournaments</div>
        <Link className="nav-link" to={{ pathname: '/'}}>
          <div className="sb-nav-link-icon"><i className="fas fa-tachometer-alt"></i></div>
          Página Principal
        </Link>
        <div className="sb-sidenav-menu-heading">Torneos</div>
        <Link className="nav-link" to={{ pathname: '/torneos'}}>
          <div className="sb-nav-link-icon"><i className="fas fa-tachometer-alt"></i></div>
          Lista de torneos
        </Link>
        <Link className="nav-link" to={{ pathname: '/torneos/create'}}>
            <div className="sb-nav-link-icon"><i className="fas fa-tachometer-alt"></i></div>
            Organizar nuevo torneo
        </Link>
        <div className="sb-sidenav-menu-heading">Mi espacio</div>
        <a className="nav-link" href="/torneos">
          <div className="sb-nav-link-icon"><i className="fas fa-tachometer-alt"></i></div>
          Mi perfil
        </a>
        <a className="nav-link" href="/torneos/create">
          <div className="sb-nav-link-icon"><i className="fas fa-tachometer-alt"></i></div>
          Mis torneos
        </a>
      </div>
    </div>
    <SideNavbarFooter />
  </nav>
  )
}
