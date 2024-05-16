import { SideNavbarFooter } from "./SideNavbarFooter";

export const SideNavbar = () => {

  return (
    <nav className="sb-sidenav accordion sb-sidenav-dark " id="sidenavAccordion">
    <div className="sb-sidenav-menu">
      <div className="nav">
        <div className="sb-sidenav-menu-heading">Chess Tournaments</div>
        <a className="nav-link" href="/">
          <div className="sb-nav-link-icon"><i className="fas fa-tachometer-alt"></i></div>
          Página Principal
        </a>
        <div className="sb-sidenav-menu-heading">Torneos</div>
        <a className="nav-link" href="/torneos">
          <div className="sb-nav-link-icon"><i className="fas fa-tachometer-alt"></i></div>
          Lista de torneos
        </a>
        <a className="nav-link" href="/torneos/create">
          <div className="sb-nav-link-icon"><i className="fas fa-tachometer-alt"></i></div>
          Organizar nuevo torneo
        </a>
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
