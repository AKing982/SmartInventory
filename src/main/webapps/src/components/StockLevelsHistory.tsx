// StockHistory.tsx
import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import {
    Box,
    Typography,
    Paper,
    Button,
    styled
} from '@mui/material';
import { LineChart, Line, XAxis, YAxis, Tooltip, ResponsiveContainer, Legend } from 'recharts';
import DatePicker from 'react-datepicker';
import ArrowBackIcon from '@mui/icons-material/ArrowBack';
import MainAppBar from './MainAppBar';

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


// ... (keep the relevant interfaces)
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


const StockHistory: React.FC = () => {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();
    const [item, setItem] = useState<InventoryItemWithHistory | null>(null);
    const [startDate, setStartDate] = useState<Date | undefined>(new Date(new Date().setMonth(new Date().getMonth() - 1)));
    const [endDate, setEndDate] = useState<Date | undefined>(new Date());

    useEffect(() => {
        // In a real application, fetch the item data based on the id
        // For now, we'll use dummy data
        const dummyItem: InventoryItemWithHistory = {
            id: parseInt(id!),
            name: 'Dummy Item',
            sku: 'SKU001',
            category: 'Electronics',
            quantity: 50,
            price: 99.99,
            supplier: 'Supplier Inc',
            status: 'In Stock',
            history: generateDummyHistory(50)
        };
        setItem(dummyItem);
    }, [id]);

    const generateDummyHistory = (currentQuantity: number): StockHistory[] => {
        // ... (keep the same implementation as in StockLevels)
        return [];
    };

    const handleStartDateChange = (date: Date | null) => {
        setStartDate(date || undefined);
    };

    const handleEndDateChange = (date: Date | null) => {
        setEndDate(date || undefined);
    };

    const handleBack = () => {
        navigate('/stock-levels');
    };

    if (!item) {
        return <Typography>Loading...</Typography>;
    }

    return (
        <BackgroundContainer>
            <MainAppBar title={`Stock History - ${item.name}`} />
            <ContentContainer>
                <Button startIcon={<ArrowBackIcon />} onClick={handleBack} sx={{ mb: 2 }}>
                    Back to Stock Levels
                </Button>
                <Paper elevation={3} sx={{ p: 2, mb: 4 }}>
                    <Typography variant="h6" gutterBottom>
                        Stock History for {item.name}
                    </Typography>
                    <Box sx={{ mb: 2 }}>
                        <DatePicker
                            selected={startDate}
                            onChange={handleStartDateChange}
                            selectsStart
                            startDate={startDate}
                            endDate={endDate}
                            placeholderText="Start Date"
                        />
                        <DatePicker
                            selected={endDate}
                            onChange={handleEndDateChange}
                            selectsEnd
                            startDate={startDate}
                            endDate={endDate}
                            minDate={startDate}
                            placeholderText="End Date"
                        />
                    </Box>
                    <ResponsiveContainer width="100%" height={400}>
                        <LineChart data={item.history.filter(h =>
                            startDate && endDate && new Date(h.date) >= startDate && new Date(h.date) <= endDate
                        )}>
                            <XAxis dataKey="date" />
                            <YAxis />
                            <Tooltip />
                            <Legend />
                            <Line type="monotone" dataKey="quantity" stroke="#8884d8" />
                        </LineChart>
                    </ResponsiveContainer>
                </Paper>
            </ContentContainer>
        </BackgroundContainer>
    );
};

export default StockHistory;