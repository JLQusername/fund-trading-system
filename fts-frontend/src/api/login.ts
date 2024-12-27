import request from '@/utils/request.ts'
import type { LoginDTO } from '@/types/login.d.ts';

export function loginService(data: LoginDTO) {
    return request.post('/login', data);
}

export function resetPwService(data: LoginDTO) {
    return request.patch('/login/change_password', data);
}