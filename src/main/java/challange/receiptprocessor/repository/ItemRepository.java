package challange.receiptprocessor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import challange.receiptprocessor.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
    
}
