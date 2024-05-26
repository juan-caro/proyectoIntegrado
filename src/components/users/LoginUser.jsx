import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

export const LoginUser = ({ handleLogin, setUserLogged }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMessage, setErrorMessage] = useState('');

  const navigate = useNavigate();

  const handleLoginClick = async () => {
    const response = await fetch('http://localhost:8080/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ username, password })
    });

    if (response.ok) {
      const user = await response.json();
      console.log("response ok: " + user);
      handleLogin(user);
      setUserLogged(user);
      navigate('/');
    } else {
      setErrorMessage('Invalid credentials');
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
                  <button type="submit" className="btn btn-primary btn-block">Login</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

