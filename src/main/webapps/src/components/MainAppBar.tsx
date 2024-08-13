import React, { useState } from 'react';
import {
    AppBar, Toolbar, Typography, Button, IconButton, InputBase, Badge,
    Menu, MenuItem, Avatar, Tabs, Tab, Box
} from '@mui/material';
import {
    Menu as MenuIcon,
    Logout as LogoutIcon,
    Notifications as NotificationsIcon,
    Search as SearchIcon,
    Add as AddIcon,
    Person as PersonIcon
} from '@mui/icons-material';
import { useNavigate, useLocation } from 'react-router-dom';
import SideDrawer from './SideDrawer';

interface MainAppBarProps {
    title: string;
}

const MainAppBar: React.FC<MainAppBarProps> = ({ title }) => {
    const navigate = useNavigate();
    const location = useLocation();
    const [drawerOpen, setDrawerOpen] = useState<boolean>(false);
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const user = localStorage.getItem('user');

    const handleDrawerToggle = () => {
        setDrawerOpen(!drawerOpen);
    };

    const handleLogout = () => {
        localStorage.clear();
        navigate('/');
    };

    const handleProfileMenuOpen = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    const handleTabChange = (event: React.SyntheticEvent, newValue: string) => {
        navigate(newValue);
    };

    // Define your tabs here
    const tabs = [
        { label: 'Dashboard', value: '/home' },
        { label: 'Inventory', value: '/inventory' },
        { label: 'Orders', value: '/orders' },
        { label: 'Customers', value: '/customers' },
        { label: 'Reports', value: '/reports' },
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
                    <Typography variant="h6" noWrap component="div" sx={{ display: { xs: 'none', sm: 'block' } }}>
                        {title}
                    </Typography>
                    <div style={{ flexGrow: 1 }} />
                    <div style={{ position: 'relative', marginRight: '16px', borderRadius: '4px', backgroundColor: 'rgba(255, 255, 255, 0.15)' }}>
                        <div style={{ padding: '0 8px', height: '100%', position: 'absolute', pointerEvents: 'none', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                            <SearchIcon />
                        </div>
                        <InputBase
                            placeholder="Search…"
                            inputProps={{ 'aria-label': 'search' }}
                            style={{ color: 'inherit', padding: '8px 8px 8px 0', paddingLeft: '48px', width: '100%' }}
                        />
                    </div>
                    <IconButton color="inherit" onClick={() => navigate('/add-inventory')}>
                        <AddIcon />
                    </IconButton>
                    <IconButton color="inherit">
                        <Badge badgeContent={4} color="secondary">
                            <NotificationsIcon />
                        </Badge>
                    </IconButton>
                    <IconButton
                        edge="end"
                        aria-label="account of current user"
                        aria-haspopup="true"
                        onClick={handleProfileMenuOpen}
                        color="inherit"
                    >
                        <Avatar sx={{ width: 32, height: 32 }}>
                            {user?.charAt(0).toUpperCase()}
                        </Avatar>
                    </IconButton>
                </Toolbar>
                <Box sx={{ bgcolor: 'primary.dark' }}>
                    <Tabs
                        value={location.pathname}
                        onChange={handleTabChange}
                        indicatorColor="secondary"
                        textColor="inherit"
                        variant="scrollable"
                        scrollButtons="auto"
                        aria-label="main navigation tabs"
                    >
                        {tabs.map((tab) => (
                            <Tab key={tab.value} label={tab.label} value={tab.value} />
                        ))}
                    </Tabs>
                </Box>
            </AppBar>
            <Menu
                anchorEl={anchorEl}
                anchorOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                }}
                keepMounted
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'right',
                }}
                open={Boolean(anchorEl)}
                onClose={handleMenuClose}
            >
                <MenuItem onClick={() => { handleMenuClose(); navigate('/profile'); }}>
                    <PersonIcon sx={{ mr: 1 }} /> Profile
                </MenuItem>
                <MenuItem onClick={handleLogout}>
                    <LogoutIcon sx={{ mr: 1 }} /> Logout
                </MenuItem>
            </Menu>
            <SideDrawer
                open={drawerOpen}
                onClose={handleDrawerToggle}
                username={user}
                userTitle="Manager"
            />
            {/* Add a toolbar to push content below the AppBar */}
            <Toolbar />
            <Toolbar /> {/* Second Toolbar for the tabs */}
        </>
    );
};

export default MainAppBar;