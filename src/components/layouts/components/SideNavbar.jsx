import { SideNavbarFooter } from "./SideNavbarFooter";
import { Link } from 'react-router-dom';

export const SideNavbar = ({isLoggedIn, userLogged}) => {

  return (
    <nav className="sb-sidenav accordion sb-sidenav-dark " id="sidenavAccordion">
    <div className="sb-sidenav-menu">
      <div className="nav">
        <div className="sb-sidenav-menu-heading"><i className="fa-solid fa-chess me-2"></i>Chess Tournaments</div>
        <Link className="nav-link" to={{ pathname: '/'}}>
          <div className="sb-nav-link-icon"><i className="fa-solid fa-house"></i></div>
          Página Principal
        </Link>
        <div className="sb-sidenav-menu-heading"><i className="fa-solid fa-gamepad me-2"></i>Torneos</div>
        <Link className="nav-link" to={{ pathname: '/torneos'}}>
          <div className="sb-nav-link-icon"><i className="fa-solid fa-list-ul"></i></div>
          Lista de torneos
        </Link>
        <Link className="nav-link" to={{ pathname: '/torneos/create'}}>
            <div className="sb-nav-link-icon"><i className="fa-solid fa-plus"></i></div>
            Organizar nuevo torneo
        </Link>
        <div className="sb-sidenav-menu-heading"><i className="fa-solid fa-person me-2"></i>Mi espacio</div>
        <Link className="nav-link" to={{ pathname: '/miperfil'}}>
          <div className="sb-nav-link-icon"><i className="fa-solid fa-id-card"></i></div>
          Mi perfil
        </Link>
        <Link className="nav-link" to={{ pathname: '/torneos_participados'}}>
          <div className="sb-nav-link-icon"><i className="fa-solid fa-list-ul"></i></div>
          Torneos Participados
        </Link>
        <Link className="nav-link" to={{ pathname: '/torneos_organizados'}}>
          <div className="sb-nav-link-icon"><i className="fa-solid fa-list-ul"></i></div>
          Torneos Organizados
        </Link>
        <div className="sb-sidenav-menu-heading"><i className="fa-solid fa-person me-2"></i>Clubs</div>
        <Link className="nav-link" to={{ pathname: '/clubs'}}>
          <div className="sb-nav-link-icon"><i className="fa-solid fa-id-card"></i></div>
          Lista de clubs
        </Link>
        <Link className="nav-link" to={{ pathname: '/clubs/create'}}>
          <div className="sb-nav-link-icon"><i className="fa-solid fa-list-ul"></i></div>
          Fundar un nuevo club
        </Link>
        <div className="sb-sidenav-menu-heading"><i className="fa-solid fa-person me-2"></i>Plataformas</div>
        <Link className="nav-link" to={{ pathname: '/platforms'}}>
          <div className="sb-nav-link-icon"><i className="fa-solid fa-id-card"></i></div>
          Plataformas existentes
        </Link>
        <Link className="nav-link" to={{ pathname: '/platforms/create'}}>
          <div className="sb-nav-link-icon"><i className="fa-solid fa-list-ul"></i></div>
          Registrar plataforma
        </Link>
      </div>
    </div>
    <SideNavbarFooter />
  </nav>
  )
}
