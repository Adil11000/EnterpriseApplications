<script lang="ts">
    import { Button } from "$lib/components/ui/button/index.js";
    import * as Card from "$lib/components/ui/card/index.js";
    import { Input } from "$lib/components/ui/input/index.js";
    import { Label } from "$lib/components/ui/label/index.js";
    import { toast } from "svelte-sonner";
    import { auth } from "../auth";

    let { current = $bindable(), lock = $bindable() } = $props();

    async function login() {
        if (lock) {
            toast.error("Please wait for the current request to finish");
            return;
        }
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        lock = true;
        await auth("signin", email, password);
        lock = false;
    }
</script>

<Card.Root class="mx-auto max-w-sm">
    <Card.Header>
        <Card.Title class="text-2xl">Login</Card.Title>
        <Card.Description
            >Enter your email below to login to your account</Card.Description
        >
    </Card.Header>
    <Card.Content>
        <div class="grid gap-4">
            <div class="grid gap-2">
                <Label for="email">Email</Label>
                <Input
                    id="email"
                    type="email"
                    placeholder="me@example.com"
                    required
                />
            </div>
            <div class="grid gap-2">
                <div class="flex items-center">
                    <Label for="password">Password</Label>
                    <!-- <a href="##" class="ml-auto inline-block text-sm underline"> -->
                    <!--    Forgot your password? -->
                    <!-- </a> -->
                </div>
                <Input id="password" type="password" required />
            </div>
            <Button class="w-full" onclick={login}>Login</Button>
            <!-- <Button variant="outline" class="w-full">Login with Google</Button> -->
        </div>
        <div class="mt-4 text-center text-sm">
            Don't have an account?
            <a
                href="##"
                class="underline"
                onclick={() => (current = "register")}
            >
                Sign up
            </a>
        </div>
    </Card.Content>
</Card.Root>
