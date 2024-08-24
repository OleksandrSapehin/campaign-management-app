import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, Link } from 'react-router-dom';

const CampaignList = () => {
    const { productId } = useParams();
    const [campaigns, setCampaigns] = useState([]);

    const fetchCampaigns = async () => {
        try {
            const response = await axios.get(`/api/campaigns/${productId}`);
            setCampaigns(response.data);
        } catch (error) {
            console.error('Error fetching campaigns:', error);
        }
    };

    useEffect(() => {
        if (productId) {
            fetchCampaigns();
        }
    }, [productId]);

    const handleDeleteCampaign = async (campaignId) => {
        try {
            await axios.delete(`/api/campaigns/${campaignId}`);
            setCampaigns(campaigns.filter(c => c.id !== campaignId));
        } catch (error) {
            console.error('Error deleting campaign:', error);
        }
    };

    return (
        <div className="container mt-5">
            <h2 className="mb-4">Campaign List for Product ID: {productId}</h2>
            {campaigns.length > 0 ? (
                <ul className="list-group">
                    {campaigns.map(campaign => (
                        <li key={campaign.id} className="list-group-item">
                            <p><strong>ID:</strong> {campaign.id}</p>
                            <p><strong>Campaign Name:</strong> {campaign.campaignName}</p>
                            <p><strong>Keywords:</strong> {campaign.keywords.join(', ')}</p>
                            <p><strong>Bid Amount:</strong> {campaign.bidAmount}</p>
                            <p><strong>Campaign Fund:</strong> {campaign.campaignFund}</p>
                            <p><strong>Status:</strong> {campaign.status ? 'Enabled' : 'Disabled'}</p>
                            <p><strong>Town:</strong> {campaign.town}</p>
                            <p><strong>Radius(km):</strong> {campaign.radius} km</p>
                            <div className="d-flex justify-content-between">
                                <Link to={`/edit-campaign/${campaign.id}`} className="btn btn-warning">Edit</Link>
                                <button onClick={() => handleDeleteCampaign(campaign.id)} className="btn btn-danger">Delete</button>
                            </div>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No campaigns available for this product.</p>
            )}
        </div>
    );
};

export default CampaignList;