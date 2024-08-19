import React, { useState } from 'react';
import {
    Box, Container, Paper, Toolbar, Typography, Grid, Card, CardContent, CardMedia, Button,
    Table, TableBody, TableCell, TableContainer, TableHead, TableRow, TextField, MenuItem
} from '@mui/material';
import {
    Inventory as InventoryIcon,
    ShoppingCart as ShoppingCartIcon,
    History as HistoryIcon,
    Person as PersonIcon
} from '@mui/icons-material';
import MainAppBar from './MainAppBar'; // Ensure this import path is correct

type NavItem = {
    text: string;
    icon: React.ReactElement;
    section: string;
};

interface BasicUserHomePageProps {}

const drawerWidth = 240;

const BasicUserHomePage: React.FC<BasicUserHomePageProps> = () => {
    const [activeSection, setActiveSection] = useState<string>('catalog');

    const menuItems = [
        { text: 'Product Catalog', icon: <InventoryIcon />, path: '/catalog' },
        { text: 'Place Order', icon: <ShoppingCartIcon />, path: '/order-placement' },
        { text: 'Order History', icon: <HistoryIcon />, path: '/order-history' },
        { text: 'Update Profile', icon: <PersonIcon />, path: '/profile' },
    ];

    const handleMenuItemSelect = (selectedItem: string) => {
        setActiveSection(selectedItem.toLowerCase().replace(' ', '-'));
    };

    const renderContent = (): React.ReactNode => {
        switch (activeSection) {
            case 'product-catalog':
                return <ProductCatalog />;
            case 'place-order':
                return <OrderSection />;
            case 'order-history':
                return <OrderHistory />;
            case 'update-profile':
                return <UserProfile />;
            default:
                return <ProductCatalog />;
        }
    };

    return (
        <Box sx={{ display: 'flex' }}>
            <MainAppBar title="User Home" drawerItems={menuItems} />
            <Box component="main" sx={{ flexGrow: 1, p: 3, width: { sm: `calc(100% - ${drawerWidth}px)` } }}>
                <Toolbar />
                <Container maxWidth="lg">
                    <Paper elevation={3} sx={{ p: 2 }}>
                        {renderContent()}
                    </Paper>
                </Container>
            </Box>
        </Box>
    );
};

const ProductCatalog: React.FC = () => {
    const products = [
        { id: 1, name: 'Product A', price: 19.99, image: 'https://via.placeholder.com/150' },
        { id: 2, name: 'Product B', price: 29.99, image: 'https://via.placeholder.com/150' },
        { id: 3, name: 'Product C', price: 39.99, image: 'https://via.placeholder.com/150' },
        { id: 4, name: 'Product D', price: 49.99, image: 'https://via.placeholder.com/150' },
    ];

    return (
        <div>
            <Typography variant="h4" gutterBottom>Product Catalog</Typography>
            <Grid container spacing={3}>
                {products.map((product) => (
                    <Grid item xs={12} sm={6} md={4} key={product.id}>
                        <Card>
                            <CardMedia
                                component="img"
                                height="140"
                                image={product.image}
                                alt={product.name}
                            />
                            <CardContent>
                                <Typography gutterBottom variant="h5" component="div">
                                    {product.name}
                                </Typography>
                                <Typography variant="body2" color="text.secondary">
                                    ${product.price.toFixed(2)}
                                </Typography>
                                <Button variant="contained" color="primary" sx={{ mt: 2 }}>
                                    Add to Cart
                                </Button>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </div>
    );
};

const OrderSection: React.FC = () => {
    const [orderItems, setOrderItems] = useState([
        { id: 1, name: 'Product A', quantity: 1, price: 19.99 },
    ]);

    const handleQuantityChange = (id: number, newQuantity: number) => {
        setOrderItems(orderItems.map(item =>
            item.id === id ? { ...item, quantity: newQuantity } : item
        ));
    };

    const totalPrice = orderItems.reduce((sum, item) => sum + item.quantity * item.price, 0);

    return (
        <div>
            <Typography variant="h4" gutterBottom>Place Order</Typography>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Product</TableCell>
                            <TableCell align="right">Quantity</TableCell>
                            <TableCell align="right">Price</TableCell>
                            <TableCell align="right">Total</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {orderItems.map((item) => (
                            <TableRow key={item.id}>
                                <TableCell component="th" scope="row">
                                    {item.name}
                                </TableCell>
                                <TableCell align="right">
                                    <TextField
                                        type="number"
                                        value={item.quantity}
                                        onChange={(e) => handleQuantityChange(item.id, parseInt(e.target.value))}
                                        InputProps={{ inputProps: { min: 1 } }}
                                    />
                                </TableCell>
                                <TableCell align="right">${item.price.toFixed(2)}</TableCell>
                                <TableCell align="right">${(item.quantity * item.price).toFixed(2)}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
            <Typography variant="h6" align="right" sx={{ mt: 2 }}>
                Total: ${totalPrice.toFixed(2)}
            </Typography>
            <Button variant="contained" color="primary" sx={{ mt: 2 }}>
                Place Order
            </Button>
        </div>
    );
};

const OrderHistory: React.FC = () => {
    const orders = [
        { id: 1, date: '2024-08-15', total: 59.97, status: 'Delivered' },
        { id: 2, date: '2024-08-10', total: 89.98, status: 'Shipped' },
        { id: 3, date: '2024-08-05', total: 129.95, status: 'Processing' },
    ];

    return (
        <div>
            <Typography variant="h4" gutterBottom>Order History</Typography>
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <TableCell>Order ID</TableCell>
                            <TableCell>Date</TableCell>
                            <TableCell align="right">Total</TableCell>
                            <TableCell>Status</TableCell>
                            <TableCell>Action</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {orders.map((order) => (
                            <TableRow key={order.id}>
                                <TableCell component="th" scope="row">
                                    {order.id}
                                </TableCell>
                                <TableCell>{order.date}</TableCell>
                                <TableCell align="right">${order.total.toFixed(2)}</TableCell>
                                <TableCell>{order.status}</TableCell>
                                <TableCell>
                                    <Button variant="outlined" size="small">
                                        View Details
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </div>
    );
};

const UserProfile: React.FC = () => {
    const [profile, setProfile] = useState({
        firstName: 'John',
        lastName: 'Doe',
        email: 'john.doe@example.com',
        phone: '123-456-7890',
    });

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setProfile(prev => ({ ...prev, [name]: value }));
    };

    return (
        <div>
            <Typography variant="h4" gutterBottom>Update Profile</Typography>
            <form>
                <Grid container spacing={3}>
                    <Grid item xs={12} sm={6}>
                        <TextField
                            fullWidth
                            label="First Name"
                            name="firstName"
                            value={profile.firstName}
                            onChange={handleChange}
                        />
                    </Grid>
                    <Grid item xs={12} sm={6}>
                        <TextField
                            fullWidth
                            label="Last Name"
                            name="lastName"
                            value={profile.lastName}
                            onChange={handleChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="Email"
                            name="email"
                            type="email"
                            value={profile.email}
                            onChange={handleChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <TextField
                            fullWidth
                            label="Phone"
                            name="phone"
                            value={profile.phone}
                            onChange={handleChange}
                        />
                    </Grid>
                    <Grid item xs={12}>
                        <Button variant="contained" color="primary">
                            Update Profile
                        </Button>
                    </Grid>
                </Grid>
            </form>
        </div>
    );
};

export default BasicUserHomePage;