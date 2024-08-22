import React, { useState, useEffect, useMemo } from 'react';
import {
    Box, Container, Typography, TextField, Button, Paper, Table, TableBody, TableCell,
    TableContainer, TableHead, TableRow, Snackbar, Alert, Dialog, DialogTitle, DialogContent,
    DialogActions, List, ListItem, ListItemText, Chip, FormControl, InputLabel, Select, MenuItem,
    SelectChangeEvent, Tooltip, IconButton, Collapse, TablePagination, Switch, FormControlLabel
} from '@mui/material';
import {
    Add as AddIcon,
    Search as SearchIcon,
    Visibility as VisibilityIcon,
    Edit as EditIcon,
    Delete as DeleteIcon,
    ExpandMore as ExpandMoreIcon,
    ImportExport as ImportExportIcon,
    Assessment as AssessmentIcon,
    Category as CategoryIcon,
    Warehouse as WarehouseIcon,
    People as PeopleIcon,
    Business as BusinessIcon, Settings as SettingsIcon
} from '@mui/icons-material';
import MainAppBar from './MainAppBar';
import InventoryIcon from "@mui/icons-material/Inventory"; // Assuming you have this component

interface Contact {
    id: number;
    name: string;
    email: string;
    phone: string;
    company: string;
    group: string;
    notes: string;
    history: string[];
    permissions: string[];
    active: boolean;
}

type SearchMethod = 'name' | 'email' | 'phone' | 'company' | 'group';

