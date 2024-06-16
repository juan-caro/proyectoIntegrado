import { useState, useEffect } from "react";
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';

/**
 * @brief Componente funcional para editar un torneo.
 * @param {Object} userLogged - Objeto que contiene la información del usuario autenticado.
 * @returns {JSX.Element} Componente de formulario para editar torneos.
 */
export const TorneosEdit = ({ userLogged }) => {
    const location = useLocation();
    const { tournament } = location.state;
    const [name, setName] = useState(tournament.name || '');
    const [dateTime, setDateTime] = useState(tournament.dateTime || '');
    const [format, setFormat] = useState(tournament.format || 'Bala');
    const [state, setState] = useState(tournament.state || 'Activo');
    const [rounds, setRounds] = useState(tournament.rounds || '');
    const [file, setFile] = useState(null);
    const [platforms, setPlatforms] = useState([]);
    const [selectedPlatform, setSelectedPlatform] = useState(tournament.platform?.id || '');
    const navigate = useNavigate();

    /**
     * @brief Efecto para cargar las plataformas disponibles al cargar el componente.
     */
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
            title: "Torneo Actualizado!",
            text: "El torneo se ha actualizado con éxito.",
            icon: "success"
        });
    }

    /**
     * @brief Maneja la presentación del formulario para actualizar un torneo existente.
     * @param {Event} e - Evento de envío del formulario.
     */
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const updatedTournament = {
                name,
                dateTime,
                format,
                state,
                rounds
            };

            // Actualizar el torneo
            console.log(tournament.id);
            try{
                console.log(updatedTournament);
            const response = await axios.put(`http://localhost:8080/tournaments/${tournament.id}`, updatedTournament);
            console.log("response: " + response.data);
            }catch(error){
                console.error('error al enviar la solicitud: ' + error);
            }
            // Subir el archivo relacionado (si existe)
            if (file) {
                const formData = new FormData();
                formData.append('id', tournament.id);
                formData.append('file', file);

                await axios.put('http://localhost:8080/tournaments/photo', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                });
            }

            showSwal();

            navigate('/torneos');
        } catch (error) {
            console.error('Error updating tournament:', error);
        }
    };

    /**
     * @brief Maneja el cambio de archivo seleccionado para el icono del torneo.
     * @param {Event} e - Evento de cambio de archivo.
     */
    const handleFileChange = (e) => {
        setFile(e.target.files[0]);
    };

    return (
        <div className="row justify-content-center mt-5">
            <div className="col-lg-7">
                <div className="card shadow-lg border-0 rounded-lg mt-5">
                    <div className="card-header">
                        <h4 className="text-center font-weight-light my-4">Editar Torneo</h4>
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
                                        <option value="Bala">Bala</option>
                                        <option value="Blitz">Blitz</option>
                                        <option value="Rápida">Rápida</option>
                                        <option value="Por correspondencia">Por correspondencia</option>
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
                            
                            <button type="submit" className="btn btn-primary">Actualizar Torneo</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};
