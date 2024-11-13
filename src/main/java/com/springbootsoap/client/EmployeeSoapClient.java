package com.springbootsoap.client;

import com.springbootsoap.gen.AddEmployeeRequest;
import com.springbootsoap.gen.AddEmployeeResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class EmployeeSoapClient extends WebServiceGatewaySupport {

    public AddEmployeeResponse addEmployee(AddEmployeeRequest request) {
        try {
            return (AddEmployeeResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(
                            getDefaultUri(),
                            request,
                            new SoapActionCallback("http://com.springbootsoap.allapis/AddEmployeeRequest")
                    );
        } catch (Exception e) {
            throw new RuntimeException("Error al llamar al servicio SOAP", e);
        }
    }
}
