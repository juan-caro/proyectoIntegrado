import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

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

  const handleDetails = (club) => {
    navigate('/clubs/details', { state: { club } });
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
                    <table id="datatablesSimple" className="table table-striped">
                        <thead>
                            <tr>
                                <th scope="col">Nombre</th>
                                <th scope="col">Descripción</th>
                                <th>Rating</th>
                                <th className="d-flex justify-content-end">Ver detalles</th>

                            </tr>
                        </thead>
                        <tbody>
                            {!clubs || clubs.length === 0 ? (
                                <tr>
                                    <td colSpan="4">Actualmente no hay clubs fundados.</td>
                                </tr>
                            ) : (
                                clubs.map(club => (
                                    <tr key={club.id}>
                                        <td>{club.name}</td>
                                        <td>{club.description}</td>
                                        <td>{club.rating}</td>
                                        <td>
                                            <button
                                                className="btn btn-primary btn-sm d-flex float-end"
                                                onClick={() => handleDetails(club)}
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
                                <th scope="col">Descripción</th>
                                <th>Rating</th>
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
