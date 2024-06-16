import { useState, useEffect } from "react";
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Swal from 'sweetalert2';

/**
 * Componente funcional para permitir a los usuarios editar su perfil, incluyendo ELO, perfil de Chess.com y foto de perfil.
 * 
 * @param {Object} userLogged - Objeto con la información del usuario loggeado.
 * @param {Function} setUserLogged - Función para actualizar el usuario loggeado después de editar el perfil.
 * @param {Function} handleLogin - Función para manejar el inicio de sesión del usuario.
 * @returns {JSX.Element} Componente de React que muestra el formulario de edición de perfil.
 */
export const UserProfileForm = ({ userLogged, setUserLogged, handleLogin }) => {
    const [elo, setElo] = useState('');
    const [chessprofile, setChessProfile] = useState('');
    const [profilePicture, setProfilePicture] = useState(null);
    const navigate = useNavigate();


    /**
     * Efecto secundario que se ejecuta al montar el componente para obtener los datos actuales del usuario y llenar el formulario.
     */
    useEffect(() => {
        const fetchUserData = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/users/${userLogged.id}`);
                setElo(response.data.elo);
                setChessProfile(response.data.chessComProfile);
            } catch (error) {
                console.error('Error fetching user data:', error);
            }
        };

        fetchUserData();
    }, [userLogged.id]);

    const showSwal = () => {
        Swal.fire({
            title: "Perfil Actualizado!",
            text: "Tu perfil se ha actualizado con éxito.",
            icon: "success"
        }).then(() => {
            window.location.reload();
        });
    }

    /**
     * Función para manejar el envío del formulario de edición de perfil.
     * 
     * @param {Event} e - Evento del formulario.
     */
    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            let updatedUser;
            if(chessprofile !== ''){
                updatedUser = {
                    elo,
                    id: userLogged.id,
                    hasChessComProfile: true,
                    chessComProfile: chessprofile
                };
            } else {
                updatedUser = {
                    elo,
                    id: userLogged.id,
                };
            }
    
            // Update the user profile
            await axios.put(`http://localhost:8080/users/${userLogged.id}`, updatedUser);
    
            // Update the profile picture if a new one is selected
            if (profilePicture) {
                const formData = new FormData();
                formData.append('id', userLogged.id);
                formData.append('file', profilePicture);
    
                await axios.put('http://localhost:8080/users/photo', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                });
                console.log("photo subida");
            }
    
            
    
            // Fetch the updated user data
            const response = await axios.get(`http://localhost:8080/users/${userLogged.id}`);
            // Update userLogged with the updated data

            sessionStorage.setItem('login', JSON.stringify({
                isAuth: true,
                userLogged: response.data,
              }));
    
              showSwal();
            navigate('/miperfil');

        } catch (error) {
            console.error('Error updating profile:', error);
        }
    };

    /**
     * Función para manejar el cambio de archivo de la foto de perfil seleccionada.
     * 
     * @param {Event} e - Evento del input de archivo.
     */
    const handleFileChange = (e) => {
        setProfilePicture(e.target.files[0]);
    };

    return (
        <div className="row justify-content-center mt-5">
            <div className="col-lg-7">
                <div className="card shadow-lg border-0 rounded-lg mt-5">
                    <div className="card-header">
                        <h4 className="text-center font-weight-light my-4">Editar Perfil</h4>
                    </div>
                    <div className="card-body">
                        <form onSubmit={handleSubmit}>
                            <div className="mb-3">
                                <label htmlFor="elo" className="form-label">ELO</label>
                                <input
                                    type="number"
                                    className="form-control"
                                    id="elo"
                                    value={elo}
                                    onChange={(e) => setElo(e.target.value)}
                                    required
                                />
                            </div>
                            <div className="mb-3">
                                <label htmlFor="chessprofile" className="form-label">Usuario de Chess.com</label>
                                <input
                                    type="text"
                                    className="form-control"
                                    id="chessprofile"
                                    value={chessprofile}
                                    onChange={(e) => setChessProfile(e.target.value)}
                                />
                            </div>
                            <div className="mb-3">
                                <label htmlFor="profilePicture" className="form-label">Foto de Perfil</label>
                                <input
                                    type="file"
                                    className="form-control"
                                    id="profilePicture"
                                    onChange={handleFileChange}
                                />
                            </div>
                            <button type="submit" className="btn btn-primary">Actualizar Perfil</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
};
