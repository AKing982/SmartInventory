import {Box, CircularProgress, useTheme} from "@mui/material";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {keyframes} from "@emotion/react";
import LoadingText from "./LoadingText";

interface LoadingScreenProps {
    redirectDelay?: number;
}

const pulse = keyframes`
  0% {
    transform: scale(0.8);
    opacity: 0.7;
  }
  50% {
    transform: scale(1.2);
    opacity: 1;
  }
  100% {
    transform: scale(0.8);
    opacity: 0.7;
  }
`;


const LoadingScreen: React.FC<LoadingScreenProps> = ({ redirectDelay = 6000 }) => {
    const theme = useTheme();
    const navigate = useNavigate();


    const handleRoleNavigation = (role: string | null) : void => {
        switch(role){
            case "ROLE_EMPLOYEE":
                navigate('/home');
                break;
            case "ROLE_USER":
                navigate('/catalog');
                break;
            case "ROLE_SUPPLIER":
                navigate('/supplier/dashboard');
                break;
            default:
                console.warn(`No such role: ${role}`);
                navigate('/');
                break;
        }
    }

    useEffect(() => {
        const redirectTimer = setTimeout(() => {
            const userRole = localStorage.getItem('authority');
            handleRoleNavigation(userRole);
        }, redirectDelay);

        return () => {
            clearTimeout(redirectTimer);
        };
    }, [navigate, redirectDelay]);

    return (
        <Box
            sx={{
                display: 'flex',
                flexDirection: 'column',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100vh',
                width: '100vw',
                background: `linear-gradient(45deg, ${theme.palette.primary.main} 30%, ${theme.palette.secondary.main} 90%)`,
                transition: 'background 0.5s ease-in-out',
            }}
        >
            <Box
                sx={{
                    animation: `${pulse} 1.5s ease-in-out infinite`,
                    backgroundColor: 'rgba(255, 255, 255, 0.2)',
                    borderRadius: '50%',
                    padding: '20px',
                }}
            >
                <CircularProgress size={60} sx={{ color: 'white' }} />
            </Box>
            <LoadingText />
        </Box>
    );
};

export default LoadingScreen;
