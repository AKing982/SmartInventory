import React, { useState } from 'react';
import {
    Container,
    Typography,
    TextField,
    Button,
    Grid,
    Paper,
    MenuItem,
    Snackbar,
    InputAdornment, Box, AppBar, Toolbar,
} from '@mui/material';
import MuiAlert, { AlertProps } from '@mui/material/Alert';
import {ExitToApp as LogoutIcon} from  '@mui/icons-material';
import InventoryIcon from "@mui/icons-material/Inventory";
import {useNavigate} from "react-router-dom";

// You might want to replace this with actual categories from your backend
const categories = [
    'Electronics',
    'Clothing',
    'Food',
    'Books',
    'Toys',
    'Other'
];

interface InventoryItem {
    name: string;
    sku: string;
    quantity: number;
    price: number;
    category: string;
    description: string;
}

const Alert = React.forwardRef<HTMLDivElement, AlertProps>(function Alert(
    props,
    ref,
) {
    return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

const AddInventoryPage: React.FC = () => {
    const [item, setItem] = useState<InventoryItem>({
        name: '',
        sku: '',
        quantity: 0,
        price: 0,
        category: '',
        description: '',
    });

    const [errors, setErrors] = useState<Partial<InventoryItem>>({});
    const [openSnackbar, setOpenSnackbar] = useState<boolean>(false);
    const navigate = useNavigate();

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setItem(prevItem => ({
            ...prevItem,
            [name]: value
        }));
        // Clear error when user starts typing
        if (errors[name as keyof InventoryItem]) {
            setErrors(prevErrors => ({ ...prevErrors, [name]: undefined }));
        }
    };

    const validateForm = (): boolean => {
        const newErrors: Partial<InventoryItem> = {};
        if (!item.name) newErrors.name = 'Name is required';
        if (!item.sku) newErrors.sku = 'SKU is required';
        // if (item.quantity < 0) newErrors.quantity = 'Quantity must be 0 or greater';
        // if (item.price < 0) newErrors.price = 'Price must be 0 or greater';
        if (!item.category) newErrors.category = 'Category is required';

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (validateForm()) {
            // Here you would typically send the data to your backend
            console.log('Submitting item:', item);
            // Clear form and show success message
            setItem({
                name: '',
                sku: '',
                quantity: 0,
                price: 0,
                category: '',
                description: '',
            });
            setOpenSnackbar(true);
        }
    };

    const handleCloseSnackbar = (event?: React.SyntheticEvent | Event, reason?: string) => {
        if (reason === 'clickaway') {
            return;
        }
        setOpenSnackbar(false);
    };

    const handleLogout = () => {
        // Implement logout logic here
        console.log('Logging out...');
        localStorage.clear();
        navigate('/');
    };


    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static">
                <Toolbar>
                    <InventoryIcon sx={{ mr: 2 }} />
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        SmartInventory Management System
                    </Typography>
                    <Button color="inherit" onClick={() => navigate('/')}>Dashboard</Button>
                    <Button color="inherit" onClick={() => navigate('/inventory')}>Inventory</Button>
                    <Button color="inherit" onClick={() => navigate('/reports')}>Reports</Button>
                    <Button color="inherit" onClick={() => navigate('/profile')}>Profile</Button>
                    <Button color="inherit" onClick={handleLogout} startIcon={<LogoutIcon />}>
                        Logout
                    </Button>
                </Toolbar>
            </AppBar>

            <Container maxWidth="md">
                <Paper elevation={3} sx={{ p: 4, mt: 4 }}>
                    <Typography variant="h4" gutterBottom>
                        Add New Inventory Item
                    </Typography>
                    <form onSubmit={handleSubmit}>
                        <Grid container spacing={3}>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required
                                    fullWidth
                                    label="Item Name"
                                    name="name"
                                    value={item.name}
                                    onChange={handleChange}
                                    error={!!errors.name}
                                    helperText={errors.name}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required
                                    fullWidth
                                    label="SKU"
                                    name="sku"
                                    value={item.sku}
                                    onChange={handleChange}
                                    error={!!errors.sku}
                                    helperText={errors.sku}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required
                                    fullWidth
                                    type="number"
                                    label="Quantity"
                                    name="quantity"
                                    value={item.quantity}
                                    onChange={handleChange}
                                    error={!!errors.quantity}
                                    helperText={errors.quantity}
                                    InputProps={{ inputProps: { min: 0 } }}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    required
                                    fullWidth
                                    type="number"
                                    label="Price"
                                    name="price"
                                    value={item.price}
                                    onChange={handleChange}
                                    error={!!errors.price}
                                    helperText={errors.price}
                                    InputProps={{
                                        startAdornment: <InputAdornment position="start">$</InputAdornment>,
                                        inputProps: { min: 0, step: 0.01 }
                                    }}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    required
                                    fullWidth
                                    select
                                    label="Category"
                                    name="category"
                                    value={item.category}
                                    onChange={handleChange}
                                    error={!!errors.category}
                                    helperText={errors.category}
                                >
                                    {categories.map((option) => (
                                        <MenuItem key={option} value={option}>
                                            {option}
                                        </MenuItem>
                                    ))}
                                </TextField>
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    fullWidth
                                    multiline
                                    rows={4}
                                    label="Description"
                                    name="description"
                                    value={item.description}
                                    onChange={handleChange}
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <Button type="submit" variant="contained" color="primary" size="large">
                                    Add Item
                                </Button>
                            </Grid>
                        </Grid>
                    </form>
                </Paper>
                <Snackbar open={openSnackbar} autoHideDuration={6000} onClose={handleCloseSnackbar}>
                    <Alert onClose={handleCloseSnackbar} severity="success" sx={{ width: '100%' }}>
                        Item added successfully!
                    </Alert>
                </Snackbar>
            </Container>
        </Box>
    );
};

export default AddInventoryPage;