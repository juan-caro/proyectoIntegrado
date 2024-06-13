import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';

export const ClubsIndex = () => {
  const [clubs, setClubs] = useState([]);
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const navigate = useNavigate();

  useEffect(() => {
    fetchClubs();
  }, [page, size]);

  const fetchClubs = async () => {
    try {
      const response = await axios.get(`http://localhost:8080/clubs?page=${page}&size=${size}`);
      console.log("clubs" + response.data);
      setClubs(response.data.content);
    } catch (error) {
      console.error('Error fetching clubs:', error);
    }
  };

  const handleDetails = (clubId) => {
    navigate(`/clubs/details`, { state: { clubId} });
};

const buttonTemplate = (rowData) => {
    console.log(rowData.membersCount);
    return (
        
        <button
            className="btn btn-primary btn-sm"
            onClick={() => handleDetails(rowData.id)}
        >
            Ver detalles
        </button>
    );

    
};

const membersCountTemplate = (rowData) => {
    return rowData.members ? rowData.members.length : 0;
};

  return (
    <>
        <div className="container-fluid px-4">
            
            <h1 className="mt-4">Lista de clubs</h1>
            <ol className="breadcrumb mb-4">
                <li className="breadcrumb-item active">Clubs</li>
            </ol>
            <div className="card mb-4">
                <div className="card-header">
                <i className="fa-solid fa-people-group me-1"></i>
                    Clubs
                </div>
                <div className="card-body">
                    <DataTable value={clubs} paginator rows={5} rowsPerPageOptions={[5, 10, 25, 50]} stripedRows tableStyle={{ minWidth: '50rem' }} locale="es">
                        <Column field="name" header="Nombre" sortable style={{ width: '15%' }}></Column>
                        <Column field="description" header="Descripción" sortable style={{ width: '15%' }}></Column>
                        <Column header="Nº Miembros" body={membersCountTemplate} sortable style={{ width: '15%' }}></Column>
                        <Column header="Detalles" body={buttonTemplate} style={{ width: '15%' }}></Column>
                    </DataTable>
                   
                </div>
            </div>
        </div>  
    </>
  );
};
