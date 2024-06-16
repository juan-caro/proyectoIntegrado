import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link, useParams, useNavigate } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import Swal from 'sweetalert2';

/**
 * @brief Componente funcional para mostrar los detalles de un club.
 * @param {object} user - Usuario actualmente logueado.
 * @param {boolean} isLoggedIn - Indica si el usuario está logueado.
 * @returns {JSX.Element} Componente de detalles del club.
 */
export const ClubsDetails = ({ user, isLoggedIn }) => {
    const { state } = useLocation();
    const { clubId } = state; // Obtener clubId de los parámetros de la URL
    const [club, setClub] = useState(null);
    const [creator, setCreator] = useState(null);
    const [isMember, setIsMember] = useState(false);
    const [hasVoted, setHasVoted] = useState(false);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    console.log(clubId);

    /**
     * @brief Efecto secundario que se ejecuta al montar el componente o cuando cambia clubId.
     *        Realiza las llamadas a la API para obtener detalles del club, el estado de membresía del usuario
     *        y los detalles del creador del club.
     */
    useEffect(() => {

        /**
         * @brief Función asíncrona para obtener los detalles del club.
         */
        const fetchClubDetails = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/clubs/${clubId}`);
                setClub(response.data);
            } catch (error) {
                setError('Error fetching club details');
            }
        };

         /**
         * @brief Función asíncrona para verificar si el usuario es miembro del club.
         */
        const fetchIsMember = async () => {
            try {
                const isMemberResponse = await axios.get(`http://localhost:8080/clubs/isMember`, {
                    params: { 
                        clubId: clubId,
                        userId: user.id
                     }
                });
                setIsMember(isMemberResponse.data);
            } catch (error) {
                setError('Error fetching club details');
            }
        };

        /**
         * @brief Función asíncrona para obtener los detalles del creador del club.
         */
        const fetchCreator = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/clubs/creator`, {
                    params: { clubId }
                });
                setCreator(response.data);
            } catch (error) {
                setError('Error fetching club creator');
            }
        };

        /**
         * @brief Función que ejecuta todas las llamadas a la API necesarias para cargar los datos del club.
         */
        const fetchData = async () => {
            setLoading(true);
            await fetchClubDetails();
            await fetchIsMember();
            await fetchCreator();
            setLoading(false);
        };

        fetchData();
    }, [clubId, user.id]);

    /**
     * @brief Efecto secundario que se ejecuta cuando cambia clubId, user.id o isLoggedIn.
     *        Verifica si el usuario ha votado por el club actual.
     */
    useEffect(() => {
        const checkIfUserVoted = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/users/${user.id}/votedClubs`);
                const votedClubs = response.data;
                const hasVotedInThisClub = votedClubs.some(vc => vc.id === clubId);
                setHasVoted(hasVotedInThisClub);
            } catch (error) {
                setError('Error checking if user voted');
            }
        };

        if (isLoggedIn && user.id) {
            checkIfUserVoted();
        }
    }, [clubId, user.id, isLoggedIn]);

    /**
     * @brief Manejador para unirse al club.
     */
    const handleJoin = async () => {
        try {
            await axios.post(`http://localhost:8080/clubs/${clubId}/join`, null, { params: { userId: user.id } });
            setIsMember(true);
        } catch (error) {
            setError('Error joining the club');
        }
    };

    /**
     * @brief Manejador para dejar el club.
     */
    const handleLeave = async () => {
        try {
            await axios.post(`http://localhost:8080/clubs/${clubId}/leave`, null, { params: { userId: user.id } });
            setIsMember(false);
        } catch (error) {
            setError('Error leaving the club');
        }
    };

    /**
     * @brief Manejador para votar por el club.
     */

    const handleVote = async () => {
        try {
            await axios.post(`http://localhost:8080/clubs/${clubId}/vote`, null, { params: { userId: user.id } });
            setHasVoted(true);
            Swal.fire({
                icon: 'success',
                title: '¡Votación realizada!',
                text: 'Gracias por tu voto.',
            }).then(() => {
                window.location.reload();
            });
        } catch (error) {
            setError('Error al votar: ' + error);
        }
    };

    /**
     * @brief Manejador para eliminar el club.
     */
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
                const response = await axios.delete(`http://localhost:8080/clubs/${clubId}`);
                console.log(response);
                navigate('/clubs');
            } catch (error) {
                setError('Error deleting the club: ' + error);
            }
        }
    };

    if (loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;
    console.log("rating: " + club.rating);

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
                                <p className='me-3'><strong>Rating:</strong> {club.rating === undefined ? '0' : club.rating}</p>
                                
                                {creator && (
                                    <p className='me-3'><strong>Creador:</strong> {creator.username}</p>
                                )}
                            </div>
                            <p className='me-3'><strong>Descripción:</strong> {club.description}</p>
                        </div>
                        <div className='d-flex justify-content-between align-items-center'>
                            {isLoggedIn ? (
                                isMember ? (
                                    <>
                                        <button className="btn btn-sm btn-danger" onClick={handleLeave}>Dejar el Club</button>
                                        {!hasVoted && <button className="btn btn-sm btn-primary ms-2" onClick={handleVote}>Votar</button>}
                                    </>
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
            </div>
        </div>
    );
};
