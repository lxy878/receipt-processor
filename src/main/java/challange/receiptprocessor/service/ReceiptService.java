package challange.receiptprocessor.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import challange.receiptprocessor.domain.Item;
import challange.receiptprocessor.domain.Receipt;
import challange.receiptprocessor.repository.ItemRepository;
import challange.receiptprocessor.repository.ReceiptRepository;

@Service
public class ReceiptService {
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private ItemRepository itemRepository;
    
    public Receipt save(Receipt receipt){
        Receipt newReceipt = new Receipt(receipt);
        receiptRepository.save(newReceipt);
        itemRepository.saveAll(newReceipt.getItems());
        return newReceipt;
    }

    public Integer point(Long id){
        Optional<Receipt> or = receiptRepository.findById(id);
        if(!or.isPresent()){
            return null;
        }
        Receipt r = or.get();
        Integer result = 0;
        result += alphCount(r.getRetailer());
        double total = Double.parseDouble(r.getTotal());
        result += isInt(total);
        result += isMultBy25(total);
        result += isOddDate(r.getPurchaseDate());
        result += timeCheck(r.getPurchaseTime());
        result += pairCount(r.getItems());
        result += itemCharCount(r.getItems());
        
        return result;
    }

    public int alphCount(String str){
        return str.replaceAll("[^a-zA-Z0-9]", "").length();
    }
    public int isInt(Double d){
        return d == Math.floor(d) ? 50:0;
    }
    public int isMultBy25(Double d){
        return ((d*100)%25==0) ? 25:0;
    }
    public int isOddDate(String date){
        int d = LocalDate.parse(date).getDayOfMonth();
        return (d%2==1) ? 6:0;
    }
    public int timeCheck(String time){
        LocalTime t = LocalTime.parse(time);
        return (t.isBefore(LocalTime.of(16,0))&&t.isAfter(LocalTime.of(14,0))) ? 10:0;
    }
    public int pairCount(List<Item> items){
        return (items.size()/2)*5;
    }
    public Integer itemCharCount(List<Item> items){
        Integer point = 0;
        for(Item i : items){
            String des = i.getShortDescription().trim();
            if(des.length()%3==0){
                point += (int) Math.ceil(Double.parseDouble(i.getPrice())*0.2);
            }
        }
        return point;
    }
    
}
