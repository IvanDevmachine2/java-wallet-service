package wallet_service.service;

import wallet_service.model.OperationType;
import wallet_service.model.Wallet;
import wallet_service.model.WalletRequest;
import wallet_service.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Wallet processOperation(WalletRequest request) {
        Wallet wallet = walletRepository.findByIdWithLock(request.getWalletId())
                .orElseGet(() -> {
                    Wallet newWallet = new Wallet(request.getWalletId(), BigDecimal.ZERO);
                    return walletRepository.save(newWallet);
                });

        BigDecimal newAmount;
        if (request.getOperationType() == OperationType.DEPOSIT) {
            newAmount = wallet.getAmount().add(request.getAmount());
        } else {
            newAmount = wallet.getAmount().subtract(request.getAmount());
            if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException(
                        "Insufficient funds. Current balance: " + wallet.getAmount());
            }
        }

        wallet.setAmount(newAmount);
        return walletRepository.save(wallet);
    }

    @Transactional(readOnly = true)
    public BigDecimal getBalance(UUID walletId) {
        return walletRepository.findById(walletId)
                .map(Wallet::getAmount)
                .orElseThrow(() -> new RuntimeException("Wallet not found: " + walletId));
    }
}