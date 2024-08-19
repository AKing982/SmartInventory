import React, { useState, useEffect } from 'react';
import {
    Box, Container, Grid, Paper, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow,
    Button, Chip, IconButton, LinearProgress, Card, CardContent, CardActions, TextField, MenuItem, Tooltip,
    List, ListItem, ListItemText, ListItemIcon, Divider, Drawer, Toolbar
} from '@mui/material';
import {
    BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip as RechartsTooltip, Legend, ResponsiveContainer,
    LineChart, Line, PieChart, Pie, Cell
} from 'recharts';
import { styled } from '@mui/material/styles';
import {
    ViewList as ViewIcon,
    Add as AddIcon,
    Inventory as InventoryIcon,
    ShoppingCart as OrderIcon,
    Assessment as ReportIcon,
    Settings as SettingsIcon,
    Refresh as RefreshIcon,
    Warning as WarningIcon,
    TrendingUp as TrendingUpIcon,
    TrendingDown as TrendingDownIcon,
    Receipt as ReceiptIcon,
    LocalShipping as LocalShippingIcon
} from '@mui/icons-material';
import MainAppBar from './MainAppBar';
import backgroundImage from "../images/pexels-tiger-lily-4483610.jpg";

const drawerWidth = 240;

interface InventoryItem {
    name: string;
    stock: number;
    capacity: number;
    sku: string;
}

interface LowStockAlert extends InventoryItem {
    alertType: string;
}

interface Order {
    id: string;
    customer: string;
    items: number;
    total: number;
    status: string;
    date: string;
}

interface SalesDataPoint {
    name: string;
    sales: number;
    orders: number;
}

interface ProductPerformance {
    name: string;
    value: number;
}

interface TrendingProduct {
    name: string;
    trend: 'up' | 'down';
    percentage: number;
}


const BackgroundContainer = styled('div')({
    minHeight: '120vh',
    display: 'flex',
    flexDirection: 'column',
    background: 'linear-gradient(120deg, #f6f7f9 0%, #e3e6ec 100%)',
});


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

