import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, useNavigate } from 'react-router-dom';

const ProductForm = () => {
    const { productId } = useParams();
    const navigate = useNavigate();
    const [name, setName] = useState('');
    const [errors, setErrors] = useState({});

    useEffect(() => {
        if (productId) {
            axios.get(`/api/products/${productId}`)
                .then(response => {
                    setName(response.data.name);
                })
                .catch(error => console.error('Error fetching product:', error));
        } else {
            setName('');
        }
    }, [productId]);

    const handleSubmit = (e) => {
        e.preventDefault();
        const product = { name };

        setErrors({});

        if (productId) {
            axios.put(`/api/products/${productId}`, product)
                .then(response => {
                    navigate('/products');
                })
                .catch(error => {
                    if (error.response && error.response.status === 400) {
                        setErrors(error.response.data);
                    } else {
                        console.error('Error updating product:', error);
                    }
                });
        } else {
            axios.post('/api/products', product)
                .then(response => {
                    navigate('/products');
                })
                .catch(error => {
                    if (error.response && error.response.status === 400) {
                        setErrors(error.response.data);
                    } else {
                        console.error('Error creating product:', error);
                    }
                });
        }
    };

    return (
        <form onSubmit={handleSubmit} className="container mt-5">
            <h2 className="mb-4">{productId ? 'Edit Product' : 'Create Product'}</h2>
            <div className="form-group">
                <label>Product Name:</label>
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    className="form-control"
                    required
                />
                {errors.name && <div className="text-danger">{errors.name}</div>}
            </div>
            <button type="submit" className="btn btn-primary mt-3">{productId ? 'Update' : 'Create'}</button>
        </form>
    );
};

export default ProductForm;