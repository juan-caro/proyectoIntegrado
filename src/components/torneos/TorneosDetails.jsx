import React from 'react'


export const TorneosDetails = ({tournament}) => {

    

    return (
        <div className="container">
            <h1 className="mt-4">Detalles del Torneo</h1>
            <div className="card mt-4">
                <div className="card-body">
                    <h5 className="card-title">{tournament.name}</h5>
                    <p className="card-text">Fecha y Hora: {new Date(tournament.dateTime).toLocaleString()}</p>
                    <p className="card-text">Formato: {tournament.format}</p>
                    <p className="card-text">Estado: {tournament.state}</p>
                    <p className="card-text">Rondas: {tournament.rounds}</p>
                    {/* Muestra la imagen del torneo */}
                    {tournament.iconUrl && (
                        <div className="mt-4">
                            <img
                                src={tournament.iconUrl}
                                alt="Torneo Icon"
                                className="img-fluid"
                                style={{ maxWidth: "100%", height: "auto" }}
                            />
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}
