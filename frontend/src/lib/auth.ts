import {toast} from "svelte-sonner";

async function fetchwrap(endpoint: string, method: string, body?: any) {
    const response = await window.fetch(`http://localhost:8080/${endpoint}`, {
        method,
        credentials: 'include',
        headers: {
            Accept: "application/json",
            "Content-Type": "application/json",
        },
        body: body ? JSON.stringify(body) : undefined,
    });
    if (response.status === 401 || response.status === 403) {
        location.reload();
    }
    return response;
}

async function auth(endpoint: string, email: string, password: string) {
    if (!email || !password) {
        toast.error("Please fill in all fields");
        return;
    }

    try {
        const response = await fetch(
            "http://localhost:8080/api/v1/auth/" + endpoint,
            {
                credentials: 'include',
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ email, password }),
            },
        );

        if (!response.ok) {
            const { error } = await response.json();
            toast.error("Failed to auth: " + error);
            return;
        }
        location.reload();
    } catch (error) {
        console.error(error);
        toast.error("Failed to auth: " + error);
    }
    return;
}

async function getUser(): Promise<any> {
    try {
        const response = await fetch("http://localhost:8080/api/v1/user", {
            method: "GET",
            credentials: 'include',
        });
        if (response.status == 403) {
            return null;
        }
        return await response.json();
    } catch (error) {
        console.error(error);
        toast.error("Failed to fetch user data");
    }
    return null
}

async function logout() {
    try {
        await fetch("http://localhost:8080/api/v1/auth/signout", {
            method: "POST",
            credentials: 'include',
        });
        location.reload();
    } catch (error) {
        console.error(error);
        toast.error("Failed to logout");
    }
}

export { auth, logout, getUser, fetchwrap };
