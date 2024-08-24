import React from 'react';
import { Link } from 'react-router-dom';

const HomePage = () => {
    return (
        <div>
            <h1>Welcome to the Management App</h1>
            <div>
                <Link to="/products">
                    <button>Manage Products</button>
                </Link>
            </div>
            <div>
                <Link to="/sellers">
                    <button>Manage Sellers</button>
                </Link>
            </div>
            <div>
                <Link to="/campaigns">
                    <button>Manage Campaigns</button>
                </Link>
            </div>
        </div>
    );
};

export default HomePage;