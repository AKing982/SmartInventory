import {useLocation, useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";

import NotificationsIcon from "@mui/icons-material/Notifications";
import SideDrawer from "./SideDrawer";
import {Avatar, Badge, Box, InputBase, Menu, MenuItem, Tab, Tabs} from "@mui/material";
import {AppBar, IconButton, Toolbar,
    Typography,
  } from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import SearchIcon from "@mui/icons-material/Search";
import {
    Add as AddIcon,
    Person as PersonIcon,
    Logout as LogoutIcon
} from '@mui/icons-material';
import DynamicTabs from "./DynamicTabs";

interface MainAppBarProps {
    title: string;
}

interface TabConfig {
    label: string;
    value: string;
}


const MainAppBar: React.FC<MainAppBarProps> = ({ title }) => {
    const navigate = useNavigate();
    const location = useLocation();
    const [drawerOpen, setDrawerOpen] = useState<boolean>(false);
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const user = localStorage.getItem('user');
    const [selectedSection, setSelectedSection] = useState<string>('dashboard');
    const [selectedTabs, setSelectedTabs] = useState<TabConfig[]>([]);

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
        console.log('Changing tabs');
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

    const tabConfigs: Record<string, TabConfig[]> = {
        dashboard: [
            { label: 'User Performance', value: '/dashboard/user-performance' },
            { label: 'Sales Overview', value: '/dashboard/sales-overview' },
            { label: 'Inventory Status', value: '/dashboard/inventory-status' },
            { label: 'Financial Summary', value: '/dashboard/financial-summary' },
        ],
        inventory: [
            { label: 'New Inventory', value: '/inventory/new' },
            { label: 'Generate Inventory Report', value: '/inventory/report' },
            { label: 'Stock Levels', value: '/inventory/stock-levels' },
            { label: 'Inventory Valuation', value: '/inventory/valuation' },
        ],
        categories: [
            { label: 'View Category Groups', value: '/categories/groups' },
            { label: 'View Category History', value: '/categories/history' },
            { label: 'Manage Categories', value: '/categories/manage' },
        ],
        orders: [
            { label: 'New Orders', value: '/orders/new' },
            { label: 'Order History', value: '/orders/history' },
            { label: 'Returns', value: '/orders/returns' },
        ],
        customers: [
            { label: 'Customer List', value: '/customers/list' },
            { label: 'Customer Analytics', value: '/customers/analytics' },
            { label: 'Loyalty Program', value: '/customers/loyalty' },
        ],
        reports: [
            { label: 'Sales Reports', value: '/reports/sales' },
            { label: 'Inventory Reports', value: '/reports/inventory' },
            { label: 'Financial Reports', value: '/reports/financial' },
        ],
    };

    const handleSectionChange = (_event: React.SyntheticEvent, newValue: string) => {
        setSelectedSection(newValue.split('/')[1]);
        navigate(newValue);
    };

    // useEffect(() => {
    //     const currentSection = location.pathname.split('/')[1] || 'dashboard';
    //     setSelectedSection(currentSection);
    //     const newTabs = tabConfigs[currentSection] || [];
    //     setSelectedTabs(newTabs);
    // }, [location]);

    useEffect(() => {
        const currentSection = location.pathname.split('/')[1] || 'dashboard';
        setSelectedSection(currentSection);
    }, [location]);

    const getCurrentTabs = (): TabConfig[] => {
        return tabConfigs[selectedSection] || [];
    }

    const handleTabSelect = (selectedPage: string) : TabConfig[] | null => {
        const pageKey = selectedPage.toLowerCase();
        if(tabConfigs[pageKey]){
            const tabs = tabConfigs[pageKey];
            console.log('Tabs: ', tabs);
            return tabs;
        }else{
            return null;
        }
    }

    const handleMenuItemSelect = (selectedItem: string) => {
        setSelectedSection(selectedItem.toLowerCase());
        const newTabs = tabConfigs[selectedItem.toLowerCase()] || [];
        setSelectedTabs(newTabs);
        if (newTabs.length > 0) {
            navigate(newTabs[0].value);
        }
        setDrawerOpen(false);
    };


    // @ts-ignore
    return (
        <>
            <AppBar position="fixed">
                <Toolbar>
                    <IconButton
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
                    {/*<Tabs*/}
                    {/*    value={location.pathname}*/}
                    {/*    onChange={handleTabChange}*/}
                    {/*    indicatorColor="secondary"*/}
                    {/*    textColor="inherit"*/}
                    {/*    variant="scrollable"*/}
                    {/*    scrollButtons="auto"*/}
                    {/*    aria-label="main navigation tabs"*/}
                    {/*>*/}
                    {/*    {tabs.map((tab) => (*/}
                    {/*        <Tab key={tab.label} label={tab.label} value={tab.value} />*/}
                    {/*    ))}*/}
                    {/*</Tabs>*/}
                    <DynamicTabs selectedMenuItem={selectedSection}/>
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
                userTitle="Manager"/>
            {/* Add a toolbar to push content below the AppBar */}
            <Toolbar />
            <Toolbar /> {/* Second Toolbar for the tabs */}
        </>
    );
};
export default MainAppBar;