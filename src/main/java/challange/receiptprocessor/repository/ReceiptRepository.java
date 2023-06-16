package challange.receiptprocessor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import challange.receiptprocessor.domain.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, Long>{
    
}
