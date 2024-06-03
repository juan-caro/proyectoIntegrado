import axios from 'axios';
import { useEffect, useState } from 'react';

export const TorneosUser = ({ userLogged }) => {
  const [tournaments, setTournaments] = useState([]);

  useEffect(() => {
    const fetchTournaments = async () => {
      try {
        const response = await axios.get('/participations/userTournaments', {
          params: { userId: userLogged.id }
        });
        setTournaments(response.data);
      } catch (error) {
        console.error('Error fetching user tournaments', error);
      }
    };

    fetchTournaments();
  }, [userLogged.id]);

  return (
    <div>
      <h2>Your Tournaments</h2>
      <ul>
        {tournaments.map(tournament => (
          <li key={tournament.id}>{tournament.name}</li>
        ))}
      </ul>
    </div>
  );
};


