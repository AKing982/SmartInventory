import React from 'react';
import {
    Paper,
    Typography,
    TextField,
    Button,
    Box, FormControlLabel, Switch, Divider, Grid
} from '@mui/material';

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


interface PlaceOrderFormProps{
    orderForm: OrderForm;
    handleInputChange: (field: keyof OrderForm, value: string | number | boolean | Date) => void;
    handleSubmit: () => void;
}

const PlaceOrderForm: React.FC<PlaceOrderFormProps> = ({
                                                           orderForm,
                                                           handleInputChange,
                                                           handleSubmit
                                                       }) => {
    return (
        <Paper elevation={3} sx={{ p: 3, bgcolor: '#f0f0f0' }}>
            <Typography variant="h6" sx={{ mb: 2 }}>Order Summary</Typography>
            <Divider sx={{ mb: 2 }} />

            <Grid container spacing={2}>
                <Grid item xs={6}>
                    <Typography variant="body2" color="text.secondary">Order Number</Typography>
                    <Typography variant="body1">{orderForm.orderNumber}</Typography>
                </Grid>
                <Grid item xs={6}>
                    <Typography variant="body2" color="text.secondary">Product Name</Typography>
                    <Typography variant="body1">{orderForm.productName}</Typography>
                </Grid>
                <Grid item xs={12}>
                    <Typography variant="body2" color="text.secondary">Description</Typography>
                    <Typography variant="body1">{orderForm.description || 'N/A'}</Typography>
                </Grid>
                <Grid item xs={6}>
                    <Typography variant="body2" color="text.secondary">Unit Price</Typography>
                    <Typography variant="body1">${orderForm.orderAmount.toFixed(2)}</Typography>
                </Grid>
                <Grid item xs={6}>
                    <TextField
                        fullWidth
                        label="Quantity"
                        type="number"
                        value={orderForm.orderQuantity}
                        onChange={(e) => handleInputChange('orderQuantity', Number(e.target.value))}
                        variant="outlined"
                        size="small"
                    />
                </Grid>
                <Grid item xs={12}>
                    <TextField
                        fullWidth
                        label="Order Date"
                        type="date"
                        value={orderForm.orderDate.toISOString().split('T')[0]}
                        onChange={(e) => handleInputChange('orderDate', new Date(e.target.value))}
                        InputLabelProps={{
                            shrink: true,
                        }}
                        variant="outlined"
                        size="small"
                    />
                </Grid>
            </Grid>

            <Divider sx={{ my: 2 }} />

            <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
                <Typography variant="h6">Total Amount</Typography>
                <Typography variant="h6">${(orderForm.orderAmount * orderForm.orderQuantity).toFixed(2)}</Typography>
            </Box>
            <Button
                fullWidth
                variant="contained"
                color="primary"
                onClick={handleSubmit}
                sx={{
                    bgcolor: '#4CAF50',
                    '&:hover': { bgcolor: '#45a049' }
                }}
            >
                Confirm Order
            </Button>
        </Paper>
    );
};

export default PlaceOrderForm;