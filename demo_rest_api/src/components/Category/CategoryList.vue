<template>
    <div class="container">
        <h2 class="my-4">Danh sách danh mục</h2>
        <div class="mb-3">
            <button @click="showForm()" class="btn btn-success">Thêm danh mục</button>
        </div>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Tên</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="category in categories" :key="category.id">
                <td>{{ category.id }}</td>
                <td>{{ category.name }}</td>
                <td>
                    <div class="btn-group">
                        <button @click="editCategory(category.id)" class="btn btn-primary">Sửa</button>
                    </div>
                    <div class="btn-group">
                        <button @click="deleteCategory(category.id)" class="btn btn-danger">Xóa</button>
                    </div>
                </td>
            </tr>
            </tbody>
        </table>

        <div class="modal background-modal d-block" v-if="isShow">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title">{{
                            category.id === null ? "Thêm mới danh mục" : "Chỉnh sửa danh mục"
                            }}</h3>
                        <button type="button" class="close">
                            <span aria-hidden="true" @click="closeForm()">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form @submit.prevent="submitForm">
                            <div class="form-group">
                                <input type="hidden" class="form-control" v-model="category.id" required>
                            </div>

                            <div class="form-group">
                                <label for="name">Tên:</label>
                                <input type="text" class="form-control" v-model="category.name" required>
                            </div>
                            <div class="btn-group-cs">
                                <div class="btn-group">
                                    <button type="button" class="btn btn-secondary" @click="closeForm()">Đóng</button>
                                </div>
                                <div class="btn-group">
                                    <button type="submit" class="btn btn-primary">
                                        {{ category.id === null ? "Thêm danh mục" : "Cập nhật" }}
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import swal from "sweetalert";
import CategoryService from "@/services/category.service";

export default {
    data() {
        return {
            isShow: false,
            reloadData: false,
            category: {
                id: null,
                name: ""
            },
            categories: []
        };
    },

    methods: {
        showForm() {
            this.isShow = true
        },

        closeForm() {
            this.isShow = false
            this.category = {
                id: null,
                name: ""
            }
        },

        async loadCategory() {
            await CategoryService.getCategoryList()
                .then((response) => {
                    this.categories = response.data;
                    this.reloadData = !this.reloadData;
                })
                .catch((error) => {
                    console.log(error);
                });
        },

        deleteCategory(id) {
            swal({
                title: "Bạn có chắc chắn?",
                text: "Sau khi xóa, bạn sẽ không thể khôi phục danh mục này!",
                icon: "warning",
                buttons: true,
                dangerMode: true,
            }).then((willDelete) => {
                if (willDelete) {
                    CategoryService.deleteCategories(id)
                        .then(() => {
                            swal("Thành công!", "Danh mục của bạn đã được xóa.", "success");
                            this.loadCategory();
                        })
                        .catch((error) => {
                            this.$message.error({message: error.response.data, showClose: true});
                        });
                }
            });
        },

        editCategory(id) {
            this.getCategory(id)
            this.showForm()
        },

        async getCategory(CategoryId) {
            await CategoryService.getCategoriesDetails(CategoryId)
                .then(response => {
                    this.category = response.data;
                })
                .catch(error => {
                    console.log(error);
                });
        },

        async submitForm() {
            if (this.category.id == null) {
                await CategoryService.createCategories(this.category)
                    .then(() => {
                        swal({
                            title: "Thành Công!",
                            text: "Bạn đã thêm mới danh mục thành công!",
                            icon: "success",
                            showConfirmButton: false,
                            timer: 1500
                        });
                    })
                    .catch((error) => {
                        this.$message.error({message: error.response.data, showClose: true});
                    });
            } else {
                await CategoryService.updateCategories(this.category.id, this.category)
                    .then(() => {
                        swal({
                            title: "Thành Công!",
                            text: "Danh mục đã được sửa đổi thành công!",
                            icon: "success",
                            timer: 1500
                        });
                    })
                    .catch((error) => {
                        this.$message.error({message: error.response.data, showClose: true});
                    });
            }
            await this.loadCategory();
            this.closeForm();

        }
    },

    created() {
        this.loadCategory();
    }
};
</script>

<style scoped>
@import "@/assets/style.css";
</style>
