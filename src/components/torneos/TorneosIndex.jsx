import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
        
/**
 * Componente funcional que muestra una lista de torneos y permite ver detalles de cada torneo.
 * Utiliza PrimeReact para la tabla de datos.
 * 
 * @returns {JSX.Element} Componente de React que muestra la lista de torneos.
 */
export const TorneosIndex = () => {
  
    const [tournaments, setTournaments] = useState([]);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    /**
     * Efecto secundario que se ejecuta al montar el componente para obtener la lista de torneos desde la API.
     */
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

    /**
     * Función para manejar la navegación a la página de detalles de un torneo específico.
     * 
     * @param {Object} tournament - Objeto que representa el torneo seleccionado.
     */
    const handleDetails = (tournament) => {
        navigate('/torneos/details', { state: { tournament } });
    };


    if (error) {
        return <div>Error fetching tournaments: {error}</div>;
    }

    /**
     * Plantilla de botón para cada fila de la tabla de torneos.
     * 
     * @param {Object} rowData - Datos de la fila actual de la tabla.
     * @returns {JSX.Element} Botón para ver detalles de un torneo específico.
     */
    const buttonTemplate = (rowData) => {
        return (
            <button
                className="btn btn-primary btn-sm"
                onClick={() => handleDetails(rowData)}
            >
                Ver detalles
            </button>
        );
    };
  
    return (

    <>
        <div className="container-fluid px-4">
            
            <h1 className="mt-4">Lista de torneos</h1>
            <ol className="breadcrumb mb-4">
                <li className="breadcrumb-item active">Torneos</li>
            </ol>
            <div className="card mb-4">
                <div className="card-header">
                    <i className="fa-solid fa-trophy me-1"></i>
                    Torneos
                </div>
                <div className="card-body">

                <DataTable value={tournaments} paginator rows={5} rowsPerPageOptions={[5, 10, 25, 50]} stripedRows tableStyle={{ minWidth: '50rem' }} locale="es">
                    <Column field="name" header="Nombre" sortable style={{ width: '15%' }}></Column>
                    <Column field="dateTime" header="Fecha y Hora" sortable style={{ width: '15%' }} body={(rowData) => (
                        <span>{new Date(rowData.dateTime).toLocaleString()}</span>
                    )}></Column>
                    <Column field="format" header="Formato" sortable style={{ width: '15%' }}></Column>
                    <Column field="state" header="Estado" sortable style={{ width: '15%' }}></Column>
                    <Column field="rounds" header="Rondas" sortable style={{ width: '15%' }}></Column>
                    <Column field="participantCount" header="Participantes" sortable style={{ width: '15%' }}></Column>
                    <Column header="Acciones" body={buttonTemplate} style={{ width: '15%' }}></Column>
                </DataTable>
                </div>
            </div>
        </div>  
    </>

        )
}
