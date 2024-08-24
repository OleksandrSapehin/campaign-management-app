import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import ProductForm from './product/ProductForm';
import SellerForm from './seller/SellerForm';
import CampaignForm from './campaign/CampaignForm';
import CampaignList from './campaign/CampaignList';
import ProductList from './product/ProductList';
import SellerList from './seller/SellerList';
import Home from './home/Home';

const App = () => {
  return (
      <Router>
        <div className="App">
          <nav className="navbar navbar-expand-lg navbar-light bg-light">
            <div className="container-fluid">
              <Link className="navbar-brand" to="/">Campaign Manager</Link> {}
              <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
              </button>
              <div className="collapse navbar-collapse" id="navbarNav">
                <ul className="navbar-nav">
                  <li className="nav-item">
                    <Link className="nav-link" to="/create-product">Create Product</Link>
                  </li>
                  <li className="nav-item">
                    <Link className="nav-link" to="/create-seller">Create Seller</Link>
                  </li>
                  <li className="nav-item">
                    <Link className="nav-link" to="/create-campaign">Create Campaign</Link>
                  </li>
                  <li className="nav-item">
                    <Link className="nav-link" to="/products">Manage Products</Link>
                  </li>
                  <li className="nav-item">
                    <Link className="nav-link" to="/sellers">Manage Sellers</Link>
                  </li>
                </ul>
              </div>
            </div>
          </nav>

          <div className="container mt-4">
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/create-product" element={<ProductForm />} />
              <Route path="/create-seller" element={<SellerForm />} />
              <Route path="/create-campaign" element={<CampaignForm />} />
              <Route path="/products" element={<ProductList />} />
              <Route path="/sellers" element={<SellerList />} />
              <Route path="/campaigns/:productId" element={<CampaignList />} />
              <Route path="/edit-product/:productId" element={<ProductForm />} />
              <Route path="/edit-seller/:sellerId" element={<SellerForm />} />
              <Route path="/edit-campaign/:campaignId" element={<CampaignForm />} />
            </Routes>
          </div>
        </div>
      </Router>
  );
};

export default App;