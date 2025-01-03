// api/product.ts
import request from '@/utils/request';
import type { Product } from '@/types/product';

export function fetchProducts(page = 1, pageSize = 10) {
    return request.get('/product/list', { params: { page, pageSize } });
}

export function searchProducts(keyword:string) {
    return request.get(`/product/search/${keyword}`);
}

export function fetchNetValue(productId: number, date: string) {
    return request.get(`/product/netvalue/${productId}/${date}`);
}

export function fetchTransactionDate() {
    return request.get('/settle/system/transaction-date');
}
