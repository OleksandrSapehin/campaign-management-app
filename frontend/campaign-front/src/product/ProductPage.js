import React, { useState, useEffect } from 'react';
import ProductForm from './ProductForm';
import ProductList from './ProductList';
import axios from 'axios';

const ProductPage = () => {
    const [products, setProducts] = useState([]);
    const [editingProductId, setEditingProductId] = useState(null);

    useEffect(() => {
        fetchProducts();
    }, []);

    const fetchProducts = () => {
        axios.get('/api/products')
            .then(response => {
                setProducts(response.data);
            })
            .catch(error => console.error('Error fetching products:', error));
    };

    const handleSave = (product) => {
        setEditingProductId(null);
        fetchProducts();
    };

    const handleEdit = (productId) => {
        setEditingProductId(productId);
    };

    const handleDelete = (productId) => {
        axios.delete(`/api/products/${productId}`)
            .then(() => {
                fetchProducts();
            })
            .catch(error => console.error('Error deleting product:', error));
    };

    return (
        <div>
            <h2>Manage Products</h2>
            <ProductForm productId={editingProductId} onSave={handleSave} />
            <ProductList products={products} onEdit={handleEdit} onDelete={handleDelete} />
        </div>
    );
};

export default ProductPage;