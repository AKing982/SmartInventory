import React from 'react';
import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import {createTheme, CssBaseline, ThemeProvider} from "@mui/material";
import LoginForm from "./components/LoginForm";
import RegistrationForm from "./components/RegistrationForm";
import ForgotPasswordForm from "./components/ForgotPasswordForm";
import HomePage from "./components/HomePage";
import AddInventoryPage from "./components/AddInventoryPage";

const theme = createTheme({
    palette: {
        primary: {
            main: '#1976d2', // You can adjust this color to match your brand
        },
    },
});


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
                      </Routes>
                  </div>
              </Router>
          </div>
      </ThemeProvider>
);
}

export default App;
