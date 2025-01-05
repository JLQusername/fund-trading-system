import request from '@/utils/request.ts'
import { ElMessage } from 'element-plus';
import type { SubscriptionDTO,RedemptionDTO } from '@/types/transaction';

export function submitSubscriptionService(data: SubscriptionDTO) {
    return request.post('/transaction/subscription', data);
}

export function submitRedemptionService(data: RedemptionDTO) {
    return request.post('/transaction/redemption', data);
}

export function cancelService(transactionId: string){
    return request.post('/transaction/cancel',{}, { params: {transactionId} });
}