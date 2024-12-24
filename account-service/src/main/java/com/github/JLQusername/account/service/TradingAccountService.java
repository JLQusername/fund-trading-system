package com.github.JLQusername.account.service;

import com.github.JLQusername.api.Bankcard;
import com.github.JLQusername.account.domain.dto.BankcardDTO;
import com.github.JLQusername.account.domain.vo.BankcardVO;

import java.util.List;

public interface TradingAccountService {
    long addBankcard(BankcardDTO bankcardDTO);

    boolean deleteBankcard(long tradingAccountId);

    List<BankcardVO> getBankcards(long fundAccount);

    Bankcard getBankcardByTradingAccountId(long tradingAccountId);

    boolean updateBalance(Bankcard bankcard);
}
