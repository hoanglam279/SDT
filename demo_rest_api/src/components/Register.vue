<template>
    <div class="col-md-6 offset-md-3">
        <div class="card card-container">
            <form @submit="handleRegister">
                <div class="form-group">
                    <label for="username">Username</label>
                    <input v-model="username" type="text" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input v-model="email" type="text" class="form-control" required/>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input v-model="password" type="password" class="form-control" required/>
                </div>

                <div class="form-group">
                    <button class="btn btn-primary btn-block" :disabled="loading">
                        <span v-show="loading" class="spinner-border spinner-border-sm"></span>
                        <span>Register</span>
                    </button>
                </div>

                <div class="form-group">
                    <div v-if="registerError" class="alert alert-danger" role="alert">
                        Lỗi khi đăng ký tài khoản.
                    </div>
                    <div v-if="registerSuccess" class="alert alert-success" role="alert">
                        Đăng ký thành công! Đang chuyển hướng đến trang đăng nhập...
                    </div>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
export default {
    name: `Register`,
    data() {
        return {
            loading: false,
            registerError: false,
            username: '',
            email: '',
            password: '',
            registerSuccess: false,
        };
    },
    methods: {
        handleRegister(event) {
            event.preventDefault();
            this.loading = true;
            this.registerError = false;

            const user = {
                username: this.username,
                email: this.email,
                password: this.password,
            };
            this.$store.dispatch('auth/register', user)
                .then(() => {
                    this.registerSuccess = true;
                    setTimeout(() => {
                        this.$router.push('/login');
                    }, 1500);
                })
                .catch(() => {
                    this.loading = false;
                    this.registerError = true;
                });
        },
    },
};
</script>

<style scoped>

</style>
