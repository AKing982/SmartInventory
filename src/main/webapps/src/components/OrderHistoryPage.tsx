import React, { useState, useEffect } from 'react';
import {
    Box,
    Typography,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TablePagination,
    IconButton,
    Chip,
    TextField,
    MenuItem,
    Button,
    Dialog,
    DialogTitle,
    DialogContent,
    Grid,
    Accordion,
    AccordionSummary,
    DialogActions, AccordionDetails
} from '@mui/material';
import { styled } from '@mui/material/styles';
import MainAppBar from "./MainAppBar";
import InventoryIcon from '@mui/icons-material/Inventory';
import ShoppingCartIcon from '@mui/icons-material/ShoppingCart';
import HistoryIcon from '@mui/icons-material/History';
import PersonIcon from '@mui/icons-material/Person';
import VisibilityIcon from '@mui/icons-material/Visibility';
import SearchIcon from "@mui/icons-material/Search";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

interface Order {
    id: number;
    orderNumber: string;
    orderDate: string;
    productName: string;
    quantity: number;
    totalAmount: number;
    status: 'Pending' | 'Shipped' | 'Delivered' | 'Cancelled',
    trackingNumber?: string;
    [key: string]: any;
    estimatedDelivery?: string;
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

const OrderHistoryPage: React.FC = () => {
    const [orders, setOrders] = useState<Order[]>([]);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
    const [searchTerm, setSearchTerm] = useState<string>('');
    const [statusFilter, setStatusFilter] = useState<Order['status'] | 'All'>('All');
    const [sortBy, setSortBy] = useState<keyof Order>('orderDate');
    const [sortDirection, setSortDirection] = useState<'asc' | 'desc'>('desc');
    const [selectedOrder, setSelectedOrder] = useState<Order | null>(null);
    const [isDetailDialogOpen, setIsDetailDialogOpen] = useState<boolean>(false);

    const menuItems = [
        { text: 'Product Catalog', icon: <InventoryIcon />, path: '/catalog' },
        { text: 'Place Order', icon: <ShoppingCartIcon />, path: '/order-placement' },
        { text: 'Order History', icon: <HistoryIcon />, path: '/order-history' },
        { text: 'Update Profile', icon: <PersonIcon />, path: '/profile' },
    ];

    useEffect(() => {
        // Fetch orders from an API or load from local storage
        // For this example, we'll use dummy data
        const dummyOrders: Order[] = [
            { id: 1, orderNumber: 'ORD-001', orderDate: '2023-08-15', productName: 'Ergonomic Chair', quantity: 2, totalAmount: 399.98, status: 'Delivered' },
            { id: 2, orderNumber: 'ORD-002', orderDate: '2023-08-17', productName: 'Wireless Mouse', quantity: 1, totalAmount: 29.99, status: 'Shipped' },
            { id: 3, orderNumber: 'ORD-003', orderDate: '2023-08-20', productName: 'LED Desk Lamp', quantity: 3, totalAmount: 119.97, status: 'Pending' },
            // Add more dummy orders as needed
        ];
        setOrders(dummyOrders);
    }, []);


    const filteredOrders = orders.filter(order =>
        (order.orderNumber.toLowerCase().includes(searchTerm.toLowerCase()) ||
            order.productName.toLowerCase().includes(searchTerm.toLowerCase())) &&
        (statusFilter === 'All' || order.status === statusFilter)
    ).sort((a, b) => {
        if (a[sortBy] < b[sortBy]) return sortDirection === 'asc' ? -1 : 1;
        if (a[sortBy] > b[sortBy]) return sortDirection === 'asc' ? 1 : -1;
        return 0;
    });


    const getStatusColor = (status: Order['status']) => {
        switch (status) {
            case 'Pending': return 'warning';
            case 'Shipped': return 'info';
            case 'Delivered': return 'success';
            case 'Cancelled': return 'error';
            default: return 'default';
        }
    };

    const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const handleSort = (column: keyof Order) => {
        setSortBy(column);
        setSortDirection(prev => prev === 'asc' ? 'desc' : 'asc');
    };

    const handleViewDetails = (order: Order) => {
        setSelectedOrder(order);
        setIsDetailDialogOpen(true);
    };

    return (
        <BackgroundContainer>
            <MainAppBar title="Order History" drawerItems={menuItems} />
            <Box sx={{ width: '100%', p: 3 }}>
                <Typography variant="h4" sx={{ mb: 2 }}>
                    Recent Orders
                </Typography>
                <StyledPaper>
                    <Box sx={{ mb: 2, display: 'flex', justifyContent: 'space-between' }}>
                        <TextField
                            label="Search Orders"
                            variant="outlined"
                            size="small"
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            InputProps={{
                                startAdornment: <SearchIcon />,
                            }}
                        />
                        <TextField
                            select
                            label="Filter by Status"
                            value={statusFilter}
                            onChange={(e) => setStatusFilter(e.target.value as Order['status'] | 'All')}
                            variant="outlined"
                            size="small"
                            sx={{ width: 150 }}
                        >
                            <MenuItem value="All">All</MenuItem>
                            <MenuItem value="Pending">Pending</MenuItem>
                            <MenuItem value="Shipped">Shipped</MenuItem>
                            <MenuItem value="Delivered">Delivered</MenuItem>
                            <MenuItem value="Cancelled">Cancelled</MenuItem>
                        </TextField>
                    </Box>
                    <TableContainer>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell>
                                        <Button onClick={() => handleSort('orderNumber')}>
                                            Order Number {sortBy === 'orderNumber' && (sortDirection === 'asc' ? '▲' : '▼')}
                                        </Button>
                                    </TableCell>
                                    <TableCell>
                                        <Button onClick={() => handleSort('orderDate')}>
                                            Order Date {sortBy === 'orderDate' && (sortDirection === 'asc' ? '▲' : '▼')}
                                        </Button>
                                    </TableCell>
                                    <TableCell>Product</TableCell>
                                    <TableCell>Quantity</TableCell>
                                    <TableCell>
                                        <Button onClick={() => handleSort('totalAmount')}>
                                            Total Amount {sortBy === 'totalAmount' && (sortDirection === 'asc' ? '▲' : '▼')}
                                        </Button>
                                    </TableCell>
                                    <TableCell>Status</TableCell>
                                    <TableCell>Actions</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {filteredOrders
                                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                    .map((order) => (
                                        <TableRow key={order.id}>
                                            <TableCell>{order.orderNumber}</TableCell>
                                            <TableCell>{order.orderDate}</TableCell>
                                            <TableCell>{order.productName}</TableCell>
                                            <TableCell>{order.quantity}</TableCell>
                                            <TableCell>${order.totalAmount.toFixed(2)}</TableCell>
                                            <TableCell>
                                                <Chip
                                                    label={order.status}
                                                    color={getStatusColor(order.status)}
                                                />
                                            </TableCell>
                                            <TableCell>
                                                <IconButton color="primary" onClick={() => handleViewDetails(order)}>
                                                    <VisibilityIcon />
                                                </IconButton>
                                            </TableCell>
                                        </TableRow>
                                    ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                    <TablePagination
                        rowsPerPageOptions={[5, 10, 25]}
                        component="div"
                        count={filteredOrders.length}
                        rowsPerPage={rowsPerPage}
                        page={page}
                        onPageChange={handleChangePage}
                        onRowsPerPageChange={handleChangeRowsPerPage}
                    />
                </StyledPaper>
            </Box>

            <Dialog open={isDetailDialogOpen} onClose={() => setIsDetailDialogOpen(false)} maxWidth="md" fullWidth>
                <DialogTitle>Order Details</DialogTitle>
                <DialogContent>
                    {selectedOrder && (
                        <Grid container spacing={2}>
                            <Grid item xs={6}>
                                <Typography variant="subtitle1">Order Number: {selectedOrder.orderNumber}</Typography>
                                <Typography variant="subtitle1">Order Date: {selectedOrder.orderDate}</Typography>
                                <Typography variant="subtitle1">Product: {selectedOrder.productName}</Typography>
                                <Typography variant="subtitle1">Quantity: {selectedOrder.quantity}</Typography>
                            </Grid>
                            <Grid item xs={6}>
                                <Typography variant="subtitle1">Total Amount: ${selectedOrder.totalAmount.toFixed(2)}</Typography>
                                <Typography variant="subtitle1">Status: {selectedOrder.status}</Typography>
                                {selectedOrder.trackingNumber && (
                                    <Typography variant="subtitle1">Tracking Number: {selectedOrder.trackingNumber}</Typography>
                                )}
                                {selectedOrder.estimatedDelivery && (
                                    <Typography variant="subtitle1">Estimated Delivery: {selectedOrder.estimatedDelivery}</Typography>
                                )}
                            </Grid>
                            <Grid item xs={12}>
                                <Accordion>
                                    <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                                        <Typography>Order Timeline</Typography>
                                    </AccordionSummary>
                                    <AccordionDetails>
                                        {/* You can add a timeline component here showing order status changes */}
                                        <Typography>Order placed on {selectedOrder.orderDate}</Typography>
                                        {/* Add more status updates as needed */}
                                    </AccordionDetails>
                                </Accordion>
                            </Grid>
                        </Grid>
                    )}
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setIsDetailDialogOpen(false)}>Close</Button>
                </DialogActions>
            </Dialog>
        </BackgroundContainer>
    );
};

export default OrderHistoryPage;
