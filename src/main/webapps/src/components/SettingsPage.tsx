import React, { useState } from 'react';
import {
    Box, Container, Grid, Paper, Typography, TextField, Switch, Button, Divider,
    List, ListItem, ListItemText, ListItemSecondaryAction, FormControlLabel,
    Select, MenuItem, InputLabel, FormControl, Snackbar, Alert, Tabs, Tab
} from '@mui/material';
import {
    Save as SaveIcon,
    Notifications as NotificationsIcon,
    Security as SecurityIcon,
    Language as LanguageIcon,
    ColorLens as ThemeIcon,
    Storage as DatabaseIcon,
    CreditCard as BillingIcon,
    Group as UserIcon,
    Memory as MemoryIcon,
    CloudQueue as CloudIcon,
    Build as MaintenanceIcon
} from '@mui/icons-material';
import MainAppBar from "./MainAppBar";
import {styled} from "@mui/material/styles";

interface SettingsState {
    accountName: string;
    email: string;
    language: string;
    darkMode: boolean;
    emailNotifications: boolean;
    smsNotifications: boolean;
    lowStockThreshold: number;
    autoBilling: boolean;
    dataRetentionDays: number;
    twoFactorAuth: boolean;
}

interface TabPanelProps {
    children?: React.ReactNode;
    index: number;
    value: number;
}

