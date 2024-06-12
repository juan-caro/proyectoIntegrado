import React, { useState } from 'react'
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';

export const ClubsForm = ({userLogged}) => {
    const [name, setName] = useState('');
    const [description, setDescription] = useState('');
    const [rating, setRating] = useState(0);
    const [file, setFile] = useState(null);
    const navigate = useNavigate();

    console.log("user id: " + userLogged.id);

    const handleSubmit = async (event) => {
        event.preventDefault();

        const newClub = {
        name,
        description,
        rating,
        creator_id: userLogged.id,
        };
        
        try {
        const response = await axios.post('http://localhost:8080/clubs', newClub);
        const clubId = response.data.id;
        console.log('Club created:', response.data);
        if (file) {
    
            const formData = new FormData();
            formData.append('id', clubId);
            formData.append('file', file);

            await axios.put('http://localhost:8080/clubs/photo', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
            });

            

        }
        navigate('/clubs');
        // Optionally, you can reset the form or redirect the user
        } catch (error) {
        console.error('Error creating club:', error);
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
            <h4 className="text-center font-weight-light my-4">Fundar un Nuevo Club</h4>
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
                
                

                <div className="mb-3 col-md-12">
                <label htmlFor="description" className="form-label">Descripción</label>
                    <textarea
                        className="form-control"
                        id="description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                        required
                    />
                </div>
            </div>
            <button type="submit" className="btn btn-primary">Fundar Club</button>
            </form>
        </div>
        </div>
    </div>
    </div>
);
}
