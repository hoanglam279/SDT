import {createWebHistory, createRouter} from "vue-router";
import Home from "./components/Home.vue";
import Login from "./components/Login.vue";
import Register from "./components/Register.vue";
import ProductList from "@/components/product/ProductList.vue";
import CategoryList from "@/components/Category/CategoryList.vue";
import Profile from "@/components/Profile.vue";
import Forbidden from "@/components/Forbidden.vue";
import LifecycleExample from "@/components/Example/LifecycleExample.vue";

const routes = [
    {
        path: "/",
        name: "home",
        component: Home,
    },
    {
        path: "/home",
        component: Home,
    },
    {
        path: "/login",
        component: Login,
    },
    {
        path: "/register",
        component: Register,
    },
    {
        path: "/profile",
        name: "profile",
        component: Profile,
    },
    {
        path: "/products",
        name: "products",
        component: ProductList,
    },
    {
        path: "/categories",
        name: "categories",
        component: CategoryList,
    },
    {
        path: "/403",
        name: "forbidden",
        component: Forbidden,
    },
    {
        path: "/test2",
        name: "lifecycle",
        component: LifecycleExample,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});
router.beforeEach((to, from, next) => {
    const publicPages = ['/login', '/register', '/home', '/products', '/test2'];
    const adminPages = ['/categories'];
    const authRequired = !publicPages.includes(to.path);
    const adminRequired = adminPages.some(page => to.path.startsWith(page));
    const loggedIn = localStorage.getItem('user');

    if (authRequired && !loggedIn) {
        next('/login');
    } else if (adminRequired) {
        const user = JSON.parse(localStorage.getItem('user'));
        if (user && user.role === 'ROLE_ADMIN') {
            next();
        } else {
            next('/403');
        }
    } else {
        next();
    }
});

export default router;
