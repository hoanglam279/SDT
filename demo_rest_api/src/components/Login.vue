<template>
    <div class="col-md-6 offset-md-3">
        <div class="card card-container">
            <form @submit="handleLogin">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input v-model="username" type="text" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input v-model="password" type="password" class="form-control" required/>
                </div>

                <div class="form-group">
                    <button class="btn btn-primary btn-block" :disabled="loading">
                        <span v-show="loading" class="spinner-border spinner-border-sm"></span>
                        <span>Login</span>
                    </button>
                </div>

                <div class="form-group">
                    <div v-if="loginError" class="alert alert-danger" role="alert">
                        Tài khoản hoặc mật khẩu của bạn không đúng.
                    </div>
                    <div v-if="loginSuccess" class="alert alert-success" role="alert">
                        Đăng nhập thành công! Đang chuyển hướng đến quản lý sản phẩm...
                    </div>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
export default {
    name: `Login`,
    data() {
        return {
            loading: false,
            loginError: false,
            username: "",
            password: "",
            loginSuccess: false,
        };
    },
    computed: {
        loggedIn() {
            return this.$store.state.auth.status.loggedIn;
        },
    },

    methods: {
        handleLogin(event) {
            event.preventDefault();
            this.loading = true;
            this.loginError = false;

            const user = {
                username: this.username,
                password: this.password,
            };
            //gọi action trong Vuex store
            this.$store.dispatch('auth/login', user).then(
                () => {

                    this.loginSuccess = true;
                    setTimeout(() => {
                        this.$router.push('/products');
                    }, 1500);
                },
                () => {
                    this.loading = false;
                    this.loginError = true;
                }
            );
        },
    },
};
</script>

<style scoped>

</style>
