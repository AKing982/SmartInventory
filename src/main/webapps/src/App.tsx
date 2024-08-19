import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import {createTheme, CssBaseline, ThemeProvider} from "@mui/material";
import LoginForm from "./components/LoginForm";
import RegistrationForm from "./components/RegistrationForm";
import ForgotPasswordForm from "./components/ForgotPasswordForm";
import HomePage from "./components/HomePage";
import AddInventoryPage from "./components/AddInventoryPage";
import CategoriesPage from "./components/CategoriesPage";
import ContactsPage from "./components/ContactsPage";
import InventoryPage from "./components/InventoryPage";
import InventoryHistoryPage from "./components/InventoryHistoryPage";
import NewInventoryPage from "./components/NewInventoryPage";
import StockLevels from "./components/StockLevels";
import LoadingScreen from "./components/LoadingScreen";
import BasicUserHomePage from "./components/BasicUserHomePage";
import {withPermission} from "./components/withPermission";
import {Permissions} from "./PermissionUtils";
import {RoleType} from "./items/Items";
import {withRole} from "./components/withRole";
import SupplierHomePage from "./components/SupplierHomePage";
import OrderPlacementPage from "./components/OrderPlacementPage";
import OrderHistoryPage from "./components/OrderHistoryPage";
import ProductCatalogPage from "./components/ProductCatalogPage";
import UserProfilePage from "./components/UserProfilePage";
import SupplierDashboard from "./components/SupplierDashboard";
import SettingsPage from "./components/SettingsPage";

const theme = createTheme({
    palette: {
        primary: {
            main: '#1976d2', // You can adjust this color to match your brand
        },
    },
});

const ProtectedBasicUserHomePage = withRole([RoleType.ROLE_USER])(BasicUserHomePage);
const ProtectedHomePage = withRole([RoleType.ROLE_EMPLOYEE])(HomePage);


function App() {
  return (
      <ThemeProvider theme={theme}>
          <CssBaseline/>
          <div style={{
              minHeight: '120vh',
              background: 'linear-gradient(45deg, #f3f4f6 30%, #e5e7eb 90%)',
              backgroundImage: `url("data:image/svg+xml,%3Csvg width='40' height='40' viewBox='0 0 40 40' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='%23a0aec0' fill-opacity='0.1' fill-rule='evenodd'%3E%3Cpath d='M0 40L40 0H20L0 20M40 40V20L20 40'/%3E%3C/g%3E%3C/svg%3E")`,
          }}>
              <Router>
                  <div className="App">
                      <Routes>
                          <Route path="/" element={<LoginForm/>}/>
                          <Route path="/register" element={<RegistrationForm />}/>
                          <Route path="/forgot-password" element={<ForgotPasswordForm />}/>
                          <Route path="/home" element={<HomePage />}/>
                          <Route path="/addInventory" element={<AddInventoryPage /> }/>
                          <Route path="/categories" element={<CategoriesPage />}/>
                          <Route path="/contacts" element={<ContactsPage />}/>
                          <Route path="/inventory" element={<InventoryPage />}/>
                          <Route path="/inventory-history" element={<InventoryHistoryPage />}/>
                          <Route path="/inventory/new" element={<NewInventoryPage />}/>
                          <Route path="/stocks" element={<StockLevels />}/>
                          <Route path="/loading" element={<LoadingScreen />}/>
                          <Route path="/userHome" element={<BasicUserHomePage />}/>
                          <Route path="/supplier/home" element={<SupplierHomePage />} />
                          <Route path="/order-placement" element={<OrderPlacementPage />}/>
                          <Route path="/order-history" element={<OrderHistoryPage/>}/>
                          <Route path="/catalog" element={<ProductCatalogPage />}/>
                          <Route path="/profile" element={<UserProfilePage />}/>
                          <Route path="/supplier/dashboard" element={<SupplierDashboard />}/>
                          <Route path="/settings" element={<SettingsPage />}/>
                      </Routes>
                  </div>
              </Router>
          </div>
      </ThemeProvider>
);
}

export default App;
