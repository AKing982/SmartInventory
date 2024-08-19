import React, { useState, useEffect } from 'react';
import {
    Box,
    Typography,
    Paper,
    TextField,
    Button,
    Grid,
    Switch,
    FormControlLabel,
    Avatar,
    Divider,
    Snackbar,
    Alert,
    Select,
    MenuItem,
    InputLabel,
    FormControl
} from '@mui/material';
import { styled } from '@mui/material/styles';
import MainAppBar from "./MainAppBar";
import InventoryIcon from '@mui/icons-material/Inventory';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import HistoryIcon from '@mui/icons-material/History';
import PersonIcon from '@mui/icons-material/Person';
import SaveIcon from '@mui/icons-material/Save';

interface UserProfilePage {
    id: string;
    firstName: string;
    lastName: string;
    email: string;
    phoneNumber: string;
    jobTitle: string;
    department: string;
    avatar: string;
    notifications: boolean;
    twoFactorAuth: boolean;
    defaultCurrency: string;
    language: string;
    theme: 'light' | 'dark';
    lowStockThreshold: number;
}

const BackgroundContainer = styled('div')({
    minHeight: '100vh',
    display: 'flex',
    flexDirection: 'column',
    background: 'linear-gradient(120deg, #f6f7f9 0%, #e3e6ec 100%)',
});

const StyledPaper = styled(Paper)(({ theme }) => ({
    padding: theme.spacing(3),
    margin: theme.spacing(2, 0),
    backgroundColor: '#f8f9fa',
    boxShadow: '0 4px 6px rgba(0, 0, 0, 0.1)',
}));

