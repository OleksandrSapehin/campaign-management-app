import React, { useState, useEffect } from 'react';
import CampaignForm from './CampaignForm';
import CampaignList from './CampaignList';
import axios from 'axios';

const CampaignPage = () => {
    const [products, setProducts] = useState([]);
    const [selectedProductId, setSelectedProductId] = useState(null);
    const [campaigns, setCampaigns] = useState([]);
    const [editingCampaignId, setEditingCampaignId] = useState(null);

    useEffect(() => {
        fetchProducts();
    }, []);

    const fetchProducts = () => {
        axios.get('/api/products')
            .then(response => {
                setProducts(response.data);
                if (response.data.length > 0) {
                    setSelectedProductId(response.data[0].id);
                }
            })
            .catch(error => console.error('Error fetching products:', error));
    };

    const fetchCampaigns = () => {
        if (selectedProductId) {
            axios.get(`/api/campaigns/products/${selectedProductId}`)
                .then(response => {
                    setCampaigns(response.data);
                })
                .catch(error => console.error('Error fetching campaigns:', error));
        }
    };

    const handleSave = () => {
        setEditingCampaignId(null);
        fetchCampaigns();
    };

    const handleEdit = (campaignId) => {
        setEditingCampaignId(campaignId);
    };

    const handleDelete = (campaignId) => {
        axios.delete(`/api/campaigns/${campaignId}`)
            .then(() => {
                fetchCampaigns();
            })
            .catch(error => console.error('Error deleting campaign:', error));
    };

    return (
        <div>
            <h2>Manage Campaigns</h2>
            <div>
                <label>Select Product:</label>
                <select onChange={(e) => setSelectedProductId(e.target.value)} value={selectedProductId}>
                    {products.map(product => (
                        <option key={product.id} value={product.id}>
                            {product.name}
                        </option>
                    ))}
                </select>
                <button onClick={fetchCampaigns}>Find</button>
            </div>
            <CampaignForm productId={selectedProductId} campaignId={editingCampaignId} onSave={handleSave} />
            <CampaignList campaigns={campaigns} onEdit={handleEdit} onDelete={handleDelete} />
        </div>
    );
};

export default CampaignPage;