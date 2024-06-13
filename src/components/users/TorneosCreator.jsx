import axios from 'axios';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import Swal from "sweetalert2";
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';

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

const handleEdit = (tournament) => {
    navigate('/torneos/edit', { state: { tournament } });
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
            ).then(() => {
                window.location.reload();
            });
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

const buttonTemplate = (rowData) => {
    return (
            <div>
                <button
                    className="btn btn-primary btn-sm me-3"
                    onClick={() => handleDetails(rowData)}
                >
                    Ver detalles
                </button>
                <button
                    className='btn btn-warning btn-sm me-3 text-white'
                    onClick={() => handleEdit(rowData)}
                >
                    Editar
                </button>
                <button
                    className="btn btn-danger btn-sm"
                    onClick={() => handleDelete(rowData)}
                >
                    Eliminar
                </button>
            </div>
    );
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
                    <i className="fa-solid fa-trophy me-1"></i>
                    Torneos
                </div>
                <div className="card-body">
                <DataTable value={tournaments} paginator rows={5} rowsPerPageOptions={[5, 10, 25, 50]} stripedRows tableStyle={{ minWidth: '50rem' }} locale="es">
                    <Column field="name" header="Nombre" sortable style={{ width: '10%' }}></Column>
                    <Column field="dateTime" header="Fecha y Hora" sortable style={{ width: '10%' }} body={(rowData) => (
                        <span>{new Date(rowData.dateTime).toLocaleString()}</span>
                    )}></Column>
                    <Column field="format" header="Formato" sortable style={{ width: '10%' }}></Column>
                    <Column field="state" header="Estado" sortable style={{ width: '10%' }}></Column>
                    <Column field="rounds" header="Rondas" sortable style={{ width: '10%' }}></Column>
                    <Column field="participantCount" header="Participantes" sortable style={{ width: '10%' }}></Column>
                    <Column header="Acciones" body={buttonTemplate} style={{ width: '20%' }}></Column>
                </DataTable>                </div>
            </div>
        </div>  
    </>
  );
};

