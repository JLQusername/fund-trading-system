import type { CustomerDTO,BankcardDTO,UpdateInfoDTO } from '@/types/account';
import request from '@/utils/request.ts'
export function createAccountService(data: CustomerDTO) {
    return request.post('/account/create', data);
}

export function addBankcardService(data: BankcardDTO) {
    return request.post('/account/add_bankcard', data);
}

export function UpdateInfoService(data: UpdateInfoDTO) {
    return request.put('/account/info', data);
}

export function getBankcardsService(fundAccount: string) {
    return request.get('/account/bankcards', { params: { fundAccount } });
}

export function deleteBankcardService(tradingAccountId: string) {
    return request.get('/account//delete_bankcard', { params: { tradingAccountId } });
}
