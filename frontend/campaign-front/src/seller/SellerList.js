import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const SellerList = () => {
    const [sellers, setSellers] = useState([]);

    useEffect(() => {
        fetchSellers();
    }, []);

    const fetchSellers = async () => {
        try {
            const response = await axios.get('/api/sellers');
            setSellers(response.data);
        } catch (error) {
            console.error('Error fetching sellers:', error);
        }
    };

    const handleDeleteSeller = async (sellerId) => {
        try {
            await axios.delete(`/api/sellers/${sellerId}`);
            setSellers(sellers.filter(s => s.id !== sellerId));
        } catch (error) {
            console.error('Error deleting seller:', error);
        }
    };

    return (
        <div className="container mt-5">
            <h2 className="mb-4">Seller List</h2>
            {sellers.length > 0 ? (
                <ul className="list-group">
                    {sellers.map(seller => (
                        <li key={seller.id} className="list-group-item">
                            <p><strong>ID:</strong> {seller.id}</p>
                            <p><strong>Username:</strong> {seller.username}</p>
                            <p><strong>Email:</strong> {seller.email}</p>
                            <p><strong>Balance:</strong> {seller.balance}</p>
                            <div className="d-flex justify-content-between">
                                <Link to={`/edit-seller/${seller.id}`} className="btn btn-warning">Edit</Link>
                                <button onClick={() => handleDeleteSeller(seller.id)} className="btn btn-danger">Delete</button>
                            </div>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No sellers available.</p>
            )}
        </div>
    );
};

export default SellerList;