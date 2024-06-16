import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';

/**
 * Componente funcional para mostrar el perfil de usuario y detalles relacionados con torneos de ajedrez y Chess.com.
 * 
 * @param {Object} userLogged - Objeto con la información del usuario loggeado.
 * @param {Function} handleLogin - Función para manejar el inicio de sesión del usuario.
 * @returns {JSX.Element} Componente de React que muestra los detalles del perfil del usuario.
 */
export const UserProfile = ({ userLogged, handleLogin }) => {
  const navigate = useNavigate();
  const [chessStats, setChessStats] = useState(null);
  const [chessProfile, setChessProfile] = useState(null);

  /**
   * Efecto secundario que se ejecuta al montar el componente para obtener las estadísticas de ajedrez y el perfil de Chess.com del usuario.
   * Se realiza una solicitud GET a la API de Chess.com si el usuario tiene un perfil de Chess.com configurado.
   */
  useEffect(() => {
    const fetchChessStats = async () => {
        if (userLogged.hasChessComProfile) {
            try {
                const response = await axios.get(`https://api.chess.com/pub/player/${userLogged.chessComProfile}/stats`);
                setChessStats(response.data);
            } catch (error) {
                console.error('Error fetching chess stats:', error);
            }
        }
    };

    const fetchChessProfile = async () => {
        try {
            const response = await axios.get(`https://api.chess.com/pub/player/${userLogged.chessComProfile}`);
            setChessProfile(response.data);
        } catch (error) {
            console.error('Error fetching chess profile:', error);
        }
    };

    fetchChessStats();
    fetchChessProfile();
}, [userLogged.hasChessComProfile, userLogged.chessComProfile]);

  console.log("chesscomprofile: " + (userLogged.chessComProfile));
  
  /**
   * Función para manejar la navegación a la página de edición de perfil.
   */
  const handleEdit = () => {
    navigate('/miperfil/edit');
};

/**
   * Función para calcular el porcentaje de victorias basado en el número de victorias, derrotas y empates.
   * 
   * @param {number} wins - Número de victorias.
   * @param {number} losses - Número de derrotas.
   * @param {number} draws - Número de empates.
   * @returns {string} Porcentaje de victorias formateado.
   */
const calculateWinrate = (wins, losses, draws) => {
  const totalGames = wins + losses + draws;
  return totalGames === 0 ? 0 : ((wins / totalGames) * 100).toFixed(2);
};

/**
   * Función para obtener el porcentaje de victorias de un tipo de juego específico (rápido, bala, blitz).
   * 
   * @param {Object} stats - Estadísticas del tipo de juego.
   * @returns {string} Porcentaje de victorias formateado.
   */
const getWinrate = (stats) => {
  if (!stats) {
      return 'N/A';
  }
  const { win, loss, draw } = stats.record;
  return calculateWinrate(win, loss, draw);
};

/**
   * Función para obtener la URL de la última partida jugada basada en las estadísticas.
   * 
   * @param {Object} stats - Estadísticas de ajedrez.
   * @returns {string|null} URL de la última partida jugada o null si no está disponible.
   */
const getLastGameUrl = (stats) => {
  if (!stats || !stats.chess_rapid || !stats.chess_rapid.best || !stats.chess_rapid.best.game) {
      return null;
  }
  return stats.chess_rapid.best.game;
};


  return (
    <div className='container' style={{ maxWidth: '80%'}}>
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
            {userLogged.hasChessComProfile && userLogged.chessComProfile !== null && chessStats && (
                  <>
                      <h6>Datos Chess.com</h6>
                      <div className="col d-flex align-items-center">
                          <div className='row'>
                            <div className='col'>
                            <p><strong>Rapid Winrate:</strong> {chessStats.chess_rapid ? getWinrate(chessStats.chess_rapid): 'N/A'}%</p>
                            <p><strong>Bullet Winrate:</strong> {chessStats.chess_bullet ? getWinrate(chessStats.chess_bullet): 'N/A'}%</p>
                            <p><strong>Blitz Winrate:</strong> {chessStats.chess_blitz ? getWinrate(chessStats.chess_blitz) : 'N/A'}%</p>
                            </div>
                          {chessProfile && (
                              <>
                                  <div className='col'>
                                    <p><strong>Título:</strong> {chessProfile.verified ? 'Verificado' : 'No verificado'}</p>
                                    <p><strong>Estatus de la cuenta:</strong> {chessProfile.status}</p>
                                  </div>
                                  <div className='col'>
                                    <p><strong>Streamer:</strong> {chessProfile.is_streamer ? 'Sí' : 'No'}</p>
                                    {chessProfile.is_streamer && (
                                        <p><a href={chessProfile.twitch_url} className='btn btn-primary btn-sm'>Twitch</a></p>
                                    )}
                                  </div>
                              </>
                            
                          )}
                          </div>
                      </div>
                      {getLastGameUrl(chessStats) && (
                          <div className="mt-3">
                              <a href={getLastGameUrl(chessStats)} target="_blank" rel="noopener noreferrer" className="btn btn-primary">Última partida jugada</a>
                          </div>
                      )}
                  </>
              )}
          </div>
          {/* Añade más campos según sea necesario */}
        </div>
        <div className='card-footer'>

          <Link className="btn btn-primary btn-sm float-end" to={{ pathname: '/miperfil/edit'}}>
                
                Editar perfil
            </Link>

        </div>
      </div>
    </div>
  );
};


