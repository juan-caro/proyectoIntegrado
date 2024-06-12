import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import CryptoJS from 'crypto-js';
import { Link } from 'react-router-dom';

export const LoginUser = ({ handleLogin, setUserLogged }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const navigate = useNavigate();
  

  // Función para cifrar una contraseña
  const encryptPassword = (password) => {
    const key = CryptoJS.enc.Utf8.parse('secret'); // Clave de cifrado (debe ser de 16, 24 o 32 bytes para AES)
    const encrypted = CryptoJS.AES.encrypt(password, key, { 
        mode: CryptoJS.mode.ECB, // Modo ECB para cifrado determinista
        padding: CryptoJS.pad.Pkcs7 
    });
    return encrypted.toString();
};

  // Función para descifrar la contraseña (si es necesario)
  // const decryptPassword = (ciphertext) => {
  //     const bytes = CryptoJS.AES.decrypt(ciphertext, 'secret');
  //     const originalPassword = bytes.toString(CryptoJS.enc.Utf8);
  //     return originalPassword;
  // };

  const handleLoginClick = async () => {

    const decryptedPassword = password;
    const encryptedPassword = encryptPassword(password);
    setPassword(encryptedPassword);
    console.log(encryptedPassword);
    const response = await fetch('http://localhost:8080/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ username, password: encryptedPassword })
    });

    if (response.ok) {

      const user = await response.json();
      console.log(response.json());
      setPassword(decryptedPassword);
      handleLogin(user);
      setUserLogged(user);
      navigate('/');
    } else {
      setPassword(decryptedPassword);
      setErrorMessage('Credenciales incorrectas');
    }
  };

  return (
    <div className="container">
      <div className="row justify-content-center">
        <div className="col-lg-7">
          <div className="card shadow-lg border-0 rounded-lg mt-5">
            <div className="card-header"><h3 className="text-center font-weight-light my-4">Login</h3></div>
            <div className="card-body">
              <form onSubmit={(e) => { e.preventDefault(); handleLoginClick(); }}>
                <div className="form-floating mb-3">
                  <input className="form-control" id="inputUsername" type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} required />
                  <label htmlFor="inputUsername">Username</label>
                </div>
                <div className="form-floating mb-3">
                  <input className="form-control" id="inputPassword" type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                  <label htmlFor="inputPassword">Password</label>
                </div>
                {errorMessage && <div className="text-danger">{errorMessage}</div>}
                <div className="d-grid">
                  <button type="submit" className="btn btn-primary btn-block">Iniciar Sesión</button>
                </div>
              </form>
            </div>
            <div className='card-footer'>
                ¿No tienes cuenta? <Link to={{ pathname: '/register'}}>Regístrate aquí</Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