const SupplierDashboard: React.FC = () => {
    const [open, setOpen] = useState(false);
    const [selectedTimeRange, setSelectedTimeRange] = useState('7days');
    const [lowStockAlerts, setLowStockAlerts] = useState<LowStockAlert[]>([]);

    const handleDrawerToggle = () => {
        setOpen(!open);
    };

    const drawerItems = [
        { text: 'Dashboard', icon: <ReportIcon />, path: '/supplier/dashboard' },
        { text: 'Manage Inventory', icon: <InventoryIcon />, path: '/supplier/inventory' },
        { text: 'View Orders', icon: <OrderIcon />, path: '/supplier/orders' },
        { text: 'Reports', icon: <ReportIcon />, path: '/supplier/reports' },
        { text: 'Settings', icon: <SettingsIcon />, path: '/supplier/settings' },
    ];

    const recentOrders = [
        { id: '1001', customer: 'ABC Corp', items: 5, total: 1500, status: 'Pending', date: '2024-08-15' },
        { id: '1002', customer: 'XYZ Ltd', items: 3, total: 800, status: 'Shipped', date: '2024-08-14' },
        { id: '1003', customer: '123 Industries', items: 7, total: 2200, status: 'Processing', date: '2024-08-13' },
        { id: '1004', customer: 'Best Buy Co', items: 2, total: 600, status: 'Delivered', date: '2024-08-12' },
        { id: '1005', customer: 'Tech Solutions', items: 4, total: 1800, status: 'Pending', date: '2024-08-11' },
    ];

    const inventoryStatus = [
        { name: 'Product A', stock: 150, capacity: 200, sku: 'SKU001' },
        { name: 'Product B', stock: 80, capacity: 100, sku: 'SKU002' },
        { name: 'Product C', stock: 60, capacity: 150, sku: 'SKU003' },
        { name: 'Product D', stock: 30, capacity: 100, sku: 'SKU004' },
        { name: 'Product E', stock: 90, capacity: 120, sku: 'SKU005' },
    ];
    const salesData = [
        { name: 'Jan', sales: 4000, orders: 200 },
        { name: 'Feb', sales: 3000, orders: 180 },
        { name: 'Mar', sales: 5000, orders: 250 },
        { name: 'Apr', sales: 4500, orders: 220 },
        { name: 'May', sales: 6000, orders: 300 },
        { name: 'Jun', sales: 5500, orders: 280 },
        { name: 'Jul', sales: 7000, orders: 350 },
        { name: 'Aug', sales: 6500, orders: 320 },
    ];

    const productPerformance = [
        { name: 'Product A', value: 400 },
        { name: 'Product B', value: 300 },
        { name: 'Product C', value: 200 },
        { name: 'Product D', value: 100 },
        { name: 'Others', value: 150 },
    ];

    const trendingProducts = [
        { name: 'Product X', trend: 'up', percentage: 15 },
        { name: 'Product Y', trend: 'down', percentage: 8 },
        { name: 'Product Z', trend: 'up', percentage: 22 },
    ];

    const COLORS = ['#0088FE', '#00C49F', '#FFBB28', '#FF8042', '#8884D8'];

    useEffect(() => {
        const alerts: LowStockAlert[] = inventoryStatus
            .filter(item => (item.stock / item.capacity) < 0.2)
            .map(item => ({ ...item, alertType: 'Low Stock' }));
        setLowStockAlerts(alerts);
    }, []);

    return (
        <BackgroundContainer>
            <MainAppBar title="Supplier Dashboard" drawerItems={drawerItems} />
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
                    {drawerItems.map((item) => (
                        <ListItem button key={item.text}>
                            <ListItemIcon>{item.icon}</ListItemIcon>
                            <ListItemText primary={item.text} />
                        </ListItem>
                    ))}
                </List>
            </Drawer>
            <Main open={open}>
                <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
                    <Grid container spacing={3}>
                        <Grid item xs={12} md={3}>
                            <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', height: 140 }}>
                                <Typography component="h2" variant="h6" color="primary" gutterBottom>
                                    Total Revenue
                                </Typography>
                                <Typography component="p" variant="h4">
                                    $24,500
                                </Typography>
                                <Typography color="text.secondary" sx={{ flex: 1 }}>
                                    15% increase from last month
                                </Typography>
                            </Paper>
                        </Grid>
                        <Grid item xs={12} md={3}>
                            <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', height: 140 }}>
                                <Typography component="h2" variant="h6" color="primary" gutterBottom>
                                    Total Orders
                                </Typography>
                                <Typography component="p" variant="h4">
                                    156
                                </Typography>
                                <Typography color="text.secondary" sx={{ flex: 1 }}>
                                    10% increase from last month
                                </Typography>
                            </Paper>
                        </Grid>
                        <Grid item xs={12} md={3}>
                            <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', height: 140 }}>
                                <Typography component="h2" variant="h6" color="primary" gutterBottom>
                                    Average Order Value
                                </Typography>
                                <Typography component="p" variant="h4">
                                    $157
                                </Typography>
                                <Typography color="text.secondary" sx={{ flex: 1 }}>
                                    5% increase from last month
                                </Typography>
                            </Paper>
                        </Grid>
                        <Grid item xs={12} md={3}>
                            <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', height: 140 }}>
                                <Typography component="h2" variant="h6" color="primary" gutterBottom>
                                    Products in Stock
                                </Typography>
                                <Typography component="p" variant="h4">
                                    410
                                </Typography>
                                <Typography color="text.secondary" sx={{ flex: 1 }}>
                                    3 products low in stock
                                </Typography>
                            </Paper>
                        </Grid>

                        <Grid item xs={12} md={8}>
                            <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', height: 400 }}>
                                <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
                                    <Typography component="h2" variant="h6" color="primary" gutterBottom>
                                        Sales Overview
                                    </Typography>
                                    <TextField
                                        select
                                        value={selectedTimeRange}
                                        onChange={(e) => setSelectedTimeRange(e.target.value)}
                                        variant="outlined"
                                        size="small"
                                    >
                                        <MenuItem value="7days">Last 7 Days</MenuItem>
                                        <MenuItem value="30days">Last 30 Days</MenuItem>
                                        <MenuItem value="90days">Last 90 Days</MenuItem>
                                        <MenuItem value="12months">Last 12 Months</MenuItem>
                                    </TextField>
                                </Box>
                                <ResponsiveContainer width="100%" height="100%">
                                    <LineChart
                                        data={salesData}
                                        margin={{ top: 5, right: 30, left: 20, bottom: 5 }}
                                    >
                                        <CartesianGrid strokeDasharray="3 3" />
                                        <XAxis dataKey="name" />
                                        <YAxis yAxisId="left" />
                                        <YAxis yAxisId="right" orientation="right" />
                                        <RechartsTooltip />
                                        <Legend />
                                        <Line yAxisId="left" type="monotone" dataKey="sales" stroke="#8884d8" activeDot={{ r: 8 }} />
                                        <Line yAxisId="right" type="monotone" dataKey="orders" stroke="#82ca9d" />
                                    </LineChart>
                                </ResponsiveContainer>
                            </Paper>
                        </Grid>
                        <Grid item xs={12} md={4}>
                            <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', height: 400 }}>
                                <Typography component="h2" variant="h6" color="primary" gutterBottom>
                                    Product Performance
                                </Typography>
                                <ResponsiveContainer width="100%" height="100%">
                                    <PieChart>
                                        <Pie
                                            data={productPerformance}
                                            cx="50%"
                                            cy="50%"
                                            labelLine={false}
                                            outerRadius={80}
                                            fill="#8884d8"
                                            dataKey="value"
                                            label={({ name, percent }) => `${name} ${(percent * 100).toFixed(0)}%`}
                                        >
                                            {productPerformance.map((entry, index) => (
                                                <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                                            ))}
                                        </Pie>
                                        <RechartsTooltip />
                                    </PieChart>
                                </ResponsiveContainer>
                            </Paper>
                        </Grid>

                        <Grid item xs={12}>
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
                                                <TableCell>Date</TableCell>
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
                                                    <TableCell>{order.date}</TableCell>
                                                    <TableCell>
                                                        <Chip
                                                            label={order.status}
                                                            color={order.status === 'Shipped' ? 'success' :
                                                                order.status === 'Pending' ? 'warning' :
                                                                    order.status === 'Delivered' ? 'primary' : 'default'}
                                                            size="small"
                                                        />
                                                    </TableCell>
                                                    <TableCell>
                                                        <Tooltip title="View Order Details">
                                                            <IconButton size="small">
                                                                <ViewIcon />
                                                            </IconButton>
                                                        </Tooltip>
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

                        <Grid item xs={12} md={6}>
                            <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', height: '100%' }}>
                                <Typography component="h2" variant="h6" color="primary" gutterBottom>
                                    Inventory Status
                                </Typography>
                                {inventoryStatus.map((item) => (
                                    <Box key={item.sku} sx={{ mb: 2 }}>
                                        <Typography variant="body2">{item.name} (SKU: {item.sku})</Typography>
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
                                    startIcon={<RefreshIcon />}
                                    sx={{ mt: 'auto' }}
                                >
                                    Update Inventory
                                </Button>
                            </Paper>
                        </Grid>

                        <Grid item xs={12} md={6}>
                            <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', height: '100%' }}>
                                <Typography component="h2" variant="h6" color="primary" gutterBottom>
                                    Low Stock Alerts
                                </Typography>
                                {lowStockAlerts.length > 0 ? (
                                    lowStockAlerts.map((alert) => (
                                        <Card key={alert.sku} sx={{ mb: 2, bgcolor: 'warning.light' }}>
                                            <CardContent>
                                                <Typography variant="h6" component="div">
                                                    {alert.name} (SKU: {alert.sku})
                                                </Typography>
                                                <Typography variant="body2">
                                                    Current Stock: {alert.stock} / {alert.capacity}
                                                </Typography>
                                            </CardContent>
                                            <CardActions>
                                                <Button size="small" variant="contained" color="primary">
                                                    Restock
                                                </Button>
                                                <Button size="small" variant="outlined" color="primary">
                                                    Adjust Threshold
                                                </Button>
                                            </CardActions>
                                        </Card>
                                    ))
                                ) : (
                                    <Typography variant="body1" color="text.secondary">
                                        No low stock alerts at this time.
                                    </Typography>
                                )}
                            </Paper>
                        </Grid>

                        <Grid item xs={12}>
                            <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
                                <Typography component="h2" variant="h6" color="primary" gutterBottom>
                                    Quick Actions
                                </Typography>
                                <Grid container spacing={2}>
                                    {[
                                        { text: 'Add New Product', icon: <AddIcon />, action: '/supplier/add-product' },
                                        { text: 'Generate Invoice', icon: <ReceiptIcon />, action: '/supplier/generate-invoice' },
                                        { text: 'View Reports', icon: <ReportIcon />, action: '/supplier/reports' },
                                        { text: 'Manage Shipments', icon: <LocalShippingIcon />, action: '/supplier/shipments' },
                                    ].map((action) => (
                                        <Grid item xs={12} sm={6} md={3} key={action.text}>
                                            <Card>
                                                <CardContent>
                                                    <Typography variant="h6" component="div">
                                                        {action.text}
                                                    </Typography>
                                                </CardContent>
                                                <CardActions>
                                                    <Button
                                                        size="small"
                                                        variant="contained"
                                                        color="primary"
                                                        startIcon={action.icon}
                                                        onClick={() => {/* Handle navigation or action */}}
                                                    >
                                                        Go
                                                    </Button>
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
        </BackgroundContainer>
    );
};

export default SupplierDashboard;