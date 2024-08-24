import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';
import Autosuggest from 'react-autosuggest';
import './CampaignForm.css';

const predefinedKeywords = [
    "Java", "JavaScript", "React", "Spring", "Hibernate", "SQL", "NoSQL", "Docker",
    "Kubernetes", "CI/CD", "Python", "Django", "Flask", "Node.js", "Express"
];

const towns = [
    "Warsaw", "Krakow", "Lodz", "Wroclaw", "Poznan", "Gdansk", "Szczecin",
    "Bydgoszcz", "Lublin", "Katowice", "Bialystok", "Gdynia", "Czestochowa",
    "Radom", "Rzeszow", "Torun", "Kielce", "Gliwice", "Zabrze", "Olsztyn"
];

const getSuggestions = value => {
    const inputValue = value.trim().toLowerCase();
    const inputLength = inputValue.length;

    return inputLength === 0 ? [] : predefinedKeywords.filter(
        keyword => keyword.toLowerCase().startsWith(inputValue)
    );
};

const getSuggestionValue = suggestion => suggestion;

const renderSuggestion = suggestion => (
    <div className="suggestion-item">
        {suggestion}
    </div>
);

const CampaignForm = () => {
    const { campaignId } = useParams();
    const navigate = useNavigate();

    const [campaignName, setCampaignName] = useState('');
    const [keywords, setKeywords] = useState('');
    const [bidAmount, setBidAmount] = useState('');
    const [campaignFund, setCampaignFund] = useState('');
    const [status, setStatus] = useState(true);
    const [town, setTown] = useState('');
    const [radius, setRadius] = useState('');
    const [sellerId, setSellerId] = useState('');
    const [productId, setProductId] = useState('');
    const [errors, setErrors] = useState({});
    const [suggestions, setSuggestions] = useState([]);
    const [insufficientFundsError, setInsufficientFundsError] = useState('');

    useEffect(() => {
        if (campaignId) {
            axios.get(`/api/campaigns/campaign/${campaignId}`)
                .then(response => {
                    const campaign = response.data;

                    setCampaignName(campaign.campaignName || '');
                    setKeywords(Array.isArray(campaign.keywords) ? campaign.keywords.join(' ') : '');
                    setBidAmount(campaign.bidAmount || '');
                    setCampaignFund(campaign.campaignFund || '');
                    setStatus(campaign.status || false);
                    setTown(campaign.town || '');
                    setRadius(campaign.radius || '');
                    setProductId(campaign.productId || '');
                })
                .catch(error => console.error('Error fetching campaign:', error));
        }
    }, [campaignId]);

    const handleSubmit = (e) => {
        e.preventDefault();

        const trimmedKeywords = keywords.trim();

        const campaign = {
            campaignName,
            keywords: trimmedKeywords.split(' ').filter(k => k.trim() !== ''),
            bidAmount,
            campaignFund,
            status,
            town,
            radius
        };

        setErrors({});
        setInsufficientFundsError('');

        const navigateToHome = () => {
            navigate('/');
        };

        if (campaignId) {
            axios.put(`/api/campaigns/${campaignId}`, campaign)
                .then(response => {
                    alert('Campaign updated successfully!');
                    navigateToHome();
                })
                .catch(error => {
                    if (error.response && error.response.status === 400) {
                        if (error.response.data.error === 'Insufficient funds') {
                            setInsufficientFundsError(error.response.data.message);
                        } else {
                            setErrors(error.response.data);
                        }
                    } else {
                        console.error('Error updating campaign:', error);
                    }
                });
        } else {
            axios.post(`/api/campaigns/products/${productId}?sellerId=${sellerId}`, campaign)
                .then(response => {
                    alert('Campaign created successfully!');
                    navigateToHome();
                })
                .catch(error => {
                    if (error.response && error.response.status === 400) {
                        if (error.response.data.error === 'Insufficient funds') {
                            setInsufficientFundsError(error.response.data.message);
                        } else {
                            setErrors(error.response.data);
                        }
                    } else {
                        console.error('Error creating campaign:', error);
                    }
                });
        }
    };

    const onSuggestionsFetchRequested = ({ value }) => {
        const lastWord = value.split(' ').pop().trim();
        setSuggestions(getSuggestions(lastWord));
    };

    const onSuggestionsClearRequested = () => {
        setSuggestions([]);
    };

    const onKeywordChange = (event, { newValue }) => {
        setKeywords(newValue);
    };

    const onSuggestionSelected = (event, { suggestionValue }) => {
        const words = keywords.split(' ');
        words[words.length - 1] = suggestionValue;
        setKeywords(words.join(' ') + ' ');
    };

    return (
        <form onSubmit={handleSubmit} className="container mt-5">
            <h2 className="mb-4">{campaignId ? 'Edit Campaign' : 'Create Campaign'}</h2>
            {insufficientFundsError && <div className="alert alert-danger">{insufficientFundsError}</div>}
            <div className="form-group">
                <label>Campaign Name:</label>
                <input
                    type="text"
                    value={campaignName}
                    onChange={(e) => setCampaignName(e.target.value)}
                    className="form-control"
                    required
                />
                {errors.campaignName && <div className="text-danger">{errors.campaignName}</div>}
            </div>
            <div className="form-group">
                <label>Keywords:</label>
                <Autosuggest
                    suggestions={suggestions}
                    onSuggestionsFetchRequested={onSuggestionsFetchRequested}
                    onSuggestionsClearRequested={onSuggestionsClearRequested}
                    getSuggestionValue={getSuggestionValue}
                    renderSuggestion={renderSuggestion}
                    onSuggestionSelected={onSuggestionSelected}
                    inputProps={{
                        placeholder: 'Type keywords separated by spaces',
                        value: keywords,
                        onChange: onKeywordChange,
                        className: 'form-control'
                    }}
                />
                {errors.keywords && <div className="text-danger">{errors.keywords}</div>}
            </div>
            <div className="form-group">
                <label>Bid Amount:</label>
                <input
                    type="number"
                    value={bidAmount}
                    onChange={(e) => setBidAmount(e.target.value)}
                    className="form-control"
                    required
                    min="1.00"
                    step="0.01"
                />
                {errors.bidAmount && <div className="text-danger">{errors.bidAmount}</div>}
            </div>
            <div className="form-group">
                <label>Campaign Fund:</label>
                <input
                    type="number"
                    value={campaignFund}
                    onChange={(e) => setCampaignFund(e.target.value)}
                    className="form-control"
                    required
                />
                {errors.campaignFund && <div className="text-danger">{errors.campaignFund}</div>}
            </div>
            <div className="form-group">
                <label>Status:</label>
                <select
                    value={status}
                    onChange={(e) => setStatus(e.target.value === 'true')}
                    className="form-control"
                    required
                >
                    <option value={true}>Enabled</option>
                    <option value={false}>Disabled</option>
                </select>
                {errors.status && <div className="text-danger">{errors.status}</div>}
            </div>
            <div className="form-group">
                <label>Town:</label>
                <select
                    value={town}
                    onChange={(e) => setTown(e.target.value)}
                    className="form-control"
                    required
                >
                    <option value="">Select a town</option>
                    {towns.map((townName, index) => (
                        <option key={index} value={townName}>{townName}</option>
                    ))}
                </select>
                {errors.town && <div className="text-danger">{errors.town}</div>}
            </div>
            <div className="form-group">
                <label>Radius(km):</label>
                <input
                    type="number"
                    value={radius}
                    onChange={(e) => setRadius(e.target.value)}
                    className="form-control"
                    required
                />
                {errors.radius && <div className="text-danger">{errors.radius}</div>}
            </div>
            {!campaignId && (
                <>
                    <div className="form-group">
                        <label>Seller ID:</label>
                        <input
                            type="number"
                            value={sellerId}
                            onChange={(e) => setSellerId(e.target.value)}
                            className="form-control"
                            required
                        />
                        {errors.sellerId && <div className="text-danger">{errors.sellerId}</div>}
                    </div>
                    <div className="form-group">
                        <label>Product ID:</label>
                        <input
                            type="number"
                            value={productId}
                            onChange={(e) => setProductId(e.target.value)}
                            className="form-control"
                            required
                        />
                        {errors.productId && <div className="text-danger">{errors.productId}</div>}
                    </div>
                </>
            )}
            <button type="submit" className="btn btn-primary mt-3">{campaignId ? 'Update' : 'Create'}</button>
        </form>
    );
};

export default CampaignForm;