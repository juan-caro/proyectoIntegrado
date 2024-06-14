import React, { useEffect } from 'react'
import { useNavigate } from 'react-router-dom';

export const LogoutUser = ({handleLogout}) => {
  const navigate = useNavigate();
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
