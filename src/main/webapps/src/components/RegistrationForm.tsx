import {Backdrop, CircularProgress, FormControlLabel, Link, Paper, styled} from "@mui/material";
import { Container } from "@mui/material";
import { Button } from "@mui/material";
import { Checkbox } from "@mui/material";
import { MenuItem } from "@mui/material";
import { Grid } from "@mui/material";
import {Box, TextField} from "@mui/material";
import {Typography} from "@mui/material";
import {Link as RouterLink, useNavigate} from 'react-router-dom';
import React, { useState } from "react";
import {registerUser, Registration} from '../api/RegistrationApiService';
import {roleItems, RoleType} from '../items/Items';
import backgroundImage from "../images/pexels-tiger-lily-4483610.jpg";


interface FormErrors {
    password?: string;
    firstName?: string;
    lastName?: string;
    email?: string;
    username?: string;
    confirmPassword?: string;
    companyName?: string;
    jobTitle?: string;
    role?: string;
    agreeToTerms?: string;
}

interface FormData {
    password: string;
    firstName: string;
    lastName: string;
    email: string;
    username: string;
    confirmPassword: string;
    companyName: string;
    jobTitle: string;
    role: RoleType | '';
    agreeToTerms: boolean;
}


interface RegistrationFormData {
    firstName: string;
    lastName: string;
    email: string;
    password: string;
    username: string;
    confirmPassword: string;
    companyName: string;
    jobTitle: string;
    role: RoleType;
    agreeToTerms: boolean;
}

interface FormErrors {
    usernameOrEmail?: string;
    password?: string;
    firstName?: string;
    lastName?: string;
    email?: string;
    username?: string;
    confirmPassword?: string;
    companyName?: string;
    jobTitle?: string;
    role?: string;
    agreeToTerms?: string;
}


const StyledPaper = styled(Paper)(({ theme }) => ({
    backgroundColor: 'rgba(255, 255, 255, 0.8)', // Semi-transparent white
    padding: theme.spacing(4),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    backdropFilter: 'blur(10px)', // Add a blur effect
}));

const BackgroundContainer = styled('div')({
    backgroundImage: `url(${backgroundImage})`,
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    minHeight: '120vh',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
});



const RegistrationForm: React.FC = () => {
    const [formData, setFormData] = useState<FormData>({
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

    const [errors, setErrors] = useState<FormErrors>({});
    const [isLoading, setIsLoading] = useState<boolean>(false);
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
        const newErrors: FormErrors = {};
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

    const handleBackToLogin = () => {
        navigate('/');
    }

    const createRegistrationRequest = (formData: FormData) : Registration => {
        return {
            firstName: formData.firstName,
            lastName: formData.lastName,
            email: formData.email,
            username: formData.username,
            password: formData.password,
            companyName: formData.companyName,
            jobTitle: formData.jobTitle,
            role: formData.role as RoleType
        }
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (validateForm()) {
            setIsLoading(true);
            try
            {
                if(!formData.role){
                    throw new Error('Role is required for registration');
                }
                console.log('Registration data:', formData);
                let request = createRegistrationRequest(formData);
                const response = await registerUser(request);
                await new Promise(resolve => setTimeout(resolve, 6000));
                console.log('Registration successful');
                navigate('/');

            }catch(error)
            {
                console.error('Error: ', error);
            }finally{
                setIsLoading(false);
            }
        }
    };

    return (
        <BackgroundContainer>


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
                                <MenuItem value="" disabled>Select a role</MenuItem>
                                {roleItems.map((roleItem) => (
                                    <MenuItem key={roleItem.role} value={roleItem.role}>
                                        {roleItem.item.roleName}
                                    </MenuItem>
                                ))}
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
                        <Grid item xs={12}>
                            <Button
                                type="submit"
                                fullWidth
                                variant="contained"
                                sx={{ mt: 3, mb: 2 }}
                            >
                                Register
                            </Button>
                        </Grid>
                        <Grid item xs={12}>
                            <Link component="button" variant="body2" onClick={handleBackToLogin}>
                                Already have an account? Sign in
                            </Link>
                        </Grid>
                    </Grid>
                </Box>
            </Paper>
            <Backdrop
                sx={{color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1}}
                open={isLoading}
            >
                <CircularProgress color="inherit"/>
            </Backdrop>
        </Container>
        </BackgroundContainer>
    );
};

export default RegistrationForm;