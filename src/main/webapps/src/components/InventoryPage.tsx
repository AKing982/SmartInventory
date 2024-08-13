import React, { useState, useEffect, useMemo } from 'react';
import {
    Box, Typography, TextField, Button, Snackbar, Alert,
    Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper,
    IconButton, Tooltip, Select, MenuItem, InputLabel, FormControl,
    SelectChangeEvent, Dialog, DialogTitle, DialogContent, DialogActions,
    List, ListItem, ListItemText, styled, TablePagination, Chip
} from '@mui/material';
import {
    Add as AddIcon,
    Delete as DeleteIcon,
    Edit as EditIcon,
    Search as SearchIcon,
    History as HistoryIcon,
    Comment as CommentIcon,
    Sort as SortIcon
} from '@mui/icons-material';
import { Link, useNavigate } from 'react-router-dom';
import MainAppBar from './MainAppBar';

interface InventoryItem {
    id: number;
    name: string;
    sku: string;
    category: string;
    quantity: number;
    price: number;
    supplier: string;
    comments: string;
    history: { date: string; action: string }[];
    status: 'In Stock' | 'Low Stock' | 'Out of Stock';
}

const BackgroundContainer = styled('div')({
    minHeight: '120vh',
    display: 'flex',
    flexDirection: 'column',
    background: 'linear-gradient(120deg, #f6f7f9 0%, #e3e6ec 100%)',
});

const ContentContainer = styled(Box)({
    flexGrow: 1,
    padding: '24px',
});

