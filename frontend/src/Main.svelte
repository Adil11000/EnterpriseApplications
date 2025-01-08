<script lang="ts">
    import ChevronLeft from "lucide-svelte/icons/chevron-left";
    import ChevronRight from "lucide-svelte/icons/chevron-right";

    import * as Select from "$lib/components/ui/select/index.js";
    import * as Pagination from "$lib/components/ui/pagination/index.js";
    import * as Table from "$lib/components/ui/table/index.js";
    import * as Dialog from "$lib/components/ui/dialog/index.js";

    import { Label } from "$lib/components/ui/label/index.js";
    import { Input } from "$lib/components/ui/input/index.js";

    import { onMount } from "svelte";
    import { logout, fetchwrap } from "./lib/auth";

    import CalendarIcon from "lucide-svelte/icons/calendar";
    import {
        type DateValue,
        DateFormatter,
        getLocalTimeZone,
    } from "@internationalized/date";
    import { cn } from "$lib/utils.js";
    import { Button, buttonVariants } from "$lib/components/ui/button/index.js";
    import { Calendar } from "$lib/components/ui/calendar/index.js";
    import * as Popover from "$lib/components/ui/popover/index.js";
    import { toast } from "svelte-sonner";

    const df = new DateFormatter("en-US", {
        dateStyle: "long",
    });

    function formatDate(date: string) {
        const parsed = new Date(date);
        return df.format(parsed);
    }

    let returnDate = $state<DateValue>();

    let { user = $bindable() } = $props();

    interface Category {
        id: number;
        name: string;
    }

    interface ProductPage {
        content: Product[];
        totalPages: number;
        totalElements: number;
        first: boolean;
        last: boolean;
        number: number;
        empty: boolean;
    }

    interface Product {
        id: number;
        name: string;
        description: string;
        price: number;
    }

    interface CartItem {
        product: Product;
        quantity: number;
    }

    interface Cart {
        items: CartItem[];
    }

    interface Reservation {
        id: number;
        returned: boolean;
        reservedUntil: string;
        items: CartItem[];
    }

    const pageSize = 20;
    let page = $state(1);
    let sortDirection: "ASC" | "DESC" = $state("ASC");
    let sortField: "name" | "price" = $state("name");
    let searchQuery = $state("");
    let searchCategory: number | null = $state(null);

    let fullQuery = $state("");
    $effect(() => {
        regenerateQuery();
    });

    function regenerateQuery() {
        fullQuery = `?page=${page}&size=${pageSize}&sortField=${sortField}&sortDirection=${sortDirection}${searchQuery ? `&query=${searchQuery}` : ""}${
            searchCategory ? `&category=${searchCategory}` : ""
        }`;
    }

    let categories: Category[] = $state([]);
    let productsPage: ProductPage = $state({
        content: [],
        totalPages: 0,
        totalElements: 0,
        first: false,
        last: false,
        number: 0,
        empty: true,
    });
    let cartItems: CartItem[] = $state([]);
    let reservations: Reservation[] = $state([]);

    onMount(async () => {
        categories = await fetchwrap("api/v1/categories", "GET", null).then(
            (res) => res.json(),
        );
        productsPage = await fetchwrap(
            `api/v1/products${fullQuery}`,
            "GET",
            null,
        ).then((res) => res.json());
        const cart = await fetchwrap("api/v1/user/cart", "GET", null).then(
            (res) => res.json(),
        );
        cartItems = cart.items;
        reservations = await fetchwrap(
            "api/v1/user/reservations",
            "GET",
            null,
        ).then((res) => res.json());
    });

    async function addItemToCart(product: Product) {
        const cart = await fetchwrap("api/v1/user/cart/add", "POST", {
            productId: product.id,
            quantity: 1,
        }).then((res) => res.json());
        cartItems = cart.items;
    }

    async function removeItemFromCart(product: Product) {
        const cart = await fetchwrap("api/v1/user/cart/remove", "POST", {
            productId: product.id,
            quantity: 1,
        }).then((res) => res.json());
        cartItems = cart.items;
    }

    async function search(searchCategoryArg: number | null = searchCategory) {
        searchCategory = searchCategoryArg;
        regenerateQuery();
        productsPage = await fetchwrap(
            `api/v1/products${fullQuery}`,
            "GET",
            null,
        ).then((res) => res.json());
    }

    async function checkout() {
        if (cartItems.length === 0) {
            toast.error("Cart is empty");
            return;
        }
        if (!returnDate) {
            toast.error("Please select a return date");
            return;
        }
        const reservation = await fetchwrap(
            "api/v1/user/cart/checkout",
            "POST",
            {
                reservedUntil: returnDate.toDate(getLocalTimeZone()),
            },
        ).then((res) => res.json());
        cartItems = [];
        returnDate = undefined;
    }

    async function returnReservation(reservation: Reservation) {
        await fetchwrap("api/v1/user/reservations/return", "POST", {
            reservationId: reservation.id,
        }).then(() => {
            reservations = reservations.map((r) =>
                r.id === reservation.id ? { ...r, returned: true } : r,
            );
        });
    }
