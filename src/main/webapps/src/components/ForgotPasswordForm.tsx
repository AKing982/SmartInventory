import React, { useState } from 'react';
import {
    Button,
    TextField,
    Typography,
    Container,
    Box,
    Paper,
    Alert,
    CircularProgress, styled
} from '@mui/material';
import EmailIcon from '@mui/icons-material/Email';
import { Link as MuiLink } from '@mui/material';
import {useNavigate} from "react-router-dom";
import backgroundImage from "../images/pexels-tiger-lily-4483610.jpg";

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


const ForgotPasswordForm: React.FC = () => {
    const [email, setEmail] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState(false);
    const [isLoading, setIsLoading] = useState(false);
    const navigate = useNavigate();

    const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setEmail(e.target.value);
        setError('');
    };

    const validateEmail = (email: string): boolean => {
        const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return re.test(email);
    };

    const handleBackToLogin = () => {
        navigate('/');
    }

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (!email) {
            setError('Email is required');
            return;
        }
        if (!validateEmail(email)) {
            setError('Please enter a valid email address');
            return;
        }

        setIsLoading(true);
        try {
            // Here you would typically send a request to your backend to initiate the password reset process
            // For this example, we'll simulate an API call with a timeout
            await new Promise(resolve => setTimeout(resolve, 1500));

            setSuccess(true);
            setEmail('');
        } catch (err) {
            setError('An error occurred. Please try again later.');
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <BackgroundContainer>
        <Container component="main" maxWidth="xs">
            <Paper elevation={6} sx={{ mt: 8, p: 4 }}>
                <Box
                    sx={{
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                    }}
                >
                    <EmailIcon sx={{ m: 1, color: 'primary.main', fontSize: 40 }} />
                    <Typography component="h1" variant="h5">
                        Forgot Password
                    </Typography>
                    <Typography variant="body2" sx={{ mt: 1, mb: 3, textAlign: 'center' }}>
                        Enter your email address and we'll send you instructions to reset your password.
                    </Typography>
                    <Box component="form" onSubmit={handleSubmit} noValidate sx={{ width: '100%' }}>
                        <TextField
                            margin="normal"
                            required
                            fullWidth
                            id="email"
                            label="Email Address"
                            name="email"
                            autoComplete="email"
                            autoFocus
                            value={email}
                            onChange={handleEmailChange}
                            error={!!error}
                            helperText={error}
                            disabled={isLoading}
                        />
                        {success && (
                            <Alert severity="success" sx={{ mt: 2 }}>
                                If an account exists for {email}, you will receive password reset instructions.
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
                        <Box sx={{ display: 'flex', justifyContent: 'space-between', mt: 2 }}>
                            <MuiLink component="button" variant="body2" onClick={handleBackToLogin}>
                                Back to Login
                            </MuiLink>
                        </Box>
                    </Box>
                </Box>
            </Paper>
        </Container>
        </BackgroundContainer>
    );
};

export default ForgotPasswordForm;