const InventoryPage: React.FC = () => {
    const [inventoryItems, setInventoryItems] = useState<InventoryItem[]>([]);
    const [openSnackbar, setOpenSnackbar] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');
    const [searchTerm, setSearchTerm] = useState('');
    const [searchCategory, setSearchCategory] = useState('all');
    const [sortBy, setSortBy] = useState<keyof InventoryItem>('name');
    const [sortDirection, setSortDirection] = useState<'asc' | 'desc'>('asc');
    const [openHistoryDialog, setOpenHistoryDialog] = useState(false);
    const [selectedItemHistory, setSelectedItemHistory] = useState<{ date: string; action: string }[]>([]);
    const [selectedItemId, setSelectedItemId] = useState<number | null>(null);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);

    const navigate = useNavigate();

    const categories = ['Electronics', 'Clothing', 'Books', 'Food', 'Other'];

    useEffect(() => {
        // In a real application, you'd fetch this data from an API
        const dummyData: InventoryItem[] = [
            { id: 1, name: 'Laptop', sku: 'TECH001', category: 'Electronics', quantity: 50, price: 999.99, supplier: 'TechCorp', comments: 'High demand item', history: [{ date: '2023-01-01', action: 'Added to inventory' }], status: 'In Stock' },
            { id: 2, name: 'T-Shirt', sku: 'CLOTH001', category: 'Clothing', quantity: 100, price: 19.99, supplier: 'FashionInc', comments: 'Popular sizes running low', history: [{ date: '2023-02-15', action: 'Restocked' }], status: 'In Stock' },
            { id: 3, name: 'Novel', sku: 'BOOK001', category: 'Books', quantity: 75, price: 14.99, supplier: 'BookWorm Ltd', comments: 'New release', history: [{ date: '2023-03-10', action: 'Price updated' }], status: 'In Stock' },
            { id: 4, name: 'Smartphone', sku: 'TECH002', category: 'Electronics', quantity: 30, price: 699.99, supplier: 'TechCorp', comments: 'Latest model', history: [{ date: '2023-04-01', action: 'Added to inventory' }], status: 'In Stock' },
            { id: 5, name: 'Jeans', sku: 'CLOTH002', category: 'Clothing', quantity: 80, price: 49.99, supplier: 'FashionInc', comments: 'Best seller', history: [{ date: '2023-04-15', action: 'Restocked' }], status: 'In Stock' },
            { id: 6, name: 'Cookbook', sku: 'BOOK002', category: 'Books', quantity: 5, price: 24.99, supplier: 'BookWorm Ltd', comments: 'Low stock', history: [{ date: '2023-05-01', action: 'Low stock alert' }], status: 'Low Stock' },
            { id: 7, name: 'Tablet', sku: 'TECH003', category: 'Electronics', quantity: 0, price: 399.99, supplier: 'TechCorp', comments: 'Out of stock', history: [{ date: '2023-05-15', action: 'Out of stock' }], status: 'Out of Stock' },
            { id: 8, name: 'Dress', sku: 'CLOTH003', category: 'Clothing', quantity: 60, price: 79.99, supplier: 'FashionInc', comments: 'New collection', history: [{ date: '2023-06-01', action: 'Added to inventory' }], status: 'In Stock' },
            { id: 9, name: 'Science Fiction', sku: 'BOOK003', category: 'Books', quantity: 40, price: 12.99, supplier: 'BookWorm Ltd', comments: 'Bestseller', history: [{ date: '2023-06-15', action: 'Restocked' }], status: 'In Stock' },
            { id: 10, name: 'Smartwatch', sku: 'TECH004', category: 'Electronics', quantity: 25, price: 199.99, supplier: 'TechCorp', comments: 'Popular item', history: [{ date: '2023-07-01', action: 'Price updated' }], status: 'In Stock' },
            { id: 11, name: 'Sneakers', sku: 'CLOTH004', category: 'Clothing', quantity: 3, price: 89.99, supplier: 'FashionInc', comments: 'Low stock', history: [{ date: '2023-07-15', action: 'Low stock alert' }], status: 'Low Stock' },
            { id: 12, name: 'Cookbook', sku: 'BOOK004', category: 'Books', quantity: 50, price: 29.99, supplier: 'BookWorm Ltd', comments: 'New edition', history: [{ date: '2023-08-01', action: 'Added to inventory' }], status: 'In Stock' },
            { id: 13, name: 'Headphones', sku: 'TECH005', category: 'Electronics', quantity: 35, price: 149.99, supplier: 'TechCorp', comments: 'Noise-cancelling', history: [{ date: '2023-08-15', action: 'Restocked' }], status: 'In Stock' },
        ];
        setInventoryItems(dummyData);
    }, []);

    // ... (keep other handlers as they are)

    const filteredAndSortedItems = useMemo(() => {
        return inventoryItems
            .filter(item =>
                (searchCategory === 'all' || item.category === searchCategory) &&
                (item.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
                    item.sku.toLowerCase().includes(searchTerm.toLowerCase()))
            )
            .sort((a, b) => {
                if (a[sortBy] < b[sortBy]) return sortDirection === 'asc' ? -1 : 1;
                if (a[sortBy] > b[sortBy]) return sortDirection === 'asc' ? 1 : -1;
                return 0;
            });
    }, [inventoryItems, searchTerm, searchCategory, sortBy, sortDirection]);

    const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const handleSort = (column: keyof InventoryItem) => {
        setSortBy(column);
        setSortDirection(prevDirection => prevDirection === 'asc' ? 'desc' : 'asc');
    };

    const handleDeleteItem = (id: number) => {
        setInventoryItems(inventoryItems.filter(item => item.id !== id));
        setSnackbarMessage('Item deleted successfully!');
        setOpenSnackbar(true);
    };

    const handleViewHistory = (history: { date: string; action: string }[], id: number) => {
        setSelectedItemHistory(history);
        setSelectedItemId(id);
        setOpenHistoryDialog(true);
    };

    return (
        <BackgroundContainer>
            <MainAppBar title="Inventory Management" />
            <ContentContainer>
                <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
                    <Typography variant="h4">
                        Inventory Management
                    </Typography>
                    <Button
                        variant="contained"
                        color="primary"
                        startIcon={<AddIcon />}
                        onClick={() => navigate('/inventory/new')}
                    >
                        Add New Item
                    </Button>
                </Box>
                <Paper elevation={3} sx={{ p: 2, width: '100%' }}>
                    <Box sx={{ display: 'flex', mb: 2 }}>
                        <TextField
                            label="Search Items"
                            variant="outlined"
                            size="small"
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            sx={{ mr: 2 }}
                        />
                        <FormControl size="small" sx={{ minWidth: 120, mr: 2 }}>
                            <InputLabel>Category</InputLabel>
                            <Select
                                value={searchCategory}
                                onChange={(e) => setSearchCategory(e.target.value)}
                                label="Category"
                            >
                                <MenuItem value="all">All</MenuItem>
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
                                    {['name', 'sku', 'category', 'quantity', 'price', 'supplier', 'status'].map((column) => (
                                        <TableCell key={column}>
                                            <Box sx={{ display: 'flex', alignItems: 'center' }}>
                                                {column.charAt(0).toUpperCase() + column.slice(1)}
                                                <IconButton size="small" onClick={() => handleSort(column as keyof InventoryItem)}>
                                                    <SortIcon />
                                                </IconButton>
                                            </Box>
                                        </TableCell>
                                    ))}
                                    <TableCell>Actions</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {filteredAndSortedItems.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((item) => (
                                    <TableRow key={item.id}>
                                        <TableCell>{item.name}</TableCell>
                                        <TableCell>{item.sku}</TableCell>
                                        <TableCell>{item.category}</TableCell>
                                        <TableCell>{item.quantity}</TableCell>
                                        <TableCell>${item.price.toFixed(2)}</TableCell>
                                        <TableCell>{item.supplier}</TableCell>
                                        <TableCell>
                                            <Chip
                                                label={item.status}
                                                color={
                                                    item.status === 'In Stock' ? 'success' :
                                                        item.status === 'Low Stock' ? 'warning' :
                                                            'error'
                                                }
                                            />
                                        </TableCell>
                                        <TableCell>
                                            <Tooltip title="Edit">
                                                <IconButton>
                                                    <EditIcon />
                                                </IconButton>
                                            </Tooltip>
                                            <Tooltip title="Delete">
                                                <IconButton onClick={() => handleDeleteItem(item.id)}>
                                                    <DeleteIcon />
                                                </IconButton>
                                            </Tooltip>
                                            <Tooltip title="View History">
                                                <IconButton onClick={() => handleViewHistory(item.history, item.id)}>
                                                    <HistoryIcon />
                                                </IconButton>
                                            </Tooltip>
                                            <Tooltip title="Comments">
                                                <IconButton>
                                                    <CommentIcon />
                                                </IconButton>
                                            </Tooltip>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                    <TablePagination
                        rowsPerPageOptions={[5, 10, 25]}
                        component="div"
                        count={filteredAndSortedItems.length}
                        rowsPerPage={rowsPerPage}
                        page={page}
                        onPageChange={handleChangePage}
                        onRowsPerPageChange={handleChangeRowsPerPage}
                    />
                </Paper>
                {/* Keep Snackbar and Dialog components as they are */}
            </ContentContainer>
        </BackgroundContainer>
    );
};

export default InventoryPage;