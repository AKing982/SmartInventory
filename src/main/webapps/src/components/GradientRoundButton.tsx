import {ButtonProps, styled} from "@mui/material";
import { Button } from '@mui/material';

interface GradientRoundButtonProps extends ButtonProps{
    lightColor?: string;
    darkColor?: string;
}

const StyledButton = styled(Button, {
    shouldForwardProp: (prop) => prop !== 'lightColor' && prop !== 'darkColor',
})<GradientRoundButtonProps>(({ theme, lightColor, darkColor }) => ({
    borderRadius: '50px',
    padding: '10px 20px',
    background: `linear-gradient(45deg, ${lightColor || '#757575'} 30%, ${darkColor || '#212121'} 90%)`,
    color: theme.palette.common.white,
    boxShadow: '0 3px 5px 2px rgba(33, 33, 33, .3)',
    textTransform: 'none',
    fontWeight: 500,
    '&:hover': {
        background: `linear-gradient(45deg, ${lightColor || '#757575'} 20%, ${darkColor || '#212121'} 100%)`,
        boxShadow: '0 4px 6px 2px rgba(33, 33, 33, .5)',
    },
    '&:active': {
        boxShadow: '0 2px 4px 1px rgba(33, 33, 33, .4)',
        transform: 'translateY(1px)',
    },
}));

const GradientRoundButton: React.FC<GradientRoundButtonProps> = ({
                                                                     children,
                                                                     lightColor,
                                                                     darkColor,
                                                                     ...props
                                                                 }) => {
    return (
        <StyledButton lightColor={lightColor} darkColor={darkColor} {...props}>
            {children}
        </StyledButton>
    );
};

export default GradientRoundButton;