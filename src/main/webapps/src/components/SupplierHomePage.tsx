import React, { useState } from 'react';
import {
    AppBar,
    Toolbar,
    Typography,
    Drawer,
    List,
    ListItem,
    ListItemIcon,
    ListItemText,
    IconButton,
    Box,
    Container,
    Grid,
    Paper,
    Card,
    CardContent,
    CardActions,
    Button,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    LinearProgress,
    Chip
} from '@mui/material';
import {
    Menu as MenuIcon,
    Inventory as InventoryIcon,
    ShoppingCart as OrderIcon,
    Assessment as ReportIcon,
    Settings as SettingsIcon,
    Notifications as NotificationsIcon,
    Add as AddIcon,
    RemoveRedEye as ViewIcon
} from '@mui/icons-material';
import { styled } from '@mui/material/styles';
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import MainAppBar from "./MainAppBar";

const drawerWidth = 240;

const Main = styled('main', { shouldForwardProp: (prop) => prop !== 'open' })<{
    open?: boolean;
}>(({ theme, open }) => ({
    flexGrow: 1,
    padding: theme.spacing(3),
    transition: theme.transitions.create('margin', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen,
    }),
    marginLeft: `-${drawerWidth}px`,
    ...(open && {
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen,
        }),
        marginLeft: 0,
    }),
}));

