package com.example.inventoryservice.grpc;

import com.example.inventoryservice.model.Inventory;
import com.example.inventoryservice.repository.InventoryRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class InventoryGrpcService extends InventoryServiceGrpc.InventoryServiceImplBase {

    private final InventoryRepository repository;

    @Override
    public void checkAvailability(InventoryCheckRequest request, StreamObserver<InventoryCheckResponse> responseObserver) {
        boolean available = request.getItemsList().stream().allMatch(item -> {
            Inventory inv = repository.findById(item.getProductId()).orElse(null);
            return inv != null && inv.getQuantity() >= item.getQuantity();
        });

        InventoryCheckResponse response = InventoryCheckResponse.newBuilder()
                .setAvailable(available)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
