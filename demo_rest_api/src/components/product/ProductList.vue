<template>
    <div class="container">
        <h2 class="my-4">Danh sách sản phẩm</h2>
        <div class="mb-3">
            <button v-if="isLoggedIn" @click="showForm()" class="btn btn-success">Thêm sản phẩm</button>
        </div>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Tên</th>
                <th>Giá</th>
                <th>Trạng thái</th>
                <th>Mã sản phẩm</th>
                <th>Danh mục</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="product in products" :key="product.id">
                <td>{{ product.id }}</td>
                <td>{{ product.name }}</td>
                <td>{{ product.price }}</td>
                <td>{{ product.status }}</td>
                <td>{{ product.productCode }}</td>
                <td>{{ product.category.name }}</td>
                <td>
                    <div class="btn-group">
                        <button @click="getProductDetails(product.id)" class="btn btn-info">Chi tiết</button>
                    </div>
                    <div class="btn-group">
                        <button v-if="isLoggedIn" @click="editProduct(product.id)" class="btn btn-primary">Sửa</button>
                    </div>
                    <div class="btn-group">
                        <button v-if="isLoggedIn" @click="deleteProduct(product.id)" class="btn btn-danger">Xóa</button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

        <el-pagination class="pagination"
                       v-model="currentPage"
                       :page-size="pageSize"
                       :total="totalProducts"
                       layout="prev, pager, next"
                       @current-change="handlePageChange"
                       background
        ></el-pagination>

        <div class="modal background-modal d-block" v-if="isShow">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title">{{
                            product.id === null ? "Thêm mới sản phẩm" : "Chỉnh sửa sản phẩm"
                            }}</h3>
                        <button type="button" class="close">
                            <span aria-hidden="true" @click="closeForm()">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form @submit.prevent="submitForm">
                            <div class="form-group">
                                <input type="hidden" class="form-control" v-model="product.id" required>
                            </div>

                            <div class="form-group">
                                <label for="name">Tên:</label>
                                <input type="text" class="form-control" v-model="product.name" required>
                            </div>

                            <div class="form-group">
                                <label for="price">Giá:</label>
                                <input type="number" class="form-control" v-model="product.price" required>
                            </div>

                            <div class="form-group">
                                <label for="status">Tình trạng:</label>
                                <input type="text" class="form-control" v-model="product.status" required>
                            </div>

                            <div class="form-group">
                                <label for="productCode">Mã sản phẩm:</label>
                                <input type="text" class="form-control" v-model="product.productCode" required>
                            </div>

                            <div class="form-group">
                                <label for="category" class="form-label d-block">Danh mục:</label>
                                <select id="category" class="form-select form-control" v-model="product.category.id"
                                        required>
                                    <option v-for="category in categories" :value="category.id" :key="category.id">
                                        {{ category.name }}
                                    </option>
                                </select>
                            </div>
                            <div class="btn-group-cs">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-secondary" @click="closeForm()">Đóng</button>
                                </div>
                                <div class="btn-group">
                                    <button type="submit" class="btn btn-primary">
                                        {{ product.id === null ? "Thêm sản phẩm" : "Cập nhật" }}
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal d-block background-modal" v-if="isShowDetail">
            <div class="modal-dialog">
                <div class="modal-content">
                    <el-card>
                        <h2>Chi tiết sản phẩm</h2>
                        <div>
                            <strong>Tên:</strong> {{ product.name }}
                        </div>
                        <div>
                            <strong>Giá:</strong> {{ product.price }}
                        </div>
                        <div>
                            <strong>Tình trạng:</strong> {{ product.status }}
                        </div>
                        <div>
                            <strong>Mã sản phẩm:</strong> {{ product.productCode }}
                        </div>
                        <div>
                            <strong>Dạnh mục:</strong> {{ product.category !== undefined ? product.category.name : "" }}
                        </div>
                        <div class="btn-group-cs">
                            <button @click="closeForm" class="btn btn-secondary">Đóng</button>
                        </div>
                    </el-card>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import ProductService from "@/services/product.service";
