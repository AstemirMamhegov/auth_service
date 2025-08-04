package com.example.orderservice.grpc;

import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StockCheckClient {

    @GrpcClient("inventory-service")
    private StockCheckerGrpc.StockCheckerBlockingStub stockStub;

    public boolean check(StockCheckRequest request) {
        try {
            StockCheckResponse response = stockStub.checkStock(request);
            return response.getAvailable();
        } catch (StatusRuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }
}
