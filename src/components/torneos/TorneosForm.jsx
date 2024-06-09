import { useState, useEffect } from "react";
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';

export const TorneosForm = ({ userLogged }) => {
    const [name, setName] = useState('');
    const [dateTime, setDateTime] = useState('');
    const [format, setFormat] = useState('Bala');
    const [state, setState] = useState('Activo');
    const [rounds, setRounds] = useState('');
    const [file, setFile] = useState(null);
    const [platforms, setPlatforms] = useState([]);
    const [selectedPlatform, setSelectedPlatform] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const fetchPlatforms = async () => {
            try {
                const response = await axios.get('http://localhost:8080/platforms?page=0&size=100');
                setPlatforms(response.data.content);
            } catch (error) {
                console.error('Error fetching platforms:', error);
            }
        };

        fetchPlatforms();
    }, []);

    const showSwal = () => {
        Swal.fire({
            title: "Torneo Creado!",
            text: "El torneo se ha creado con éxito.",
            icon: "success"
        });
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const newTournament = {
                name,
                dateTime,
                format,
                state,
                rounds,
                creator_id: userLogged.id
            };

            // Crear el torneo
            const tournamentResponse = await axios.post('http://localhost:8080/tournaments', newTournament);
            const tournamentId = tournamentResponse.data.id;

            // Crear el archivo relacionado (si existe)
            if (file) {
                const formData = new FormData();
                formData.append('id', tournamentId);
                formData.append('file', file);

                await axios.put('http://localhost:8080/tournaments/photo', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                });
            }

            // Crear el Game relacionado
            const newGame = {
                gameDurationInSeconds: 0, // Cambia esto según tus necesidades
                platform: { id: selectedPlatform },
                tournament: { id: tournamentId }
            };

            await axios.post('http://localhost:8080/games', newGame);

            // Resetear el formulario
            setName('');
            setDateTime('');
            setFormat('');
            setState('');
            setRounds('');
            setFile(null);
            setSelectedPlatform('');
            showSwal();

            navigate('/torneos');
        } catch (error) {
            console.error('Error adding tournament:', error);
        }
    };

    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
    };

    return (
        <div className="row justify-content-center mt-5">
            <div className="col-lg-7">
                <div className="card shadow-lg border-0 rounded-lg mt-5">
                    <div className="card-header">
                        <h4 className="text-center font-weight-light my-4">Crear un Nuevo Torneo</h4>
                    </div>
                    <div className="card-body">
                        <form onSubmit={handleSubmit}>
                            <div className="row">
                                <div className="mb-3 col-md-6">
                                    <label htmlFor="name" className="form-label">Nombre</label>
                                    <input
                                        type="text"
                                        className="form-control"
                                        id="name"
                                        value={name}
                                        onChange={(e) => setName(e.target.value)}
                                        required
                                    />
                                </div>
                                <div className="mb-3 col-md-6">
                                    <label htmlFor="dateTime" className="form-label">Fecha y Hora</label>
                                    <input
                                        type="datetime-local"
                                        className="form-control"
                                        id="dateTime"
                                        value={dateTime}
                                        onChange={(e) => setDateTime(e.target.value)}
                                        required
                                    />
                                </div>
                            </div>
                            <div className="row">
                                <div className="mb-3 col-md-6">
                                    <label htmlFor="format" className="form-label">Formato</label>
                                    <select
                                        className="form-select"
                                        id="format"
                                        value={format}
                                        onChange={(e) => setFormat(e.target.value)}
                                        required
                                    >
                                        <option value="Activo">Bala</option>
                                        <option value="Pendiente">Blitz</option>
                                        <option value="Finalizado">Rápida</option>
                                        <option value="Finalizado">Por correspondencia</option>
                                    </select>
                                </div>
                                <div className="mb-3 col-md-6">
                                    <label htmlFor="state" className="form-label">Estado</label>
                                    <select
                                        className="form-select"
                                        id="state"
                                        value={state}
                                        onChange={(e) => setState(e.target.value)}
                                        required
                                    >
                                        <option value="Activo">Activo</option>
                                        <option value="Pendiente">Pendiente</option>
                                        <option value="Finalizado">Finalizado</option>
                                    </select>
                                </div>
                            </div>
                            <div className="row">
                                <div className="mb-3 col-md-6">
                                    <label htmlFor="rounds" className="form-label">Rondas</label>
                                    <input
                                        type="number"
                                        className="form-control"
                                        id="rounds"
                                        value={rounds}
                                        onChange={(e) => setRounds(e.target.value)}
                                        required
                                    />
                                </div>
                                <div className="mb-3 col-md-6">
                                    <label htmlFor="file" className="form-label">Subir Icono</label>
                                    <input
                                        type="file"
                                        className="form-control"
                                        id="file"
                                        onChange={handleFileChange}
                                    />
                                </div>
                            </div>
                            <div className="row">
                                <div className="mb-3 col-md-6">
                                    <label htmlFor="platform" className="form-label">Plataforma</label>
                                    <select
                                        className="form-select"
                                        id="platform"
                                        value={selectedPlatform}
                                        onChange={(e) => setSelectedPlatform(e.target.value)}
                                        required
                                    >
                                        <option value="">Seleccione una plataforma</option>
                                        {platforms.map(platform => (
                                            <option key={platform.id} value={platform.id}>
                                                {platform.name}
                                            </option>
                                        ))}
                                    </select>
                                </div>
                            </div>
                            <button type="submit" className="btn btn-primary">Añadir Torneo</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};
