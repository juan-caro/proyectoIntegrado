import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

/**
 * @brief Componente funcional para mostrar los detalles de un torneo y gestionar la inscripción de usuarios.
 * @param {Object} tournament - Información del torneo a mostrar.
 * @param {Object} user - Información del usuario actual.
 * @param {boolean} isLoggedIn - Indica si el usuario está autenticado.
 * @returns {JSX.Element} Componente de detalles del torneo.
 */
export const TorneosDetails = ({ tournament, user, isLoggedIn}) => {
    const [isRegistered, setIsRegistered] = useState(false);
    /**
     * @brief Efecto para verificar si el usuario está inscrito al cargar el componente.
     */
    useEffect(() => {
        // Verificar si el usuario ya está inscrito
        const checkRegistration = async () => {
            try {
                const response = await axios.get(`/participations/isRegistered`, {params: {
                    tournamentId: tournament.id,
                    userId: user.id,
                }});
                console.log("response: " + response.data);
                setIsRegistered(response.data);
            } catch (error) {
                console.error('Error checking registration status', error);
            }
        };

        checkRegistration();
    }, [tournament.id, user.id]);

    /**
     * @brief Maneja la acción de inscripción del usuario en el torneo.
     */
    const handleRegister = async () => {
        try {
            await axios.post('/participations/register', null, {
                params: {
                    tournamentId: tournament.id,
                    userId: user.id,
                }
            });
            setIsRegistered(true);
        } catch (error) {
            console.error('Error registering for tournament', error);
        }
    };

    /**
     * @brief Maneja la acción de cancelar la inscripción del usuario en el torneo.
     */
    const handleUnregister = async () => {
        try {
            await axios.delete('/participations/unregister', {
                params: {
                    tournamentId: tournament.id,
                    userId: user.id,
                }
            });
            setIsRegistered(false);
        } catch (error) {
            console.error('Error unregistering from tournament', error);
        }
    };

    /**
     * @brief Renderiza los detalles del torneo y el formulario de inscripción.
     * @returns {JSX.Element} Elemento JSX que representa los detalles del torneo.
     */
    return (
        <div className="container">
            <h1 className="mt-4">Detalles del Torneo</h1>
            <div className="card mt-4" style={{ maxWidth: '50%' }}>
                <div className='card-header'>
                    <div className='d-flex'>
                        {tournament.iconUrl && (
                            <img
                                src={tournament.iconUrl}
                                alt="Torneo Icon"
                                className="img-fluid"
                                style={{ maxWidth: "100%", height: "10%", width: "10%", borderRadius: "60%" }}
                            />
                        )}
                        <h5 className="card-title ms-3">{tournament.name}</h5>
                    </div>
                </div>
                <div className="card-body">
                    <div className="row">
                        <div className="col d-flex align-items-center">
                            <div className="me-3">
                                Fecha y Hora: {new Date(tournament.dateTime).toLocaleString()}
                            </div>
                            <div>
                                Formato: {tournament.format}
                            </div>
                        </div>
                    </div>
                    <div className='row'>
                        <p className="card-text">Estado: {tournament.state}</p>
                        <p className="card-text">Rondas: {tournament.rounds}</p>
                    </div>
                    
                    
                </div>
                <div className='card-footer'>
                {isLoggedIn ? (
                    tournament.state === 'Finalizado' ? (
                        <p>Ya no puede inscribirse porque el torneo ha finalizado.</p>
                    ) : tournament.state === 'Pendiente' ? (
                        <p>El torneo estará próximamente disponible para inscribirse.</p>
                    ) : (
                        isRegistered ? (
                            <button className='btn btn-danger' onClick={handleUnregister}>Cancelar Inscripción</button>
                        ) : (
                            <button className='btn btn-primary' onClick={handleRegister}>Inscribirse</button>
                        )
                    )
                ) : (
                    <p>Para inscribirse, por favor <Link to={{ pathname: '/login'}}>inicie sesión.</Link></p>
                )}
                </div>
            </div>
        </div>
    );
};

