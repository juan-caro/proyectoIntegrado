import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';


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
            
            <h1 className="mt-4">Torneos Participados</h1>
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
  );
};


