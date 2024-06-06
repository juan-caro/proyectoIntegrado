import { useState } from "react";
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';

export const TorneosForm = ({userLogged}) => {
    const [name, setName] = useState('');
    const [dateTime, setDateTime] = useState('');
    const [format, setFormat] = useState('');
    const [state, setState] = useState('');
    const [rounds, setRounds] = useState('');
    const [file, setFile] = useState(null);
    const navigate = useNavigate();

    const showSwal = () => {
        Swal.fire({
            title: "Torneo Creado!",
            text: "El torneo se ha creado con éxito.",
            icon: "success"
          });
    }

    console.log(userLogged.id);

    const handleSubmit = async (e) => {
        e.preventDefault();
        console.log(userLogged);
        try {

        const newTournament = {
            name,
            dateTime,
            format,
            state,
            rounds,
            creator_id: userLogged.id,
        };

        const response = await axios.post('http://localhost:8080/tournaments', newTournament);
        const tournamentId = response.data.id;

        console.log("Creado");

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

        setName('');
        setDateTime('');
        setFormat('');
        setState('');
        setRounds('');
        setFile(null);
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
                    <input
                        type="text"
                        className="form-control"
                        id="format"
                        value={format}
                        onChange={(e) => setFormat(e.target.value)}
                        required
                    />
                    </div>
                    <div className="mb-3 col-md-6">
                    <label htmlFor="state" className="form-label">Estado</label>
                    <input
                        type="text"
                        className="form-control"
                        id="state"
                        value={state}
                        onChange={(e) => setState(e.target.value)}
                        required
                    />
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
                <button type="submit" className="btn btn-primary">Añadir Torneo</button>
                </form>
            </div>
            </div>
        </div>
        </div>
    );
};