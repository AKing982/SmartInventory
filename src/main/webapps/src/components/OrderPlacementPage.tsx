import React, {useEffect, useMemo, useState} from "react";
import {
    Box, Button, Chip, Container, Divider,
    FormControl, Grid, IconButton,
    InputLabel,
    MenuItem,
    Paper,
    Select,
    styled, Table, TableBody, TableCell,
    TableContainer, TableHead, TablePagination, TableRow,
    TextField,
    Typography
} from "@mui/material";
import MainAppBar from "./MainAppBar";
import {
    Add as AddIcon,
    Delete as DeleteIcon,
    Edit as EditIcon,
    Search as SearchIcon,
    Comment as CommentIcon,
    Sort as SortIcon,
    Assessment as AssessmentIcon,
    Category as CategoryIcon,
    Warehouse as WarehouseIcon,
    People as PeopleIcon, Business as BusinessIcon, Settings as SettingsIcon
} from '@mui/icons-material';
import {
    History as HistoryIcon,
    Inventory as InventoryIcon, Person as PersonIcon,
    ShoppingCart as ShoppingCartIcon
} from "@mui/icons-material";
import PlaceOrderForm from "./PlaceOrderForm";
import CloseIcon from '@mui/icons-material/Close';

interface OrderForm {
    id: number;
    orderNumber: string;
    productName: string;
    description?: string;
    orderAmount: number;
    orderQuantity: number;
    orderDate: Date;
    isSubmitted: boolean;
}

interface Product {
    id: number;
    productNumber: number;
    productName: string;
    productDescription?: string;
    productPrice: number;
    productQuantity: number;
    productSku: string;
    productCategory: string;
    [key: string]: any;
    status: 'In Stock' | 'Out of Stock' | 'Low Stock'
}

interface OrderPlacementProps {
    order: OrderForm;
    product: Product[];
}



