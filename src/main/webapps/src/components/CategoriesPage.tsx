
import {
    Paper, Avatar,
    AppBar,
    Box,
    Button,
    Drawer,
    IconButton,
    List,
    ListItem,
    ListItemIcon, ListItemText,
    Toolbar,
    TableRow,
    Typography,
    Table,
    TableContainer,
    Grid, TableHead,

    TableBody, TextField, styled
} from "@mui/material";
import { Alert } from "@mui/material";
import {Container, Snackbar} from "@mui/material";
import React, {useEffect, useState} from "react";
import AddCircleOutlineIcon from '@mui/icons-material/AddCircleOutline';
import {useNavigate} from "react-router-dom";
import MenuIcon from "@mui/icons-material/Menu";
import DeleteIcon from "@mui/icons-material/Delete";
import {TableCell} from "@mui/material";
import {
    Logout as LogoutIcon,
    Category as CategoryIcon,
    Edit as EditIcon,
    Assessment as AssessmentIcon,
    Warehouse as WarehouseIcon,
    People as PeopleIcon,
    Business as BusinessIcon, Settings as SettingsIcon
} from '@mui/icons-material'
import MainAppBar from "./MainAppBar";
import InventoryIcon from "@mui/icons-material/Inventory";


interface ProductCategory
{
    id: number;
    name: string;
    description?: string;
    createdAt: string;
}

const BackgroundContainer = styled('div')({
    minHeight: '120vh',
    display: 'flex',
    flexDirection: 'column',
    background: 'linear-gradient(120deg, #f6f7f9 0%, #e3e6ec 100%)',
});

// Styled component for the content area
const ContentContainer = styled(Box)({
    flexGrow: 1,
    padding: '24px 0',
});

