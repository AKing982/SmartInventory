import {
    Alert, Backdrop,
    Box,
    Button, Checkbox, CircularProgress,
    Container, FormControlLabel,
    Grid,
    IconButton,
    InputAdornment,
    Link, Menu, MenuItem, Paper, styled,
    TextField,
    Typography
} from "@mui/material";
import React, {useState} from "react"
import {Link as RouterLink, useNavigate} from 'react-router-dom';
import InventoryIcon from '@mui/icons-material/Inventory';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import {authenticateUser, LoginCredentials, AuthenticationResponse} from '../api/LoginApiService';
import backgroundImage from '../images/pexels-tiger-lily-4483610.jpg';
import EmailIcon from "@mui/icons-material/Email";
import ForgotPasswordForm from "./ForgotPasswordForm";
import RegistrationForm from "./RegistrationForm";
import {registerUser} from "../api/RegistrationApiService";

interface LoginFormData
{
    usernameOrEmail: string;
    password: string;
}

interface FormErrors{
    usernameOrEmail?: string;
    password?: string;
}

const BackgroundContainer = styled('div')({
    backgroundImage: `url(${backgroundImage})`,
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    minHeight: '120vh',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
});

type ViewType = 'login' | 'forgotPassword' | 'register';

const StyledPaper = styled(Paper)(({ theme }) => ({
    backgroundColor: 'rgba(255, 255, 255, 0.8)', // Semi-transparent white
    padding: theme.spacing(4),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    backdropFilter: 'blur(10px)', // Add a blur effect
}));

interface FormData {
    usernameOrEmail: string;
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

export interface Registration
{
    firstName: string;
    lastName: string;
    email?: string;
    password: string;
    companyName?: string;
    jobTitle: string;
    username: string;
    role: RoleType;
}

enum RoleType {
    ROLE_SUPPLIER = "ROLE_SUPPLIER",
    ROLE_USER = "ROLE_USER",
    ROLE_EMPLOYEE = "ROLE_EMPLOYEE",
    ROLE_MANAGER = "ROLE_MANAGER",
    ROLE_ADMIN = "ROLE_ADMIN"
}

interface RoleModel {
    role: RoleType;
    item: RoleItem;
}

interface RoleItem {
    roleName: string;
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

const roleItems : RoleModel[] = [
    { role: RoleType.ROLE_SUPPLIER, item: { roleName: 'Inventory Supplier' } },
    { role: RoleType.ROLE_ADMIN, item: { roleName: 'Administrator' } },
    { role: RoleType.ROLE_USER, item: { roleName: 'Regular User' } },
    { role: RoleType.ROLE_EMPLOYEE, item: { roleName: 'Inventory Specialist' } },
    { role: RoleType.ROLE_MANAGER, item: { roleName: 'Inventory Manager' } }
]

function extractRole(roles: string[] | Array<{authority: string}>): string {
    if(roles.length === 0){
        return 'ROLE_GUEST';
    }
    const firstRole = roles[0];
    if(typeof firstRole === 'string'){
        return firstRole;
    }else if(typeof firstRole === 'object' && 'authority' in firstRole){
        return firstRole.authority;
    }
    return 'ROLE_GUEST';
}

const LoginForm: React.FC = () => {
    const [formData, setFormData] = useState<FormData>({
        usernameOrEmail: '',
        password: '',
        firstName: '',
        lastName: '',
        email: '',
        username: '',
        confirmPassword: '',
        companyName: '',
        jobTitle: '',
        role: '',
        agreeToTerms: false,
    });

    const [showRegistrationForm, setShowRegistrationForm] = useState<boolean>(false);
    const [showForgotPasswordForm, setShowForgotPasswordForm] = useState<boolean>(false);
    const [errors, setErrors] = useState<FormErrors>({});
    const [submitError, setSubmitError] = useState<string | null>(null);
    const [showPassword, setShowPassword] = useState<boolean>(false);
    const navigate = useNavigate();

    const [showForgotPassword, setShowForgotPassword] = useState<boolean>(false);
    const [email, setEmail] = useState('');
    const [forgotPasswordError, setForgotPasswordError] = useState<string>('');
    const [forgotPasswordSuccess, setForgotPasswordSuccess] = useState<boolean>(false);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [view, setView] = useState<ViewType>('login');

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        if (name === 'role') {
            console.log('Role selected:', value);
            setFormData(prevData => ({
                ...prevData,
                [name]: value as RoleType  // Ensure it's cast to RoleType
            }));
        } else {
            setFormData(prevData => ({
                ...prevData,
                [name]: value
            }));
        }
    };