const ContactsPage: React.FC = () => {
    const [contacts, setContacts] = useState<Contact[]>([]);
    const [newContact, setNewContact] = useState<Omit<Contact, 'id' | 'history' | 'permissions'>>({
        name: '', email: '', phone: '', company: '', group: '', notes: '', active: true
    });
    const [openSnackbar, setOpenSnackbar] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');
    const [searchTerm, setSearchTerm] = useState('');
    const [searchMethod, setSearchMethod] = useState<SearchMethod>('name');
    const [openDetailDialog, setOpenDetailDialog] = useState(false);
    const [selectedContact, setSelectedContact] = useState<Contact | null>(null);
    const [groups, setGroups] = useState<string[]>(['Work', 'Family', 'Friends']);
    const [showAddContact, setShowAddContact] = useState(false);
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
    const [filters, setFilters] = useState({ group: '', active: null as boolean | null });

    useEffect(() => {
        // Fetch contacts from API (simulated)
        const dummyContacts: Contact[] = [
            { id: 1, name: 'John Doe', email: 'john@example.com', phone: '123-456-7890', company: 'ABC Corp', group: 'Work', notes: 'Met at conference', history: ['Created on 2023-01-01'], permissions: ['view', 'edit'], active: true },
            { id: 2, name: 'Jane Smith', email: 'jane@example.com', phone: '098-765-4321', company: 'XYZ Inc', group: 'Family', notes: 'Birthday on July 15', history: ['Created on 2023-02-15', 'Updated on 2023-03-01'], permissions: ['view'], active: false },
        ];
        setContacts(dummyContacts);
    }, []);

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setNewContact(prev => ({ ...prev, [name]: value }));
    };

    const handleAddContact = (e: React.FormEvent) => {
        e.preventDefault();
        const newId = contacts.length > 0 ? Math.max(...contacts.map(c => c.id)) + 1 : 1;
        const contactToAdd = {
            ...newContact,
            id: newId,
            history: [`Created on ${new Date().toISOString().split('T')[0]}`],
            permissions: ['view', 'edit']
        };
        setContacts([...contacts, contactToAdd]);
        setNewContact({ name: '', email: '', phone: '', company: '', group: '', notes: '', active: true });
        setSnackbarMessage('Contact added successfully!');
        setOpenSnackbar(true);
        setShowAddContact(false);
    };

    const handleDeleteContact = (id: number) => {
        setContacts(contacts.filter(contact => contact.id !== id));
        setSnackbarMessage('Contact deleted successfully!');
        setOpenSnackbar(true);
    };

    const handleCloseSnackbar = (event?: React.SyntheticEvent | Event, reason?: string) => {
        if (reason === 'clickaway') {
            return;
        }
        setOpenSnackbar(false);
    };

    const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setSearchTerm(event.target.value);
    };

    const handleGroupChange = (event: SelectChangeEvent<string>) => {
        setNewContact(prev => ({ ...prev, group: event.target.value }));
    };

    const handleSearchMethodChange = (event: SelectChangeEvent<SearchMethod>) => {
        setSearchMethod(event.target.value as SearchMethod);
    };

    const handleOpenDetailDialog = (contact: Contact) => {
        setSelectedContact(contact);
        setOpenDetailDialog(true);
    };

    const handleCloseDetailDialog = () => {
        setOpenDetailDialog(false);
        setSelectedContact(null);
    };

    const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
    };

    const handleFilterChange = (event: SelectChangeEvent<string>) => {
        const { name, value } = event.target;
        setFilters(prev => ({ ...prev, [name]: value }));
    };

    const handleActiveFilterChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setFilters(prev => ({ ...prev, active: event.target.checked ? true : null }));
    };

    const handleImportExport = () => {
        // Placeholder for import/export functionality
        console.log('Import/Export functionality to be implemented');
    };

    const filteredContacts = useMemo(() => {
        return contacts.filter(contact =>
            contact[searchMethod].toLowerCase().includes(searchTerm.toLowerCase()) &&
            (filters.group === '' || contact.group === filters.group) &&
            (filters.active === null || contact.active === filters.active)
        );
    }, [contacts, searchTerm, searchMethod, filters]);

    const menuItems2 = [
        { text: 'Dashboard', icon: <AssessmentIcon />, path: '/home' },
        { text: 'Inventory', icon: <InventoryIcon />, path: '/inventory' },
        { text: 'Categories', icon: <CategoryIcon />, path: '/categories' },
        { text: 'Warehouses', icon: <WarehouseIcon />, path: '/warehouses' },
        { text: 'Contacts', icon: <PeopleIcon />, path: '/contacts' },
        { text: 'Departments', icon: <BusinessIcon />, path: '/departments' },
        { text: 'Employees', icon: <PeopleIcon />, path: '/employees' },
        { text: 'Customers', icon: <PeopleIcon />, path: '/customers' },
        { text: 'Reports', icon: <AssessmentIcon />, path: '/reports' },
        { text: 'Settings', icon: <SettingsIcon />, path: '/settings' },
    ];

    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', minHeight: '100vh', bgcolor: 'background.default' }}>
            <MainAppBar title="Contacts" drawerItems={menuItems2} />
            <Container maxWidth={false} sx={{ mt: 8, mb: 4 }}>
                <Paper elevation={3} sx={{ p: 2 }}>
                    <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
                        <Typography variant="h6">Contacts</Typography>
                        <Box>
                            <Button
                                variant="contained"
                                startIcon={<ImportExportIcon />}
                                onClick={handleImportExport}
                                sx={{ mr: 2 }}
                            >
                                Import/Export
                            </Button>
                            <Button
                                variant="contained"
                                startIcon={showAddContact ? <ExpandMoreIcon /> : <AddIcon />}
                                onClick={() => setShowAddContact(!showAddContact)}
                            >
                                {showAddContact ? 'Hide Form' : 'Add Contact'}
                            </Button>
                        </Box>
                    </Box>

                    <Collapse in={showAddContact}>
                        <form onSubmit={handleAddContact}>
                            <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 2, mb: 2 }}>
                                <TextField label="Name" name="name" value={newContact.name} onChange={handleInputChange} required />
                                <TextField label="Email" name="email" type="email" value={newContact.email} onChange={handleInputChange} required />
                                <TextField label="Phone" name="phone" value={newContact.phone} onChange={handleInputChange} required />
                                <TextField label="Company" name="company" value={newContact.company} onChange={handleInputChange} />
                                <FormControl sx={{ minWidth: 120 }}>
                                    <InputLabel>Group</InputLabel>
                                    <Select name="group" value={newContact.group} onChange={handleGroupChange} label="Group">
                                        {groups.map((group) => (
                                            <MenuItem key={group} value={group}>{group}</MenuItem>
                                        ))}
                                    </Select>
                                </FormControl>
                                {/*<TextField label="Notes" name="notes" value={newContact.notes} onChange={handleInputChange} multiline rows={2} />*/}
                                <Switch checked={newContact.active} onChange={(e) => setNewContact(prev => ({ ...prev, active: e.target.checked }))} name="active" />
                            </Box>
                            <Button type="submit" variant="contained" color="primary" startIcon={<AddIcon />}>
                                Add Contact
                            </Button>
                        </form>
                    </Collapse>

                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 2, mb: 2 }}>
                        <FormControl sx={{ minWidth: 120 }}>
                            <InputLabel id="search-method-label">Search By</InputLabel>
                            <Select<SearchMethod>
                                labelId="search-method-label"
                                value={searchMethod}
                                onChange={handleSearchMethodChange}
                                label="Search By"
                            >
                                <MenuItem value="name">Name</MenuItem>
                                <MenuItem value="email">Email</MenuItem>
                                <MenuItem value="phone">Phone</MenuItem>
                                <MenuItem value="company">Company</MenuItem>
                                <MenuItem value="group">Group</MenuItem>
                            </Select>
                        </FormControl>
                        <TextField
                            label="Search Contacts"
                            value={searchTerm}
                            onChange={handleSearchChange}
                            InputProps={{
                                startAdornment: <SearchIcon sx={{ mr: 1, color: 'text.secondary' }} />,
                            }}
                        />
                        <FormControl sx={{ minWidth: 120 }}>
                            <InputLabel>Filter by Group</InputLabel>
                            <Select
                                name="group"
                                value={filters.group}
                                onChange={handleFilterChange}
                                label="Filter by Group"
                            >
                                <MenuItem value="">All</MenuItem>
                                {groups.map((group) => (
                                    <MenuItem key={group} value={group}>{group}</MenuItem>
                                ))}
                            </Select>
                        </FormControl>
                        <FormControlLabel
                            control={
                                <Switch
                                    checked={filters.active === true}
                                    onChange={handleActiveFilterChange}
                                    name="active"
                                />
                            }
                            label="Active Only"
                        />
                    </Box>

                    <TableContainer>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell>Name</TableCell>
                                    <TableCell>Email</TableCell>
                                    <TableCell>Phone</TableCell>
                                    <TableCell>Company</TableCell>
                                    <TableCell>Group</TableCell>
                                    <TableCell>Active</TableCell>
                                    <TableCell>Actions</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {filteredContacts
                                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                    .map((contact) => (
                                        <TableRow key={contact.id}>
                                            <TableCell>{contact.name}</TableCell>
                                            <TableCell>{contact.email}</TableCell>
                                            <TableCell>{contact.phone}</TableCell>
                                            <TableCell>{contact.company}</TableCell>
                                            <TableCell>{contact.group}</TableCell>
                                            <TableCell>{contact.active ? 'Yes' : 'No'}</TableCell>
                                            <TableCell>
                                                <Tooltip title="View Details">
                                                    <IconButton onClick={() => handleOpenDetailDialog(contact)}>
                                                        <VisibilityIcon />
                                                    </IconButton>
                                                </Tooltip>
                                                {contact.permissions.includes('edit') && (
                                                    <Tooltip title="Edit">
                                                        <IconButton>
                                                            <EditIcon />
                                                        </IconButton>
                                                    </Tooltip>
                                                )}
                                                {contact.permissions.includes('edit') && (
                                                    <Tooltip title="Delete">
                                                        <IconButton onClick={() => handleDeleteContact(contact.id)}>
                                                            <DeleteIcon />
                                                        </IconButton>
                                                    </Tooltip>
                                                )}
                                            </TableCell>
                                        </TableRow>
                                    ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                    <TablePagination
                        rowsPerPageOptions={[5, 10, 25]}
                        component="div"
                        count={filteredContacts.length}
                        rowsPerPage={rowsPerPage}
                        page={page}
                        onPageChange={handleChangePage}
                        onRowsPerPageChange={handleChangeRowsPerPage}
                    />
                </Paper>
            </Container>
            <Snackbar open={openSnackbar} autoHideDuration={6000} onClose={handleCloseSnackbar}>
                <Alert onClose={handleCloseSnackbar} severity="success" sx={{ width: '100%' }}>
                    {snackbarMessage}
                </Alert>
            </Snackbar>
            <Dialog open={openDetailDialog} onClose={handleCloseDetailDialog}>
                <DialogTitle>Contact Details</DialogTitle>
                <DialogContent>
                    {selectedContact && (
                        <>
                            <Typography variant="h6">{selectedContact.name}</Typography>
                            <Typography>Email: {selectedContact.email}</Typography>
                            <Typography>Phone: {selectedContact.phone}</Typography>
                            <Typography>Company: {selectedContact.company}</Typography>
                            <Typography>Group: {selectedContact.group}</Typography>
                            <Typography>Active: {selectedContact.active ? 'Yes' : 'No'}</Typography>
                            <Typography variant="h6" sx={{ mt: 2 }}>Notes</Typography>
                            <Typography>{selectedContact.notes}</Typography>
                            <Typography variant="h6" sx={{ mt: 2 }}>History</Typography>
                            <List>
                                {selectedContact.history.map((event, index) => (
                                    <ListItem key={index}>
                                        <ListItemText primary={event} />
                                    </ListItem>
                                ))}
                            </List>
                            <Typography variant="h6" sx={{ mt: 2 }}>Permissions</Typography>
                            <Box sx={{ display: 'flex', gap: 1 }}>
                                {selectedContact.permissions.map((permission) => (
                                    <Chip key={permission} label={permission} />
                                ))}
                            </Box>
                        </>
                    )}
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDetailDialog}>Close</Button>
                </DialogActions>
            </Dialog>
            {/* Keep Snackbar and Dialog components */}
        </Box>
    );

    // return (
    //     <Box sx={{ display: 'flex', minHeight: '100vh', bgcolor: 'background.default' }}>
    //         <MainAppBar title="Contacts" />
    //         <Container component="main" sx={{ flexGrow: 1, p: 3, mt: 8 }}>
    //             <Typography variant="h4" component="h1" gutterBottom>
    //                 Contacts
    //             </Typography>
    //             <Grid container spacing={3}>
    //                 <Grid item xs={12} md={4}>
    //                     <Paper elevation={3} sx={{ p: 2 }}>
    //                         <Typography variant="h6" gutterBottom>
    //                             Add New Contact
    //                         </Typography>
    //                         <form onSubmit={handleAddContact}>
    //                             <TextField
    //                                 fullWidth
    //                                 label="Name"
    //                                 name="name"
    //                                 value={newContact.name}
    //                                 onChange={handleInputChange}
    //                                 margin="normal"
    //                                 required
    //                             />
    //                             <TextField
    //                                 fullWidth
    //                                 label="Email"
    //                                 name="email"
    //                                 type="email"
    //                                 value={newContact.email}
    //                                 onChange={handleInputChange}
    //                                 margin="normal"
    //                                 required
    //                             />
    //                             <TextField
    //                                 fullWidth
    //                                 label="Phone"
    //                                 name="phone"
    //                                 value={newContact.phone}
    //                                 onChange={handleInputChange}
    //                                 margin="normal"
    //                                 required
    //                             />
    //                             <TextField
    //                                 fullWidth
    //                                 label="Company"
    //                                 name="company"
    //                                 value={newContact.company}
    //                                 onChange={handleInputChange}
    //                                 margin="normal"
    //                             />
    //                             <FormControl fullWidth margin="normal">
    //                                 <InputLabel>Group</InputLabel>
    //                                 <Select
    //                                     name="group"
    //                                     value={newContact.group}
    //                                     onChange={handleGroupChange}
    //                                     label="Group"
    //                                 >
    //                                     {groups.map((group) => (
    //                                         <MenuItem key={group} value={group}>{group}</MenuItem>
    //                                     ))}
    //                                 </Select>
    //                             </FormControl>
    //                             <TextField
    //                                 fullWidth
    //                                 label="Notes"
    //                                 name="notes"
    //                                 value={newContact.notes}
    //                                 onChange={handleInputChange}
    //                                 margin="normal"
    //                                 multiline
    //                                 rows={3}
    //                             />
    //                             <Button
    //                                 type="submit"
    //                                 variant="contained"
    //                                 color="primary"
    //                                 startIcon={<AddIcon />}
    //                                 sx={{ mt: 2 }}
    //                             >
    //                                 Add Contact
    //                             </Button>
    //                         </form>
    //                     </Paper>
    //                 </Grid>
    //                 <Grid item xs={12} md={8}>
    //                     <Paper elevation={3} sx={{ p: 2 }}>
    //                         <Typography variant="h6" gutterBottom>
    //                             Contact List
    //                         </Typography>
    //                         <Box sx={{ display: 'flex', mb: 2 }}>
    //                             <FormControl sx={{ minWidth: 120, mr: 2 }}>
    //                                 <InputLabel id="search-method-label">Search By</InputLabel>
    //                                 <Select<SearchMethod>
    //                                     labelId="search-method-label"
    //                                     value={searchMethod}
    //                                     onChange={handleSearchMethodChange}
    //                                     label="Search By"
    //                                 >
    //                                     <MenuItem value="name">Name</MenuItem>
    //                                     <MenuItem value="email">Email</MenuItem>
    //                                     <MenuItem value="phone">Phone</MenuItem>
    //                                     <MenuItem value="company">Company</MenuItem>
    //                                     <MenuItem value="group">Group</MenuItem>
    //                                 </Select>
    //                             </FormControl>
    //                             <TextField
    //                                 fullWidth
    //                                 label="Search Contacts"
    //                                 value={searchTerm}
    //                                 onChange={handleSearchChange}
    //                                 InputProps={{
    //                                     startAdornment: <SearchIcon sx={{ mr: 1, color: 'text.secondary' }} />,
    //                                 }}
    //                             />
    //                         </Box>
    //                         <Box sx={{ mb: 2 }}>
    //                             <Button startIcon={<ImportExportIcon />} onClick={handleImport} sx={{ mr: 1 }}>
    //                                 Import
    //                             </Button>
    //                             <Button startIcon={<ImportExportIcon />} onClick={handleExport}>
    //                                 Export
    //                             </Button>
    //                         </Box>
    //                         <TableContainer>
    //                             <Table>
    //                                 <TableHead>
    //                                     <TableRow>
    //                                         <TableCell>Name</TableCell>
    //                                         <TableCell>Email</TableCell>
    //                                         <TableCell>Phone</TableCell>
    //                                         <TableCell>Company</TableCell>
    //                                         <TableCell>Group</TableCell>
    //                                         <TableCell>Actions</TableCell>
    //                                     </TableRow>
    //                                 </TableHead>
    //                                 <TableBody>
    //                                     {filteredContacts.map((contact) => (
    //                                         <TableRow key={contact.id}>
    //                                             <TableCell>{contact.name}</TableCell>
    //                                             <TableCell>{contact.email}</TableCell>
    //                                             <TableCell>{contact.phone}</TableCell>
    //                                             <TableCell>{contact.company}</TableCell>
    //                                             <TableCell>{contact.group}</TableCell>
    //                                             <TableCell>
    //                                                 <Tooltip title="View Details">
    //                                                     <IconButton onClick={() => handleOpenDetailDialog(contact)}>
    //                                                         <VisibilityIcon />
    //                                                     </IconButton>
    //                                                 </Tooltip>
    //                                                 {contact.permissions.includes('edit') && (
    //                                                     <Tooltip title="Edit">
    //                                                         <IconButton>
    //                                                             <EditIcon />
    //                                                         </IconButton>
    //                                                     </Tooltip>
    //                                                 )}
    //                                                 {contact.permissions.includes('edit') && (
    //                                                     <Tooltip title="Delete">
    //                                                         <IconButton onClick={() => handleDeleteContact(contact.id)}>
    //                                                             <DeleteIcon />
    //                                                         </IconButton>
    //                                                     </Tooltip>
    //                                                 )}
    //                                             </TableCell>
    //                                         </TableRow>
    //                                     ))}
    //                                 </TableBody>
    //                             </Table>
    //                         </TableContainer>
    //                     </Paper>
    //                 </Grid>
    //             </Grid>
    //
    //         </Container>
    //     </Box>
    // );
};





export default ContactsPage;
