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
    FormControl,
    InputLabel,
    Select,
    MenuItem,
    Button,
    TextField,
    Grid,
    styled, IconButton,
    Tooltip as MuiToolTip
} from '@mui/material';
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, Cell, LineChart, Line, Legend } from 'recharts';
import DatePicker  from 'react-datepicker';
import FileDownloadIcon from '@mui/icons-material/FileDownload';
import TrendingUpIcon from '@mui/icons-material/TrendingUp';
import MainAppBar from './MainAppBar';
import {useNavigate} from "react-router-dom";
import TrendingDownIcon from "@mui/icons-material/TrendingDown";
import HistoryIcon from '@mui/icons-material/History';

interface InventoryItem {
    id: number;
    name: string;
    sku: string;
    category: string;
    quantity: number;
    price: number;
    supplier: string;
    status: 'In Stock' | 'Low Stock' | 'Out of Stock';
}

interface StockHistory {
    date: string;
    quantity: number;
}

interface InventoryItemWithHistory extends InventoryItem {
    history: StockHistory[];
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

const StockLevels: React.FC = () => {
    const [inventoryItems, setInventoryItems] = useState<InventoryItemWithHistory[]>([]);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(5);
    const [categoryFilter, setCategoryFilter] = useState('all');
    const [searchTerm, setSearchTerm] = useState('');
    const [lowStockThreshold, setLowStockThreshold] = useState(10);
    const [criticalStockThreshold, setCriticalStockThreshold] = useState(5);
    const [startDate, setStartDate] = useState<Date | undefined>(new Date(new Date().setMonth(new Date().getMonth() - 1)));
    const [endDate, setEndDate] = useState<Date | undefined>(new Date());
    const [selectedItem, setSelectedItem] = useState<InventoryItemWithHistory | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        // Fetch data (using dummy data with history for now)
        const dummyData: InventoryItemWithHistory[] = [
            { id: 1, name: 'Laptop', sku: 'TECH001', category: 'Electronics', quantity: 50, price: 999.99, supplier: 'TechCorp', status: 'In Stock', history: generateDummyHistory(50) },
            { id: 2, name: 'T-Shirt', sku: 'CLOTH001', category: 'Clothing', quantity: 100, price: 19.99, supplier: 'FashionInc', status: 'In Stock', history: generateDummyHistory(100) },
            { id: 3, name: 'Novel', sku: 'BOOK001', category: 'Books', quantity: 75, price: 14.99, supplier: 'BookWorm Ltd', status: 'In Stock', history: generateDummyHistory(75) },
            { id: 4, name: 'Smartphone', sku: 'TECH002', category: 'Electronics', quantity: 30, price: 699.99, supplier: 'TechCorp', status: 'In Stock', history: generateDummyHistory(30) },
            { id: 5, name: 'Jeans', sku: 'CLOTH002', category: 'Clothing', quantity: 8, price: 49.99, supplier: 'FashionInc', status: 'Low Stock', history: generateDummyHistory(8) },
            { id: 6, name: 'Cookbook', sku: 'BOOK002', category: 'Books', quantity: 3, price: 24.99, supplier: 'BookWorm Ltd', status: 'Low Stock', history: generateDummyHistory(3) },
            { id: 7, name: 'Tablet', sku: 'TECH003', category: 'Electronics', quantity: 0, price: 399.99, supplier: 'TechCorp', status: 'Out of Stock', history: generateDummyHistory(0) },
        ];
        setInventoryItems(dummyData);
    }, []);

    const generateDummyHistory = (currentQuantity: number): StockHistory[] => {
        const history: StockHistory[] = [];
        let date = new Date();
        date.setMonth(date.getMonth() - 1);
        for (let i = 0; i < 30; i++) {
            history.push({
                date: date.toISOString().split('T')[0],
                quantity: Math.max(0, currentQuantity + Math.floor(Math.random() * 20) - 10)
            });
            date.setDate(date.getDate() + 1);
        }
        return history;
    };

    const handleViewHistory = (item: InventoryItem) => {
        navigate(`/stock-history/${item.id}`)
    }

    const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage);
    };

    const handleStartDateChange = (date: Date | null) => {
        setStartDate(date || undefined);
    };

    const handleEndDateChange = (date: Date | null) => {
        setEndDate(date || undefined);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const filteredItems = inventoryItems.filter(item =>
        (categoryFilter === 'all' || item.category === categoryFilter) &&
        (item.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
            item.sku.toLowerCase().includes(searchTerm.toLowerCase()))
    );

    const chartData = filteredItems.map(item => ({
        name: item.name,
        quantity: item.quantity,
    }));

    const getStockColor = (quantity: number) => {
        if (quantity <= criticalStockThreshold) return '#ff0000';
        if (quantity <= lowStockThreshold) return '#ffa500';
        return '#4caf50';
    };

    const calculateTotalStockValue = () => {
        return filteredItems.reduce((total, item) => total + (item.quantity * item.price), 0);
    };

    const handleExport = () => {
        const csvContent = "data:text/csv;charset=utf-8,"
            + "Name,SKU,Category,Quantity,Price,Status\n"
            + filteredItems.map(item =>
                `${item.name},${item.sku},${item.category},${item.quantity},${item.price},${item.status}`
            ).join("\n");

        const encodedUri = encodeURI(csvContent);
        const link = document.createElement("a");
        link.setAttribute("href", encodedUri);
        link.setAttribute("download", "stock_levels.csv");
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    };

    const handleItemClick = (item: InventoryItemWithHistory) => {
        setSelectedItem(item);
    };

    return (
        <BackgroundContainer>
            <MainAppBar title="Stock Levels" />
            <ContentContainer>
                <Typography variant="h4" gutterBottom>
                    Stock Levels
                </Typography>
                <Grid container spacing={2} sx={{ mb: 2 }}>
                    <Grid item xs={12} md={3}>
                        <FormControl fullWidth>
                            <InputLabel>Category</InputLabel>
                            <Select
                                value={categoryFilter}
                                label="Category"
                                onChange={(e) => setCategoryFilter(e.target.value)}
                            >
                                <MenuItem value="all">All</MenuItem>
                                <MenuItem value="Electronics">Electronics</MenuItem>
                                <MenuItem value="Clothing">Clothing</MenuItem>
                                <MenuItem value="Books">Books</MenuItem>
                            </Select>
                        </FormControl>
                    </Grid>
                    <Grid item xs={12} md={3}>
                        <TextField
                            label="Search"
                            variant="outlined"
                            fullWidth
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                        />
                    </Grid>
                    <Grid item xs={12} md={3}>
                        <DatePicker
                                selected={startDate}
                                onChange={handleStartDateChange}
                                selectsStart
                                startDate={startDate}
                                endDate={endDate}
                                placeholderText="Start Date"
                        />
                    </Grid>
                    <Grid item xs={12} md={3}>
                            <DatePicker
                                selected={endDate}
                                onChange={handleEndDateChange}
                                selectsEnd
                                startDate={startDate}
                                endDate={endDate}
                                minDate={startDate}
                                placeholderText="End Date"
                            />
                    </Grid>
                </Grid>
                <Button
                    variant="contained"
                    startIcon={<FileDownloadIcon />}
                    onClick={handleExport}
                    sx={{ mb: 2 }}
                >
                    Export CSV
                </Button>
                <Paper elevation={3} sx={{ p: 2, mb: 4 }}>
                    <Typography variant="h6" gutterBottom>
                        Current Stock Levels
                    </Typography>
                    <ResponsiveContainer width="100%" height={300}>
                        <BarChart data={chartData}>
                            <XAxis dataKey="name" />
                            <YAxis />
                            <Tooltip />
                            <Bar dataKey="quantity" onClick={(data) => handleItemClick(inventoryItems.find(item => item.name === data.name)!)}>
                                {chartData.map((entry, index) => (
                                    <Cell key={`cell-${index}`} fill={getStockColor(entry.quantity)} />
                                ))}
                            </Bar>
                        </BarChart>
                    </ResponsiveContainer>
                </Paper>
                {selectedItem && (
                    <Paper elevation={3} sx={{ p: 2, mb: 4 }}>
                        <Typography variant="h6" gutterBottom>
                            Stock History for {selectedItem.name}
                        </Typography>
                        <ResponsiveContainer width="100%" height={300}>
                            <LineChart data={selectedItem.history.filter(h =>
                                new Date(h.date) >= startDate! && new Date(h.date) <= endDate!
                            )}>
                                <XAxis dataKey="date" />
                                <YAxis />
                                <Tooltip />
                                <Legend />
                                <Line type="monotone" dataKey="quantity" stroke="#8884d8" />
                            </LineChart>
                        </ResponsiveContainer>
                    </Paper>
                )}
                <Paper elevation={3} sx={{ p: 2, mb: 4 }}>
                    <Typography variant="h6" gutterBottom>
                        Stock Value Summary
                    </Typography>
                    <Typography variant="body1">
                        Total Stock Value: ${calculateTotalStockValue().toFixed(2)}
                    </Typography>
                </Paper>
                <Paper elevation={3} sx={{ p: 2 }}>
                    <Typography variant="h6" gutterBottom>
                        Stock Level Details
                    </Typography>
                    <TableContainer>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell>Name</TableCell>
                                    <TableCell>SKU</TableCell>
                                    <TableCell>Category</TableCell>
                                    <TableCell>Quantity</TableCell>
                                    <TableCell>Price</TableCell>
                                    <TableCell>Status</TableCell>
                                    <TableCell>Trend</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {filteredItems
                                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                    .map((item) => (
                                        <TableRow key={item.id} onClick={() => handleItemClick(item)} style={{cursor: 'pointer'}}>
                                            <TableCell>{item.name}</TableCell>
                                            <TableCell>{item.sku}</TableCell>
                                            <TableCell>{item.category}</TableCell>
                                            <TableCell style={{ color: getStockColor(item.quantity) }}>{item.quantity}</TableCell>
                                            <TableCell>${item.price.toFixed(2)}</TableCell>
                                            <TableCell>{item.status}</TableCell>
                                            <TableCell>
                                                <MuiToolTip title={item.history[item.history.length - 1].quantity > item.history[0].quantity ? "Increasing" : "Decreasing"}>
                                                    {item.history[item.history.length - 1].quantity > item.history[0].quantity
                                                        ? <TrendingUpIcon color="success" />
                                                        : <TrendingDownIcon color="error" />
                                                    }
                                                </MuiToolTip>
                                            </TableCell>
                                            <TableCell>
                                                <IconButton
                                                    color="primary"
                                                    onClick={() => handleViewHistory(item)}
                                                    aria-label="view history"
                                                >
                                                    <HistoryIcon />
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
                        count={filteredItems.length}
                        rowsPerPage={rowsPerPage}
                        page={page}
                        onPageChange={handleChangePage}
                        onRowsPerPageChange={handleChangeRowsPerPage}
                    />
                </Paper>
            </ContentContainer>
        </BackgroundContainer>
    );
};

export default StockLevels;