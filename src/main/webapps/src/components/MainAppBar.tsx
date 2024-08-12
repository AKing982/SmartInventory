import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import {
    AppBar, Toolbar, Typography, Button, IconButton, Drawer, List,
    ListItem, ListItemIcon, ListItemText, Box
} from '@mui/material';
import {
    Menu as MenuIcon,
    Logout as LogoutIcon,
    Dashboard as DashboardIcon,
    Inventory as InventoryIcon,
    Category as CategoryIcon,
    Assessment as AssessmentIcon,
    Settings as SettingsIcon
} from '@mui/icons-material';
import WarehouseIcon from "@mui/icons-material/Warehouse";
import PeopleIcon from "@mui/icons-material/People";
import BusinessIcon from "@mui/icons-material/Business";

interface MainAppBarProps {
    title: string;
}

const MainAppBar: React.FC<MainAppBarProps> = ({ title }) => {
    const navigate = useNavigate();
    const [drawerOpen, setDrawerOpen] = useState<boolean>(false);

    const handleDrawerToggle = () => {
        setDrawerOpen(!drawerOpen);
    };

    const handleLogout = () => {
        localStorage.clear();
        navigate('/');
    };

    const menuItems = [
        { title: 'Dashboard', icon: <AssessmentIcon />, path: '/home' },
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


    return (
        <>
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
                        {title}
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
                <Box
                    sx={{ width: 250 }}
                    role="presentation"
                    onClick={handleDrawerToggle}
                    onKeyDown={handleDrawerToggle}
                >
                    <List>
                        {menuItems.map((item) => (
                            <ListItem button key={item.title} onClick={() => navigate(item.path)}>
                                <ListItemIcon>{item.icon}</ListItemIcon>
                                <ListItemText primary={item.title} />
                            </ListItem>
                        ))}
                    </List>
                </Box>
            </Drawer>
        </>
    );
};

export default MainAppBar;