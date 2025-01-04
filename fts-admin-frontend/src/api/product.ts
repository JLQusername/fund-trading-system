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

export function apiAddProduct(product: Product) {
    return request.post('/product/add', product);
}

export function apiUpdateProduct(product: Product): Promise<Product> {
    return request({
        url: `/product/${product.productId}`, // 假设这是更新产品的API路径
        method: 'put', // 使用PUT方法进行更新
        data: {
            productName: product.productName,
            riskLevel: product.riskLevel,
            productType: product.productType,
            productStatus: product.productStatus,
            // 如果有其他字段需要更新，请在此添加
        }
    });
}
