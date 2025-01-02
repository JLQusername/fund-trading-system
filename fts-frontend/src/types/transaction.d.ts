export interface SubscriptionDTO{
    tradingAccountId: string;
    fundAccount: string;
    productId: number;
    amount: number;
}

export interface RedemptionDTO{
    tradingAccountId: string;
    fundAccount: string;
    productId: number;
    shares: number;
}