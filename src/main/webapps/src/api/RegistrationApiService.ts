import apiUrl from "../config/api";
import axios from "axios";


enum RoleType {
    ROLE_SUPPLIER = "ROLE_SUPPLIER",
    ROLE_USER = "ROLE_USER",
    ROLE_EMPLOYEE = "ROLE_EMPLOYEE",
    ROLE_MANAGER = "ROLE_MANAGER",
    ROLE_ADMIN = "ROLE_ADMIN"
}


export interface Registration
{
    firstName: string;
    lastName: string;
    email?: string;
    password: string;
    companyName?: string;
    jobTitle: string;
    username: string;
    role: RoleType;
}

export async function registerUser(registration: Registration) : Promise<any>
{
    const {firstName, lastName, email, jobTitle, password, username, role} = registration;
    console.log('Registration: ', registration);
    console.log('Role: ', role);
    if(!role){
        throw new Error("Role is required for registration.");
    }
    try
    {
        const response = await axios.post(`${apiUrl}/api/register/`, registration);

        console.log('Response Status: ', response.status);

        return response.data;

    }catch(error)
    {
        if (axios.isAxiosError(error)) {
            console.error("Registration Error: ", error.response?.data);
            throw new Error(error.response?.data?.message || "An error occurred during registration");
        } else {
            console.error("Unexpected error: ", error);
            throw error;
        }
    }
}