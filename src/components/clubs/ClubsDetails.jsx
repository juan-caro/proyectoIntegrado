import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

export const ClubsDetails = ({ club, user, isLoggedIn}) => {
    console.log(club);
    const [isMember, setIsMember] = useState(false);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchClubDetails = async () => {
            try {
                const isMemberResponse = await axios.get(`http://localhost:8080/clubs/isMember`, {
                    params: { 
                        clubId: club.id,
                        userId: user.id,
                        
                     }
                });
                console.log("isMemberResponse: " + isMemberResponse.data);
                setIsMember(isMemberResponse.data);
            } catch (error) {
                setError('Error fetching club details');
            } finally {
                setLoading(false);
            }
        };

        fetchClubDetails();
    }, [club.id, user]);

    const handleJoin = async () => {
        try {
            await axios.post(`http://localhost:8080/clubs/${club.id}/join`, null, { params: { userId: user.id } });
            setIsMember(true);
        } catch (error) {
            setError('Error joining the club');
        }
    };

    const handleLeave = async () => {
        try {
            await axios.post(`http://localhost:8080/clubs/${club.id}/leave`, null, { params: { userId: user.id } });
            setIsMember(false);
        } catch (error) {
            setError('Error leaving the club');
        }
    };

    if (loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;

    return (
        <div className="card border-primary mb-3">
            <h5 className="card-header text-bg-primary">{club.name}</h5>
            <div className="card-body">
                <p className="card-text">{club.description}</p>
                <p className="card-text">Rating: {club.rating}</p>
                {isLoggedIn ? (isMember ? (
                    <button className="btn btn-danger" onClick={handleLeave}>Dejar el Club</button>
                ) : (
                    <button className="btn btn-primary" onClick={handleJoin}>Unirse al Club</button>
                )) : <p>Para ser miembro del club, por favor <Link to={{ pathname: '/login'}}>inicie sesión.</Link></p>}
            </div>
        </div>
    );
};

