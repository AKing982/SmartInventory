import React, {useEffect, useState} from 'react';
import {
    Container,
    Typography,
    TextField,
    Button,
    Grid,
    Paper,
    MenuItem,
    Snackbar,
    InputAdornment, Box, AppBar, Toolbar, styled, TableCell,
    TableRow,
    TableBody,
    TableContainer,
    Table,
    TableHead, IconButton, CircularProgress,
} from '@mui/material';
import MuiAlert, { AlertProps } from '@mui/material/Alert';
import {ExitToApp as LogoutIcon} from  '@mui/icons-material';
import InventoryIcon from "@mui/icons-material/Inventory";
import {useNavigate} from "react-router-dom";
import DeleteIcon from '@mui/icons-material/Delete';
import {fetchAllProductsInInventory, addProductToInventory, ProductData, deleteProductFromInventory} from "../api/InventoryApiService";

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
    productId: number;
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

const BackgroundContainer = styled('div')({
    minHeight: '100vh',
    display: 'flex',
    flexDirection: 'column',
    background: 'linear-gradient(120deg, #f6f7f9 0%, #e3e6ec 100%)',
});

// Styled component for the content area
const ContentContainer = styled(Box)({
    flexGrow: 1,
    padding: '24px 0',
});



const AddInventoryPage: React.FC = () => {
    const [item, setItem] = useState<InventoryItem>({
        productId: 0,
        name: '',
        sku: '',
        quantity: 0,
        price: 0,
        category: '',
        description: '',
    });

    const [errors, setErrors] = useState<Partial<InventoryItem>>({});
    const [error, setError] = useState<string>('');
    const [openSnackbar, setOpenSnackbar] = useState<boolean>(false);
    const [snackbarMessage, setSnackbarMessage] = useState<string>('');
    const [snackbarSeverity, setSnackbarSeverity] = useState<'success' | 'error'>('success');
    const navigate = useNavigate();
    const [inventoryItems, setInventoryItems] = useState<InventoryItem[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(false);

    useEffect(() => {
        fetchInventoryItems();
    }, [])

    const fetchInventoryItems = async () => {
        try
        {
            const items = await fetchAllProductsInInventory();
            setInventoryItems(items || []);
        }catch(err)
        {
            console.error('Error fetching inventory items: ', err);
            setSnackbarMessage('Failed to fetch inventory items');
            setSnackbarSeverity('error');
            setOpenSnackbar(true);
        }
    }

    const handleDeleteItems = async(productId: number) => {
        try
        {
            await deleteProductFromInventory(productId);
            setSnackbarMessage('Item deleted succesfully');
            setSnackbarSeverity('success');
            setOpenSnackbar(true);
            await fetchInventoryItems();

        }catch(err)
        {
            console.error('Error deleting item: ', err);
            setSnackbarMessage('Failed to delete item: ' + (err instanceof Error ? err.message : String(err)));
            setSnackbarSeverity('error');
            setOpenSnackbar(true);
        }
    };

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

    const generateSKU = () => {
        const prefix = item.category ? item.category.substring(0, 3).toUpperCase() : 'XXX';
        const randomPart = Math.random().toString(36).substring(2, 7).toUpperCase();
        const newSKU = `${prefix}-${randomPart}`;

        setItem(prevItem => ({
            ...prevItem,
            sku: newSKU
        }));

        if(errors.sku){
            setErrors(prevErrors => ({...prevErrors, sku: undefined}));
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

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (validateForm()) {
            setIsLoading(true);
            try
            {
                const productData: ProductData = {
                    productName: item.name,
                    productDescription: item.description,
                    productSKU: item.sku,
                    productCategory: item.category,
                    productQuantity: item.quantity,
                    productPrice: item.price,
                };
                const response = await addProductToInventory(productData);
                console.log('Product Added: ', response);
                setItem({
                    productId: 0,
                    name: '',
                    sku: '',
                    quantity: 0,
                    price: 0,
                    category: '',
                    description: '',
                });
                setSnackbarMessage('Item added successfully');
                setSnackbarSeverity('success');
                setOpenSnackbar(true);
                await fetchInventoryItems();

            }catch(error)
            {
                console.error('Error adding item:', error);
                setSnackbarMessage('Failed to add item');
                setSnackbarSeverity('error');
                setOpenSnackbar(true);
            }finally{
                setIsLoading(false);
            }
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
        <BackgroundContainer>
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

            <ContentContainer>
                <Container maxWidth="lg">
                    <Grid container spacing={3}>
                        <Grid item xs={12} md={6}>
                            <Paper elevation={3} sx={{ p: 4, mt: 4 }}>
                                <Typography variant="h4" gutterBottom>
                                    Add New Inventory Item
                                </Typography>
                                <form onSubmit={handleSubmit}>
                                    <Grid container spacing={3}>
                                        <Grid item xs={12}>
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
                                                fullWidth
                                                label="SKU"
                                                name="sku"
                                                value={item.sku}
                                                InputProps={{
                                                    readOnly: true,
                                                }}
                                                error={!!errors.sku}
                                                helperText={errors.sku}
                                            />
                                        </Grid>
                                        <Grid item xs={12} sm={6}>
                                            <Button
                                                variant="contained"
                                                color="secondary"
                                                onClick={() => generateSKU()}
                                                fullWidth
                                                sx={{ height: '56px' }}
                                            >
                                                Generate SKU
                                            </Button>
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
                                            <Button
                                                type="submit"
                                                variant="contained"
                                                color="primary"
                                                size="large"
                                                disabled={isLoading}
                                            >
                                                {isLoading ? 'Adding...' : 'Add Item'}
                                            </Button>
                                        </Grid>
                                    </Grid>
                                </form>
                            </Paper>
                        </Grid>
                        <Grid item xs={12} md={6}>
                            <Paper elevation={3} sx={{ p: 4, mt: 4 }}>
                                <Typography variant="h4" gutterBottom>
                                    Inventory Items
                                </Typography>
                                {isLoading ? (
                                    <Box display="flex" justifyContent="center" my={4}>
                                        <CircularProgress />
                                    </Box>
                                ) : error ? (
                                    <Alert severity="error">{error}</Alert>
                                ) : inventoryItems.length === 0 ? (
                                    <Typography>No items in inventory.</Typography>
                                ) : (
                                    <TableContainer>
                                        <Table>
                                            <TableHead>
                                                <TableRow>
                                                    <TableCell>Name</TableCell>
                                                    <TableCell>SKU</TableCell>
                                                    <TableCell>Quantity</TableCell>
                                                    <TableCell>Price</TableCell>
                                                    <TableCell>Category</TableCell>
                                                    <TableCell>Action</TableCell>
                                                </TableRow>
                                            </TableHead>
                                            <TableBody>
                                                {inventoryItems.map((item) => (
                                                    <TableRow key={item.productId}>
                                                        <TableCell>{item.name}</TableCell>
                                                        <TableCell>{item.sku}</TableCell>
                                                        <TableCell>{item.quantity}</TableCell>
                                                        <TableCell>${item.price.toFixed(2)}</TableCell>
                                                        <TableCell>{item.category}</TableCell>
                                                        <TableCell>
                                                            <IconButton
                                                                aria-label="delete"
                                                                onClick={() => handleDeleteItems(item.productId)}
                                                            >
                                                                <DeleteIcon />
                                                            </IconButton>
                                                        </TableCell>
                                                    </TableRow>
                                                ))}
                                            </TableBody>
                                        </Table>
                                    </TableContainer>
                                )}
                            </Paper>
                        </Grid>
                    </Grid>
                </Container>
                <Snackbar open={openSnackbar} autoHideDuration={6000} onClose={handleCloseSnackbar}>
                    <Alert onClose={handleCloseSnackbar} severity={snackbarSeverity} sx={{ width: '100%' }}>
                        {snackbarMessage}
                    </Alert>
                </Snackbar>
            </ContentContainer>
        </BackgroundContainer>
    );

};

export default AddInventoryPage;