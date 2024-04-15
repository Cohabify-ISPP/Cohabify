package org.ispp4.cohabify.order;

import java.util.Map;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.LineItemCollection;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionListLineItemsParams;
import org.springframework.http.HttpStatus;
import org.ispp4.cohabify.utils.Global;
import org.ispp4.cohabify.user.Plan;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserService;
import lombok.AllArgsConstructor;
@RestController
@AllArgsConstructor
public class OrderController {

    @Autowired
    private Environment environment;

    private UserService userService;
    private Global global;


    @PostMapping("/api/stripe/session")
    public ResponseEntity<Map<String, String>> handleStripeSession(@RequestBody Map<String,String> requestBody) throws StripeException {
        String sessionId = requestBody.get("sessionId");
        // Configura tu clave secreta de Stripe

        
        String secretKey = environment.getProperty("stripe.secret.key"); 

        Stripe.apiKey = secretKey;
    
        // Utiliza el sessionId para obtener los detalles del pago desde Stripe
        Session session = Session.retrieve(sessionId);
        
        SessionListLineItemsParams params = SessionListLineItemsParams.builder().build();
        LineItemCollection lineItems = session.listLineItems(params);
        String plan = lineItems.getData().get(0).getDescription();
        //Si plan es null o el pago no se ha completado, devuelve un error 404
        
        if (plan == null || session.getPaymentStatus().equals("unpaid")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (plan.equals("Propietario")) {
                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("plan", "owner");
                User user = global.getCurrentUser();
                user.setPlan(Plan.OWNER);
                user.setIsOwner(true);
                userService.save(user);
                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            } else if (plan.equals("Explorador")) {
                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("plan", "explorer");
                User user = global.getCurrentUser();
                user.setPlan(Plan.EXPLORER);
                userService.save(user);
                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            }else if(plan.equals("Promocionar")){
                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("plan", "promocionar");
                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            }else if (plan.equals("Anuncio")){
                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("plan", "anuncio");
                User user = global.getCurrentUser();
                user.setNumAdvertisements(user.getNumAdvertisements() + 1);
                userService.save(user);
                System.out.println("Anuncio" + user.getNumAdvertisements());
                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }

    
}
