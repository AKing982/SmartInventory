import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from './AuthContext';
import { Permissions, hasPermission } from '../PermissionUtils';

export const withPermission = (requiredPermission: Permissions) =>
    <P extends object>(WrappedComponent: React.ComponentType<P>): React.FC<P> => {
        return (props: P) => {
            const { user } = useAuth();

            if (!user || !hasPermission(user.role, requiredPermission)) {
                return <Navigate to="/unauthorized" replace />;
            }

            return <WrappedComponent {...props} />;
        };
    };

