import { RoutesApp } from "./routes/RoutesApp"
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const initialLogin = JSON.parse(sessionStorage.getItem('login')) || {
  isAuth: false,
  userLogged: {
    username: null,
  },
}

export const ChessTournamentsApp = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(initialLogin.isAuth);
  const [userLogged, setUserLogged] = useState(initialLogin.userLogged);
  const navigate = useNavigate();

  const handleLogin = (user) => {
    console.log(user);
    setIsLoggedIn(true);
    setUserLogged(user);
    sessionStorage.setItem('login', JSON.stringify({
      isAuth: true,
      userLogged: user,
    }));
  };

  const handleLogout = () => {
    setIsLoggedIn(false);
    setUserLogged(null);
    sessionStorage.setItem('login', JSON.stringify({
      isAuth: false,
      userLogged: {
        username: null,
      },
    }));
    navigate('/');
  };

  return (
    <>
      <RoutesApp
      isLoggedIn={isLoggedIn}
      handleLogin={handleLogin}
      handleLogout={handleLogout}
      userLogged={userLogged}
      setUserLogged={setUserLogged}
      />
    </>
  )
}

