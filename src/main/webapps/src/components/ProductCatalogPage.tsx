import React, {useState, useEffect, useMemo} from 'react';
import {
    Box,
    Typography,
    Paper,
    Grid,
    Card,
    CardContent,
    CardMedia,
    CardActions,
    Button,
    TextField,
    Select,
    MenuItem,
    FormControl,
    InputLabel,
    Pagination,
    Chip,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow, Slider, Checkbox, IconButton, Menu
} from '@mui/material';
import { styled } from '@mui/material/styles';
import MainAppBar from "./MainAppBar";
import InventoryIcon from '@mui/icons-material/Inventory';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import HistoryIcon from '@mui/icons-material/History';
import PersonIcon from '@mui/icons-material/Person';
import SearchIcon from '@mui/icons-material/Search';
import SortIcon from '@mui/icons-material/Sort';
import MoreVertIcon from '@mui/icons-material/MoreVert';
import FileDownloadIcon from "@mui/icons-material/FileDownload";
import CompareArrowsIcon from '@mui/icons-material/CompareArrows';

interface Product {
    id: number;
    name: string;
    description: string;
    category: string;
    price: number;
    quantity: number;
    sku: string;
    image: string;
    status: 'In Stock' | 'Low Stock' | 'Out of Stock';
    reorderPoint: number;
    tags: string[]
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

const ProductCatalogPage: React.FC = () => {
    const [products, setProducts] = useState<Product[]>([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [categoryFilter, setCategoryFilter] = useState('All');
    const [sortBy, setSortBy] = useState<keyof Product>('name');
    const [sortDirection, setSortDirection] = useState<'asc' | 'desc'>('asc');
    const [page, setPage] = useState(1);
    const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
    const [isDetailDialogOpen, setIsDetailDialogOpen] = useState(false);
    const [priceRange, setPriceRange] = useState<number[]>([0, 1000]);
    const [selectedProducts, setSelectedProducts] = useState<number[]>([]);
    const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
    const [currentProductId, setCurrentProductId] = useState<number | null>(null);

    const productsPerPage = 12;

    const menuItems = [
        { text: 'Product Catalog', icon: <InventoryIcon />, path: '/catalog' },
        { text: 'Place Order', icon: <ShoppingCartIcon />, path: '/order-placement' },
        { text: 'Order History', icon: <HistoryIcon />, path: '/order-history' },
        { text: 'Update Profile', icon: <PersonIcon />, path: '/profile' },
    ];

    useEffect(() => {
        // Fetch products from an API or load from local storage
        const dummyProducts: Product[] = [
            {
                id: 1,
                name: "Ergonomic Chair",
                description: "Comfortable office chair with lumbar support",
                category: "Furniture",
                price: 199.99,
                quantity: 50,
                sku: "CHAIR-001",
                image: "https://via.placeholder.com/150",
                status: "In Stock",
                reorderPoint: 20,
                tags: ["office", "comfort"]
            },
            {
                id: 2,
                name: "Wireless Mouse",
                description: "Bluetooth-enabled wireless mouse",
                category: "Electronics",
                price: 29.99,
                quantity: 100,
                sku: "MOUSE-001",
                image: "https://via.placeholder.com/150",
                status: "In Stock",
                reorderPoint: 30,
                tags: ["computer", "wireless"]
            },
            {
                id: 3,
                name: "LED Desk Lamp",
                description: "Adjustable LED lamp with multiple brightness levels",
                category: "Lighting",
                price: 39.99,
                quantity: 75,
                sku: "LAMP-001",
                image: "https://via.placeholder.com/150",
                status: "In Stock",
                reorderPoint: 25,
                tags: ["office", "lighting"]
            },
            {
                id: 4,
                name: "Standing Desk",
                description: "Adjustable height standing desk for ergonomic working",
                category: "Furniture",
                price: 299.99,
                quantity: 30,
                sku: "DESK-001",
                image: "https://via.placeholder.com/150",
                status: "Low Stock",
                reorderPoint: 15,
                tags: ["office", "ergonomic"]
            },
            {
                id: 5,
                name: "Mechanical Keyboard",
                description: "High-performance mechanical keyboard for typing enthusiasts",
                category: "Electronics",
                price: 89.99,
                quantity: 5,
                sku: "KEY-001",
                image: "https://via.placeholder.com/150",
                status: "Low Stock",
                reorderPoint: 10,
                tags: ["computer", "typing"]
            },
            {
                id: 6,
                name: "Monitor Stand",
                description: "Adjustable monitor stand for better posture and desk organization",
                category: "Furniture",
                price: 49.99,
                quantity: 0,
                sku: "STAND-001",
                image: "https://via.placeholder.com/150",
                status: "Out of Stock",
                reorderPoint: 15,
                tags: ["office", "ergonomic"]
            },
            {
                id: 7,
                name: "Wireless Headphones",
                description: "Noise-cancelling wireless headphones for immersive audio",
                category: "Electronics",
                price: 159.99,
                quantity: 40,
                sku: "AUDIO-001",
                image: "https://via.placeholder.com/150",
                status: "In Stock",
                reorderPoint: 20,
                tags: ["audio", "wireless"]
            },
            {
                id: 8,
                name: "Desk Organizer",
                description: "Multi-compartment desk organizer for office supplies",
                category: "Office Supplies",
                price: 24.99,
                quantity: 80,
                sku: "ORG-001",
                image: "https://via.placeholder.com/150",
                status: "In Stock",
                reorderPoint: 30,
                tags: ["office", "organization"]
            },
            {
                id: 9,
                name: "Ergonomic Mouse Pad",
                description: "Comfortable mouse pad with wrist support",
                category: "Office Supplies",
                price: 19.99,
                quantity: 12,
                sku: "PAD-001",
                image: "https://via.placeholder.com/150",
                status: "Low Stock",
                reorderPoint: 25,
                tags: ["computer", "ergonomic"]
            },
            {
                id: 10,
                name: "4K Monitor",
                description: "High-resolution 4K monitor for crisp visuals",
                category: "Electronics",
                price: 349.99,
                quantity: 15,
                sku: "MON-001",
                image: "https://via.placeholder.com/150",
                status: "In Stock",
                reorderPoint: 10,
                tags: ["computer", "display"]
            }
        ];
        setProducts(dummyProducts);
    }, []);

    const filteredProducts = useMemo(() => {
        return products.filter(product =>
            (product.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                product.sku.toLowerCase().includes(searchTerm.toLowerCase())) &&
            (categoryFilter === 'All' || product.category === categoryFilter) &&
            (product.price >= priceRange[0] && product.price <= priceRange[1])
        ).sort((a, b) => {
            if (a[sortBy] < b[sortBy]) return sortDirection === 'asc' ? -1 : 1;
            if (a[sortBy] > b[sortBy]) return sortDirection === 'asc' ? 1 : -1;
            return 0;
        });
    }, [products, searchTerm, categoryFilter, sortBy, sortDirection, priceRange]);

    const paginatedProducts = filteredProducts.slice((page - 1) * productsPerPage, page * productsPerPage);

    const categories = useMemo(() => {
        const categorySet = new Set<string>();
        products.forEach(product => categorySet.add(product.category));
        return ['All', ...Array.from(categorySet)];
    }, [products]);


    const handleSort = (column: keyof Product) => {
        if (sortBy === column) {
            setSortDirection(prev => prev === 'asc' ? 'desc' : 'asc');
        } else {
            setSortBy(column);
            setSortDirection('asc');
        }
    };

    const handleViewDetails = (product: Product) => {
        setSelectedProduct(product);
        setIsDetailDialogOpen(true);
    };

    const getStatusColor = (status: Product['status']) => {
        switch (status) {
            case 'In Stock': return 'success';
            case 'Low Stock': return 'warning';
            case 'Out of Stock': return 'error';
            default: return 'default';
        }
    };

    const handleUpdateStock = () => {
        // Implement update stock logic
        console.log(`Update stock for product ${currentProductId}`);
        handleCloseMenu();
    };

    const handleQuickAction = (event: React.MouseEvent<HTMLButtonElement>, productId: number) => {
        setAnchorEl(event.currentTarget);
        setCurrentProductId(productId);
    };

    const handleCloseMenu = () => {
        setAnchorEl(null);
        setCurrentProductId(null);
    };

    const handleAddToOrder = () => {
        // Implement add to order logic
        console.log(`Add product ${currentProductId} to order`);
        handleCloseMenu();
    };


    const handleExport = () => {
        // Implement export logic
        console.log('Exporting product list');
    };


    const handleCompare = () => {
        // Implement compare logic
        console.log(`Comparing products: ${selectedProducts.join(', ')}`);
    };

    const handleCheckboxChange = (productId: number) => {
        setSelectedProducts(prev =>
            prev.includes(productId)
                ? prev.filter(id => id !== productId)
                : [...prev, productId]
        );
    };


    return (
        <BackgroundContainer>
            <MainAppBar title="Product Catalog" drawerItems={menuItems} />
            <Box sx={{ width: '100%', p: 3 }}>
                <Typography variant="h4" sx={{ mb: 2 }}>
                    Product Catalog
                </Typography>
                <StyledPaper>
                    <Box sx={{ mb: 2, display: 'flex', justifyContent: 'space-between', flexWrap: 'wrap', gap: 2 }}>
                        <TextField
                            label="Search Products"
                            variant="outlined"
                            size="small"
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            InputProps={{
                                startAdornment: <SearchIcon />,
                            }}
                        />
                        <FormControl variant="outlined" size="small" sx={{ minWidth: 120 }}>
                            <InputLabel>Category</InputLabel>
                            <Select
                                value={categoryFilter}
                                onChange={(e) => setCategoryFilter(e.target.value)}
                                label="Category"
                            >
                                {categories.map(category => (
                                    <MenuItem key={category} value={category}>{category}</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                        <FormControl variant="outlined" size="small" sx={{ minWidth: 120 }}>
                            <InputLabel>Sort By</InputLabel>
                            <Select
                                value={sortBy}
                                onChange={(e) => handleSort(e.target.value as keyof Product)}
                                label="Sort By"
                            >
                                <MenuItem value="name">Name</MenuItem>
                                <MenuItem value="price">Price</MenuItem>
                                <MenuItem value="quantity">Quantity</MenuItem>
                            </Select>
                        </FormControl>
                        <Box sx={{ width: 300 }}>
                            <Typography gutterBottom>Price Range</Typography>
                            <Slider
                                value={priceRange}
                                onChange={(_, newValue) => setPriceRange(newValue as number[])}
                                valueLabelDisplay="auto"
                                min={0}
                                max={1000}
                            />
                        </Box>
                    </Box>
                    <Box sx={{ mb: 2, display: 'flex', justifyContent: 'space-between' }}>
                        <Button
                            variant="outlined"
                            startIcon={<FileDownloadIcon />}
                            onClick={handleExport}
                        >
                            Export
                        </Button>
                        <Button
                            variant="outlined"
                            startIcon={<CompareArrowsIcon />}
                            onClick={handleCompare}
                            disabled={selectedProducts.length < 2}
                        >
                            Compare Selected
                        </Button>
                    </Box>
                    <Grid container spacing={3}>
                        {paginatedProducts.map(product => (
                            <Grid item xs={12} sm={6} md={4} lg={3} key={product.id}>
                                <Card>
                                    <CardMedia
                                        component="img"
                                        height="140"
                                        image={product.image}
                                        alt={product.name}
                                    />
                                    <CardContent>
                                        <Typography gutterBottom variant="h6" component="div">
                                            {product.name}
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary">
                                            SKU: {product.sku}
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary">
                                            Price: ${product.price.toFixed(2)}
                                        </Typography>
                                        <Chip
                                            label={product.status}
                                            color={getStatusColor(product.status)}
                                            size="small"
                                            sx={{ mt: 1 }}
                                        />
                                        {product.quantity <= product.reorderPoint && (
                                            <Chip
                                                label="Low Stock"
                                                color="error"
                                                size="small"
                                                sx={{ mt: 1, ml: 1 }}
                                            />
                                        )}
                                    </CardContent>
                                    <CardActions>
                                        <Checkbox
                                            checked={selectedProducts.includes(product.id)}
                                            onChange={() => handleCheckboxChange(product.id)}
                                        />
                                        <Button size="small" onClick={() => handleViewDetails(product)}>View Details</Button>
                                        <IconButton onClick={(e) => handleQuickAction(e, product.id)}>
                                            <MoreVertIcon />
                                        </IconButton>
                                    </CardActions>
                                </Card>
                            </Grid>
                        ))}
                    </Grid>
                    <Box sx={{ mt: 2, display: 'flex', justifyContent: 'center' }}>
                        <Pagination
                            count={Math.ceil(filteredProducts.length / productsPerPage)}
                            page={page}
                            onChange={(event, value) => setPage(value)}
                        />
                    </Box>
                </StyledPaper>
            </Box>

            <Menu
                anchorEl={anchorEl}
                open={Boolean(anchorEl)}
                onClose={handleCloseMenu}
            >
                <MenuItem onClick={handleAddToOrder}>Add to Order</MenuItem>
                <MenuItem onClick={handleUpdateStock}>Update Stock</MenuItem>
                <MenuItem onClick={() => {
                    handleViewDetails(products.find(p => p.id === currentProductId)!);
                    handleCloseMenu();
                }}>View Details</MenuItem>
            </Menu>

            {/* ... (previous Dialog component for product details) */}
        </BackgroundContainer>
    );
};

export default ProductCatalogPage;