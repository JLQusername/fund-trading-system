import request from '@/utils/request.ts'

export function getTransactionsService(fundAccount: string) {
    return request.get('/query/transactions', { params: { fundAccount } });
}