import axios from "axios";
import authHeader from "@/services/auth-header";

const API_URL = 'http://localhost:8080/categories';

class CategoryService {
    getCategoryList() {
        return axios.get(API_URL);
    }

    getCategoriesDetails(id) {
        return axios.get(API_URL + '/' + id);
    }

    createCategories(data) {
        return axios.post(API_URL + '/add', data, {headers: authHeader()});
    }

    updateCategories(id, data) {
        return axios.put(API_URL + '/edit/' + id, data, {headers: authHeader()});
    }

    deleteCategories(id) {
        return axios.delete(API_URL + '/delete/' + id, {headers: authHeader()});
    }
}

export default new CategoryService();

