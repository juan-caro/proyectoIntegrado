import React from 'react';

export const UserProfile = ({ userLogged }) => {


  return (
    <div className="user-profile">
      <h2>Perfil del Usuario</h2>
      <div className="profile-details">
        <p><strong>ID:</strong> {userLogged.id}</p>
        <p><strong>Nombre:</strong> {userLogged.name}</p>
        <p><strong>Email:</strong> {userLogged.email}</p>
        {/* Añade más campos según sea necesario */}
      </div>
    </div>
  );
};


