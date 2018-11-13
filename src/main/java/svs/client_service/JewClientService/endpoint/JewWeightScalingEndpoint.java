package svs.client_service.JewClientService.endpoint;

import ch.qos.logback.core.status.Status;
import com.fazecast.jSerialComm.SerialPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import svs.client_service.JewClientService.service.WeightScalingService;

import javax.ws.rs.core.Response;

@CrossOrigin
@RestController
@RequestMapping("/jewweightscalingapi")
public class JewWeightScalingEndpoint {

    @Autowired
    private WeightScalingService weightScalingService;

    @GetMapping("/getWeight")
    public Response getweight(){
        Response response;
        String weight = null;
        SerialPort[] portList = weightScalingService.getCommPortList();
        for(SerialPort port : portList){
            System.out.println("try to get weight from + " + port.getSystemPortName());
            weight = weightScalingService.getWeightFromUsb(port.getSystemPortName());
            if(weight != null){
                break;
            }
        }
        if(weight != null){
            System.out.println(weight);
            response = Response.ok(weight).build();
        }else{
            response = Response.status(408).build();
        }
        return response;
    }

}
