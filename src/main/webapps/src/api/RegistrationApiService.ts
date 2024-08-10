import apiUrl from "../config/api";

export interface RegistrationResponse {

}

export interface Registration{
    firstName: string;
    lastName: string;
    email?: string;
    password: string;
    confirmPassword: string;
    companyName?: string;
    jobTitle: string;
    username: string;
    role: string;
    agreeToTerms: false;
}

export async function registerUser(registration: Registration) : Promise<any>
{
    const {firstName, lastName, email, jobTitle, password, username, role} = registration;

    try
    {
        const response = await fetch(`${apiUrl}/api/register/`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                firstName: firstName,
                lastName: lastName,
                email: email,
                jobTitle: jobTitle,
                password: password,
                username: username,
                role: role
            }),
        });

        console.log('Response Status: ', response.status);

        if(!response.ok)
        {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        return await response.json();

    }catch(error)
    {
        console.error("Registration Error: ", error);
    }
}