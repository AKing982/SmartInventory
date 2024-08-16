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

    // const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    //     const {name, value} = e.target;
    //     setFormData(prevData => ({
    //         ...prevData,
    //         [name]:value
    //     }));
    //     console.log(`Updated ${name}:`, value);
    //
    //     if(errors[name as keyof FormErrors])
    //     {
    //         setErrors(prevErrors => ({...prevErrors, [name]: undefined}))
    //     }
    // };

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
        // if(!roleModel){
        //     console.error('Role not found. FormData role:', formData.role);
        //     console.error('RoleItems:', JSON.stringify(roleItems, null, 2));
        //     throw new Error(`Invalid Role: ${formData.role}`);
        // }

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
                if (view === 'login') {
                    const loginData: LoginCredentials = {
                        usernameOrEmail: formData.usernameOrEmail,
                        password: formData.password
                    };
                    const response = await authenticateUser(loginData);
                    console.log('Authentication Successful: ', response);
                    localStorage.setItem('token', response.token);
                    localStorage.setItem('user', response.username);

                    await new Promise(resolve => setTimeout(resolve, 3000));

                    navigate('/home');
                } else if (view === 'forgotPassword') {
                    setIsLoading(true);
                    // Implement forgot password logic
                    await new Promise(resolve => setTimeout(resolve, 8000));
                    setForgotPasswordSuccess(true);
                } else if (view === 'register') {
                    console.log('Registering user');
                    if(!formData.role){
                        throw new Error("Role is required for registration.");
                    }

                    let request = createRegistrationRequest(formData);
                    console.log('Registration Request: ', request);
                    const response = await registerUser(request);
                    await new Promise(resolve => setTimeout(resolve, 8000));

                    console.log('Registration successful:', response);
                    setView('login');
                }
                setSubmitError(null);
            } catch (err) {
                console.error(err);
            }finally {
                setIsLoading(false);
            }
        }
    };

    // Form submission
    // const handleSubmit = async (e: React.FormEvent) => {
    //     e.preventDefault();
    //     if (validateForm()) {
    //         try {
    //
    //             console.log('Authenticating Username: ', formData.usernameOrEmail);
    //             const response = await authenticateUser(formData);
    //             console.log('Authentication Successful: ', response);
    //             localStorage.setItem('token', response.token);
    //             localStorage.setItem('user', response.username);
    //             // Simulating API call
    //             console.log('Logging in with:', formData);
    //             // Here you would typically make an API call to your backend
    //             // await loginUser(formData.email, formData.password);
    //             setSubmitError(null);
    //
    //             navigate('/home');
    //             // Handle successful login (e.g., redirect)
    //         } catch (err) {
    //             handleLoginError(err);
    //         }
    //     }
    // };

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


    const handleClickShowPassword = () => {
        setShowPassword(!showPassword);
    };

    const renderLoginForm = () => (
        <>
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
                                onClick={() => setShowPassword(!showPassword)}
                                edge="end"
                            >
                                {showPassword ? <VisibilityOff /> : <Visibility />}
                            </IconButton>
                        </InputAdornment>
                    )
                }}
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
                <Link component="button" variant="body2" onClick={() => setView('forgotPassword')}>
                    Forgot password?
                </Link>
                <Button
                    variant="outlined"
                    size="small"
                    onClick={() => setView('register')}
                >
                    Create Account
                </Button>
            </Box>
        </>
    );



    const renderForgotPasswordForm = () => (
        <>
            <Typography variant="body2" sx={{ mt: 1, mb: 3, textAlign: 'center' }}>
                Enter your email address and we'll send you instructions to reset your password.
            </Typography>
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
                disabled={isLoading}
            />
            {forgotPasswordSuccess && (
                <Alert severity="success" sx={{ mt: 2 }}>
                    If an account exists for {formData.email}, you will receive password reset instructions.
                </Alert>
            )}
            <Button
                type="submit"
                fullWidth
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
                disabled={isLoading}
            >
                {isLoading ? <CircularProgress size={24} /> : 'Reset Password'}
            </Button>
            <Link component="button" variant="body2" onClick={() => setView('login')}>
                Back to Login
            </Link>
        </>
    );

    const renderRegistrationForm = () => (
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
                    {/*{roleItems.map((roleModel) => (*/}
                    {/*    <MenuItem key={roleModel.role} value={roleModel.role}>*/}
                    {/*        {roleModel.item.roleName}*/}
                    {/*    </MenuItem>*/}
                    {/*))}*/}
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
                <Link component="button" variant="body2" onClick={() => setView('login')}>
                    Already have an account? Sign in
                </Link>
            </Grid>
        </Grid>
    );

    return (
        <BackgroundContainer>
            <Container component="main" maxWidth="sm">
                <StyledPaper elevation={6}>
                    <InventoryIcon sx={{ fontSize: 50, color: 'primary.main', mb: 2 }} />
                    <Typography component="h1" variant="h5" sx={{ mb: 3 }}>
                        {view === 'login' && 'Welcome to SmartInventory Management System'}
                        {view === 'forgotPassword' && 'Forgot Password'}
                        {view === 'register' && 'Create an Account'}
                    </Typography>
                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1, width: '100%' }}>
                        {view === 'login' && renderLoginForm()}
                        {view === 'forgotPassword' && renderForgotPasswordForm()}
                        {view === 'register' && renderRegistrationForm() }
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
