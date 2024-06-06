import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CryptoJS from 'crypto-js';

export const RegisterUser = ({ handleLogin, setUserLogged }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [email, setEmail] = useState('');
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();

  
    const encryptPassword = (password) => {
        const ciphertext = CryptoJS.AES.encrypt(password, 'secret').toString(); // Cifra la contraseña usando una clave secreta
        return ciphertext;
    };
    
    
    // const decryptPassword = (ciphertext) => {
    //     const bytes = CryptoJS.AES.decrypt(ciphertext, 'secret');
    //     const originalPassword = bytes.toString(CryptoJS.enc.Utf8);
    //     return originalPassword;
    // };
  
  const handleRegister = async () => {
    event.preventDefault();
    if (password !== confirmPassword) {
        setErrorMessage('Las contraseñas no coinciden');
        return;
      }

      const encryptedPassword = encryptPassword(password);
      console.log(encryptedPassword);
      setPassword(encryptedPassword);
    const response = await fetch('http://localhost:8080/users', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ username, password, email })
    });

    if (response.ok) {

        const responseLogin = await fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password }),
        });

        if(responseLogin.ok){
            const user = await response.json();
            handleLogin(user);
            setUserLogged(user);
            navigate('/');
        }
      
    } else {
      console.error('Error en el registro');
    }
  };

  return (
    <>
        <div className="container">
            <div className="row justify-content-center">
                <div className="col-lg-7">
                    <div className="card shadow-lg border-0 rounded-lg mt-5">
                        <div className="card-header"><h3 className="text-center font-weight-light my-4">Crear Cuenta</h3></div>
                        <div className="card-body">
                            <form>
                                <div className="row mb-3">
                                    <div className="col-md-6">
                                        <div className="form-floating mb-3 mb-md-0">
                                            <input className="form-control" id="inputUsername" type="text" placeholder="Introduzca su nombre de usuario" value={username} onChange={(e) => setUsername(e.target.value)} required />
                                            <label htmlFor="inputUsername">Nombre de Usuario</label>
                                        </div>
                                    </div>

                                    <div className='col-md-6'>
                                        <div className='form-floating mb-3 mb-md-0'>
                                            <input className="form-control" id="inputEmail" type="email" placeholder="name@example.com" value={email} onChange={(e) => setEmail(e.target.value)} required />
                                            <label htmlFor="inputEmail">Correo Electrónico</label>
                                        </div>
                                    </div>
                                </div>
                                
                                <div className="row mb-3">
                                    <div className="col-md-6">
                                        <div className="form-floating mb-3 mb-md-0">
                                            <input className="form-control" id="inputPassword" type="password" placeholder="Introduzca su contraseña" value={password} onChange={(e) => setPassword(e.target.value)} required />
                                            <label htmlFor="inputPassword">Contraseña</label>
                                        </div>
                                    </div>
                                    <div className="col-md-6">
                                        <div className="form-floating mb-3 mb-md-0">
                                            <input className="form-control" id="inputPasswordConfirm" type="password" placeholder="Confirmar contraseña" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} required />
                                            <label htmlFor="inputPasswordConfirm">Confirmar Contraseña</label>
                                            {errorMessage && <div className="text-danger">{errorMessage}</div>}
                                        </div>
                                    </div>
                                </div>
                                <div className="mt-4 mb-0">
                                
                                    <div className="d-grid"> <button type="submit" className='btn btn-primary btn-block' onClick={handleRegister}>Registrarse</button></div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </>
  );
};
