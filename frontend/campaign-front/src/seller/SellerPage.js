import React, { useState, useEffect } from 'react';
import SellerForm from './SellerForm';
import SellerList from './SellerList';
import axios from 'axios';

const SellerPage = () => {
    const [sellers, setSellers] = useState([]);
    const [editingSellerId, setEditingSellerId] = useState(null);

    useEffect(() => {
        fetchSellers();
    }, []);

    const fetchSellers = () => {
        axios.get('/api/sellers')
            .then(response => {
                setSellers(response.data);
            })
            .catch(error => console.error('Error fetching sellers:', error));
    };

    const handleSave = (seller) => {
        setEditingSellerId(null);
        fetchSellers();
    };

    const handleEdit = (sellerId) => {
        setEditingSellerId(sellerId);
    };

    const handleDelete = (sellerId) => {
        axios.delete(`/api/sellers/${sellerId}`)
            .then(() => {
                fetchSellers();
            })
            .catch(error => console.error('Error deleting seller:', error));
    };

    return (
        <div>
            <h2>Manage Sellers</h2>
            <SellerForm sellerId={editingSellerId} onSave={handleSave} />
            <SellerList sellers={sellers} onEdit={handleEdit} onDelete={handleDelete} />
        </div>
    );
};

export default SellerPage;