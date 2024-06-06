import React, { useEffect, useState } from 'react';
import axios from 'axios';

export const TorneosDetails = ({ tournament, user }) => {
    const [isRegistered, setIsRegistered] = useState(false);

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
                    {isRegistered ? (
                        <button className='btn btn-danger' onClick={handleUnregister}>Cancelar Inscripción</button>
                    ) : (
                        <button className='btn btn-primary' onClick={handleRegister}>Inscribirse</button>
                    )}
                </div>
            </div>
        </div>
    );
};

