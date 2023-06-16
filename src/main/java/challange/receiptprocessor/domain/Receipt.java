package challange.receiptprocessor.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String retailer;
    @NonNull
    private String purchaseDate;
    @NonNull
    private String purchaseTime;
    @NonNull
    private String total;
    @OneToMany(mappedBy="receipt")
    @NonNull
    private List<Item> items;

    public Receipt(Receipt r) {
        this.retailer = r.retailer;
        this.purchaseDate = r.purchaseDate;
        this.purchaseTime = r.purchaseTime;
        this.total = r.total;
        addItems(r.getItems());
    }

    public Receipt(Long id, String retailer, String purchaseDate, String purchaseTime,
            String total, List<Item> items) {
        this.id = id;
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.total = total;
        addItems(items);
    }

    private void addItems(List<Item> items){
        this.items = items;
        for(Item i : this.getItems()){
            i.setReceipt(this);
        }
    }
    
}
