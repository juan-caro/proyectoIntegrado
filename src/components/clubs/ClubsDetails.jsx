import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';

export const ClubsDetails = ({ club, user, isLoggedIn }) => {
    const [creator, setCreator] = useState(null);
    const [isMember, setIsMember] = useState(false);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchClubDetails = async () => {
            try {
                const isMemberResponse = await axios.get(`http://localhost:8080/clubs/isMember`, {
                    params: { 
                        clubId: club.id,
                        userId: user.id
                     }
                });
                setIsMember(isMemberResponse.data);
            } catch (error) {
                setError('Error fetching club details');
            }
        };

        const fetchCreator = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/clubs/creator`, {
                    params: { clubId: club.id }
                });
                setCreator(response.data);
            } catch (err) {
                setError('Error fetching club creator');
            }
        };

        const fetchData = async () => {
            setLoading(true);
            await fetchClubDetails();
            await fetchCreator();
            setLoading(false);
        };

        fetchData();
    }, [club.id, user.id]);

    const handleJoin = async () => {
        try {
            await axios.post(`http://localhost:8080/clubs/${club.id}/join`, null, { params: { userId: user.id } });
            setIsMember(true);
        } catch (error) {
            setError('Error joining the club');
        }
    };

    const handleLeave = async () => {
        try {
            await axios.post(`http://localhost:8080/clubs/${club.id}/leave`, null, { params: { userId: user.id } });
            setIsMember(false);
        } catch (error) {
            setError('Error leaving the club');
        }
    };

    const handleDelete = async () => {
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
                const response = await axios.delete(`http://localhost:8080/clubs/${club.id}`);
                console.log(response);
                navigate('/clubs');
            } catch (error) {
                setError('Error deleting the club: ' + error);
            }
        }
    };

    if (loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;

    return (
        <div className='container' style={{ maxWidth: '80%' }}>
            <div className="card shadow-lg border-0 rounded-lg mt-5 club-details">
                <div className='card-header'>
                    <div className='d-flex align-items-center'>
                        {club.iconUrl && (
                            <img
                                src={club.iconUrl}
                                alt="Club Icon"
                                className="img-fluid"
                                style={{ maxWidth: "100%", height: "6%", width: "6%", borderRadius: "60%" }}
                            />
                        )}
                        <h5 className='card-title ms-3'> {club.name} </h5>
                    </div>
                </div>
                <div className="card-body">
                    <div className='row'>
                        <div className="col d-flex flex-column align-items-start">
                            <div className='d-flex'>
                                <p className='me-3'><strong>Descripción:</strong> {club.description}</p>
                                <p className='me-3'><strong>Rating:</strong> {club.rating}</p>
                                {creator && (
                                    <p className='me-3'><strong>Creador:</strong> {creator.username}</p>
                                )}
                            </div>
                            
                        </div>
                        <div className='d-flex justify-content-between align-items-center'>
                                {isLoggedIn ? (
                                    isMember ? (
                                        <button className="btn btn-sm btn-danger" onClick={handleLeave}>Dejar el Club</button>
                                    ) : (
                                        <button className="btn btn-sm btn-primary" onClick={handleJoin}>Unirse al Club</button>
                                    )
                                ) : (
                                    <p>Para ser miembro del club, por favor <Link to="/login">inicie sesión.</Link></p>
                                )}
                                {creator && creator.id === user.id ? (
                                    <button className="btn btn-sm btn-danger ms-auto" onClick={handleDelete}>Eliminar el Club</button>
                                ) : null}
                            </div>
                    </div>
                </div>
                <div className='card-footer'>
                    <Link className="btn btn-primary btn-sm float-end" to={{ pathname: `/clubs/${club.id}/edit` }}>
                        Editar club
                    </Link>
                </div>
            </div>
        </div>
    );
};
