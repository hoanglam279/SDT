import axios from "axios";
import authHeader from "@/services/auth-header";

const API_URL = 'http://localhost:8080/products';

class ProductService {
    getProductList(params) {
        return axios.get(API_URL,{params: params});
    }

    getProductDetails(id) {
        return axios.get(API_URL + '/' + id);
    }

    createProduct(data) {
        return axios.post(API_URL + '/add', data, {headers: authHeader()});
    }

    updateProduct(id, data) {
        return axios.put(API_URL + '/edit/' + id, data, {headers: authHeader()});
    }

    deleteProduct(id) {
        return axios.delete(API_URL + '/delete/' + id, {headers: authHeader()});
    }
}

export default new ProductService();

