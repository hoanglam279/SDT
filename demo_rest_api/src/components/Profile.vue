<template>
    <div class="container">
        <header class="jumbotron">
            <h3>
                <strong>{{ currentUser.username }}</strong> Profile
            </h3>
        </header>
        <p>
            <strong>Token:</strong>
            {{ currentUser.token }}
        </p>
        <p>
            <strong>Username:</strong>
            {{ currentUser.username }}
        </p>
        <p>
            <strong>Role:</strong>
            {{ currentUser.role }}
        </p>
        <router-link to="/products" class="btn-secondary">Quay lại</router-link>
    </div>
</template>

<script>
export default {
    name: `Profile`,
    computed: {
        currentUser() {
            return this.$store.state.auth.user;
        }
    }
};
</script>
