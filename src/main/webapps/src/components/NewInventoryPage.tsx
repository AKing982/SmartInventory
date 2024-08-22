import React, { useState } from 'react';
import {
    Container, Box, Typography, TextField, Button, Snackbar, Alert,
    Paper, Grid, FormControl, InputLabel, Select, MenuItem, SelectChangeEvent,
    InputAdornment, styled, IconButton
} from '@mui/material';
import {
    Assessment as AssessmentIcon, Business as BusinessIcon,
    Category as CategoryIcon, People as PeopleIcon,
    Save as SaveIcon, Settings as SettingsIcon,
    Warehouse as WarehouseIcon
} from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';
import MainAppBar from './MainAppBar';
import InventoryIcon from "@mui/icons-material/Inventory";
import {addProductToInventory, fetchSkuNumber} from "../api/InventoryApiService";
import AddIcon from '@mui/icons-material/Add'

interface NewInventoryItem {
    productName: string;
    productSKU: string;
    productBrand: string;
    modelNumber: string;
    productCategory: string;
    productQuantity: number;
    packageQuantity: number;
    reorderPoint: number;
    reorderQuantity: number;
    productPrice: number;
    costPrice: number;
    markupPercentage: number;
    supplier: string;
    expirationDate: string;
    productDescription: string;
    notes: string;
    images: string[]; // This will store image URLs
}


const StyledPaper = styled(Paper)(({ theme }) => ({
    padding: theme.spacing(4),
    width: '100%',
    boxShadow: '0 4px 20px rgba(0,0,0,0.1)',
    borderRadius: '12px',
    transition: 'box-shadow 0.3s ease-in-out',
    '&:hover': {
        boxShadow: '0 6px 25px rgba(0,0,0,0.15)',
    },
}));

const StyledTextField = styled(TextField)(({ theme }) => ({
    '& .MuiOutlinedInput-root': {
        '&:hover fieldset': {
            borderColor: theme.palette.primary.main,
        },
    },
}));

const StyledButton = styled(Button)(({ theme }) => ({
    textTransform: 'none',
    fontWeight: 600,
    padding: '8px 16px',
    borderRadius: '4px',
    boxShadow: 'none',
    transition: 'all 0.3s ease-in-out',
    '&:hover': {
        boxShadow: 'none',
        backgroundColor: theme.palette.primary.dark,
    },
    '&:active': {
        boxShadow: 'none',
        backgroundColor: theme.palette.primary.main,
    },
}));

const StyledIconButton = styled(IconButton)(({ theme }) => ({
    padding: '8px',
    borderRadius: '4px',
    color: theme.palette.secondary.main,
    '&:hover': {
        backgroundColor: theme.palette.secondary.light,
    },
    '&:active': {
        backgroundColor: theme.palette.secondary.main,
        color: theme.palette.common.white,
    },
}));

const StyledFormControl = styled(FormControl)(({ theme }) => ({
    '& .MuiOutlinedInput-root': {
        '&:hover fieldset': {
            borderColor: theme.palette.primary.main,
        },
    },
}));


const BackgroundContainer = styled('div')({
    minHeight: '120vh',
    display: 'flex',
    flexDirection: 'column',
    background: 'linear-gradient(120deg, #f6f7f9 0%, #e3e6ec 100%)',
});

const ContentContainer = styled(Box)(({ theme }) => ({
    flexGrow: 1,
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: theme.spacing(3),
    paddingTop: theme.spacing(10), // Increased top padding to account for AppBar
    paddingBottom: theme.spacing(3),
    minHeight: 'calc(100vh - 64px)', // Subtract AppBar height
}));

