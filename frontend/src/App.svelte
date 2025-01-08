<script lang="ts">
    import { Toaster } from "$lib/components/ui/sonner/index.js";

    import LoginForm from "$lib/components/login-form.svelte";
    import RegisterForm from "$lib/components/register-form.svelte";
    import Main from "./Main.svelte";

    import { onMount } from "svelte";
    import { getUser } from "./lib/auth";

    interface User {
        email: string;
    }

    let user = $state(null);
    let current = $state("load");

    onMount(async () => {
        user = await getUser();
        if (!user) {
            current = "login";
        }
    });

    let lock = $state(false);

    $effect(() => {
        if (user) {
            current = "main";
        }
    });
</script>

<Toaster />
<main>
    {#if current === "load"}
        <p>Loading...</p>
    {:else if current === "login"}
        <div class="flex h-screen w-full items-center justify-center px-4">
            <LoginForm bind:current bind:lock />
        </div>
    {:else if current === "register"}
        <div class="flex h-screen w-full items-center justify-center px-4">
            <RegisterForm bind:current bind:lock />
        </div>
    {:else}
        <Main bind:user />
    {/if}
</main>
