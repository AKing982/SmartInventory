import apiUrl from "../config/api";

export interface AuthenticationResponse {
    token: string;
    tokenType: string;
    username: string;
    roles: string[]
}

export interface LoginCredentials{
    usernameOrEmail: string;
    password: string;
}

export async function authenticateUser(credentials: LoginCredentials) : Promise<AuthenticationResponse>
{
    const {usernameOrEmail, password} = credentials;
    console.log('Authenticate User: ', usernameOrEmail);

    try
    {
        const response = await fetch(`${apiUrl}/api/auth/login`,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json',
                },
                body: JSON.stringify({
                    username: usernameOrEmail,
                    password: password
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
        console.error("Authentication Error: ", error);
        throw error;
    }
}
