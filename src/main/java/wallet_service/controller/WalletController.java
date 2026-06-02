package wallet_service.controller;

import wallet_service.model.Wallet;
import wallet_service.model.WalletRequest;
import wallet_service.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/wallet")
    public ResponseEntity<Map<String, Object>> processOperation(
            @Valid @RequestBody WalletRequest request) {
        Wallet updatedWallet = walletService.processOperation(request);
        return ResponseEntity.ok(Map.of(
                "walletId", updatedWallet.getId(),
                "balance", updatedWallet.getAmount(),
                "message", "Operation completed successfully"
        ));
    }

    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<Map<String, Object>> getBalance(@PathVariable UUID walletId) {
        return ResponseEntity.ok(Map.of(
                "walletId", walletId,
                "balance", walletService.getBalance(walletId)
        ));
    }
}