export interface SubscriptionDTO{
    tradingAccountId: string;
    productId: number;
    amount: number;
}

export interface RedemptionDTO{
    tradingAccountId: string;
    productId: number;
    shares: number;
}