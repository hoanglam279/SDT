<template>
    <div id="app">
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <router-link to="/home" class="navbar-brand">
                <font-awesome-icon icon="home"/>
                Home
            </router-link>
            <button
                    class="navbar-toggler"
                    type="button"
                    data-toggle="collapse"
                    data-target="#navbarNav"
                    aria-controls="navbarNav"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
            >
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <router-link to="/products" class="nav-link">Product</router-link>
                    </li>
                    <li v-if="showCategory" class="nav-item">
                        <router-link to="/categories" class="nav-link">Category</router-link>
                    </li>
                </ul>
                <ul class="navbar-nav ml-auto">
                    <li v-if="!currentUser" class="nav-item">
                        <router-link to="/register" class="nav-link">
                            <font-awesome-icon icon="user-plus"/>
                            Sign Up
                        </router-link>
                    </li>
                    <li v-if="!currentUser" class="nav-item">
                        <router-link to="/login" class="nav-link">
                            <font-awesome-icon icon="sign-in-alt"/>
                            Login
                        </router-link>
                    </li>
                    <li v-if="currentUser" class="nav-item dropdown">
                        <a
                                class="nav-link dropdown-toggle"
                                href="#"
                                role="button"
                                data-toggle="dropdown"
                                aria-haspopup="true"
                                aria-expanded="false"
                        >
                            <font-awesome-icon icon="user"/>
                            {{ currentUser.username }}
                        </a>
                        <div class="dropdown-menu dropdown-menu-right">
                            <router-link to="/profile" class="dropdown-item">
                                <font-awesome-icon icon="user"/>
                                Profile
                            </router-link>
                            <a class="dropdown-item" href="#" @click.prevent="logOut">
                                <font-awesome-icon icon="sign-out-alt"/>
                                Logout
                            </a>
                        </div>
                    </li>
                </ul>
            </div>
        </nav>

        <div class="container mt-4">
            <router-view/>
        </div>
    </div>
</template>

<script>
export default {
    computed: {
        currentUser() {
            return this.$store.state.auth.user;
        },
        showCategory() {
            if (this.currentUser && this.currentUser.role) {
                return this.currentUser.role.includes("ROLE_ADMIN");
            }
            return false;
        }
    },

    methods: {
        logOut() {
            this.$store.dispatch("auth/logout");
            this.$router.push("/login");
        },
    },
};
</script>
