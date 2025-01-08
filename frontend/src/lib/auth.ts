import { toast } from "svelte-sonner";

async function fetchwrap(endpoint: string, method: string, body?: any) {
    const accessToken = localStorage.getItem("accessToken");
    if (!accessToken) {
        location.reload();
        return;
    }
    const response = await window.fetch(`http://localhost:8080/${endpoint}`, {
        method,
        credentials: 'include',
        headers: {
            Accept: "application/json",
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json",
        },
        body: body ? JSON.stringify(body) : undefined,
    });
    if (response.status === 401 || response.status === 403) {
        localStorage.removeItem("accessToken");
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
        // Store the token in local storage
        const { accessToken } = await response.json();
        localStorage.setItem("accessToken", accessToken);
        location.reload();
    } catch (error) {
        console.error(error);
        toast.error("Failed to auth: " + error);
    }
    return;
}

async function getUser(): Promise<any> {
    try {
        let accessToken = localStorage.getItem("accessToken");
        if (!accessToken) {
            return null;
        }
        const response = await fetch("http://localhost:8080/api/v1/user", {
            method: "GET",
            credentials: 'include',
            headers: {
                Authorization: `Bearer ${accessToken}`,
            },
        });
        if (!response.ok) {
            const error = await response.json();
            toast.error("Failed to fetch user data: " + error.error);
            return null;
        }
        return await response.json();
    } catch (error) {
        console.error(error);
        toast.error("Failed to fetch user data");
        localStorage.removeItem("accessToken");
        location.reload();
    }
    return null
}

async function logout() {
    localStorage.removeItem("accessToken");
    location.reload();
}

export { auth, getUser, logout, fetchwrap };
