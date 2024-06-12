import { useState } from 'react';
import axios from 'axios';
import Swal from 'sweetalert2';
import { useNavigate } from 'react-router-dom';

export const PlatformsForm = () => {
    const [name, setName] = useState('');
    const [faceToFace, setFaceToFace] = useState(false);
    const [platformUrl, setPlatformUrl] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        
        const newPlatform = {
            name,
            faceToFace,
            platformUrl
        };

        try {
            console.log("plataforma nueva: " + newPlatform.faceToFace);
            await axios.post('http://localhost:8080/platforms', newPlatform);
            Swal.fire({
                title: "Plataforma Creada",
                text: "La plataforma se ha creado con éxito.",
                icon: "success"
            });
            navigate('/platforms');
        } catch (error) {
            Swal.fire({
                title: "Error",
                text: "Hubo un error al crear la plataforma.",
                icon: "error"
            });
            console.error('Error creating platform:', error);
        }
    };

    return (
        <div className="row justify-content-center mt-5">
            <div className="col-lg-7">
                <div className="card shadow-lg border-0 rounded-lg mt-5">
                    <div className="card-header">
                        <h4 className="text-center font-weight-light my-4">Crear una Nueva Plataforma</h4>
                    </div>
                    <div className="card-body">
                        <form onSubmit={handleSubmit}>
                            <div className="mb-3">
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
                            <div className="mb-3">
                                <label htmlFor="faceToFace" className="form-label">Tipo de Plataforma</label>
                                <select
                                    className="form-select"
                                    id="faceToFace"
                                    value={faceToFace}
                                    onChange={(e) => setFaceToFace(e.target.value)}
                                    required
                                >
                                    <option value="false">Online</option>
                                    <option value="true">Presencial</option>
                                </select>
                            </div>
                            <div className="mb-3">
                                <label htmlFor="platformUrl" className="form-label">URL de la Plataforma</label>
                                <input
                                    type="url"
                                    className="form-control"
                                    id="platformUrl"
                                    value={platformUrl}
                                    onChange={(e) => setPlatformUrl(e.target.value)}
                                    required
                                />
                            </div>
                            <button type="submit" className="btn btn-primary">Crear Plataforma</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};

