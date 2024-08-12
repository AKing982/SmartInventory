import React, {useEffect, useState} from 'react';
import {
    AppBar,
    Toolbar,
    Typography,
    Container,
    Grid,
    Paper,
    Button,
    Box,
    Card,
    CardContent,
    CardActions,
    styled,
    List,
    ListItem,
    ListItemText,
    Divider,
    Chip,
    Avatar,
    IconButton,
    ListItemIcon,
    ListItemButton,
    Drawer,
} from '@mui/material';
import {
    Inventory as InventoryIcon,
    AddBox as AddBoxIcon,
    Assessment as AssessmentIcon,
    Settings as SettingsIcon,
    ExitToApp as LogoutIcon,
} from '@mui/icons-material';
import {useNavigate} from "react-router-dom";
import NotificationsIcon from '@mui/icons-material/Notifications';
import TrendingUpIcon from '@mui/icons-material/TrendingUp';
import TrendingDownIcon from '@mui/icons-material/TrendingDown';
import PeopleIcon from '@mui/icons-material/People';
import BusinessIcon from '@mui/icons-material/Business';
import CategoryIcon from '@mui/icons-material/Category';
import WarehouseIcon from '@mui/icons-material/Warehouse';
import MenuIcon from '@mui/icons-material/Menu';
import backgroundImage from '../images/pexels-tiger-lily-4483610.jpg';


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


// Styled component for the content area


