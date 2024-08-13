import React, { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import {
    Container, Box, Typography, Paper, Table, TableBody, TableCell,
    TableContainer, TableHead, TableRow, Button, Divider, Grid
} from '@mui/material';
import { ArrowBack as ArrowBackIcon } from '@mui/icons-material';
import MainAppBar from './MainAppBar';

interface HistoryEvent {
    date: string;
    action: string;
    user: string;
    details: string;
}

interface InventoryItem {
    id: number;
    name: string;
    sku: string;
    category: string;
    quantity: number;
    price: number;
    supplier: string;
    history: HistoryEvent[];
}

const InventoryHistoryPage: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const [item, setItem] = useState<InventoryItem | null>(null);

    useEffect(() => {
        // In a real application, fetch the item data from your API
        // For this example, we'll use mock data
        const mockItem: InventoryItem = {
            id: parseInt(id!),
            name: "Sample Item",
            sku: "SAMPLE001",
            category: "Electronics",
            quantity: 100,
            price: 199.99,
            supplier: "Sample Supplier",
            history: [
                { date: "2023-06-01", action: "Added to inventory", user: "John Doe", details: "Initial stock" },
                { date: "2023-06-15", action: "Updated quantity", user: "Jane Smith", details: "Restocked +50 units" },
                { date: "2023-07-01", action: "Price changed", user: "Admin User", details: "Price updated from $189.99 to $199.99" },
                // ... more history events ...
            ]
        };
        setItem(mockItem);
    }, [id]);

    if (!item) {
        return <Typography>Loading...</Typography>;
    }

    return (
        <Box sx={{ display: 'flex' }}>
            <MainAppBar title={`Inventory History - ${item.name}`} />
            <Container component="main" sx={{ flexGrow: 1, p: 3, mt: 8 }}>
                <Box sx={{ mb: 4 }}>
                    <Button component={Link} to="/inventory" startIcon={<ArrowBackIcon />}>
                        Back to Inventory
                    </Button>
                </Box>
                <Grid container spacing={3}>
                    <Grid item xs={12} md={4}>
                        <Paper elevation={3} sx={{ p: 2, mb: 2 }}>
                            <Typography variant="h6" gutterBottom>Item Details</Typography>
                            <Divider sx={{ mb: 2 }} />
                            <Typography><strong>Name:</strong> {item.name}</Typography>
                            <Typography><strong>SKU:</strong> {item.sku}</Typography>
                            <Typography><strong>Category:</strong> {item.category}</Typography>
                            <Typography><strong>Current Quantity:</strong> {item.quantity}</Typography>
                            <Typography><strong>Current Price:</strong> ${item.price.toFixed(2)}</Typography>
                            <Typography><strong>Supplier:</strong> {item.supplier}</Typography>
                        </Paper>
                    </Grid>
                    <Grid item xs={12} md={8}>
                        <Paper elevation={3} sx={{ p: 2 }}>
                            <Typography variant="h6" gutterBottom>History</Typography>
                            <TableContainer>
                                <Table>
                                    <TableHead>
                                        <TableRow>
                                            <TableCell>Date</TableCell>
                                            <TableCell>Action</TableCell>
                                            <TableCell>User</TableCell>
                                            <TableCell>Details</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {item.history.map((event, index) => (
                                            <TableRow key={index}>
                                                <TableCell>{event.date}</TableCell>
                                                <TableCell>{event.action}</TableCell>
                                                <TableCell>{event.user}</TableCell>
                                                <TableCell>{event.details}</TableCell>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </TableContainer>
                        </Paper>
                    </Grid>
                </Grid>
            </Container>
        </Box>
    );
};

export default InventoryHistoryPage;
