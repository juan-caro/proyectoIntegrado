import { useEffect, useState } from "react";

export const TorneosIndex = () => {
  
    const [tournaments, setTournaments] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchTournaments = async () => {
            try {
                const response = await fetch('http://localhost:8080/tournaments?page=0&size=10');
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                const data = await response.json();
                setTournaments(data.content);
            } catch (error) {
                setError(error.message);
            }
        };

        fetchTournaments();
    }, []);

    if (error) {
        return <div>Error fetching tournaments: {error}</div>;
    }


  
    return (

    <>
        <div className="container-fluid px-4">
            
            <h1 className="mt-4">Lista de torneos</h1>
            <ol className="breadcrumb mb-4">
                <li className="breadcrumb-item active">Torneos</li>
            </ol>
            <div>
                <a href="/salazoom/create" className="btn btn-success mb-4 fs-6"> + Nueva Sala </a>
            </div>
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
                                <th scope="col">Fecha</th>
                                <th>Formato</th>
                                <th>Estado</th>
                                <th>Rondas</th>
                                <th className="d-flex justify-content-end">Ver detalles</th>

                            </tr>
                        </thead>
                        <tbody>
                            {tournaments.length === 0 ? (
                                <tr>
                                    <td colSpan="5">No tournaments available</td>
                                </tr>
                            ) : (
                                tournaments.map(tournament => (
                                    <tr key={tournament.id}>
                                        <td>{tournament.name}</td>
                                        <td>{tournament.dateTime}</td>
                                        <td>{tournament.format}</td>
                                        <td>{tournament.state}</td>
                                        <td>{tournament.rounds}</td>
                                        <td>
                                            <a className="btn btn-primary btn-sm d-flex float-end" href="#">
                                                Ver detalles
                                            </a>
                                        </td>
                                    </tr>
                                ))
                            )}
                        </tbody>
                        <tfoot>
                            <tr>
                                <th scope="col">Nombre</th>
                                <th scope="col">Fecha</th>
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

        )
}