</script>

<!-- Layout Description -->
<!-- * Header (Has email, old reservartions popup button, logout button) -->
<!-- * Sidebar left (~15%) (Has categories) -->
<!-- * Middle content (~70%) (Has search results) -->
<!-- * Sidebar right (~15%) (Has cart) -->

<div class="flex justify-center">
    <header class="p-4 w-[700px] flex flex-row justify-between">
        <span>Welcome, {user.email}</span>
        <Dialog.Root>
            <Dialog.Trigger class={buttonVariants({ variant: "default" })}
                >Reservations</Dialog.Trigger
            >
            <Dialog.Content class="sm:max-w-[800px]">
                <Dialog.Header>
                    <Dialog.Title>Reservations</Dialog.Title>
                    <Dialog.Description>
                        See your old reservations
                    </Dialog.Description>
                </Dialog.Header>
                <div class="grid gap-4 py-4">
                    {#each reservations as reservation (reservation.id)}
                        <div class="flex flex-row justify-between">
                            <div class="flex flex-col">
                                <span
                                    class={cn(
                                        "text-sm",
                                        reservation.returned
                                            ? "text-green-500"
                                            : "text-red-500",
                                    )}
                                    >{reservation.returned
                                        ? "Returned"
                                        : "Not returned"}</span
                                >
                                <span
                                    >Reserved until: {formatDate(
                                        reservation.reservedUntil,
                                    )}</span
                                >
                            </div>
                            {#if !reservation.returned}
                                <Button
                                    onclick={() => {
                                        returnReservation(reservation);
                                    }}>Return</Button
                                >
                            {/if}
                        </div>
                        <ul>
                            {#each reservation.items as item (item.product.id)}
                                <li>
                                    <div class="flex flex-row justify-between">
                                        <span>{item.product.name}</span>
                                        <span>{item.quantity}</span>
                                    </div>
                                </li>
                            {/each}
                        </ul>
                    {/each}
                </div>
            </Dialog.Content>
        </Dialog.Root>
        <Button
            onclick={() => {
                logout();
            }}
        >
            Logout
        </Button>
    </header>
</div>

<div class="flex flex-row justify-center">
    <main class="w-[700px] mr-32">
        <div class="flex flex-row justify-between gap-3">
            <Select.Root
                type="single"
                onValueChange={(value) => {
                    sortDirection = value;
                    search();
                }}
            >
                <Select.Trigger>{sortDirection}</Select.Trigger>
                <Select.Content>
                    <Select.Item value="ASC">Ascending</Select.Item>
                    <Select.Item value="DESC">Descending</Select.Item>
                </Select.Content>
            </Select.Root>
            <Select.Root
                type="single"
                onValueChange={(value) => {
                    sortField = value;
                    search();
                }}
            >
                <Select.Trigger>{sortField}</Select.Trigger>
                <Select.Content>
                    <Select.Item value="name">Name</Select.Item>
                    <Select.Item value="price">Price</Select.Item>
                </Select.Content>
            </Select.Root>
            <Select.Root
                type="single"
                onValueChange={(value) => {
                    searchCategory = value;
                    search(searchCategory);
                }}
            >
                <Select.Trigger
                    >{searchCategory
                        ? categories.find((c) => c.id === searchCategory)?.name
                        : "All"}</Select.Trigger
                >
                <Select.Content>
                    <Select.Item value={null}>All</Select.Item>
                    {#each categories as category (category.id)}
                        <Select.Item value={category.id}
                            >{category.name}</Select.Item
                        >
                    {/each}
                </Select.Content>
            </Select.Root>
            <Input
                type="text"
                placeholder="Search"
                oninput={(e) => {
                    searchQuery = e.target.value;
                    search();
                }}
            />
        </div>
        <Table.Root>
            <Table.Header>
                <Table.Row>
                    <Table.Head class="w-[100px]">Name</Table.Head>
                    <Table.Head>Description</Table.Head>
                    <Table.Head class="text-right">Price</Table.Head>
                </Table.Row>
            </Table.Header>
            <Table.Body>
                {#each productsPage.content as product, i (i)}
                    <Table.Row>
                        <Table.Cell class="font-medium"
                            >{product.name}</Table.Cell
                        >
                        <Table.Cell>{product.description}</Table.Cell>
                        <Table.Cell class="text-right"
                            >{product.price}</Table.Cell
                        >
                        <Table.Cell class="text-right">
                            <button
                                onclick={() => {
                                    addItemToCart(product);
                                }}
                            >
                                Add to cart
                            </button>
                        </Table.Cell>
                    </Table.Row>
                {/each}
            </Table.Body>
        </Table.Root>
        <Pagination.Root count={productsPage.totalElements} perPage={pageSize}>
            {#snippet children({ pages, currentPage })}
                <Pagination.Content>
                    <Pagination.Item>
                        <Pagination.PrevButton>
                            <ChevronLeft class="size-4" />
                            <span class="hidden sm:block">Previous</span>
                        </Pagination.PrevButton>
                    </Pagination.Item>
                    {#each pages as page (page.key)}
                        {#if page.type === "ellipsis"}
                            <Pagination.Item>
                                <Pagination.Ellipsis />
                            </Pagination.Item>
                        {:else}
                            <Pagination.Item>
                                <Pagination.Link
                                    {page}
                                    isActive={currentPage === page.value}
                                >
                                    {page.value}
                                </Pagination.Link>
                            </Pagination.Item>
                        {/if}
                    {/each}
                    <Pagination.Item>
                        <Pagination.NextButton>
                            <span class="hidden sm:block">Next</span>
                            <ChevronRight class="size-4" />
                        </Pagination.NextButton>
                    </Pagination.Item>
                </Pagination.Content>
            {/snippet}
        </Pagination.Root>
    </main>
    <aside class="flex flex-col">
        <h2 class="text-lg font-semibold">Cart</h2>
        <ul>
            {#if cartItems.length === 0}
                <li>Cart is empty</li>
            {/if}
            {#each cartItems as cartItem (cartItem.product.id)}
                <li>
                    <div class="flex flex-col justify-between">
                        <div class="flex flex-row justify-between">
                            <span>{cartItem.product.name}</span>
                        </div>
                        <div class="flex flex-row justify-between">
                            <Button
                                onclick={() => {
                                    removeItemFromCart(cartItem.product);
                                }}
                            >
                                -
                            </Button>
                            <span>{cartItem.quantity}</span>
                            <Button
                                onclick={() => {
                                    addItemToCart(cartItem.product);
                                }}
                            >
                                +
                            </Button>
                        </div>
                    </div>
                </li>
            {/each}
        </ul>
        <Popover.Root>
            <Popover.Trigger>
                {#snippet child({ props })}
                    <Button
                        variant="outline"
                        class={cn(
                            "w-[280px] justify-start text-left font-normal",
                            !returnDate && "text-muted-foreground",
                        )}
                        {...props}
                    >
                        <CalendarIcon class="mr-2 size-4" />
                        {returnDate
                            ? df.format(returnDate.toDate(getLocalTimeZone()))
                            : "Select a date"}
                    </Button>
                {/snippet}
            </Popover.Trigger>
            <Popover.Content class="w-auto p-0">
                <Calendar bind:value={returnDate} type="single" initialFocus />
            </Popover.Content>
        </Popover.Root>
        <Button
            onclick={() => {
                checkout();
            }}
        >
            Checkout
        </Button>
    </aside>
</div>

<footer></footer>