const NewInventoryPage: React.FC = () => {
    const navigate = useNavigate();
    const [newItem, setNewItem] = useState<NewInventoryItem>({
        productName: '',
        productSKU: '',
        productBrand: '',
        modelNumber: '',
        productCategory: '',
        productQuantity: 0,
        packageQuantity: 1,
        reorderPoint: 0,
        reorderQuantity: 0,
        productPrice: 0,
        costPrice: 0,
        markupPercentage: 0,
        supplier: '',
        expirationDate: '',
        productDescription: '',
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
            [name]: ['productQuantity', 'packageQuantity', 'reorderPoint', 'reorderQuantity', 'productPrice', 'costPrice', 'markupPercentage'].includes(name) ? Number(value) : value
        }));
    };

    const generateModelNumber = () => {
        const prefix = newItem.productBrand ? newItem.productBrand.substring(0, 2).toUpperCase() : 'XX';
        const randomPart = Math.random().toString(36).substring(2, 6).toUpperCase();
        const newModelNumber = `${prefix}-${randomPart}`;

        setNewItem(prevItem => ({
            ...prevItem,
            modelNumber: newModelNumber
        }));
    };

    const createNewProductRequest = (newItem: NewInventoryItem) => {
        return {
            productName: newItem.productName,
            productSKU: newItem.productSKU,
            productQuantity: newItem.productQuantity,
            productPrice: newItem.productPrice,
            productDescription: newItem.productDescription,
            productCategory: newItem.productCategory,
            productBrand: newItem.productBrand,
            costPrice: newItem.costPrice,
            expirationDate: newItem.expirationDate,
            notes: newItem.notes,
            reorderPoint: newItem.reorderPoint,
            supplier: newItem.supplier,
            modelNumber: newItem.modelNumber,
            markupPercentage: newItem.markupPercentage
        }
    }

    const generateSKU = async () => {

        const selectedCategory = newItem.productCategory;
        const generatedSku = await fetchSkuNumber(selectedCategory);
        const categoryCode = generatedSku.categoryCode;
        const supplierCode = generatedSku.supplierCode;
        if(categoryCode === undefined || supplierCode === undefined){
            return "N/A";
        }
        const combined = `${generatedSku.categoryCode}-${generatedSku.supplierCode}`;
        console.log('Combined: ', combined);
        console.log('New Sku: ', generatedSku);

        setNewItem(prevItem => ({
            ...prevItem,
            productSKU:combined
        }));
    };



    const handleCategoryChange = (event: SelectChangeEvent<string>) => {
        setNewItem(prev => ({ ...prev, productCategory: event.target.value }));
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        // Here you would typically send the data to your backend
        console.log('Submitting new item:', newItem);
        const request = createNewProductRequest(newItem);
        console.log('Request: ', request);
        const response = await addProductToInventory(newItem);
        console.log('Response: ', response);
        if(response.status === 200 || response.status === 201){
            setSnackbarMessage('Item added successfully!');
            setOpenSnackbar(true);
        }
        // Reset form or navigate back to inventory page after successful submission
        // navigate('/inventory');
    };

    const handleCloseSnackbar = (event?: React.SyntheticEvent | Event, reason?: string) => {
        if (reason === 'clickaway') {
            return;
        }
        setOpenSnackbar(false);
    };

    const menuItems2 = [
        { text: 'Dashboard', icon: <AssessmentIcon />, path: '/home' },
        { text: 'Inventory', icon: <InventoryIcon />, path: '/inventory' },
        { text: 'Categories', icon: <CategoryIcon />, path: '/categories' },
        { text: 'Warehouses', icon: <WarehouseIcon />, path: '/warehouses' },
        { text: 'Contacts', icon: <PeopleIcon />, path: '/contacts' },
        { text: 'Departments', icon: <BusinessIcon />, path: '/departments' },
        { text: 'Employees', icon: <PeopleIcon />, path: '/employees' },
        { text: 'Customers', icon: <PeopleIcon />, path: '/customers' },
        { text: 'Reports', icon: <AssessmentIcon />, path: '/reports' },
        { text: 'Settings', icon: <SettingsIcon />, path: '/settings' },
    ];

    return (
        <BackgroundContainer>
            <MainAppBar title="Add New Inventory Item" drawerItems={menuItems2}/>
            <ContentContainer>
                <StyledPaper elevation={3} sx={{ p: 4, width: '100%' }}>
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
                                    name="productName"
                                    value={newItem.productName}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Grid>
                            <Grid item xs={12} md={3}>
                                <TextField
                                    fullWidth
                                    label="SKU"
                                    name="productSKU"
                                    value={newItem.productSKU}
                                    onChange={handleInputChange}
                                    required
                                    InputProps={{
                                        endAdornment: (
                                            <StyledIconButton
                                                onClick={generateSKU}
                                                color="secondary"
                                                size="small"
                                                >
                                                <AddIcon />
                                            </StyledIconButton>
                                        ),
                                    }}
                                />
                            </Grid>
                            <Grid item xs={12} md={3}>
                                <TextField
                                    fullWidth
                                    label="Brand"
                                    name="productBrand"
                                    value={newItem.productBrand}
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
                                    InputProps={{
                                        endAdornment: (
                                            <StyledIconButton
                                                onClick={generateModelNumber}
                                                color="secondary"
                                                size="small"
                                            >
                                                <AddIcon />
                                            </StyledIconButton>
                                        ),
                                    }}
                                />
                            </Grid>

                            {/* Second row */}
                            <Grid item xs={12} md={3}>
                                <FormControl fullWidth>
                                    <InputLabel>Category</InputLabel>
                                    <Select
                                        value={newItem.productCategory}
                                        onChange={handleCategoryChange}
                                        label="productCategory"
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
                                    name="productQuantity"
                                    type="number"
                                    value={newItem.productQuantity}
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
                                    name="productPrice"
                                    type="number"
                                    value={newItem.productPrice}
                                    onChange={handleInputChange}
                                    required
                                    InputProps={{
                                        startAdornment: <InputAdornment position="start">$</InputAdornment>,
                                    }}
                                />
                            </Grid>
                            <Grid item xs={12} md={3}>
                                <StyledTextField
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
                                <StyledTextField
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
                                <StyledTextField
                                    fullWidth
                                    label="Supplier"
                                    name="supplier"
                                    value={newItem.supplier}
                                    onChange={handleInputChange}
                                    required
                                />
                            </Grid>
                            <Grid item xs={12} md={6}>
                                <StyledTextField
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
                                    name="productDescription"
                                    value={newItem.productDescription}
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
                                <StyledButton
                                    type="submit"
                                    variant="contained"
                                    color="primary"
                                    size="large"
                                    startIcon={<SaveIcon />}
                                >
                                    Save New Item
                                </StyledButton>
                            </Grid>
                        </Grid>
                    </form>
                </StyledPaper>
                <Snackbar open={openSnackbar} autoHideDuration={6000} onClose={handleCloseSnackbar}>
                    <Alert onClose={handleCloseSnackbar} severity="success" sx={{ width: '100%' }}>
                        {snackbarMessage}
                    </Alert>
                </Snackbar>
            </ContentContainer>
        </BackgroundContainer>
    );
};

export default NewInventoryPage;