const UserProfilePage: React.FC = () => {
    const [profile, setProfile] = useState<UserProfilePage>({
        id: '1',
        firstName: 'John',
        lastName: 'Doe',
        email: 'john.doe@example.com',
        phoneNumber: '123-456-7890',
        jobTitle: 'Inventory Manager',
        department: 'Operations',
        avatar: 'https://via.placeholder.com/150',
        notifications: true,
        twoFactorAuth: false,
        defaultCurrency: 'USD',
        language: 'English',
        theme: 'light',
        lowStockThreshold: 10
    });

    const [isEditing, setIsEditing] = useState(false);
    const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' as 'success' | 'error' });

    const menuItems = [
        { text: 'Product Catalog', icon: <InventoryIcon />, path: '/catalog' },
        { text: 'Place Order', icon: <ShoppingCartIcon />, path: '/order-placement' },
        { text: 'Order History', icon: <HistoryIcon />, path: '/order-history' },
        { text: 'Update Profile', icon: <PersonIcon />, path: '/profile' },
    ];

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target;
        setProfile(prev => ({ ...prev, [name]: value }));
    };

    const handleSwitchChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, checked } = e.target;
        setProfile(prev => ({ ...prev, [name]: checked }));
    };

    const handleSelectChange = (e: React.ChangeEvent<{ name?: string; value: unknown }>) => {
        const { name, value } = e.target;
        setProfile(prev => ({ ...prev, [name as string]: value }));
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        // Here you would typically send the updated profile to your backend
        console.log('Updated profile:', profile);
        setSnackbar({ open: true, message: 'Profile updated successfully!', severity: 'success' });
        setIsEditing(false);
    };

    return (
        <BackgroundContainer>
            <MainAppBar title="User Profile" drawerItems={menuItems} />
            <Box sx={{ width: '100%', p: 3 }}>
                <Typography variant="h4" sx={{ mb: 2 }}>
                    User Profile
                </Typography>
                <form onSubmit={handleSubmit}>
                    <StyledPaper>
                        <Grid container spacing={3}>
                            <Grid item xs={12} md={4} sx={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                                <Avatar
                                    src={profile.avatar}
                                    sx={{ width: 150, height: 150, mb: 2 }}
                                />
                                <Typography variant="h6">{`${profile.firstName} ${profile.lastName}`}</Typography>
                                <Typography variant="body1" color="textSecondary">{profile.jobTitle}</Typography>
                                <Typography variant="body2" color="textSecondary">{profile.department}</Typography>
                            </Grid>
                            <Grid item xs={12} md={8}>
                                <Typography variant="h6" sx={{ mb: 2 }}>Personal Information</Typography>
                                <Grid container spacing={2}>
                                    <Grid item xs={12} sm={6}>
                                        <TextField
                                            fullWidth
                                            label="First Name"
                                            name="firstName"
                                            value={profile.firstName}
                                            onChange={handleInputChange}
                                            disabled={!isEditing}
                                        />
                                    </Grid>
                                    <Grid item xs={12} sm={6}>
                                        <TextField
                                            fullWidth
                                            label="Last Name"
                                            name="lastName"
                                            value={profile.lastName}
                                            onChange={handleInputChange}
                                            disabled={!isEditing}
                                        />
                                    </Grid>
                                    <Grid item xs={12} sm={6}>
                                        <TextField
                                            fullWidth
                                            label="Email"
                                            name="email"
                                            value={profile.email}
                                            onChange={handleInputChange}
                                            disabled={!isEditing}
                                        />
                                    </Grid>
                                    <Grid item xs={12} sm={6}>
                                        <TextField
                                            fullWidth
                                            label="Phone Number"
                                            name="phoneNumber"
                                            value={profile.phoneNumber}
                                            onChange={handleInputChange}
                                            disabled={!isEditing}
                                        />
                                    </Grid>
                                    <Grid item xs={12} sm={6}>
                                        <TextField
                                            fullWidth
                                            label="Job Title"
                                            name="jobTitle"
                                            value={profile.jobTitle}
                                            onChange={handleInputChange}
                                            disabled={!isEditing}
                                        />
                                    </Grid>
                                    <Grid item xs={12} sm={6}>
                                        <TextField
                                            fullWidth
                                            label="Department"
                                            name="department"
                                            value={profile.department}
                                            onChange={handleInputChange}
                                            disabled={!isEditing}
                                        />
                                    </Grid>
                                </Grid>
                            </Grid>
                        </Grid>
                        <Divider sx={{ my: 3 }} />
                        <Typography variant="h6" sx={{ mb: 2 }}>Account Settings</Typography>
                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={6}>
                                <FormControlLabel
                                    control={
                                        <Switch
                                            checked={profile.notifications}
                                            onChange={handleSwitchChange}
                                            name="notifications"
                                            disabled={!isEditing}
                                        />
                                    }
                                    label="Receive Notifications"
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <FormControlLabel
                                    control={
                                        <Switch
                                            checked={profile.twoFactorAuth}
                                            onChange={handleSwitchChange}
                                            name="twoFactorAuth"
                                            disabled={!isEditing}
                                        />
                                    }
                                    label="Two-Factor Authentication"
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <FormControl fullWidth disabled={!isEditing}>
                                    <InputLabel>Default Currency</InputLabel>
                                    <Select
                                        value={profile.defaultCurrency}
                                        name="defaultCurrency"
                                    >
                                        <MenuItem value="USD">USD</MenuItem>
                                        <MenuItem value="EUR">EUR</MenuItem>
                                        <MenuItem value="GBP">GBP</MenuItem>
                                    </Select>
                                </FormControl>
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <FormControl fullWidth disabled={!isEditing}>
                                    <InputLabel>Language</InputLabel>
                                    <Select
                                        value={profile.language}
                                        name="language"
                                    >
                                        <MenuItem value="English">English</MenuItem>
                                        <MenuItem value="Spanish">Spanish</MenuItem>
                                        <MenuItem value="French">French</MenuItem>
                                    </Select>
                                </FormControl>
                            </Grid>
                        </Grid>
                        <Divider sx={{ my: 3 }} />
                        <Typography variant="h6" sx={{ mb: 2 }}>Inventory Preferences</Typography>
                        <Grid container spacing={2}>
                            <Grid item xs={12} sm={6}>
                                <TextField
                                    fullWidth
                                    label="Low Stock Threshold"
                                    name="lowStockThreshold"
                                    type="number"
                                    value={profile.lowStockThreshold}
                                    onChange={handleInputChange}
                                    disabled={!isEditing}
                                />
                            </Grid>
                            <Grid item xs={12} sm={6}>
                                <FormControl fullWidth disabled={!isEditing}>
                                    <InputLabel>Theme</InputLabel>
                                    <Select
                                        value={profile.theme}

                                        name="theme"
                                    >
                                        <MenuItem value="light">Light</MenuItem>
                                        <MenuItem value="dark">Dark</MenuItem>
                                    </Select>
                                </FormControl>
                            </Grid>
                        </Grid>
                        <Box sx={{ mt: 3, display: 'flex', justifyContent: 'flex-end' }}>
                            {!isEditing ? (
                                <Button
                                    variant="contained"
                                    color="primary"
                                    onClick={() => setIsEditing(true)}
                                >
                                    Edit Profile
                                </Button>
                            ) : (
                                <>
                                    <Button
                                        variant="outlined"
                                        color="secondary"
                                        onClick={() => setIsEditing(false)}
                                        sx={{ mr: 2 }}
                                    >
                                        Cancel
                                    </Button>
                                    <Button
                                        type="submit"
                                        variant="contained"
                                        color="primary"
                                        startIcon={<SaveIcon />}
                                    >
                                        Save Changes
                                    </Button>
                                </>
                            )}
                        </Box>
                    </StyledPaper>
                </form>
            </Box>
            <Snackbar
                open={snackbar.open}
                autoHideDuration={6000}
                onClose={() => setSnackbar({ ...snackbar, open: false })}
            >
                <Alert onClose={() => setSnackbar({ ...snackbar, open: false })} severity={snackbar.severity} sx={{ width: '100%' }}>
                    {snackbar.message}
                </Alert>
            </Snackbar>
        </BackgroundContainer>
    );
};

export default UserProfilePage;