import React from 'react';

export const UserProfile = ({ userLogged }) => {

  console.log(userLogged);

  return (
    <div className='container' style={{ maxWidth: '50%'}}>
      <div className="card shadow-lg border-0 rounded-lg mt-5 user-profile">
        <div className='card-header'>
          <div className='d-flex'>
            {userLogged.photoUrl && (
                <img
                    src={userLogged.photoUrl}
                    alt="User Icon"
                    className="img-fluid"
                    style={{ maxWidth: "100%", height: "6%", width: "6%", borderRadius: "60%" }}
                />
            )}
            <h5 className='card-title ms-3'> {userLogged.username} </h5>
          </div>
        </div>
        <div className="card-body profile-details">
          <div className='row'>
            <h6>Datos Chess Tournaments</h6>
            <div className="col d-flex align-items-center">
              <p className='me-3'><strong>Nombre:</strong> {userLogged.username}</p>
              <p className='me-3'><strong>Email:</strong> {userLogged.email}</p>
              <p><strong>Elo:</strong> {userLogged.elo}</p>
            </div>
          </div>
          {/* Añade más campos según sea necesario */}
        </div>
      </div>
    </div>
  );
};


