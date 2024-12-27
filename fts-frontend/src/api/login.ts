import request from '@/utils/request.ts'
import type { LoginDTO } from '@/types/login.d.ts';
import { ElMessage } from 'element-plus';

export function loginService(data: LoginDTO) {
    return request.post('/login', data);
}

export function resetPwService(data: LoginDTO) {
    return request.patch('/login/change_password', data);
}

export function verificationService(phoneNumber: string) {
    return request.post('/login/verification', phoneNumber);
}

export function sendCodeService(phoneNumber: string) {
    if(!phoneNumber){
        ElMessage.error('请先输入手机号');
        return;
    }
    verificationService(phoneNumber);
    setTimeout(() => {
        ElMessage.success('验证码已发送');
    }, 500);
}