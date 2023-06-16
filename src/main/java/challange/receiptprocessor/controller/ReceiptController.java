package challange.receiptprocessor.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import challange.receiptprocessor.domain.Receipt;
import challange.receiptprocessor.service.ReceiptService;

@RestController
public class ReceiptController {
    
    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/receipts/process")
    public ResponseEntity<Object> save(@RequestBody Receipt receipt){
        Receipt r = receiptService.save(receipt);
        Map<String, Object> result = new HashMap<>();
        if(r == null){
            result.put("Message", "The receipt is invalid");
            return new ResponseEntity<Object>(result, HttpStatus.BAD_REQUEST);
        }
        result.put("id", r.getId().toString());
        return new ResponseEntity<Object>(result, HttpStatus.OK);
        
    }

    @GetMapping("/receipts/{id}/points")
    public ResponseEntity<Object> getPoint(@PathVariable Long id){
        Integer point = receiptService.point(id);
        Map<String, Object> result = new HashMap<>();
        if(point == null){
            result.put("Message", "No receipt found for that id");
            return new ResponseEntity<Object>(result, HttpStatus.NOT_FOUND);
        }
        result.put("point", point.toString());
        return new ResponseEntity<Object>(result, HttpStatus.OK);
    }
}
