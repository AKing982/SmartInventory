import React, { useState } from 'react';
import {
    Container, Box, Typography, TextField, Button, Snackbar, Alert,
    Paper, Grid, FormControl, InputLabel, Select, MenuItem, SelectChangeEvent,
    InputAdornment, styled
} from '@mui/material';
import { Save as SaveIcon } from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import MainAppBar from './MainAppBar';

interface NewInventoryItem {
    name: string;
    sku: string;
    brand: string;
    modelNumber: string;
    category: string;
    quantity: number;
    packageQuantity: number;
    reorderPoint: number;
    reorderQuantity: number;
    price: number;
    costPrice: number;
    markupPercentage: number;
    supplier: string;
    expirationDate: string;
    description: string;
    notes: string;
    images: string[]; // This will store image URLs
}

const BackgroundContainer = styled('div')({
    minHeight: '120vh',
    display: 'flex',
    flexDirection: 'column',
    background: 'linear-gradient(120deg, #f6f7f9 0%, #e3e6ec 100%)',
});

// Styled component for the content area
const ContentContainer = styled(Box)({
    flexGrow: 1,
    padding: '24px 0',
});

const NewInventoryPage: React.FC = () => {
    const navigate = useNavigate();
    const [newItem, setNewItem] = useState<NewInventoryItem>({
        name: '',
        sku: '',
        brand: '',
        modelNumber: '',
        category: '',
        quantity: 0,
        packageQuantity: 1,
        reorderPoint: 0,
        reorderQuantity: 0,
        price: 0,
        costPrice: 0,
        markupPercentage: 0,
        supplier: '',
        expirationDate: '',
        description: '',
        notes: '',
        images: []
    });
    const [openSnackbar, setOpenSnackbar] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');

    const categories = ['Electronics', 'Clothing', 'Books', 'Food', 'Other'];

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setNewItem(prev => ({
            ...prev,
            [name]: ['quantity', 'packageQuantity', 'reorderPoint', 'reorderQuantity', 'price', 'costPrice', 'markupPercentage'].includes(name) ? Number(value) : value
        }));
    };

    const handleCategoryChange = (event: SelectChangeEvent<string>) => {
        setNewItem(prev => ({ ...prev, category: event.target.value }));
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        // Here you would typically send the data to your backend
        console.log('Submitting new item:', newItem);
        setSnackbarMessage('Item added successfully!');
        setOpenSnackbar(true);
        // Reset form or navigate back to inventory page after successful submission
        // navigate('/inventory');
    };

    const handleCloseSnackbar = (event?: React.SyntheticEvent | Event, reason?: string) => {
        if (reason === 'clickaway') {
            return;
        }
        setOpenSnackbar(false);
    };

    return (
        <BackgroundContainer>
            <MainAppBar title="Add New Inventory Item" />
            <Container component="main" maxWidth="xl" sx={{ flexGrow: 1, p: 3, mt: 8 }}>
                <Paper elevation={3} sx={{ p: 4, width: '100%' }}>
                    <Typography variant="h4" gutterBottom>
                        Add New Inventory Item
                    </Typography>
                    <form onSubmit={handleSubmit}>
                        <Grid container spacing={3}>
                            {/* First row */}
                            <Grid item xs={12} md={3}>
                                <TextField
                                    fullWidth
                                    label="Item Name"
                                    name="name"
                                    value={newItem.name}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Grid>
                            <Grid item xs={12} md={3}>
                                <TextField
                                    fullWidth
                                    label="SKU"
                                    name="sku"
                                    value={newItem.sku}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Grid>
                            <Grid item xs={12} md={3}>
                                <TextField
                                    fullWidth
                                    label="Brand"
                                    name="brand"
                                    value={newItem.brand}
                                    onChange={handleInputChange}
                                />
                            </Grid>
                            <Grid item xs={12} md={3}>
                                <TextField
                                    fullWidth
                                    label="Model Number"
                                    name="modelNumber"
                                    value={newItem.modelNumber}
                                    onChange={handleInputChange}
                                />
                            </Grid>

                            {/* Second row */}
                            <Grid item xs={12} md={3}>
                                <FormControl fullWidth>
                                    <InputLabel>Category</InputLabel>
                                    <Select
                                        value={newItem.category}
                                        onChange={handleCategoryChange}
                                        label="Category"
                                        required
                                    >
                                        {categories.map((category) => (
                                            <MenuItem key={category} value={category}>{category}</MenuItem>
                                        ))}
                                    </Select>
                                </FormControl>
                            </Grid>
                            <Grid item xs={12} md={3}>
                                <TextField
                                    fullWidth
                                    label="Quantity"
                                    name="quantity"
                                    type="number"
                                    value={newItem.quantity}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Grid>
                            <Grid item xs={12} md={3}>
                                <TextField
                                    fullWidth
                                    label="Package Quantity"
                                    name="packageQuantity"
                                    type="number"
                                    value={newItem.packageQuantity}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Grid>
                            <Grid item xs={12} md={3}>
                                <TextField
                                    fullWidth
                                    label="Reorder Point"
                                    name="reorderPoint"
                                    type="number"
                                    value={newItem.reorderPoint}
                                    onChange={handleInputChange}
                                />
                            </Grid>

                            {/* Third row */}
                            <Grid item xs={12} md={3}>
                                <TextField
                                    fullWidth
                                    label="Reorder Quantity"
                                    name="reorderQuantity"
                                    type="number"
                                    value={newItem.reorderQuantity}
                                    onChange={handleInputChange}
                                />
                            </Grid>
                            <Grid item xs={12} md={3}>
                                <TextField
                                    fullWidth
                                    label="Selling Price"
                                    name="price"
                                    type="number"
                                    value={newItem.price}
                                    onChange={handleInputChange}
                                    required
                                    InputProps={{
                                        startAdornment: <InputAdornment position="start">$</InputAdornment>,
                                    }}
                                />
                            </Grid>
                            <Grid item xs={12} md={3}>
                                <TextField
                                    fullWidth
                                    label="Cost Price"
                                    name="costPrice"
                                    type="number"
                                    value={newItem.costPrice}
                                    onChange={handleInputChange}
                                    InputProps={{
                                        startAdornment: <InputAdornment position="start">$</InputAdornment>,
                                    }}
                                />
                            </Grid>
                            <Grid item xs={12} md={3}>
                                <TextField
                                    fullWidth
                                    label="Markup Percentage"
                                    name="markupPercentage"
                                    type="number"
                                    value={newItem.markupPercentage}
                                    onChange={handleInputChange}
                                    InputProps={{
                                        endAdornment: <InputAdornment position="end">%</InputAdornment>,
                                    }}
                                />
                            </Grid>

                            {/* Fourth row */}
                            <Grid item xs={12} md={6}>
                                <TextField
                                    fullWidth
                                    label="Supplier"
                                    name="supplier"
                                    value={newItem.supplier}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Grid>
                            <Grid item xs={12} md={6}>
                                <TextField
                                    fullWidth
                                    label="Expiration Date"
                                    name="expirationDate"
                                    type="date"
                                    value={newItem.expirationDate}
                                    onChange={handleInputChange}
                                    InputLabelProps={{ shrink: true }}
                                />
                            </Grid>

                            {/* Fifth row */}
                            <Grid item xs={12} md={6}>
                                <TextField
                                    fullWidth
                                    label="Description"
                                    name="description"
                                    value={newItem.description}
                                    onChange={handleInputChange}
                                    multiline
                                    rows={4}
                                />
                            </Grid>
                            <Grid item xs={12} md={6}>
                                <TextField
                                    fullWidth
                                    label="Notes"
                                    name="notes"
                                    value={newItem.notes}
                                    onChange={handleInputChange}
                                    multiline
                                    rows={4}
                                />
                            </Grid>

                            {/* Image upload */}
                            <Grid item xs={12}>
                                <input
                                    accept="image/*"
                                    style={{ display: 'none' }}
                                    id="raised-button-file"
                                    multiple
                                    type="file"
                                    onChange={(e) => {
                                        // Handle image upload here
                                        console.log('Files:', e.target.files);
                                        // You would typically upload these files to your server or cloud storage
                                        // and then update the newItem.images array with the URLs
                                    }}
                                />
                                <label htmlFor="raised-button-file">
                                    <Button variant="contained" component="span">
                                        Upload Images
                                    </Button>
                                </label>
                            </Grid>

                            {/* Submit button */}
                            <Grid item xs={12}>
                                <Button
                                    type="submit"
                                    variant="contained"
                                    color="primary"
                                    size="large"
                                    startIcon={<SaveIcon />}
                                >
                                    Save New Item
                                </Button>
                            </Grid>
                        </Grid>
                    </form>
                </Paper>
                <Snackbar open={openSnackbar} autoHideDuration={6000} onClose={handleCloseSnackbar}>
                    <Alert onClose={handleCloseSnackbar} severity="success" sx={{ width: '100%' }}>
                        {snackbarMessage}
                    </Alert>
                </Snackbar>
            </Container>
        </BackgroundContainer>
    );
};

export default NewInventoryPage;