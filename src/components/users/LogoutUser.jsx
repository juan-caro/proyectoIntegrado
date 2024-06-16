import React, { useEffect } from 'react'
import { useNavigate } from 'react-router-dom';

export const LogoutUser = ({handleLogout}) => {
  const navigate = useNavigate();

  // se ejecuta una vez al renderizar el componente
  useEffect(() => {
    handleLogout();
    navigate('/login');
  }, [handleLogout, navigate]);
  return (
    <>
        <div>Cerrando sesión...</div>
    </>
  )
}
