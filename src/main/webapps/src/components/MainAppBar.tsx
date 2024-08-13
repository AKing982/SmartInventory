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
import SideDrawer from "./SideDrawer";

interface MainAppBarProps {
    title: string;
}

const MainAppBar: React.FC<MainAppBarProps> = ({ title }) => {
    const navigate = useNavigate();
    const [drawerOpen, setDrawerOpen] = useState<boolean>(false);
    const user = localStorage.getItem('user');

    const handleDrawerToggle = () => {
        setDrawerOpen(!drawerOpen);
    };

    const handleLogout = () => {
        localStorage.clear();
        navigate('/');
    };

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
            <SideDrawer open={drawerOpen}
                        onClose={handleDrawerToggle}
                        username={user}
                        userTitle="Manager"/>
        </>
    );
};

export default MainAppBar;