function TabPanel(props: TabPanelProps) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`vertical-tabpanel-${index}`}
            aria-labelledby={`vertical-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{ p: 3 }}>
                    {children}
                </Box>
            )}
        </div>
    );
}

const BackgroundContainer = styled('div')({
    minHeight: '120vh',
    display: 'flex',
    flexDirection: 'column',
    background: 'linear-gradient(120deg, #f6f7f9 0%, #e3e6ec 100%)',
});


const SettingsPage: React.FC = () => {
    const [settings, setSettings] = useState<SettingsState>({
        accountName: 'Admin User',
        email: 'admin@example.com',
        language: 'en',
        darkMode: false,
        emailNotifications: true,
        smsNotifications: false,
        lowStockThreshold: 10,
        autoBilling: true,
        dataRetentionDays: 365,
        twoFactorAuth: true,
    });

    const [tabValue, setTabValue] = useState(0);
    const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' as 'success' | 'error' });

    const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value, checked } = event.target;
        setSettings(prev => ({
            ...prev,
            [name]: value === 'on' ? checked : value
        }));
    };

    const handleSelectChange = (event: React.ChangeEvent<{ name?: string; value: unknown }>) => {
        const name = event.target.name as keyof SettingsState;
        setSettings(prev => ({
            ...prev,
            [name]: event.target.value
        }));
    };

    const handleSave = () => {
        // Here you would typically send the settings to your backend
        console.log('Saving settings:', settings);
        setSnackbar({ open: true, message: 'Settings saved successfully!', severity: 'success' });
    };

    const handleSnackbarClose = () => {
        setSnackbar({ ...snackbar, open: false });
    };

    const drawerItems = [
        { text: 'Dashboard', icon: <UserIcon />, path: '/admin/dashboard' },
        { text: 'Settings', icon: <MaintenanceIcon />, path: '/admin/settings' },
        // Add more menu items as needed
    ];


    const handleTabChange = (event: React.SyntheticEvent, newValue: number) => {
        setTabValue(newValue);
    };

    return (
        <BackgroundContainer>
            <MainAppBar title="Administrator Settings" drawerItems={drawerItems} />
            <Box component="main" sx={{ flexGrow: 1, p: 3 }}>
                <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
                    <Grid container spacing={2}>
                        <Grid item xs={12} md={3}>
                            <Tabs
                                orientation="vertical"
                                variant="scrollable"
                                value={tabValue}
                                onChange={handleTabChange}
                                aria-label="Settings tabs"
                                sx={{ borderRight: 1, borderColor: 'divider' }}
                            >
                                <Tab label="User Setup" icon={<UserIcon />} />
                                <Tab label="Database" icon={<DatabaseIcon />} />
                                <Tab label="Memory" icon={<MemoryIcon />} />
                                <Tab label="Security" icon={<SecurityIcon />} />
                                <Tab label="Notifications" icon={<NotificationsIcon />} />
                                <Tab label="Cloud Services" icon={<CloudIcon />} />
                                <Tab label="Maintenance" icon={<MaintenanceIcon />} />
                            </Tabs>
                        </Grid>
                        <Grid item xs={12} md={9}>
                            <Paper sx={{ p: 2 }}>
                                <TabPanel value={tabValue} index={0}>
                                    <Typography variant="h6" gutterBottom>User Setup</Typography>
                                    <TextField fullWidth label="Default User Role" margin="normal" />
                                    <TextField fullWidth label="Password Policy" margin="normal" />
                                    <TextField fullWidth label="Session Timeout (minutes)" type="number" margin="normal" />
                                </TabPanel>
                                <TabPanel value={tabValue} index={1}>
                                    <Typography variant="h6" gutterBottom>Database Settings</Typography>
                                    <TextField fullWidth label="Database Host" margin="normal" />
                                    <TextField fullWidth label="Database Name" margin="normal" />
                                    <TextField fullWidth label="Backup Frequency (hours)" type="number" margin="normal" />
                                </TabPanel>
                                <TabPanel value={tabValue} index={2}>
                                    <Typography variant="h6" gutterBottom>Memory Settings</Typography>
                                    <TextField fullWidth label="Cache Size (MB)" type="number" margin="normal" />
                                    <TextField fullWidth label="Max Connections" type="number" margin="normal" />
                                    <FormControlLabel control={<Switch />} label="Enable Memory Optimization" />
                                </TabPanel>
                                <TabPanel value={tabValue} index={3}>
                                    <Typography variant="h6" gutterBottom>Security Settings</Typography>
                                    <FormControlLabel control={<Switch />} label="Two-Factor Authentication" />
                                    <FormControlLabel control={<Switch />} label="SSL Encryption" />
                                    <TextField fullWidth label="API Key Expiration (days)" type="number" margin="normal" />
                                </TabPanel>
                                <TabPanel value={tabValue} index={4}>
                                    <Typography variant="h6" gutterBottom>Notification Settings</Typography>
                                    <FormControlLabel control={<Switch />} label="Email Notifications" />
                                    <FormControlLabel control={<Switch />} label="SMS Notifications" />
                                    <TextField fullWidth label="Low Stock Threshold" type="number" margin="normal" />
                                </TabPanel>
                                <TabPanel value={tabValue} index={5}>
                                    <Typography variant="h6" gutterBottom>Cloud Services</Typography>
                                    <TextField fullWidth label="Cloud Storage Provider" margin="normal" />
                                    <TextField fullWidth label="Cloud Region" margin="normal" />
                                    <FormControlLabel control={<Switch />} label="Enable Auto-scaling" />
                                </TabPanel>
                                <TabPanel value={tabValue} index={6}>
                                    <Typography variant="h6" gutterBottom>Maintenance Settings</Typography>
                                    <TextField fullWidth label="Maintenance Window Start" type="time" margin="normal" />
                                    <TextField fullWidth label="Maintenance Window End" type="time" margin="normal" />
                                    <FormControlLabel control={<Switch />} label="Enable Automatic Updates" />
                                </TabPanel>
                            </Paper>
                        </Grid>
                    </Grid>
                    <Box sx={{ display: 'flex', justifyContent: 'flex-end', mt: 2 }}>
                        <Button
                            variant="contained"
                            color="primary"
                            startIcon={<SaveIcon />}
                            onClick={handleSave}
                        >
                            Save All Settings
                        </Button>
                    </Box>
                </Container>
            </Box>
            <Snackbar open={snackbar.open} autoHideDuration={6000} onClose={handleSnackbarClose}>
                <Alert onClose={handleSnackbarClose} severity={snackbar.severity} sx={{ width: '100%' }}>
                    {snackbar.message}
                </Alert>
            </Snackbar>
        </BackgroundContainer>
    );
};

export default SettingsPage;