const CategoriesPage: React.FC = () => {
    const [formData, setFormData] = useState<Omit<ProductCategory, 'id' | 'createdAt'>>({
        name: '',
        description: ''
    });
    const navigate = useNavigate();
    const [openSnackbar, setOpenSnackbar] = useState<boolean>(false);
    const [drawerOpen, setDrawerOpen] = useState(false);
    const [categories, setCategories] = useState<ProductCategory[]>([]);
    const [recentCategories, setRecentCategories] = useState<ProductCategory[]>([]);
    const user = localStorage.getItem('user');

    useEffect(() => {
        // Fetch categories from API in a real application
        setCategories([
            { id: 1, name: 'Electronics', description: 'Electronic devices and accessories', createdAt: '2023-06-01' },
            { id: 2, name: 'Clothing', description: 'Apparel and fashion items', createdAt: '2023-06-02' },
            { id: 3, name: 'Books', description: 'Books and publications', createdAt: '2023-06-03' },
        ]);
        setRecentCategories([
            { id: 2, name: 'Clothing', description: 'Apparel and fashion items', createdAt: '2023-06-02' },
            { id: 3, name: 'Books', description: 'Books and publications', createdAt: '2023-06-03' },
        ]);
    }, []);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        // Here you would typically send the data to your backend
        console.log('Submitting category:', formData);
        // Simulating a successful submission
        const newCategory: ProductCategory = {
            id: categories.length + 1,
            ...formData,
            createdAt: new Date().toISOString().split('T')[0]
        };
        setCategories([...categories, newCategory]);
        setRecentCategories([newCategory, ...recentCategories.slice(0, 4)]);
        setOpenSnackbar(true);
        setFormData({ name: '', description: '' });
    };

    const handleCloseSnackbar = (event?: React.SyntheticEvent | Event, reason?: string) => {
        if (reason === 'clickaway') {
            return;
        }
        setOpenSnackbar(false);
    };

    const handleDrawerToggle = () => {
        setDrawerOpen(!drawerOpen);
    };

    const handleLogout = () => {
        localStorage.clear();
        navigate('/');
    };

    const menuItems = [
        { title: 'Dashboard', icon: <CategoryIcon />, path: '/dashboard' },
        { title: 'Inventory', icon: <CategoryIcon />, path: '/inventory' },
        { title: 'Categories', icon: <CategoryIcon />, path: '/categories' },
        // Add more menu items as needed
    ];

    const menuItems2 = [
        { text: 'Dashboard', icon: <AssessmentIcon />, path: '/home' },
        { text: 'Inventory', icon: <InventoryIcon />, path: '/inventory' },
        { text: 'Categories', icon: <CategoryIcon />, path: '/categories' },
        { text: 'Warehouses', icon: <WarehouseIcon />, path: '/warehouses' },
        { text: 'Contacts', icon: <PeopleIcon />, path: '/contacts' },
        { text: 'Departments', icon: <BusinessIcon />, path: '/departments' },
        { text: 'Employees', icon: <PeopleIcon />, path: '/employees' },
        { text: 'Customers', icon: <PeopleIcon />, path: '/customers' },
        { text: 'Reports', icon: <AssessmentIcon />, path: '/reports' },
        { text: 'Settings', icon: <SettingsIcon />, path: '/settings' },
    ];


    return (
        <BackgroundContainer>
            <MainAppBar title="Product Categories" drawerItems={menuItems2}/>
            <Drawer
                anchor="left"
                open={drawerOpen}
                onClose={handleDrawerToggle}
            >
                <Box sx={{ width: 250 }} role="presentation" onClick={handleDrawerToggle}>
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
            <Box
                component="main"
                sx={{
                    flexGrow: 1,
                    p: 3,
                    mt: ['48px', '56px', '64px'],
                    backgroundColor: '#f5f5f5',
                    minHeight: '100vh'
                }}
            >
                <Container maxWidth="xl">
                    <Grid container spacing={3}>
                        <Grid item xs={12} md={4}>
                            <Paper sx={{ p: 2 }}>
                                <Typography variant="h5" gutterBottom>
                                    Add New Category
                                </Typography>
                                <form onSubmit={handleSubmit}>
                                    <TextField
                                        fullWidth
                                        label="Category Name"
                                        name="name"
                                        value={formData.name}
                                        onChange={handleInputChange}
                                        required
                                        margin="normal"
                                    />
                                    <TextField
                                        fullWidth
                                        label="Description"
                                        name="description"
                                        value={formData.description}
                                        onChange={handleInputChange}
                                        multiline
                                        rows={3}
                                        margin="normal"
                                    />
                                    <Button
                                        type="submit"
                                        variant="contained"
                                        color="primary"
                                        fullWidth
                                        startIcon={<AddCircleOutlineIcon />}
                                        sx={{ mt: 2 }}
                                    >
                                        Add Category
                                    </Button>
                                </form>
                            </Paper>
                        </Grid>

                        <Grid item xs={12} md={8}>
                            <Paper sx={{ p: 2 }}>
                                <Typography variant="h5" gutterBottom>
                                    All Categories
                                </Typography>
                                <TableContainer>
                                    <Table>
                                        <TableHead>
                                            <TableRow>
                                                <TableCell>Name</TableCell>
                                                <TableCell>Description</TableCell>
                                                <TableCell>Created At</TableCell>
                                                <TableCell>Actions</TableCell>
                                            </TableRow>
                                        </TableHead>
                                        <TableBody>
                                            {categories.map((category) => (
                                                <TableRow key={category.id}>
                                                    <TableCell>{category.name}</TableCell>
                                                    <TableCell>{category.description}</TableCell>
                                                    <TableCell>{category.createdAt}</TableCell>
                                                    <TableCell>
                                                        <IconButton size="small">
                                                            <EditIcon />
                                                        </IconButton>
                                                        <IconButton size="small">
                                                            <DeleteIcon />
                                                        </IconButton>
                                                    </TableCell>
                                                </TableRow>
                                            ))}
                                        </TableBody>
                                    </Table>
                                </TableContainer>
                            </Paper>
                        </Grid>
                    </Grid>
                </Container>
                <Snackbar open={openSnackbar} autoHideDuration={6000} onClose={handleCloseSnackbar}>
                    <Alert onClose={handleCloseSnackbar} severity="success" sx={{ width: '100%' }}>
                        Category added successfully!
                    </Alert>
                </Snackbar>
            </Box>
        </BackgroundContainer>
    );
}

export default CategoriesPage;