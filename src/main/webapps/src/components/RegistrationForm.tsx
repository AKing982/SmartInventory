import {FormControlLabel, Paper} from "@mui/material";
import { Container } from "@mui/material";
import { Button } from "@mui/material";
import { Checkbox } from "@mui/material";
import { MenuItem } from "@mui/material";
import { Grid } from "@mui/material";
import {Box, TextField} from "@mui/material";
import {Typography} from "@mui/material";
import {Link as RouterLink, useNavigate} from 'react-router-dom';
import { useState } from "react";
import {registerUser, Registration} from '../api/RegistrationApiService';


interface RegistrationFormData {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    username: string;
    confirmPassword: string;
    companyName: string;
    jobTitle: string;
    role: string;
    agreeToTerms: boolean;
}

const RegistrationForm: React.FC = () => {
    const [formData, setFormData] = useState<RegistrationFormData>({
        firstName: '',
        lastName: '',
        email: '',
        password: '',
        confirmPassword: '',
        companyName: '',
        jobTitle: '',
        username: '',
        role: '',
        agreeToTerms: false,
    });

    const [errors, setErrors] = useState<Partial<RegistrationFormData>>({});
    const navigate = useNavigate();
    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value, checked } = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: name === 'agreeToTerms' ? checked : value,
        }));
        // Clear the error when the user starts typing
        if (errors[name as keyof RegistrationFormData]) {
            setErrors(prevErrors => ({ ...prevErrors, [name]: undefined }));
        }
    };

    const validateForm = (): boolean => {
        const newErrors: Partial<RegistrationFormData> = {};
        if (!formData.firstName) newErrors.firstName = 'First name is required';
        if (!formData.lastName) newErrors.lastName = 'Last name is required';
        if (!formData.email) newErrors.email = 'Email is required';
        else if (!/\S+@\S+\.\S+/.test(formData.email)) newErrors.email = 'Email is invalid';
        if (!formData.password) newErrors.password = 'Password is required';
        else if (formData.password.length < 8) newErrors.password = 'Password must be at least 8 characters';
        if (formData.password !== formData.confirmPassword) newErrors.confirmPassword = 'Passwords do not match';
        if (!formData.companyName) newErrors.companyName = 'Company name is required';
        if (!formData.jobTitle) newErrors.jobTitle = 'Job title is required';
        if (!formData.role) newErrors.role = 'Role is required';
        // if (!formData.agreeToTerms) newErrors.agreeToTerms = 'You must agree to the terms and conditions';

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (validateForm()) {
            try
            {
                // Here you would typically send the registration data to your backend
            //    const response = await registerUser(formData);
            //    console.log('Response: ', response);
                console.log('Registration data:', formData);
                navigate('/');

            }catch(error)
            {
                console.error('Error: ', error);
            }
            // Handle successful registration (e.g., redirect to login page or dashboard)
        }
    };

    return (
        <Container component="main" maxWidth="md">
            <Paper elevation={6} sx={{ mt: 8, p: 4 }}>
                <Typography component="h1" variant="h4" align="center" gutterBottom>
                    Register for SmartInventory
                </Typography>
                <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 3 }}>
                    <Grid container spacing={2}>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                required
                                fullWidth
                                label="First Name"
                                name="firstName"
                                value={formData.firstName}
                                onChange={handleInputChange}
                                error={!!errors.firstName}
                                helperText={errors.firstName}
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                required
                                fullWidth
                                label="Last Name"
                                name="lastName"
                                value={formData.lastName}
                                onChange={handleInputChange}
                                error={!!errors.lastName}
                                helperText={errors.lastName}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                label="Email Address"
                                name="email"
                                type="email"
                                value={formData.email}
                                onChange={handleInputChange}
                                error={!!errors.email}
                                helperText={errors.email}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                label="Username"
                                name="username"
                                value={formData.username}
                                onChange={handleInputChange}
                                error={!!errors.username}
                                helperText={errors.username}
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                required
                                fullWidth
                                label="Password"
                                name="password"
                                type="password"
                                value={formData.password}
                                onChange={handleInputChange}
                                error={!!errors.password}
                                helperText={errors.password}
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                required
                                fullWidth
                                label="Confirm Password"
                                name="confirmPassword"
                                type="password"
                                value={formData.confirmPassword}
                                onChange={handleInputChange}
                                error={!!errors.confirmPassword}
                                helperText={errors.confirmPassword}
                            />
                        </Grid>
                        <Grid item xs={12}>
                            <TextField
                                required
                                fullWidth
                                label="Company Name"
                                name="companyName"
                                value={formData.companyName}
                                onChange={handleInputChange}
                                error={!!errors.companyName}
                                helperText={errors.companyName}
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                required
                                fullWidth
                                label="Job Title"
                                name="jobTitle"
                                value={formData.jobTitle}
                                onChange={handleInputChange}
                                error={!!errors.jobTitle}
                                helperText={errors.jobTitle}
                            />
                        </Grid>
                        <Grid item xs={12} sm={6}>
                            <TextField
                                select
                                required
                                fullWidth
                                label="Role"
                                name="role"
                                value={formData.role}
                                onChange={handleInputChange}
                                error={!!errors.role}
                                helperText={errors.role}
                            >
                                <MenuItem value="admin">Administrator</MenuItem>
                                <MenuItem value="manager">Inventory Manager</MenuItem>
                                <MenuItem value="user">Regular User</MenuItem>
                            </TextField>
                        </Grid>
                        <Grid item xs={12}>
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        name="agreeToTerms"
                                        checked={formData.agreeToTerms}
                                        onChange={handleInputChange}
                                        color="primary"
                                    />
                                }
                                label="I agree to the terms and conditions"
                            />
                            {errors.agreeToTerms && (
                                <Typography color="error" variant="caption" display="block">
                                    {errors.agreeToTerms}
                                </Typography>
                            )}
                        </Grid>
                    </Grid>
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                    >
                        Register
                    </Button>
                    <Grid container justifyContent="flex-end">
                        <Grid item>
                            <RouterLink to="/">
                                Already have an account? Sign in
                            </RouterLink>
                        </Grid>
                    </Grid>
                </Box>
            </Paper>
        </Container>
    );
};

export default RegistrationForm;