import {SetStateAction, useEffect, useState} from "react";
import {useLocation, useNavigate} from "react-router-dom";
import {Box, Tab, Tabs} from "@mui/material";

interface TabConfig {
    label: string;
    value: string;
}

type TabConfigs = {
    [key: string]: TabConfig[];
};

const tabConfigs: TabConfigs = {
    dashboard: [
        {label: 'User Performance', value: '/dashboard/user-performance'},
        {label: 'Sales Overview', value: '/dashboard/sales-overview'},
        {label: 'Inventory Status', value: '/dashboard/inventory-status'},
        {label: 'Financial Summary', value: '/dashboard/financial-summary'},
    ],
    inventory: [
        {label: 'New Inventory', value: '/inventory/new'},
        {label: 'Generate Inventory Report', value: '/inventory/new'},
        {label: 'Stock Levels', value: '/stocks'},
        {label: 'Inventory Valuation', value: '/inventory/new'},
    ],
    categories: [
        { label: 'View Category Groups', value: '/categories/groups' },
        { label: 'View Category History', value: '/categories/history' },
        { label: 'Manage Categories', value: '/categories/manage' },
    ],
    orders: [
        { label: 'New Orders', value: '/orders/new' },
        { label: 'Order History', value: '/orders/history' },
        { label: 'Returns', value: '/orders/returns' },
    ],
    customers: [
        { label: 'Customer List', value: '/customers/list' },
        { label: 'Customer Analytics', value: '/customers/analytics' },
        { label: 'Loyalty Program', value: '/customers/loyalty' },
    ],
    reports: [
        { label: 'Sales Reports', value: '/reports/sales' },
        { label: 'Inventory Reports', value: '/reports/inventory' },
        { label: 'Financial Reports', value: '/reports/financial' },
    ],
};

interface DynamicTabProps {
    selectedMenuItem: string;
}


const DynamicTabs: React.FC<DynamicTabProps> = ({selectedMenuItem}) => {
    const [tabs, setTabs] = useState<TabConfig[]>([]);
    const [value, setValue] = useState<number>(0);
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        const menuKey = selectedMenuItem.toLowerCase();
        if (tabConfigs[menuKey]) {
            setTabs(tabConfigs[menuKey]);
            setValue(0); // Reset to first tab when menu item changes
        } else {
            setTabs([]);
        }
    }, [selectedMenuItem]);

    useEffect(() => {
        if (tabs.length > 0) {
            const currentTabIndex = tabs.findIndex(tab => tab.value === location.pathname);
            if (currentTabIndex !== -1) {
                setValue(currentTabIndex);
            }
        }
    }, [location.pathname, tabs]);

    const handleChange = (_event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
        navigate(tabs[newValue].value);
    };

    if (tabs.length === 0) return null;

    return (
        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
            <Tabs value={value} onChange={handleChange}
                  indicatorColor="secondary"
                 textColor="inherit">
                {tabs.map((tab) => (
                    <Tab key={tab.label} label={tab.label} />
                ))}
            </Tabs>
        </Box>
    );
};

export default DynamicTabs;