import {functionMixin} from "@/mixin/functionMixin";
import swal from "sweetalert";


export default {
    mixins: [functionMixin],
    data() {
        return {
            isShow: false,
            isShowDetail: false,
            reloadData: false,
            currentPage: 1,
            pageSize: 5,
            totalProducts: 0,
            product: {
                id: null,
                name: "",
                price: "",
                status: "",
                productCode: "",
                category: {
                    id: ""
                }
            },
            categories: [],
            products: []
        };
    },

    computed: {
        isLoggedIn() {
            console.log(this.$store.state.auth)
            return this.$store.state.auth.status.loggedIn;
        },
    },

    methods: {
        showForm() {
            this.isShow = true
        },

        closeForm() {
            this.isShow = false
            this.isShowDetail = false
            this.product = {
                id: null,
                name: "",
                price: "",
                status: "",
                productCode: "",
                category: {
                    id: ""
                }
            }
        },

        handlePageChange(currentPage) {
            this.currentPage = currentPage;
            this.loadProducts();
        },

        async loadProducts() {
            let params = {
                page: this.currentPage - 1,
                size: this.pageSize
            };
            await ProductService.getProductList(params)
                .then((response) => {
                    this.products = response.data.content;
                    this.reloadData = !this.reloadData;
                    this.totalProducts = response.data.totalElements;
                })
                .catch((error) => {
                    console.log(error);
                });
        },

        getProductDetails(id) {
            // this.$router.push({name: "productDetails", params: {id: id}});
            this.getProduct(id)
            this.isShowDetail = true
        },

        deleteProduct(id) {
            if (!this.isUserAllowedToDelete()) {
                swal("Không có quyền!", "Bạn không được phép xóa sản phẩm.", "warning");
                return;
            }
            swal({
                title: "Bạn có chắc chắn?",
                text: "Sau khi xóa, bạn sẽ không thể khôi phục sản phẩm này!",
                icon: "warning",
                buttons: true,
                dangerMode: true,
            }).then((willDelete) => {
                if (willDelete) {
                    ProductService.deleteProduct(id)
                        .then(() => {
                            swal("Thành công!", "Sản phẩm của bạn đã được xóa.", "success");
                            this.loadProducts();
                        })
                        .catch((error) => {
                            console.log(error);
                        });
                }
            });
        },

        isUserAllowedToDelete() {
            const userRole = this.$store.state.auth.user.role; // Lấy vai trò của người dùng từ Vuex state
            return userRole === "ROLE_ADMIN"; // Kiểm tra nếu người dùng là admin mới có quyền xóa
        },
        async getProduct(productId) {
            await ProductService.getProductDetails(productId)
                .then(response => {
                    this.product = response.data;
                })
                .catch(error => {
                    console.log(error);
                });
        },

        editProduct(id) {
            this.getProduct(id)
            this.showForm()
        },

        async submitForm() {
            if (this.product.id == null) {
                await ProductService.createProduct(this.product)
                    .then(() => {
                        swal({
                            title: "Thành Công!",
                            text: "Bạn đã thêm mới sản phẩm thành công!",
                            icon: "success",
                            showConfirmButton: false,
                            timer: 1500
                        });
                    })
                    .catch((error) => {
                        this.$message.error({message: error.response.data, showClose: true});
                    });
            } else {
                await ProductService.updateProduct(this.product.id, this.product)
                    .then(() => {
                        swal({
                            title: "Thành Công!",
                            text: "Sản phẩm đã được sửa đổi thành công!",
                            icon: "success",
                            timer: 1500
                        });
                    })
                    .catch((error) => {
                        this.$message.error({message: error.response.data, showClose: true});
                    });
            }
            await this.loadProducts();
            this.closeForm();

        }
    },

    created() {
        this.getCategories();
        this.loadProducts();
    }
};
</script>

<style scoped>
@import "@/assets/style.css";
</style>