const HomePage: React.FC = () => {
    const navigate = useNavigate();
    const features = [
        { title: 'Manage Inventory', icon: <InventoryIcon />, description: 'Track and manage your inventory in real-time.' },
        { title: 'Add New Items', icon: <AddBoxIcon />, description: 'Easily add new items to your inventory.', action: () => navigate('/addInventory') },
        { title: 'Generate Reports', icon: <AssessmentIcon />, description: 'Create detailed reports and analytics.' },
        { title: 'System Settings', icon: <SettingsIcon />, description: 'Customize the system to fit your needs.' },
    ];
    const [drawerOpen, setDrawerOpen] = useState(false);

    const [quickStats, setQuickStats] = useState({
        totalItems: 0,
        lowStockItems: 0,
        recentSales: 0,
        pendingOrders: 0
    });
    const [recentActivity, setRecentActivity] = useState<string[]>([]);


    const user = localStorage.getItem('user');

    useEffect(() => {
        setQuickStats({
            totalItems: 1250,
            lowStockItems: 15,
            recentSales: 45,
            pendingOrders: 8
        });
        setRecentActivity([
            'New item "Wireless Keyboard" added to inventory',
            'Order #1234 shipped to customer',
            'Low stock alert: "USB-C Cable" (5 remaining)',
            'Monthly sales report generated'
        ])
    }, []);


    const handleLogout = () => {
        // Implement logout logic here
        console.log('Logging out...');
        localStorage.clear();
        navigate('/');

    };

    const handleDrawerToggle = () => {
        setDrawerOpen(!drawerOpen);
    };


    const menuItems = [
        { title: 'Dashboard', icon: <AssessmentIcon />, path: '/dashboard' },
        { title: 'Inventory', icon: <InventoryIcon />, path: '/inventory' },
        { title: 'Categories', icon: <CategoryIcon />, path: '/categories' },
        { title: 'Warehouses', icon: <WarehouseIcon />, path: '/warehouses' },
        { title: 'Contacts', icon: <PeopleIcon />, path: '/contacts' },
        { title: 'Departments', icon: <BusinessIcon />, path: '/departments' },
        { title: 'Employees', icon: <PeopleIcon />, path: '/employees' },
        { title: 'Customers', icon: <PeopleIcon />, path: '/customers' },
        { title: 'Reports', icon: <AssessmentIcon />, path: '/reports' },
        { title: 'Settings', icon: <SettingsIcon />, path: '/settings' },
    ];

    const drawerContent = (
        <Box sx={{ width: 250 }} role="presentation" onClick={handleDrawerToggle}>
            <List>
                {menuItems.map((item) => (
                    <ListItem key={item.title} disablePadding>
                        <ListItemButton onClick={() => navigate(item.path)}>
                            <ListItemIcon>{item.icon}</ListItemIcon>
                            <ListItemText primary={item.title} />
                        </ListItemButton>
                    </ListItem>
                ))}
            </List>
        </Box>
    );

    return (
        <Box sx={{ display: 'flex' }}>
            <AppBar position="fixed">
                <Toolbar>
                    <IconButton
                        color="inherit"
                        aria-label="open drawer"
                        edge="start"
                        onClick={handleDrawerToggle}
                        sx={{ mr: 2 }}
                    >
                        <MenuIcon />
                    </IconButton>
                    <Typography variant="h6" noWrap component="div" sx={{ flexGrow: 1 }}>
                        SmartInventory
                    </Typography>
                    <Button color="inherit" onClick={handleLogout} startIcon={<LogoutIcon />}>
                        Logout
                    </Button>
                </Toolbar>
            </AppBar>
            <Drawer
                anchor="left"
                open={drawerOpen}
                onClose={handleDrawerToggle}
            >
                {drawerContent}
            </Drawer>
            <Box
                component="main"
                sx={{
                    flexGrow: 1,
                    p: 3,
                    width: { sm: `calc(100% - ${250}px)` },
                    ml: { sm: `${250}px` },
                    mt: ['48px', '56px', '64px'],
                }}
            >
                <Container maxWidth="lg">
                    <Grid container spacing={3}>
                        {/* User Welcome Card */}
                        <Grid item xs={12} md={4}>
                            <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                                <Avatar sx={{ width: 60, height: 60, mb: 2 }}>{user?.charAt(0).toUpperCase()}</Avatar>
                                <Typography variant="h5" gutterBottom>Welcome, {user}!</Typography>
                                <Typography variant="body2" color="text.secondary">
                                    Last login: {new Date().toLocaleString()}
                                </Typography>
                            </Paper>
                        </Grid>

                        {/* Quick Stats */}
                        <Grid item xs={12} md={8}>
                            <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
                                <Typography variant="h6" gutterBottom>Quick Stats</Typography>
                                <Grid container spacing={2}>
                                    <Grid item xs={6} sm={3}>
                                        <Chip icon={<InventoryIcon />} label={`${quickStats.totalItems} Items`} color="primary" />
                                    </Grid>
                                    <Grid item xs={6} sm={3}>
                                        <Chip icon={<TrendingDownIcon />} label={`${quickStats.lowStockItems} Low Stock`} color="warning" />
                                    </Grid>
                                    <Grid item xs={6} sm={3}>
                                        <Chip icon={<TrendingUpIcon />} label={`${quickStats.recentSales} Sales`} color="success" />
                                    </Grid>
                                    <Grid item xs={6} sm={3}>
                                        <Chip icon={<NotificationsIcon />} label={`${quickStats.pendingOrders} Orders`} color="info" />
                                    </Grid>
                                </Grid>
                            </Paper>
                        </Grid>

                        {/* Recent Activity */}
                        <Grid item xs={12} md={6}>
                            <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
                                <Typography variant="h6" gutterBottom>Recent Activity</Typography>
                                <List>
                                    {recentActivity.map((activity, index) => (
                                        <React.Fragment key={index}>
                                            <ListItem>
                                                <ListItemText primary={activity} />
                                            </ListItem>
                                            {index < recentActivity.length - 1 && <Divider />}
                                        </React.Fragment>
                                    ))}
                                </List>
                            </Paper>
                        </Grid>

                        {/* Features */}
                        {features.map((feature, index) => (
                            <Grid item xs={12} md={6} key={index}>
                                <Card>
                                    <CardContent>
                                        <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
                                            {feature.icon}
                                            <Typography variant="h6" component="div" sx={{ ml: 1 }}>
                                                {feature.title}
                                            </Typography>
                                        </Box>
                                        <Typography variant="body2" color="text.secondary">
                                            {feature.description}
                                        </Typography>
                                    </CardContent>
                                    <CardActions>
                                        <Button size="small" onClick={feature.action}>
                                            {feature.title === 'Add New Items' ? 'Add Item' : 'Learn More'}
                                        </Button>
                                    </CardActions>
                                </Card>
                            </Grid>
                        ))}
                    </Grid>
                </Container>
            </Box>
        </Box>
    );
    // return (
    //     <BackgroundContainer>
    //         <AppBar position="static">
    //             <Toolbar>
    //                 <InventoryIcon sx={{ mr: 2 }} />
    //                 <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
    //                     SmartInventory Management System
    //                 </Typography>
    //                 <Button color="inherit">Dashboard</Button>
    //                 <Button color="inherit">Inventory</Button>
    //                 <Button color="inherit">Reports</Button>
    //                 <Button color="inherit">Profile</Button>
    //                 <Button color="inherit" onClick={handleLogout} startIcon={<LogoutIcon />}>
    //                     Logout
    //                 </Button>
    //             </Toolbar>
    //         </AppBar>
    //
    //         <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
    //             <Grid container spacing={3}>
    //                 <Grid item xs={12} md={4}>
    //                     <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
    //                         <Avatar sx={{ width: 60, height: 60, mb: 2 }}>{user?.charAt(0).toUpperCase()}</Avatar>
    //                         <Typography variant="h5" gutterBottom>Welcome, {user}!</Typography>
    //                         <Typography variant="body2" color="text.secondary">
    //                             Last login: {new Date().toLocaleString()}
    //                         </Typography>
    //                     </Paper>
    //                 </Grid>
    //                 <Grid item xs={12} md={8}>
    //                     <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
    //                         <Typography variant="h6" gutterBottom>Quick Stats</Typography>
    //                         <Grid container spacing={2}>
    //                             <Grid item xs={6} sm={3}>
    //                                 <Chip
    //                                     icon={<InventoryIcon />}
    //                                     label={`${quickStats.totalItems} Items`}
    //                                     color="primary"
    //                                 />
    //                             </Grid>
    //                             <Grid item xs={6} sm={3}>
    //                                 <Chip
    //                                     icon={<TrendingDownIcon />}
    //                                     label={`${quickStats.lowStockItems} Low Stock`}
    //                                     color="warning"
    //                                 />
    //                             </Grid>
    //                             <Grid item xs={6} sm={3}>
    //                                 <Chip
    //                                     icon={<TrendingUpIcon />}
    //                                     label={`${quickStats.recentSales} Sales`}
    //                                     color="success"
    //                                 />
    //                             </Grid>
    //                             <Grid item xs={6} sm={3}>
    //                                 <Chip
    //                                     icon={<NotificationsIcon />}
    //                                     label={`${quickStats.pendingOrders} Orders`}
    //                                     color="info"
    //                                 />
    //                             </Grid>
    //                         </Grid>
    //                     </Paper>
    //                 </Grid>
    //                 <Grid item xs={12} md={6}>
    //                     <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
    //                         <Typography variant="h6" gutterBottom>Recent Activity</Typography>
    //                         <List>
    //                             {recentActivity.map((activity, index) => (
    //                                 <React.Fragment key={index}>
    //                                     <ListItem>
    //                                         <ListItemText primary={activity} />
    //                                     </ListItem>
    //                                     {index < recentActivity.length - 1 && <Divider />}
    //                                 </React.Fragment>
    //                             ))}
    //                         </List>
    //                     </Paper>
    //                 </Grid>
    //                 {features.map((feature, index) => (
    //                     <Grid item xs={12} md={6} key={index}>
    //                         <Card>
    //                             <CardContent>
    //                                 <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
    //                                     {feature.icon}
    //                                     <Typography variant="h6" component="div" sx={{ ml: 1 }}>
    //                                         {feature.title}
    //                                     </Typography>
    //                                 </Box>
    //                                 <Typography variant="body2" color="text.secondary">
    //                                     {feature.description}
    //                                 </Typography>
    //                             </CardContent>
    //                             <CardActions>
    //                                 <Button size="small" onClick={feature.action}>
    //                                     {feature.title === 'Add New Items' ? 'Add Item' : 'Learn More'}
    //                                 </Button>
    //                             </CardActions>
    //                         </Card>
    //                     </Grid>
    //                 ))}
    //             </Grid>
    //         </Container>
    //     </BackgroundContainer>
    // );
};

export default HomePage