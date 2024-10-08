import React, {useEffect, useMemo, useState} from 'react';
import {
    Container,
    Typography,
    TextField,
    Button,
    Grid,
    Paper,
    MenuItem,
    Snackbar,
    InputAdornment,
    Box,
    AppBar,
    Toolbar,
    styled,
    TableCell,
    TableRow,
    TableBody,
    TableContainer,
    Table,
    TableHead,
    IconButton,
    CircularProgress,
    Divider,
    Tooltip,
    Badge,
    CardContent,
    Card,
    Chip,
    Avatar,
    useMediaQuery,
    useTheme,
} from '@mui/material';
import MuiAlert, { AlertProps } from '@mui/material/Alert';
import {ExitToApp as LogoutIcon} from  '@mui/icons-material';
import InventoryIcon from "@mui/icons-material/Inventory";
import {FileCopy as FilyCopyIcon, Menu as MenuIcon} from '@mui/icons-material'
import SearchIcon from '@mui/icons-material/Search';
import {useNavigate} from "react-router-dom";
import DeleteIcon from '@mui/icons-material/Delete';
import {fetchAllProductsInInventory, addProductToInventory, ProductData, deleteProductFromInventory} from "../api/InventoryApiService";
import {
    // ... existing icon imports
    TrendingUp as TrendingUpIcon,
    Today as TodayIcon,
    DateRange as DateRangeIcon,
    Speed as SpeedIcon,
     Add as AddIcon
} from '@mui/icons-material';
import {BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip as RechartsTooltip, ResponsiveContainer} from 'recharts';
import {
    CloudUpload as CloudUploadIcon,
    CloudDownload as CloudDownloadIcon,
    Warning as WarningIcon,
    FileCopy as FileCopyIcon,
} from '@mui/icons-material';


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
    id: number;
    name: string;
    sku: string;
    quantity: number;
    price: number;
    category: string;
    description: string;
}