const SupplierHomePage: React.FC = () => {
    const [open, setOpen] = useState(false);

    const handleDrawerToggle = () => {
        setOpen(!open);
    };

    const drawerItems = [
        { text: 'Dashboard', icon: <ReportIcon /> },
        { text: 'Manage Inventory', icon: <InventoryIcon /> },
        { text: 'View Orders', icon: <OrderIcon /> },
        { text: 'Reports', icon: <ReportIcon /> },
        { text: 'Settings', icon: <SettingsIcon /> },
    ];

    // Mock data for recent orders
    const recentOrders = [
        { id: '1001', customer: 'ABC Corp', items: 5, total: 1500, status: 'Pending' },
        { id: '1002', customer: 'XYZ Ltd', items: 3, total: 800, status: 'Shipped' },
        { id: '1003', customer: '123 Industries', items: 7, total: 2200, status: 'Processing' },
    ];

    // Mock data for inventory status
    const inventoryStatus = [
        { name: 'Product A', stock: 150, capacity: 200 },
        { name: 'Product B', stock: 80, capacity: 100 },
        { name: 'Product C', stock: 60, capacity: 150 },
    ];

    // Mock data for sales chart
    const salesData = [
        { name: 'Jan', sales: 4000 },
        { name: 'Feb', sales: 3000 },
        { name: 'Mar', sales: 5000 },
        { name: 'Apr', sales: 4500 },
        { name: 'May', sales: 6000 },
        { name: 'Jun', sales: 5500 },
    ];

    const items = [
        { text: 'Dashboard', icon: <ReportIcon />, path: '/supplier/dashboard' },
        { text: 'Manage Inventory', icon: <InventoryIcon />, path: '/supplier/inventory' },
        { text: 'View Orders', icon: <OrderIcon />, path: '/supplier/orders' },
        { text: 'Reports', icon: <ReportIcon />, path: '/supplier/reports' },
        { text: 'Settings', icon: <SettingsIcon />, path: '/supplier/settings' },
    ];

    return (
        <Box sx={{ display: 'flex' }}>
            <MainAppBar title="Supplier Dashboard" drawerItems={items}/>
            <Drawer
                sx={{
                    width: drawerWidth,
                    flexShrink: 0,
                    '& .MuiDrawer-paper': {
                        width: drawerWidth,
                        boxSizing: 'border-box',
                    },
                }}
                variant="persistent"
                anchor="left"
                open={open}
            >
                <Toolbar />
                <List>
                    {drawerItems.map((item, index) => (
                        <ListItem button key={item.text}>
                            <ListItemIcon>{item.icon}</ListItemIcon>
                            <ListItemText primary={item.text} />
                        </ListItem>
                    ))}
                </List>
            </Drawer>
            <Main open={open}>
                <Toolbar />
                <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
                    <Grid container spacing={3}>
                        {/* Recent Orders */}
                        <Grid item xs={12} md={8} lg={9}>
                            <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
                                <Typography component="h2" variant="h6" color="primary" gutterBottom>
                                    Recent Orders
                                </Typography>
                                <TableContainer>
                                    <Table size="small">
                                        <TableHead>
                                            <TableRow>
                                                <TableCell>Order ID</TableCell>
                                                <TableCell>Customer</TableCell>
                                                <TableCell>Items</TableCell>
                                                <TableCell>Total</TableCell>
                                                <TableCell>Status</TableCell>
                                                <TableCell>Action</TableCell>
                                            </TableRow>
                                        </TableHead>
                                        <TableBody>
                                            {recentOrders.map((order) => (
                                                <TableRow key={order.id}>
                                                    <TableCell>{order.id}</TableCell>
                                                    <TableCell>{order.customer}</TableCell>
                                                    <TableCell>{order.items}</TableCell>
                                                    <TableCell>${order.total}</TableCell>
                                                    <TableCell>
                                                        <Chip
                                                            label={order.status}
                                                            color={order.status === 'Shipped' ? 'success' : 'warning'}
                                                            size="small"
                                                        />
                                                    </TableCell>
                                                    <TableCell>
                                                        <IconButton size="small">
                                                            <ViewIcon />
                                                        </IconButton>
                                                    </TableCell>
                                                </TableRow>
                                            ))}
                                        </TableBody>
                                    </Table>
                                </TableContainer>
                                <Button color="primary" sx={{ alignSelf: 'flex-end', mt: 2 }}>
                                    View All Orders
                                </Button>
                            </Paper>
                        </Grid>

                        {/* Inventory Status */}
                        <Grid item xs={12} md={4} lg={3}>
                            <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', height: '100%' }}>
                                <Typography component="h2" variant="h6" color="primary" gutterBottom>
                                    Inventory Status
                                </Typography>
                                {inventoryStatus.map((item) => (
                                    <Box key={item.name} sx={{ mb: 2 }}>
                                        <Typography variant="body2">{item.name}</Typography>
                                        <LinearProgress
                                            variant="determinate"
                                            value={(item.stock / item.capacity) * 100}
                                            sx={{ height: 10, borderRadius: 5 }}
                                        />
                                        <Typography variant="caption">
                                            {item.stock} / {item.capacity}
                                        </Typography>
                                    </Box>
                                ))}
                                <Button
                                    variant="outlined"
                                    color="primary"
                                    startIcon={<AddIcon />}
                                    sx={{ mt: 'auto' }}
                                >
                                    Add New Product
                                </Button>
                            </Paper>
                        </Grid>

                        {/* Sales Chart */}
                        <Grid item xs={12}>
                            <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', height: 400 }}>
                                <Typography component="h2" variant="h6" color="primary" gutterBottom>
                                    Sales Overview
                                </Typography>
                                <ResponsiveContainer width="100%" height="100%">
                                    <BarChart
                                        data={salesData}
                                        margin={{ top: 20, right: 30, left: 20, bottom: 5 }}
                                    >
                                        <CartesianGrid strokeDasharray="3 3" />
                                        <XAxis dataKey="name" />
                                        <YAxis />
                                        <Tooltip />
                                        <Legend />
                                        <Bar dataKey="sales" fill="#8884d8" />
                                    </BarChart>
                                </ResponsiveContainer>
                            </Paper>
                        </Grid>

                        {/* Quick Actions */}
                        <Grid item xs={12}>
                            <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
                                <Typography component="h2" variant="h6" color="primary" gutterBottom>
                                    Quick Actions
                                </Typography>
                                <Grid container spacing={2}>
                                    {['Update Inventory', 'Create Invoice', 'View Reports', 'Manage Products'].map((action) => (
                                        <Grid item xs={12} sm={6} md={3} key={action}>
                                            <Card>
                                                <CardContent>
                                                    <Typography variant="h6" component="div">
                                                        {action}
                                                    </Typography>
                                                </CardContent>
                                                <CardActions>
                                                    <Button size="small" variant="contained" color="primary">Go</Button>
                                                </CardActions>
                                            </Card>
                                        </Grid>
                                    ))}
                                </Grid>
                            </Paper>
                        </Grid>
                    </Grid>
                </Container>
            </Main>
        </Box>
    );
};

export default SupplierHomePage;