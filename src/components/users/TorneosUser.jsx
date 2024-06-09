import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

export const TorneosUser = ({ userLogged }) => {
  const [tournaments, setTournaments] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchTournaments = async () => {
      try {
        const response = await axios.get('/participations/userTournaments', {
          params: { userId: userLogged.id }
        });
        setTournaments(response.data);
      } catch (error) {
        console.error('Error fetching user tournaments', error);
      }
    };

    fetchTournaments();
  }, [userLogged.id]);

  const handleDetails = (tournament) => {
    navigate('/torneos/details', { state: { tournament } });
};


  return (
    <>
        <div className="container-fluid px-4">
            
            <h1 className="mt-4">Torneos Participados</h1>
            <ol className="breadcrumb mb-4">
                <li className="breadcrumb-item active">Torneos</li>
            </ol>
            <div className="card mb-4">
                <div className="card-header">
                    <i className="fas fa-crown me-1"></i>
                    Torneos
                </div>
                <div className="card-body">
                    <table id="datatablesSimple" className="table table-striped">
                        <thead>
                            <tr>
                                <th scope="col">Nombre</th>
                                <th scope="col">Fecha y Hora</th>
                                <th>Formato</th>
                                <th>Estado</th>
                                <th>Rondas</th>
                                <th className="d-flex justify-content-end">Ver detalles</th>

                            </tr>
                        </thead>
                        <tbody>
                            {tournaments.length === 0 ? (
                                <tr>
                                    <td colSpan="6">Todavía no has participado en ningún torneo.</td>
                                </tr>
                            ) : (
                                tournaments.map(tournament => (
                                    <tr key={tournament.id}>
                                        <td>{tournament.name}</td>
                                        <td>{new Date(tournament.dateTime).toLocaleString()}</td>
                                        <td>{tournament.format}</td>
                                        <td>{tournament.state}</td>
                                        <td>{tournament.rounds}</td>
                                        <td>
                                            <button
                                                className="btn btn-primary btn-sm d-flex float-end"
                                                onClick={() => handleDetails(tournament)}
                                            >
                                                Ver detalles
                                            </button>
                                        </td>
                                    </tr>
                                ))
                            )}
                        </tbody>
                        <tfoot>
                            <tr>
                                <th scope="col">Nombre</th>
                                <th scope="col">Fecha y Hora</th>
                                <th>Formato</th>
                                <th>Estado</th>
                                <th>Rondas</th>
                                <th className="d-flex justify-content-end">Ver detalles</th>
                            </tr>
                        </tfoot>
                    </table>

                </div>
            </div>
        </div>  
    </>
  );
};


