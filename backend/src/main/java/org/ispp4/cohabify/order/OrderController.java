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


@RestController
public class OrderController {

    @Autowired
    private Environment environment;

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
        if (plan == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (plan.equals("Propietario")) {
                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("plan", "owner");
                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            } else if (plan.equals("Explorador")) {
                Map<String, String> responseBody = new HashMap<>();
                responseBody.put("plan", "explorer");
                return new ResponseEntity<>(responseBody, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
    }

    
}
