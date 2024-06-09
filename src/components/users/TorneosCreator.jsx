import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Swal from "sweetalert2";

export const TorneosCreator = ({ userLogged }) => {
  const [tournaments, setTournaments] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchTournaments = async () => {
      try {
        const response = await axios.get('/tournaments/creatorTournaments', {
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

const handleDelete = async (tournament) => {
    const confirmed = await Swal.fire({
        title: '¿Estás seguro?',
        text: "¡No podrás revertir esto!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, eliminar!',
        cancelButtonText: 'Cancelar'
    });

    if (confirmed.isConfirmed) {
        try {
            await axios.delete('http://localhost:8080/tournaments/eliminate', {
                params: { tournamentId: tournament.id }
            });
            Swal.fire(
                'Eliminado!',
                'El torneo ha sido eliminado.',
                'success'
            );
            // Aquí puedes actualizar el estado de los torneos para reflejar la eliminación
        } catch (error) {
            console.error('Error eliminando torneo:', error);
            Swal.fire(
                'Error',
                'Hubo un problema eliminando el torneo.',
                'error'
            );
        }
    }
};

  return (
    <>
        <div className="container-fluid px-4">
            
            <h1 className="mt-4">Torneos Organizados</h1>
            <ol className="breadcrumb mb-4">
                <li className="breadcrumb-item active">Torneos</li>
            </ol>
            <div className="card mb-4"  style={{maxWidth: '1200px'}}>
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
                                <th scope="col">Formato</th>
                                <th scope="col">Estado</th>
                                <th scope="col">Rondas</th>
                                <th scope="col">Acciones</th>

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
                                            <div>
                                                <button
                                                    className="btn btn-primary btn-sm me-3"
                                                    onClick={() => handleDetails(tournament)}
                                                >
                                                    Ver detalles
                                                </button>
                                                <button
                                                    className="btn btn-danger btn-sm"
                                                    onClick={() => handleDelete(tournament)}
                                                >
                                                    Eliminar
                                                </button>
                                            </div>
                                        </td>
                                        <td>
                                            
                                        </td>
                                    </tr>
                                ))
                            )}
                        </tbody>
                        <tfoot>
                            <tr>
                                <th scope="col">Nombre</th>
                                <th scope="col">Fecha y Hora</th>
                                <th scope="col">Formato</th>
                                <th scope="col">Estado</th>
                                <th scope="col">Rondas</th>
                                <th scope="col">Acciones</th>
                                
                            </tr>
                        </tfoot>
                    </table>

                </div>
            </div>
        </div>  
    </>
  );
};

