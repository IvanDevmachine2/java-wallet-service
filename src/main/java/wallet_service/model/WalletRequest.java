package wallet_service.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public class WalletRequest {

    @NotNull(message = "Wallet ID is required")
    private UUID walletId;

    @NotNull(message = "Operation type is required")
    private OperationType operationType;

    @NotNull(message = "Сумма обязательна к указанию")
    @Positive(message = "Сумма должна быть положительной")
    @DecimalMin(value = "0.01", message = "Минимальная принимаемая сумма: 0.01")
    private BigDecimal amount;

    public WalletRequest() {
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}