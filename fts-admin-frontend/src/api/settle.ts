import request from '@/utils/request';

export function initializeDayService() {
    return request.post('/settle/init');
}

export function receiveMarketDataService() {
    return request.post('/settle/market');
}

export function confirmSubscriptionDataService() {
    return request.post('/settle/subscription');
}

export function confirmRedemptionDataService() {
    return request.post('/settle/redemption');
}

export function stopDailyApplicationsService() {
    return request.post('/settle/stop');
}

export function exportDataService() {
    return request.post('/settle/export');
}

export function getSystemService() {
    return request.get('/settle');
}