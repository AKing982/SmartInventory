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
    CardActions, styled,
} from '@mui/material';
import {
    Inventory as InventoryIcon,
    AddBox as AddBoxIcon,
    Assessment as AssessmentIcon,
    Settings as SettingsIcon,
    ExitToApp as LogoutIcon,
} from '@mui/icons-material';
import {useNavigate} from "react-router-dom";
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

    const user = localStorage.getItem('user');


    const handleLogout = () => {
        // Implement logout logic here
        console.log('Logging out...');
        localStorage.clear();
        navigate('/');

    };


    return (
        <BackgroundContainer>
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
                    <Button color="inherit" onClick={handleLogout} startIcon={<LogoutIcon />}>
                        Logout
                    </Button>
                </Toolbar>
            </AppBar>

            <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
                <Grid container spacing={3}>
                    <Grid item xs={12}>
                        <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
                            <Typography component="h1" variant="h4" color="primary" gutterBottom>
                                Welcome to SmartInventory, {user}!
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
                                    <Button size="small" onClick={feature.action}>
                                        {feature.title === 'Add New Items' ? 'Add Item' : 'Learn More'}
                                    </Button>
                                </CardActions>
                            </Card>
                        </Grid>
                    ))}
                </Grid>
            </Container>
        </BackgroundContainer>
    );
    // return (
    //     <Box sx={{ flexGrow: 1 }}>
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
    //             </Toolbar>
    //         </AppBar>
    //
    //         <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
    //             <Grid container spacing={3}>
    //                 <Grid item xs={12}>
    //                     <Paper sx={{ p: 2, display: 'flex', flexDirection: 'column' }}>
    //                         <Typography component="h1" variant="h4" color="primary" gutterBottom>
    //                             Welcome to SmartInventory Management System
    //                         </Typography>
    //                         <Typography variant="body1">
    //                             Efficiently manage your inventory, track stock levels, and generate insightful reports.
    //                         </Typography>
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
    //                                 <Button size="small">Learn More</Button>
    //                             </CardActions>
    //                         </Card>
    //                     </Grid>
    //                 ))}
    //             </Grid>
    //         </Container>
    //
    //         <Box component="footer" sx={{ bgcolor: 'background.paper', py: 6 }}>
    //             <Container maxWidth="lg">
    //                 <Typography variant="body2" color="text.secondary" align="center">
    //                     © {new Date().getFullYear()} SmartInventory. All rights reserved.
    //                 </Typography>
    //             </Container>
    //         </Box>
    //     </Box>
    // );
};

export default HomePage