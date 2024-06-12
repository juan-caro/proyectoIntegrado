import { useState, useEffect } from 'react';
import axios from 'axios';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';

export const PlatformsIndex = () => {
    const [platforms, setPlatforms] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [page, setPage] = useState(0); // Página inicial
    const [size, setSize] = useState(10); // Tamaño de la página

    useEffect(() => {
        const fetchPlatforms = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/platforms?page=${page}&size=${size}`);
                setPlatforms(response.data.content); // Usar `response.data.content` si usas paginación
                setLoading(false);
            } catch (error) {
                setError(error);
                setLoading(false);
            }
        };

        fetchPlatforms();
    }, [page, size]);
    console.log(platforms);

    const handlePreviousPage = () => {
        if (page > 0) {
            setPage(page - 1);
        }
    };

    const handleNextPage = () => {
        setPage(page + 1);
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    if (error) {
        return <p>Error: {error.message}</p>;
    }

    const buttonTemplate = (rowData) => {
        return (
            <a href={rowData.platformUrl} target="_blank" rel="noopener noreferrer" className="btn btn-primary">
                Web
            </a>
        );
    };

    return (
        <>
        <div className="container-fluid px-4">
            
            <h1 className="mt-4">Lista de plataformas</h1>
            <ol className="breadcrumb mb-4">
                <li className="breadcrumb-item active">Plataformas</li>
            </ol>
            <div className="card mb-4">
                <div className="card-header">
                <i className="fa-solid fa-gamepad me-1"></i>
                    Plataformas
                </div>
                <div className="card-body">

                <DataTable value={platforms} paginator rows={5} rowsPerPageOptions={[5, 10, 25, 50]} stripedRows tableStyle={{ minWidth: '50rem' }} locale="es">
                        <Column field="name" header="Nombre" sortable style={{ width: '15%' }}></Column>
                        <Column field="platform" header="Tipo" style={{ width: '15%' }} body={(rowData) => (
                            <span>{rowData.face_to_face ? 'Presencial' : 'En línea'}</span>
                        )} sortable></Column>
                        <Column header="Web" body={buttonTemplate} style={{ width: '15%' }}></Column>
                    </DataTable>
                    <table id="datatablesSimple" className="table table-striped">
                        <thead>
                            <tr>
                                <th scope="col">Nombre</th>
                                <th scope="col">Tipo</th>
                                <th>Sitio Web</th>
                            </tr>
                        </thead>
                        <tbody>
                            {platforms.length === 0 ? (
                                <tr>
                                    <td colSpan="6">No hay plataformas registradas actualmente.</td>
                                </tr>
                            ) : (
                                platforms.map(platform => (
                                    <tr key={platform.id}>
                                        <td>{platform.name}</td>
                                        <td>{platform.face_to_face ? 'Presencial' : 'En línea'}</td>
                                        <td>
                                            <a href={platform.platformUrl} target="_blank" rel="noopener noreferrer" className="btn btn-primary">
                                                Web
                                            </a>
                                        </td>
                                    </tr>
                                ))
                            )}
                        </tbody>
                        <tfoot>
                            <tr>
                                <th scope="col">Nombre</th>
                                <th scope="col">Tipo</th>
                                <th>Sitio Web</th>
                            </tr>
                        </tfoot>
                    </table>

                </div>
            </div>
        </div>  
    </>
    );
};

