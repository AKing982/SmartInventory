import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from './AuthContext';
import {  getRolePermissions } from '../PermissionUtils';
import {RoleType} from "../items/Items";

export const withRole = (allowedRoles: RoleType[]) =>
    <P extends object>(WrappedComponent: React.ComponentType<P>): React.FC<P> => {
        return (props: P) => {
            const { user } = useAuth();

            if (!user || !allowedRoles.includes(user.role)) {
                return <Navigate to="/unauthorized" replace />;
            }

            // Pass the user's permissions to the wrapped component
            const userPermissions = getRolePermissions(user.role);
            return <WrappedComponent {...props} permissions={userPermissions} />;
        };
    };