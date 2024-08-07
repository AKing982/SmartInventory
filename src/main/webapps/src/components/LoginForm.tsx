import {
    Alert,
    Box,
    Button,
    Container,
    Grid,
    IconButton,
    InputAdornment,
    Link, Paper,
    TextField,
    Typography
} from "@mui/material";
import React, {useState} from "react"
import { Link as RouterLink } from 'react-router-dom';
import InventoryIcon from '@mui/icons-material/Inventory';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';

interface LoginFormData
{
    email: string;
    password: string;
}

interface FormErrors{
    email?: string;
    password?: string;
}

const LoginForm: React.FC = () => {
    const [formData, setFormData] = useState<LoginFormData>({
        email: '',
        password: ''
    });
    const [errors, setErrors] = useState<FormErrors>({});
    const [submitError, setSubmitError] = useState<string | null>(null);
    const [showPassword, setShowPassword] = useState<boolean>(false);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const {name, value} = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]:value
        }));

        if(errors[name as keyof FormErrors])
        {
            setErrors(prevErrors => ({...prevErrors, [name]: undefined}))
        }
    };

    // Validation
    const validateForm = (): boolean => {
        const newErrors: FormErrors = {};
        if (!formData.email) {
            newErrors.email = 'Email is required';
        } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
            newErrors.email = 'Email is invalid';
        }
        if (!formData.password) {
            newErrors.password = 'Password is required';
        } else if (formData.password.length < 6) {
            newErrors.password = 'Password must be at least 6 characters';
        }
        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    // Form submission
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (validateForm()) {
            try {
                // Simulating API call
                console.log('Logging in with:', formData);
                // Here you would typically make an API call to your backend
                // await loginUser(formData.email, formData.password);
                setSubmitError(null);
                // Handle successful login (e.g., redirect)
            } catch (err) {
                handleLoginError(err);
            }
        }
    };

    // Error handling
    const handleLoginError = (err: any) => {
        setSubmitError('Login failed. Please try again.');
        console.error(err);
    };


    const handleClickShowPassword = () => {
        setShowPassword(!showPassword);
    };

    return (
        <Container component="main" maxWidth="xs">
            <Paper elevation={6} sx={{ marginTop: 8, padding: 4, display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                <InventoryIcon sx={{ fontSize: 50, color: 'primary.main', mb: 2 }} />
                <Typography component="h1" variant="h5" sx={{ mb: 3 }}>
                    Welcome to SmartInventory Management System
                </Typography>
                <Typography component="h2" variant="h6" sx={{ mb: 2 }}>
                    Sign in to your account
                </Typography>
                <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1, width: '100%' }}>
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        id="email"
                        label="Email Address"
                        name="email"
                        autoComplete="email"
                        autoFocus
                        value={formData.email}
                        onChange={handleInputChange}
                        error={!!errors.email}
                        helperText={errors.email}
                    />
                    <TextField
                        margin="normal"
                        required
                        fullWidth
                        name="password"
                        label="Password"
                        type={showPassword ? 'text' : 'password'}
                        id="password"
                        autoComplete="current-password"
                        value={formData.password}
                        onChange={handleInputChange}
                        error={!!errors.password}
                        helperText={errors.password}
                        InputProps={{
                            endAdornment: (
                                <InputAdornment position="end">
                                    <IconButton
                                        aria-label="toggle password visibility"
                                        onClick={handleClickShowPassword}
                                        edge="end"
                                    >
                                        {showPassword ? <VisibilityOff /> : <Visibility />}
                                    </IconButton>
                                </InputAdornment>
                            )
                        }}
                    />
                    {submitError && (
                        <Alert severity="error" sx={{ mt: 2 }}>
                            {submitError}
                        </Alert>
                    )}
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}
                    >
                        Sign In
                    </Button>
                    <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mt: 2 }}>
                        <Link component={RouterLink} to="/forgot-password" variant="body2">
                            Forgot password?
                        </Link>
                        <Button
                            component={RouterLink}
                            to="/register"
                            variant="outlined"
                            size="small"
                        >
                            Create Account
                        </Button>
                    </Box>
                </Box>
            </Paper>
        </Container>
    );
};

export default LoginForm;
