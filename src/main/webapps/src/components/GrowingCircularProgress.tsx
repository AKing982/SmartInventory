import {useEffect, useState} from "react";
import {Box, CircularProgress} from "@mui/material";

const GrowingCircularProgress = () => {
    const [size, setSize] = useState(40);
    const maxSize = 100;
    const growthRate = 1;

    useEffect(() => {
        const timer = setInterval(() => {
            setSize((prevSize) => {
                if (prevSize >= maxSize) {
                    return 40; // Reset to initial size
                }
                return prevSize + growthRate;
            });
        }, 50);

        return () => {
            clearInterval(timer);
        };
    }, []);

    return (
        <Box
            sx={{
                display: 'flex',
                justifyContent: 'center',
                alignItems: 'center',
                height: '100vh',
                bgcolor: 'background.default',
            }}
        >
            <CircularProgress size={size} />
        </Box>
    );
};

export default GrowingCircularProgress;