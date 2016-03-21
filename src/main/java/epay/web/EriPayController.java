package epay.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.base.rest.PayPalRESTException;

import epay.domain.Item;
import epay.domain.Purchase;
import epay.service.EncryptionService;
import epay.service.ItemService;
import epay.service.PriceService;

@RestController
public class EriPayController {

  @Autowired
  private EncryptionService encryptionService;

  @Autowired
  private ItemService itemService;

  @Autowired
  private PriceService priceService;

  @CrossOrigin(origins = "http://localhost:3000")
  @RequestMapping(value = "/items", method = RequestMethod.GET)
  public List<Item> getItems() {
    return itemService.getItems();
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @RequestMapping(value = "/price", method = RequestMethod.POST)
  public String postPurchase(@RequestBody final Purchase purchase) throws PayPalRESTException {
    return priceService.postPur(purchase);
  }

  // encrypt a string
  @RequestMapping("/encryption")
  public String encrypt(
        @RequestHeader(value = "text", required = true) String textToEncrypt,
        @RequestHeader(value = "secretKey", required = true) String secretKey) {
    return encryptionService.getEncryptedWithKey(textToEncrypt, secretKey);
  }

  // decrypt a string
  @RequestMapping(value = "/decryption", method = RequestMethod.GET)
  public String decrpty(
        @RequestHeader(value = "text", required = true) String textToDecrypt,
        @RequestHeader(value = "secretKey", required = true) String secretKey) {
    return encryptionService.getDecryptedWithKey(textToDecrypt, secretKey);
  }
}
