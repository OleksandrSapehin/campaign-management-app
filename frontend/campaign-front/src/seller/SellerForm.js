import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

const SellerForm = () => {
    const { sellerId } = useParams();
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [balance, setBalance] = useState('');
    const [errors, setErrors] = useState({});

    useEffect(() => {
        if (sellerId) {
            axios.get(`/api/sellers/${sellerId}`)
                .then(response => {
                    const seller = response.data;
                    setUsername(seller.username);
                    setEmail(seller.email);
                    setBalance(seller.balance);
                })
                .catch(error => console.error('Error fetching seller:', error));
        } else {
            setUsername('');
            setEmail('');
            setBalance('');
        }
    }, [sellerId]);

    const handleSubmit = (e) => {
        e.preventDefault();
        const seller = { username, email, balance };

        setErrors({});

        if (sellerId) {
            axios.put(`/api/sellers/${sellerId}`, seller)
                .then(response => {
                    navigate('/sellers');
                })
                .catch(error => {
                    if (error.response && error.response.status === 400) {
                        setErrors(error.response.data);
                    } else {
                        console.error('Error updating seller:', error);
                    }
                });
        } else {
            axios.post('/api/sellers', seller)
                .then(response => {
                    navigate('/sellers');
                })
                .catch(error => {
                    if (error.response && error.response.status === 400) {
                        setErrors(error.response.data);
                    } else {
                        console.error('Error creating seller:', error);
                    }
                });
        }
    };

    return (
        <form onSubmit={handleSubmit} className="container mt-5">
            <h2 className="mb-4">{sellerId ? 'Edit Seller' : 'Create Seller'}</h2>
            <div className="form-group">
                <label>Username:</label>
                <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    className="form-control"
                    required
                />
                {errors.username && <div className="text-danger">{errors.username}</div>}
            </div>
            <div className="form-group">
                <label>Email:</label>
                <input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    className="form-control"
                    required
                />
                {errors.email && <div className="text-danger">{errors.email}</div>}
            </div>
            <div className="form-group">
                <label>Balance:</label>
                <input
                    type="number"
                    value={balance}
                    onChange={(e) => setBalance(e.target.value)}
                    className="form-control"
                    required
                />
                {errors.balance && <div className="text-danger">{errors.balance}</div>}
            </div>
            <button type="submit" className="btn btn-primary mt-3">{sellerId ? 'Update' : 'Create'}</button>
        </form>
    );
};

export default SellerForm;