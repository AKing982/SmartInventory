import {useEffect, useState} from "react";
import {cleanup} from "@testing-library/react";
import {Box} from "@mui/material";

const LoadingText: React.FC = () => {
    const [dots, setDots] = useState('');

    useEffect(() => {
        const interval = setInterval(() => {
            setDots(prevDots => {
                switch(prevDots){
                    case '':
                        return '.';
                    case '.':
                        return "..";
                    case '..':
                        return "...";
                    default:
                        return '';
                }
            });
        }, 500);

        return () => clearInterval(interval);
    }, []);

    return (
        <Box sx={{mt: 2, color: 'white', fontWeight: 'bold', fontSize: '1.2rem', minWidth: '80px', textAlign: 'left'}}>
            Loading{dots}
        </Box>
    );
};

export default LoadingText;