import type { CustomerDTO } from '@/types/account';
import request from '@/utils/request.ts'
export function createAccountService(data: CustomerDTO) {
    return request.post('/account/create', data);
}