interface UserMetrics{
    itemsAddedToday: number;
    itemsAddedThisWeek: number;
    totalItemsAdded: number;
    lastActive: string;
    topCategory: string;
    averageItemsPerDay: number;
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

const StyledCard = styled(Card)(({ theme }) => ({
    height: '100%',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'space-between',
}));

const StyledAppBar = styled(AppBar)(({ theme }) => ({
    zIndex: theme.zIndex.drawer + 1,
}));




const AddInventoryPage: React.FC = () => {
    const [item, setItem] = useState<InventoryItem>({
        id: 0,
        name: '',
        sku: '',
        quantity: 0,
        price: 0,
        category: '',
        description: '',
    });

    const [userMetrics, setUserMetrics] = useState<UserMetrics>({
        itemsAddedToday: 0,
        itemsAddedThisWeek: 0,
        totalItemsAdded: 0,
        lastActive: '',
        topCategory: '',
        averageItemsPerDay: 0,
    });

    const [errors, setErrors] = useState<Partial<InventoryItem>>({});
    const [error, setError] = useState<string>('');
    const [openSnackbar, setOpenSnackbar] = useState<boolean>(false);
    const [snackbarMessage, setSnackbarMessage] = useState<string>('');
    const [snackbarSeverity, setSnackbarSeverity] = useState<'success' | 'error'>('success');
    const navigate = useNavigate();
    const [inventoryItems, setInventoryItems] = useState<InventoryItem[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [searchTerm, setSearchTerm] = useState<string>('');
    const [lowStockItems, setLowStockItems] = useState<string[]>([]);
    const [totalInventoryValue, setTotalInventoryValue] = useState<number>(0);
    const theme = useTheme();
    const isMobile = useMediaQuery(theme.breakpoints.down('sm'));

    useEffect(() => {
        fetchInventoryItems();
        fetchUserMetrics();
    }, [])

    const fetchUserMetrics = async () => {
        try {
            // In a real application, you would fetch this data from your backend
            // This is a placeholder for demonstration
            const metrics: UserMetrics = {
                itemsAddedToday: 5,
                itemsAddedThisWeek: 23,
                totalItemsAdded: 156,
                lastActive: new Date().toLocaleString(),
                topCategory: 'Electronics',
                averageItemsPerDay: 4.7,
            };
            setUserMetrics(metrics);
        } catch (error) {
            console.error('Error fetching user metrics:', error);
        }
    }

    const fetchTotalInventoryValue = (items: InventoryItem[]) : any => {
        if(items == null) {
            return 0;
        }
        return items.reduce((total, item) => total + item.price * item.quantity, 0);
    }

    const fetchInventoryItems = async () => {
        try
        {
            const items = await fetchAllProductsInInventory();
                // @ts-ignore
            const totalValue = fetchTotalInventoryValue(items);
                // @ts-ignore
            const lowStock = items.filter(item => item.quantity < 10).map(item => item.name);
            setTotalInventoryValue(totalValue);
                // @ts-ignore
            setInventoryItems(items);
            setLowStockItems(lowStock);
        }catch(err)
        {
            console.error('Error fetching inventory items: ', err);
            setSnackbarMessage('Failed to fetch inventory items');
            setSnackbarSeverity('error');
            setOpenSnackbar(true);
        }
    }

    const chartData = [
        { name: 'Total Items', value: inventoryItems.length },
        { name: 'Low Stock', value: lowStockItems.length },
        { name: 'Added Today', value: userMetrics.itemsAddedToday },
    ];

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
                    id: 0,
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

    const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchTerm(event.target.value);
    };

    const filteredInventoryItems = useMemo(() => {
        return inventoryItems.filter((item) =>
            Object.values(item).some((value) =>
                value.toString().toLowerCase().includes(searchTerm.toLowerCase())
            )
        );
    }, [inventoryItems, searchTerm]);

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

    const handleBulkImport = () => {
        // This is a placeholder function. In a real application, you'd implement file upload and processing logic here.
        console.log('Bulk import functionality not yet implemented');
        // You might want to open a file dialog here
        // const input = document.createElement('input');
        // input.type = 'file';
        // input.accept = '.csv,.xlsx';
        // input.onchange = (e) => {
        //     const file = (e.target as HTMLInputElement).files?.[0];
        //     if (file) {
        //         // Process the file (you'd need to implement this part)
        //         console.log('File selected:', file.name);
        //     }
        // };
        // input.click();
    };

    const handleBulkExport = () => {
        // This is a placeholder function. In a real application, you'd implement CSV generation and download here.
        console.log('Bulk export functionality not yet implemented');
        // Example of how you might implement this:
        // const csvContent = generateCSVContent(inventoryItems);
        // const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8;' });
        // const link = document.createElement('a');
        // if (link.download !== undefined) {
        //     const url = URL.createObjectURL(blob);
        //     link.setAttribute('href', url);
        //     link.setAttribute('download', 'inventory_export.csv');
        //     link.style.visibility = 'hidden';
        //     document.body.appendChild(link);
        //     link.click();
        //     document.body.removeChild(link);
        // }
    };

    const handleDuplicateItem = (itemToDuplicate: InventoryItem) => {
        // Create a new item based on the selected item, but clear unique fields
        const newItem: InventoryItem = {
            ...itemToDuplicate,
            id: 0, // Clear the ID so a new one will be assigned
            sku: '', // Clear the SKU so a new one can be generated
            quantity: 0, // Reset quantity to 0
        };
        setItem(newItem);
        // Scroll to the add item form
        window.scrollTo({ top: 0, behavior: 'smooth' });
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
                    <Grid container spacing={3} sx={{ mt: 2 }}>
                        {/* User Metrics Section */}
                        <Grid item xs={12}>
                            <Paper elevation={3} sx={{ p: 3 }}>
                                <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
                                    <Box sx={{ display: 'flex', alignItems: 'center' }}>
                                        <Avatar sx={{ width: 60, height: 60, mr: 2 }}>
                                            {localStorage.getItem('user')?.charAt(0).toUpperCase()}
                                        </Avatar>
                                        <Box>
                                            <Typography variant="h6">{localStorage.getItem('user')}</Typography>
                                            <Typography variant="body2" color="textSecondary">
                                                Last active: {userMetrics.lastActive}
                                            </Typography>
                                        </Box>
                                    </Box>
                                    <Chip
                                        icon={<TrendingUpIcon />}
                                        label={`${userMetrics.totalItemsAdded} Total Items Added`}
                                        color="primary"
                                    />
                                </Box>
                                <Box sx={{ display: 'flex', justifyContent: 'space-between', flexWrap: 'wrap', gap: 2 }}>
                                    <Tooltip title="Items added in the last 24 hours">
                                        <Chip
                                            icon={<TodayIcon />}
                                            label={`${userMetrics.itemsAddedToday} Today`}
                                            color="secondary"
                                        />
                                    </Tooltip>
                                    <Tooltip title="Items added in the last 7 days">
                                        <Chip
                                            icon={<DateRangeIcon />}
                                            label={`${userMetrics.itemsAddedThisWeek} This Week`}
                                            color="info"
                                        />
                                    </Tooltip>
                                    <Tooltip title="Average items added per day">
                                        <Chip
                                            icon={<SpeedIcon />}
                                            label={`${userMetrics.averageItemsPerDay.toFixed(1)} Avg/Day`}
                                            color="success"
                                        />
                                    </Tooltip>
                                    <Chip
                                        label={`Top Category: ${userMetrics.topCategory}`}
                                        variant="outlined"
                                    />
                                </Box>
                            </Paper>
                        </Grid>

                        {/* Inventory Stats Dashboard */}
                        <Grid item xs={12} md={4}>
                            <Card>
                                <CardContent>
                                    <Typography variant="h6">Total Items</Typography>
                                    <Typography variant="h4">{inventoryItems.length}</Typography>
                                </CardContent>
                            </Card>
                        </Grid>
                        <Grid item xs={12} md={4}>
                            <Card>
                                <CardContent>
                                    <Typography variant="h6">Low Stock Items</Typography>
                                    <Typography variant="h4">{lowStockItems.length}</Typography>
                                </CardContent>
                            </Card>
                        </Grid>
                        <Grid item xs={12} md={4}>
                            <Card>
                                <CardContent>
                                    <Typography variant="h6">Total Inventory Value</Typography>
                                    <Typography variant="h4">${new Intl.NumberFormat('en-US', { minimumFractionDigits: 2, maximumFractionDigits: 2 }).format(totalInventoryValue)}</Typography>
                                </CardContent>
                            </Card>
                        </Grid>

                        {/* Add New Item Form */}
                        <Grid item xs={12}>
                            <Paper elevation={3} sx={{ p: 4, mt: 4 }}>
                                <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                                    <Typography variant="h4">Add New Inventory Item</Typography>
                                    <Box>
                                        <Tooltip title="Bulk Import">
                                            <IconButton onClick={handleBulkImport}>
                                                <CloudUploadIcon />
                                            </IconButton>
                                        </Tooltip>
                                        <Tooltip title="Bulk Export">
                                            <IconButton onClick={handleBulkExport}>
                                                <CloudDownloadIcon />
                                            </IconButton>
                                        </Tooltip>
                                    </Box>
                                </Box>
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
                                                fullWidth
                                                label="SKU"
                                                name="sku"
                                                value={item.sku}
                                                InputProps={{
                                                    readOnly: true,
                                                    endAdornment: (
                                                        <Button
                                                            variant="contained"
                                                            color="secondary"
                                                            onClick={generateSKU}
                                                            size="small"
                                                        >
                                                            Generate
                                                        </Button>
                                                    ),
                                                }}
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

                        {/* Inventory Table */}
                        <Grid item xs={12}>
                            <Paper elevation={3} sx={{ p: 4, mt: 4 }}>
                                <Typography variant="h4" gutterBottom>
                                    Inventory Items
                                </Typography>
                                <TextField
                                    fullWidth
                                    variant="outlined"
                                    placeholder="Search Inventory..."
                                    value={searchTerm}
                                    onChange={handleSearchChange}
                                    sx={{ mb: 2 }}
                                    InputProps={{
                                        startAdornment: (
                                            <InputAdornment position="start">
                                                <SearchIcon />
                                            </InputAdornment>
                                        ),
                                    }}
                                />
                                <TableContainer>
                                    <Table>
                                        <TableHead>
                                            <TableRow>
                                                <TableCell>Name</TableCell>
                                                <TableCell>SKU</TableCell>
                                                <TableCell>Quantity</TableCell>
                                                <TableCell>Price</TableCell>
                                                <TableCell>Category</TableCell>
                                                <TableCell>Actions</TableCell>
                                            </TableRow>
                                        </TableHead>
                                        <TableBody>
                                            {filteredInventoryItems.map((item) => (
                                                <TableRow key={item.id}>
                                                    <TableCell>
                                                        {item.quantity < 10 && (
                                                            <Tooltip title="Low Stock">
                                                                <Badge badgeContent={<WarningIcon color="warning" />}>
                                                                    {item.name}
                                                                </Badge>
                                                            </Tooltip>
                                                        )}
                                                        {item.quantity >= 10 && item.name}
                                                    </TableCell>
                                                    <TableCell>{item.sku}</TableCell>
                                                    <TableCell>{item.quantity}</TableCell>
                                                    <TableCell>${item.price.toFixed(2)}</TableCell>
                                                    <TableCell>{item.category}</TableCell>
                                                    <TableCell>
                                                        <Tooltip title="Delete">
                                                            <IconButton
                                                                aria-label="delete"
                                                                onClick={() => handleDeleteItems(item.id)}
                                                            >
                                                                <DeleteIcon />
                                                            </IconButton>
                                                        </Tooltip>
                                                        <Tooltip title="Duplicate">
                                                            <IconButton
                                                                aria-label="duplicate"
                                                                onClick={() => handleDuplicateItem(item)}
                                                            >
                                                                <FileCopyIcon />
                                                            </IconButton>
                                                        </Tooltip>
                                                    </TableCell>
                                                </TableRow>
                                            ))}
                                        </TableBody>
                                    </Table>
                                </TableContainer>
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