    // Validation
    const validateForm = (): boolean => {
        const newErrors: FormErrors = {};
        if(view === 'register'){
            if (!formData.firstName) newErrors.firstName = 'First name is required';
            if (!formData.lastName) newErrors.lastName = 'Last name is required';
            if (!formData.email) newErrors.email = 'Email is required';
            if (!formData.username) newErrors.username = 'Username is required';
            if (!formData.password) newErrors.password = 'Password is required';
            if (formData.password !== formData.confirmPassword) newErrors.confirmPassword = 'Passwords do not match';
            if (!formData.companyName) newErrors.companyName = 'Company name is required';
            if (!formData.jobTitle) newErrors.jobTitle = 'Job title is required';
            if (!formData.role) newErrors.role = 'Role is required';
            if (!formData.agreeToTerms) newErrors.agreeToTerms = 'You must agree to the terms and conditions';
        }
        if (!formData.usernameOrEmail) {
            newErrors.usernameOrEmail = 'Email is required';
        }
        if (!formData.password) {
            newErrors.password = 'Password is required';
        } else if (formData.password.length < 6) {
            newErrors.password = 'Password must be at least 6 characters';
        }
        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const createRegistrationRequest = (formData: FormData) : Registration => {
        console.log('Creating registration request with role:', formData.role);
        console.log('Available roles:', roleItems.map(item => item.role));
        const roleModel = roleItems.find(item => item.item.roleName === formData.role);
        console.log('Role Model: ', roleModel);

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


    // Update the handleSubmit function
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (validateForm()) {
            setIsLoading(true);
            try {
                console.log('View: ', view);
                const loginData: LoginCredentials = {
                    usernameOrEmail: formData.usernameOrEmail,
                        password: formData.password
                };
                const response = await authenticateUser(loginData);
                let role = extractRole(response.roles);
                console.log('Role: ', role);
                console.log('Authentication Successful: ', response);
                localStorage.setItem('authority', role);
                localStorage.setItem('token', response.token);
                localStorage.setItem('user', response.username);

                await new Promise(resolve => setTimeout(resolve, 3000));
                navigate('/loading');
                setSubmitError(null);
            } catch (err) {
                console.error(err);
            }finally {
                setIsLoading(false);
            }
        }
    };

    const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(e.target.value);
        setForgotPasswordError('');
    };

    const validateEmail = (email: string): boolean => {
        const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return re.test(email);
    };

    // Error handling
    const handleLoginError = (err: any) => {
        console.error('Login error:', err);

        const userErrorMessage = 'Invalid username or password. Please try again.';

        if (err.response) {
            if (err.response.status === 401) {
                setSubmitError(userErrorMessage);
            } else {
                setSubmitError('An unexpected error occurred. Please try again later.');
            }
        } else if (err.request) {
            setSubmitError('Unable to reach the server. Please check your internet connection and try again.');
        } else {
            setSubmitError('An unexpected error occurred. Please try again later.');
        }
    };


    const handleForgotPasswordSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!email) {
            setForgotPasswordError('Email is required');
            return;
        }
        if (!validateEmail(email)) {
            setForgotPasswordError('Please enter a valid email address');
            return;
        }

        setIsLoading(true);
        try {
            // Simulate API call
            await new Promise(resolve => setTimeout(resolve, 1500));
            setForgotPasswordSuccess(true);
            setEmail('');
        } catch (err) {
            setForgotPasswordError('An error occurred. Please try again later.');
        } finally {
            setIsLoading(false);
        }
    };

    const toggleForgotPassword = () => {
        setShowForgotPassword(!showForgotPassword);
        setForgotPasswordError('');
        setForgotPasswordSuccess(false);
        setEmail('');
    };

    const handleRegisterPage = () => {
        navigate('/register');
    }

    const handleForgotPassword = () => {
        navigate('/forgot-password')
    }


    const handleClickShowPassword = () => {
        setShowPassword(!showPassword);
    };

    return (
        <BackgroundContainer>
            <Container component="main" maxWidth="sm">
                <StyledPaper elevation={6}>
                    <InventoryIcon sx={{ fontSize: 50, color: 'primary.main', mb: 2 }} />
                    <Typography component="h1" variant="h5" sx={{ mb: 3 }}>
                        Welcome to SmartInventory Management System
                    </Typography>
                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1, width: '100%' }}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="usernameOrEmail"
                            label="Username or Email"
                            name="usernameOrEmail"
                            autoComplete="username email"
                            autoFocus
                            value={formData.usernameOrEmail}
                            onChange={handleInputChange}
                            error={!!errors.usernameOrEmail}
                            helperText={errors.usernameOrEmail}
                        />
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            name="password"
                            label="Password"
                            type="password"
                            id="password"
                            autoComplete="current-password"
                            value={formData.password}
                            onChange={handleInputChange}
                            error={!!errors.password}
                            helperText={errors.password}
                        />
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            sx={{ mt: 3, mb: 2 }}
                        >
                            Sign In
                        </Button>
                        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mt: 2 }}>
                            <Link component="button" variant="body2" onClick={handleForgotPassword}>
                                Forgot password?
                            </Link>
                            <Button
                                variant="outlined"
                                size="small"
                                onClick={handleRegisterPage}
                            >
                                Create Account
                            </Button>
                        </Box>
                    </Box>
                    {submitError && (
                        <Alert severity="error" sx={{ mt: 2, width: '100%' }}>
                            {submitError}
                        </Alert>
                    )}
                </StyledPaper>
            </Container>
            <Backdrop
                sx={{color: '#fff', zIndex: (theme) => theme.zIndex.drawer + 1}}
                open={isLoading}
            >
                <CircularProgress color="inherit"/>
            </Backdrop>
        </BackgroundContainer>
    );
};

export default LoginForm;
