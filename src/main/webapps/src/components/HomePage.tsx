import React from 'react';
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
} from '@mui/material';
import {
    Inventory as InventoryIcon,
    AddBox as AddBoxIcon,
    Assessment as AssessmentIcon,
    Settings as SettingsIcon,
} from '@mui/icons-material';

const HomePage: React.FC = () => {
    const features = [
        { title: 'Manage Inventory', icon: <InventoryIcon />, description: 'Track and manage your inventory in real-time.' },
        { title: 'Add New Items', icon: <AddBoxIcon />, description: 'Easily add new items to your inventory.' },
        { title: 'Generate Reports', icon: <AssessmentIcon />, description: 'Create detailed reports and analytics.' },
        { title: 'System Settings', icon: <SettingsIcon />, description: 'Customize the system to fit your needs.' },
    ];

    return (
        <Box sx={{ flexGrow: 1 }}>
            <AppBar position="static">
                <Toolbar>
                    <InventoryIcon sx={{ mr: 2 }} />
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        SmartInventory Management System
                    </Typography>
                    <Button color="inherit">Dashboard</Button>
                    <Button color="inherit">Inventory</Button>
                    <Button color="inherit">Reports</Button>
                    <Button color="inherit">Profile</Button>
                </Toolbar>
            </AppBar>

            <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
                            <Typography component="h1" variant="h4" color="primary" gutterBottom>
                                Welcome to SmartInventory Management System
                            </Typography>
                            <Typography variant="body1">
                                Efficiently manage your inventory, track stock levels, and generate insightful reports.
                            </Typography>
                        </Paper>
                    </Grid>
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
                                    <Button size="small">Learn More</Button>
                                </CardActions>
                            </Card>
                        </Grid>
                    ))}
                </Grid>
            </Container>

            <Box component="footer" sx={{ bgcolor: 'background.paper', py: 6 }}>
                <Container maxWidth="lg">
                    <Typography variant="body2" color="text.secondary" align="center">
                        © {new Date().getFullYear()} SmartInventory. All rights reserved.
                    </Typography>
                </Container>
            </Box>
        </Box>
    );
};

export default HomePage