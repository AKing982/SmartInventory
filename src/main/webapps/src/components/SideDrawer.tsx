import React from 'react';
import {
    Drawer,
    List,
    ListItem,
    ListItemIcon,
    ListItemText,
    Box,
    Divider,
    Typography,
    Avatar
} from '@mui/material';
import {
    Assessment as AssessmentIcon,
    Inventory as InventoryIcon,
    Category as CategoryIcon,
    Warehouse as WarehouseIcon,
    People as PeopleIcon,
    Business as BusinessIcon,
    Settings as SettingsIcon
} from '@mui/icons-material';
import { useNavigate } from 'react-router-dom';

interface DrawerItem {
    text: string;
    icon: React.ReactElement;
    path: string;
}

interface SideDrawerProps {
    open: boolean;
    onClose: () => void;
    username: string | null;
    userTitle: string;
    menuItems: DrawerItem[];
    // onMenuItemSelect: (selectedPage: string) => void;
}

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

const SideDrawer: React.FC<SideDrawerProps> = ({ open,
                                                   onClose, username
                                                   ,
                                                   userTitle,
                                                   menuItems}) => {
    const navigate = useNavigate();

    const handleNavigation = (path: string, title: string) => {
        navigate(path);
        onClose();
        // onMenuItemSelect(title);
    }

    return (
        <Drawer anchor="left" open={open} onClose={onClose}>
            <Box
                sx={{ width: 250 }}
                role="presentation"
            >
                {/* User Info Section */}
                <Box sx={{ p: 2, bgcolor: 'primary.main', color: 'primary.contrastText' }}>
                    <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
                        <Avatar sx={{ mr: 2 }}>{username && username.charAt(0).toUpperCase()}</Avatar>
                        <Box>
                            <Typography variant="subtitle1">{username}</Typography>
                            <Typography variant="body2">{userTitle}</Typography>
                        </Box>
                    </Box>
                </Box>
                <Divider />
                {/* Menu Items */}
                <List>
                    {menuItems.map((item, index) => (
                        <ListItem button key={item.text} onClick={() => handleNavigation(item.path, item.text)}>
                            <ListItemIcon>{item.icon}</ListItemIcon>
                            <ListItemText primary={item.text} />
                        </ListItem>
                    ))}
                </List>
            </Box>
        </Drawer>
    );
}

export default SideDrawer;