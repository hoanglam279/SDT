import CategoryService from "@/services/category.service";

export const functionMixin = {
    methods: {
        async getCategories(){
            await CategoryService.getCategoryList()
                .then(response => {
                    this.categories = response.data;
                })
                .catch(error => {
                    console.error('Error retrieving categories:', error);
                });
        }
    }
}
