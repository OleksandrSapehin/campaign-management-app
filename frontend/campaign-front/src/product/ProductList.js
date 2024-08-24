import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const ProductList = () => {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        fetchProducts();
    }, []);

    const fetchProducts = async () => {
        try {
            const response = await axios.get('/api/products');
            setProducts(response.data);
        } catch (error) {
            console.error('Error fetching products:', error);
        }
    };

    const handleDeleteProduct = async (productId) => {
        try {
            await axios.delete(`/api/products/${productId}`);
            setProducts(products.filter(p => p.id !== productId));
        } catch (error) {
            console.error('Error deleting product:', error);
        }
    };

    return (
        <div className="container mt-5">
            <h2 className="mb-4">Product List</h2>
            {products.length > 0 ? (
                <ul className="list-group">
                    {products.map(product => (
                        <li key={product.id} className="list-group-item">
                            <p><strong>ID:</strong> {product.id}</p>
                            <p><strong>Name:</strong> {product.name}</p>
                            <div className="d-flex justify-content-between">
                                <Link to={`/edit-product/${product.id}`} className="btn btn-warning">Edit</Link>
                                <button onClick={() => handleDeleteProduct(product.id)} className="btn btn-danger">Delete</button>
                            </div>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No products available.</p>
            )}
        </div>
    );
};

export default ProductList;