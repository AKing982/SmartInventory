import React, { useState, useEffect, useMemo } from 'react';
import {
    Container, Box, Typography, TextField, Button, Snackbar, Alert,
    Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper,
    Grid, IconButton, Tooltip, Select, MenuItem, InputLabel, FormControl,
    SelectChangeEvent, Dialog, DialogTitle, DialogContent, DialogActions,
    Chip, List, ListItem, ListItemText, Divider
} from '@mui/material';
import {
    Add as AddIcon,
    Delete as DeleteIcon,
    Edit as EditIcon,
    Search as SearchIcon,
    ImportExport as ImportExportIcon,
    Visibility as VisibilityIcon,
    History as HistoryIcon,
    Note as NoteIcon
} from '@mui/icons-material';
import MainAppBar from './MainAppBar';

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
}

type SearchMethod = 'name' | 'email' | 'phone' | 'company' | 'group';

const ContactsPage: React.FC = () => {
    const [contacts, setContacts] = useState<Contact[]>([]);
    const [newContact, setNewContact] = useState<Omit<Contact, 'id' | 'history' | 'permissions'>>({
        name: '',
        email: '',
        phone: '',
        company: '',
        group: '',
        notes: ''
    });
    const [openSnackbar, setOpenSnackbar] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');
    const [searchTerm, setSearchTerm] = useState('');
    const [searchMethod, setSearchMethod] = useState<SearchMethod>('name');
    const [openDetailDialog, setOpenDetailDialog] = useState(false);
    const [selectedContact, setSelectedContact] = useState<Contact | null>(null);
    const [groups, setGroups] = useState<string[]>(['Work', 'Family', 'Friends']);

    useEffect(() => {
        // In a real app, you'd fetch contacts from an API here
        const dummyContacts: Contact[] = [
            { id: 1, name: 'John Doe', email: 'john@example.com', phone: '123-456-7890', company: 'ABC Corp', group: 'Work', notes: 'Met at conference', history: ['Created on 2023-01-01'], permissions: ['view', 'edit'] },
            { id: 2, name: 'Jane Smith', email: 'jane@example.com', phone: '098-765-4321', company: 'XYZ Inc', group: 'Family', notes: 'Birthday on July 15', history: ['Created on 2023-02-15', 'Updated on 2023-03-01'], permissions: ['view'] },
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
        setNewContact({ name: '', email: '', phone: '', company: '', group: '', notes: '' });
        setSnackbarMessage('Contact added successfully!');
        setOpenSnackbar(true);
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

    const handleImport = () => {
        // Placeholder for import functionality
        console.log('Import functionality to be implemented');
    };

    const handleExport = () => {
        // Placeholder for export functionality
        console.log('Export functionality to be implemented');
    };

    const filteredContacts = useMemo(() => {
        return contacts.filter(contact =>
            contact[searchMethod].toLowerCase().includes(searchTerm.toLowerCase())
        );
    }, [contacts, searchTerm, searchMethod]);

    return (
        <Box sx={{ display: 'flex', minHeight: '100vh', bgcolor: 'background.default' }}>
            <MainAppBar title="Contacts" />
            <Container component="main" sx={{ flexGrow: 1, p: 3, mt: 8 }}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Contacts
                </Typography>
                <Grid container spacing={3}>
                    <Grid item xs={12} md={4}>
                        <Paper elevation={3} sx={{ p: 2 }}>
                            <Typography variant="h6" gutterBottom>
                                Add New Contact
                            </Typography>
                            <form onSubmit={handleAddContact}>
                                <TextField
                                    fullWidth
                                    label="Name"
                                    name="name"
                                    value={newContact.name}
                                    onChange={handleInputChange}
                                    margin="normal"
                                    required
                                />
                                <TextField
                                    fullWidth
                                    label="Email"
                                    name="email"
                                    type="email"
                                    value={newContact.email}
                                    onChange={handleInputChange}
                                    margin="normal"
                                    required
                                />
                                <TextField
                                    fullWidth
                                    label="Phone"
                                    name="phone"
                                    value={newContact.phone}
                                    onChange={handleInputChange}
                                    margin="normal"
                                    required
                                />
                                <TextField
                                    fullWidth
                                    label="Company"
                                    name="company"
                                    value={newContact.company}
                                    onChange={handleInputChange}
                                    margin="normal"
                                />
                                <FormControl fullWidth margin="normal">
                                    <InputLabel>Group</InputLabel>
                                    <Select
                                        name="group"
                                        value={newContact.group}
                                        onChange={handleGroupChange}
                                        label="Group"
                                    >
                                        {groups.map((group) => (
                                            <MenuItem key={group} value={group}>{group}</MenuItem>
                                        ))}
                                    </Select>
                                </FormControl>
                                <TextField
                                    fullWidth
                                    label="Notes"
                                    name="notes"
                                    value={newContact.notes}
                                    onChange={handleInputChange}
                                    margin="normal"
                                    multiline
                                    rows={3}
                                />
                                <Button
                                    type="submit"
                                    variant="contained"
                                    color="primary"
                                    startIcon={<AddIcon />}
                                    sx={{ mt: 2 }}
                                >
                                    Add Contact
                                </Button>
                            </form>
                        </Paper>
                    </Grid>
                    <Grid item xs={12} md={8}>
                        <Paper elevation={3} sx={{ p: 2 }}>
                            <Typography variant="h6" gutterBottom>
                                Contact List
                            </Typography>
                            <Box sx={{ display: 'flex', mb: 2 }}>
                                <FormControl sx={{ minWidth: 120, mr: 2 }}>
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
                                    fullWidth
                                    label="Search Contacts"
                                    value={searchTerm}
                                    onChange={handleSearchChange}
                                    InputProps={{
                                        startAdornment: <SearchIcon sx={{ mr: 1, color: 'text.secondary' }} />,
                                    }}
                                />
                            </Box>
                            <Box sx={{ mb: 2 }}>
                                <Button startIcon={<ImportExportIcon />} onClick={handleImport} sx={{ mr: 1 }}>
                                    Import
                                </Button>
                                <Button startIcon={<ImportExportIcon />} onClick={handleExport}>
                                    Export
                                </Button>
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
                                            <TableCell>Actions</TableCell>
                                        </TableRow>
                                    </TableHead>
                                    <TableBody>
                                        {filteredContacts.map((contact) => (
                                            <TableRow key={contact.id}>
                                                <TableCell>{contact.name}</TableCell>
                                                <TableCell>{contact.email}</TableCell>
                                                <TableCell>{contact.phone}</TableCell>
                                                <TableCell>{contact.company}</TableCell>
                                                <TableCell>{contact.group}</TableCell>
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
                        </Paper>
                    </Grid>
                </Grid>
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
            </Container>
        </Box>
    );
};

export default ContactsPage;
