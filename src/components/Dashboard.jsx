import { useEffect, useState } from "react";
import axios from 'axios';

export const Dashboard = () => {

    const [tournaments, setTournaments] = useState([]);

  useEffect(() => {
    const fetchTournaments = async () => {
      try {
        const response = await axios.get('/tournaments/recent');
        setTournaments(response.data);
        console.log("response: " + response.data);
      } catch (error) {
        console.error('Error fetching recent tournaments', error);
      }
    };
    fetchTournaments();
  }, []);

  return (
    <>
        <div className="container-fluid px-4">
            <div className="card shadow-lg border-0 rounded-lg mt-5 mb-5">
                <div className="card-header d-flex flex-column">
                    <div className="d-flex align-items-center mt-4">
                        <img src="src/logo.png" alt="imagen" width="100" height="100" className="rounded-circle" />

                        <h1>¡Bienvenido a ChessTournaments!</h1>
                    </div>
                    <ol className="breadcrumb mb-4" style={{ marginLeft: 'calc(100px + 1rem)'}}>
                        <li className="breadcrumb-item active">Chess Tournaments</li>
                    </ol>
                </div>
                <div className="d-flex justify-content-center mt-3">
                    <div className="card border-primary mb-3 float-center" style={{ width: '800px' }}>
                        <h5 className="card-header text-bg-primary">Torneos más próximos</h5>
                        <div className="card-body">
                        {tournaments.map((tournament, index) => (
                            <p key={index} className="card-text">
                              <strong> Torneo:</strong> {tournament.name} 
                              <strong className="ms-3"> Fecha:</strong> {new Date(tournament.dateTime).toLocaleDateString()} 
                              <strong className="ms-3">Hora:</strong> {new Date(tournament.dateTime).toLocaleTimeString()}
                            </p>
                        ))}
                        </div>
                    </div>
                </div>
                
                <div className="d-flex justify-content-center mt-3">
                    <div className="card border-success mb-3" style={{ width: '800px' }}>
                        <h5 className="card-header text-bg-success">¡No olvides seguirnos en nuestras redes!</h5>
                        <div className="card-body">
                            <div className="row">
                                <div className="col d-flex">
                                    <i className="fa-brands fa-twitter me-2 mt-1"></i>
                                    <a href="https://www.x.com" target="_blank">
                                        <h5 className="ml-2">Twitter</h5>
                                    </a>
                                </div>
                                <div className="col d-flex">
                                    <i className="fa-brands fa-square-instagram me-2 mt-1"></i>
                                    <a href="https://www.instagram.com" target="_blank">
                                        <h5>Instagram</h5>
                                    </a>
                                </div>
                                <div className="col d-flex">
                                    <i className="fa-brands fa-tiktok me-2 mt-1"></i>
                                    <a href="https://www.tiktok.com" target="_blank">
                                        <h5>Tik Tok</h5>
                                    </a>
                                </div>
                                <div className="col d-flex">
                                    <i className="fa-brands fa-square-threads me-2 mt-1"></i>
                                    <a href="https://www.threads.net" target="_blank">
                                        <h5>Threads</h5>
                                    </a>
                                </div>
                                
                            </div>
                        </div>
                    </div>
                </div>

                <div className="d-flex justify-content-center mt-3 mb-5">
                    <div className="card border-warning" style={{ width: '800px' }}>
                        <h5 className="card-header text-bg-warning text-white">Sobre nosotros</h5>
                        <div className="card-body">
                            <h5 className="card-title">¡Conócenos!</h5>
                            <p className="card-text">
                                En ChessTournaments, nos dedicamos a proporcionar una plataforma intuitiva y eficiente para la gestión de torneos 
                                y clubes de ajedrez. Nuestra aplicación está diseñada para simplificar el proceso de organización y participación 
                                en eventos de ajedrez, ofreciendo herramientas robustas para administradores y usuarios por igual. Ya sea que estés 
                                buscando crear, unirte o administrar torneos, ChessTournaments está aquí para hacer que tu experiencia 
                                en el mundo del ajedrez sea más emocionante y accesible que nunca.
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </>
  )
}