const BackgroundContainer = styled('div')({
    minHeight: '120vh',
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


const OrderPlacementPage: React.FC = () => {
    const [isOrdered, setIsOrdered] = useState<boolean>(false);
    const [orderForm, setOrderForm] = useState<OrderForm>({
        id: 0,
        orderNumber: '',
        productName: '',
        description: '',
        orderAmount: 0,
        orderQuantity: 0,
        orderDate: new Date(),
        isSubmitted: false
    });

    const [productList, setProductList] = useState<Product[]>([]);
    const [openSnackbar, setOpenSnackbar] = useState<boolean>(false);
    const [searchTerm, setSearchTerm] = useState<string>('');
    const [searchCategory, setSearchCategory] = useState<string>('');
    const [sortBy, setSortBy] = useState<keyof Product>('productName');
    const [sortDirection, setSortDirection] = useState<'asc'|'desc'>('asc');
    const [page, setPage] = useState<number>(0);
    const [rowsPerPage, setRowsPerPage] = useState<number>(5);
    const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);

    const menuItems = [
        { text: 'Product Catalog', icon: <InventoryIcon />, path: '/catalog' },
        { text: 'Place Order', icon: <ShoppingCartIcon />, path: '/order-placement' },
        { text: 'Order History', icon: <HistoryIcon />, path: '/order-history' },
        { text: 'Update Profile', icon: <PersonIcon />, path: '/profile' },
    ];
    useEffect(() => {
        const dummyProducts: Product[] = [
            {
                id: 1,
                productNumber: 1001,
                productName: "Ergonomic Desk Chair",
                productDescription: "Comfortable office chair with lumbar support",
                productSku: "CHAIR-001",
                productPrice: 199.99,
                productCategory: "Furniture",
                productQuantity: 50,
                status: "In Stock"
            },
            {
                id: 2,
                productNumber: 1002,
                productName: "Wireless Mouse",
                productDescription: "Bluetooth-enabled wireless mouse",
                productSku: "MOUSE-001",
                productPrice: 29.99,
                productCategory: "Electronics",
                productQuantity: 100,
                status: "In Stock"
            },
            {
                id: 3,
                productNumber: 1003,
                productName: "LED Desk Lamp",
                productDescription: "Adjustable LED lamp with multiple brightness levels",
                productSku: "LAMP-001",
                productPrice: 39.99,
                productCategory: "Lighting",
                productQuantity: 75,
                status: "In Stock"
            },
            {
                id: 4,
                productNumber: 1004,
                productName: "Mechanical Keyboard",
                productDescription: "Mechanical gaming keyboard with RGB lighting",
                productSku: "KEY-001",
                productPrice: 89.99,
                productCategory: "Electronics",
                productQuantity: 30,
                status: "Low Stock"
            },
            {
                id: 5,
                productNumber: 1005,
                productName: "Desk Organizer",
                productDescription: "Multi-compartment desk organizer",
                productSku: "ORG-001",
                productPrice: 24.99,
                productCategory: "Office Supplies",
                productQuantity: 60,
                status: "In Stock"
            },
            {
                id: 6,
                productNumber: 1006,
                productName: "Ergonomic Footrest",
                productDescription: "Adjustable footrest for under-desk comfort",
                productSku: "FOOT-001",
                productPrice: 34.99,
                productCategory: "Furniture",
                productQuantity: 40,
                status: "In Stock"
            },
            {
                id: 7,
                productNumber: 1007,
                productName: "Wireless Keyboard",
                productDescription: "Slim wireless keyboard with long battery life",
                productSku: "KEY-002",
                productPrice: 49.99,
                productCategory: "Electronics",
                productQuantity: 0,
                status: "Out of Stock"
            },
            {
                id: 8,
                productNumber: 1008,
                productName: "Monitor Stand",
                productDescription: "Adjustable monitor stand with cable management",
                productSku: "STAND-001",
                productPrice: 59.99,
                productCategory: "Furniture",
                productQuantity: 25,
                status: "Low Stock"
            },
            {
                id: 9,
                productNumber: 1009,
                productName: "Webcam",
                productDescription: "HD webcam with built-in microphone",
                productSku: "CAM-001",
                productPrice: 69.99,
                productCategory: "Electronics",
                productQuantity: 80,
                status: "In Stock"
            },
            {
                id: 10,
                productNumber: 1010,
                productName: "Desk Pad",
                productDescription: "Large desk pad for mouse and keyboard",
                productSku: "PAD-001",
                productPrice: 19.99,
                productCategory: "Office Supplies",
                productQuantity: 90,
                status: "In Stock"
            },
            {
                id: 11,
                productNumber: 1011,
                productName: "USB Hub",
                productDescription: "7-port USB 3.0 hub",
                productSku: "USB-001",
                productPrice: 29.99,
                productCategory: "Electronics",
                productQuantity: 70,
                status: "In Stock"
            },
            {
                id: 12,
                productNumber: 1012,
                productName: "Standing Desk",
                productDescription: "Electric adjustable standing desk",
                productSku: "DESK-001",
                productPrice: 399.99,
                productCategory: "Furniture",
                productQuantity: 15,
                status: "Low Stock"
            },
            {
                id: 13,
                productNumber: 1013,
                productName: "Noise-Cancelling Headphones",
                productDescription: "Wireless noise-cancelling headphones",
                productSku: "AUDIO-001",
                productPrice: 199.99,
                productCategory: "Electronics",
                productQuantity: 40,
                status: "In Stock"
            },
            {
                id: 14,
                productNumber: 1014,
                productName: "Desk Fan",
                productDescription: "Small USB-powered desk fan",
                productSku: "FAN-001",
                productPrice: 14.99,
                productCategory: "Office Supplies",
                productQuantity: 100,
                status: "In Stock"
            },
            {
                id: 15,
                productNumber: 1015,
                productName: "Whiteboard",
                productDescription: "Magnetic dry-erase whiteboard",
                productSku: "BOARD-001",
                productPrice: 79.99,
                productCategory: "Office Supplies",
                productQuantity: 0,
                status: "Out of Stock"
            },
            {
                id: 16,
                productNumber: 1016,
                productName: "Drafting Chair",
                productDescription: "Adjustable drafting chair with footrest",
                productSku: "CHAIR-002",
                productPrice: 149.99,
                productCategory: "Furniture",
                productQuantity: 20,
                status: "Low Stock"
            },
            {
                id: 17,
                productNumber: 1017,
                productName: "Laptop Stand",
                productDescription: "Portable laptop stand for ergonomic viewing",
                productSku: "STAND-002",
                productPrice: 39.99,
                productCategory: "Office Supplies",
                productQuantity: 60,
                status: "In Stock"
            },
            {
                id: 18,
                productNumber: 1018,
                productName: "Wireless Presenter",
                productDescription: "Wireless presenter with laser pointer",
                productSku: "PRES-001",
                productPrice: 24.99,
                productCategory: "Electronics",
                productQuantity: 50,
                status: "In Stock"
            },
            {
                id: 19,
                productNumber: 1019,
                productName: "Cable Management Kit",
                productDescription: "Complete cable management solution",
                productSku: "CABLE-001",
                productPrice: 29.99,
                productCategory: "Office Supplies",
                productQuantity: 80,
                status: "In Stock"
            },
            {
                id: 20,
                productNumber: 1020,
                productName: "Ergonomic Keyboard",
                productDescription: "Split ergonomic keyboard for comfortable typing",
                productSku: "KEY-003",
                productPrice: 119.99,
                productCategory: "Electronics",
                productQuantity: 35,
                status: "In Stock"
            }
        ];
        setProductList(dummyProducts);
    }, []);
    const filteredAndSortedProducts = useMemo(() => {
        return productList
            .filter(product =>
                (searchCategory === 'all' || product.productCategory.toLowerCase() === searchCategory.toLowerCase()) &&
                (product.productName.toLowerCase().includes(searchTerm.toLowerCase()) ||
                product.productSku.toLowerCase().includes(searchTerm.toLowerCase()))
            )
            .sort((a, b) => {
                if (a[sortBy] < b[sortBy]) return sortDirection === 'asc' ? -1 : 1;
                if (a[sortBy] > b[sortBy]) return sortDirection === 'asc' ? 1 : -1;
                return 0;
            });
    }, [productList, searchTerm, searchCategory, sortBy, sortDirection]);

    const handleOrderClick = (product: Product) => {
        setSelectedProduct(product);
        setOrderForm({
            id: product.id,
            orderNumber: `ORD-${Date.now()}`,
            productName: product.productName,
            description: product.productDescription,
            orderAmount: product.productPrice,
            orderQuantity: 1,
            orderDate: new Date(),
            isSubmitted: false
        });
        setIsOrdered(true);
    };

    const categories = useMemo(() => {
        const uniqueCategories = Array.from(new Set(productList.map(product => product.productCategory)));
        return ['All', ...uniqueCategories];
    }, [productList]);

    const handleSort = (column: keyof Product) => {
        setSortBy(column);
        setSortDirection(prevDirection => prevDirection === 'asc' ? 'desc' : 'asc');
    };

    const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage);
    }

    const handleOrderFormChange = (field: any, value: any) => {
        setOrderForm(prevForm => ({
            ...prevForm,
            [field]: value
        }));
    };


    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    }

    const handleQuantityChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const newQuantity = parseInt(e.target.value, 10);
        setOrderForm(prevForm => ({
            ...prevForm,
            orderQuantity: isNaN(newQuantity) ? 0 :newQuantity
        }));
    };

    const clearOrderForm = () => {
        setOrderForm({
            id: 0,
            orderNumber: '',
            productName: '',
            description: '',
            orderAmount: 0,
            orderQuantity: 0,
            orderDate: new Date(),
            isSubmitted: false
        });
    }

    const handleCancelOrder = () => {
        setIsOrdered(false);
        setSelectedProduct(null);
        clearOrderForm();
    }

    const handleOrderDateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const dateValue = e.target.value;
        setOrderForm(prevForm => ({
            ...prevForm,
            orderDate: new Date(dateValue)
        }));
    };

    const handleOrderSubmit = () => {
        // Implement order submission logic here
        console.log('Order submitted:', { product: selectedProduct, ...orderForm });
        setIsOrdered(false);
        setSelectedProduct(null);
    };


    return (
        <BackgroundContainer>
            <MainAppBar title="Order Placement" drawerItems={menuItems}/>
            <Box sx={{width: '100%', mb: 2}}>
                <Typography variant="h4" sx={{mb: 2}}>
                    Inventory
                </Typography>

                <Paper elevation={3} sx={{p: 2, width: '100%'}}>
                    <Box sx={{display: 'flex', mb: 2}}>
                        <TextField
                            label="Search Items"
                            variant="outlined"
                            size="small"
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            sx={{mr: 2}}
                        />
                        <FormControl size="small" sx={{minWidth: 120, mr: 2}}>
                            <InputLabel>Category</InputLabel>
                            <Select
                                value={searchCategory}
                                onChange={(e) => setSearchCategory(e.target.value)}
                                label="Category"
                            >
                                {categories.map((category) => (
                                    <MenuItem key={category} value={category}>{category}</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                    </Box>
                    <TableContainer>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    {['productNumber', 'productName', 'productDescription', 'productSku', 'productPrice', 'productCategory', 'productQuantity', 'status'].map((column) => (
                                        <TableCell key={column}>
                                            <Box sx={{display: 'flex', alignItems: 'center'}}>
                                                {column.replace(/([A-Z])/g, ' $1').replace(/^./, str => str.toUpperCase())}
                                                <IconButton size="small" onClick={() => handleSort(column as keyof Product)}>
                                                    <SortIcon />
                                                </IconButton>
                                            </Box>
                                        </TableCell>
                                    ))}
                                    <TableCell>Actions</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {filteredAndSortedProducts
                                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                    .map((product) => (
                                        <TableRow key={product.id}>
                                            <TableCell>{product.productNumber}</TableCell>
                                            <TableCell>{product.productName}</TableCell>
                                            <TableCell>{product.productDescription}</TableCell>
                                            <TableCell>{product.productSku}</TableCell>
                                            <TableCell>${product.productPrice.toFixed(2)}</TableCell>
                                            <TableCell>{product.productCategory}</TableCell>
                                            <TableCell>{product.productQuantity}</TableCell>
                                            <TableCell>
                                                <Chip
                                                    label={product.status}
                                                    color={
                                                        product.status === 'In Stock' ? 'success' :
                                                            product.status === 'Low Stock' ? 'warning' :
                                                                'error'
                                                    }
                                                />
                                            </TableCell>
                                            <TableCell>
                                                <Button
                                                    variant="contained"
                                                    color="primary"
                                                    disabled={product.status === 'Out of Stock'}
                                                    onClick={() => handleOrderClick(product)}
                                                >
                                                    Order
                                                </Button>
                                            </TableCell>
                                        </TableRow>
                                    ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                    <TablePagination
                        count={filteredAndSortedProducts.length}
                        page={page}
                        rowsPerPage={rowsPerPage}
                        onPageChange={handleChangePage}
                        onRowsPerPageChange={handleChangeRowsPerPage}
                        rowsPerPageOptions={[5, 10, 15]}
                    />
                </Paper>

                {isOrdered && selectedProduct && (
                    <Box sx={{
                        display: 'flex',
                        justifyContent: 'center',
                        alignItems: 'center',
                        width: '100%',
                        mt: 4
                    }}>
                        <Container maxWidth={false} sx={{ width: '100%' }}>
                            <StyledPaper>
                                <Box sx={{ position: 'relative' }}>
                                    <IconButton
                                        aria-label="close"
                                        onClick={handleCancelOrder}
                                        sx={{
                                            position: 'absolute',
                                            right: 8,
                                            top: 8,
                                            color: (theme) => theme.palette.grey[500],
                                        }}
                                    >
                                        <CloseIcon />
                                    </IconButton>
                                    <Grid container spacing={3}>
                                        <Grid item xs={12}>
                                            <Typography variant="h5" sx={{mb: 2, display: 'flex', alignItems: 'center'}}>
                                                <ShoppingCartIcon sx={{mr: 1}} />
                                                Place Order for {selectedProduct.productName}
                                            </Typography>
                                            <Divider sx={{mb: 3}} />
                                        </Grid>
                                        <Grid item xs={12}>
                                            <PlaceOrderForm
                                                orderForm={orderForm}
                                                handleInputChange={handleOrderFormChange}
                                                handleSubmit={handleOrderSubmit}
                                            />
                                        </Grid>
                                    </Grid>
                                </Box>
                            </StyledPaper>
                        </Container>
                    </Box>
                )}
            </Box>
        </BackgroundContainer>
    );
}

export default OrderPlacementPage;