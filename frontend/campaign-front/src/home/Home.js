import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Home = () => {
    const [productId, setProductId] = useState('');
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        if (productId) {
            navigate(`/campaigns/${productId}`);
        }
    };

    return (
        <div className="container mt-5">
            <h2 className="mb-4">Enter Product ID to View Campaigns</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <input
                        type="text"
                        value={productId}
                        onChange={(e) => setProductId(e.target.value)}
                        placeholder="Enter Product ID"
                        className="form-control"
                    />
                </div>
                <button type="submit" className="btn btn-primary mt-3">View Campaigns</button>
            </form>
        </div>
    );
};

export default Home;