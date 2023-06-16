package challange.receiptprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import challange.receiptprocessor.domain.Item;
import challange.receiptprocessor.domain.Receipt;
import challange.receiptprocessor.repository.ReceiptRepository;
import challange.receiptprocessor.service.ReceiptService;

@SpringBootTest
public class ReceiptServiceTest {
    
    @Mock
    private ReceiptRepository receiptRepository;

    @InjectMocks
    private ReceiptService receiptService;

    private List<Item> items;
    private Receipt receipt;

    @BeforeEach
    void setUp(){
        items = Arrays.asList(
            new Item(1L, "Mountain Dew 12PK", "6.49", null),
            new Item(2L, "Emils Cheese Pizza", "12.25", null),
            new Item(3L, "Knorr Creamy Chicken","1.26", null),
            new Item(4L, "Doritos Nacho Cheese","3.35", null),
            new Item(5L, "   Klarbrunn 12-PK 12 FL OZ  ","12.00", null)
        );
        receipt = new Receipt(
            1L,
            "Target", 
            "2022-01-01",
            "13:01", 
            "35.35",
            items
        );
    }
    @Test
    void saveTest(){
        when(receiptRepository.save(any(Receipt.class))).thenReturn(receipt);

        Receipt saveReceipt = receiptRepository.save(receipt);

        verify(receiptRepository, times(1)).save(any(Receipt.class));

        assertEquals(receipt.getId(), saveReceipt.getId());
        assertEquals(items.size(), saveReceipt.getItems().size());
        assertEquals(items.get(0).getId(), saveReceipt.getItems().get(0).getId());
        
    }
    @Test
    void alphCountTest(){
        String str = " M&M Corner Market ";
        int expectedCount = 14;
        int actualCount = receiptService.alphCount(str);
        assertEquals(expectedCount, actualCount);
        assertNotEquals(expectedCount+1, actualCount);
    }
    @Test
    void isInt(){
        int expectedNum = 50;
        int actualNum = receiptService.isInt(10.0);
        assertEquals(expectedNum, actualNum);

        expectedNum = 0;
        actualNum = receiptService.isInt(10.1);
        assertEquals(expectedNum, actualNum);
        
    }
    @Test
    void isMultBy25Test(){
        int expectedNum = 25;
        int actualNum = receiptService.isMultBy25(0.25);
        assertEquals(expectedNum, actualNum);
        expectedNum = 0;
        actualNum = receiptService.isMultBy25(0.26);
        assertEquals(expectedNum, actualNum);

    }
    @Test
    void isOddDateTest(){
        int expectedNum = 6;
        int actualNum = receiptService.isOddDate("2022-03-21");
        assertEquals(expectedNum, actualNum);
        expectedNum = 0;
        actualNum = receiptService.isOddDate("2022-03-20");
        assertEquals(expectedNum, actualNum);
        
    }
    @Test
    void timeCheckTest(){
        int expectedNum = 10;
        int actualNum = receiptService.timeCheck("14:33");
        assertEquals(expectedNum, actualNum);
        expectedNum = 0;
        actualNum = receiptService.timeCheck("13:33");
        assertEquals(expectedNum, actualNum);
        
    }
    @Test
    void pairCountTest(){
        int expectedCount = 10;
        int actualCount = receiptService.pairCount(items);
        assertEquals(expectedCount, actualCount);
    }
    @Test
    void itemCharCountTest(){
        int expectedCount = 6;
        int actualCount = receiptService.itemCharCount(items);
        assertEquals(expectedCount, actualCount);
    }
    @Test
    void pointTest(){
        when(receiptRepository.findById(anyLong())).thenReturn(Optional.of(receipt));
        int expectedCount = 28;
        int actualCount = receiptService.point(1L);
        verify(receiptRepository, times(1)).findById(anyLong());
        assertEquals(expectedCount, actualCount);
    }
}
