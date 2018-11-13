package svs.client_service.JewClientService.endpoint;

import ch.qos.logback.core.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import svs.client_service.JewClientService.bean.JewFingerBean;
import svs.client_service.JewClientService.bean.JewUserBean;
import svs.client_service.JewClientService.service.JewFingerprintService;

import javax.ws.rs.core.Response;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/jewfingerprintapi")
public class JewFingerprintEndpoint {

    @Autowired
    private JewFingerprintService jewFingerprintService;

    @PostMapping(value = "/getMatchFingerprint")
    public Response getMatchFingerprint(@RequestBody List<JewUserBean> userBeanList) throws InterruptedException {

        if(!jewFingerprintService.isFingerprintDriverInstalled()){
            return Response.status(Status.ERROR).entity("driver not found").build();
        }
        if(!jewFingerprintService.isFingerprintSensorConnected()){
            jewFingerprintService.terminateFingerprint();
            return Response.status(Status.ERROR).entity("fingerprint sensor is not connected").build();
        }
        if(!jewFingerprintService.isOneFingerprintConnect()){
            jewFingerprintService.terminateFingerprint();
            return Response.status(Status.ERROR).entity("device is connected more than one").build();
        }
        if(!jewFingerprintService.connectFingerprint()){
            jewFingerprintService.terminateFingerprint();
            return Response.status(Status.ERROR).entity("connecting device error").build();
        }

        JewFingerBean jewFingerBean = jewFingerprintService.getFingerPrint();
        jewFingerprintService.terminateFingerprint();
        if(jewFingerBean == null){
            return Response.status(Status.ERROR).entity("time out").build();
        }else{
            Long matchFingerprintUesrId = this.jewFingerprintService.getMatchFingerprintUserId(jewFingerBean, userBeanList);
            if(matchFingerprintUesrId != 0l){
                jewFingerBean.setMatchFingerprintUserId(matchFingerprintUesrId);
            }else{
                return Response.status(204).entity("not found").build();
            }
        }

        return Response.ok(jewFingerBean).build();
    }

    @GetMapping("/getFingerTemplate")
    public Response getFingerTemplate() throws InterruptedException {
        if(!jewFingerprintService.isFingerprintDriverInstalled()){
            return Response.status(Status.ERROR).entity("driver not found").build();
        }
        if(!jewFingerprintService.isFingerprintSensorConnected()){
            jewFingerprintService.terminateFingerprint();
            return Response.status(Status.ERROR).entity("fingerprint sensor is not connected").build();
        }
        if(!jewFingerprintService.isOneFingerprintConnect()){
            jewFingerprintService.terminateFingerprint();
            return Response.status(Status.ERROR).entity("device is connected more than one").build();
        }
        if(!jewFingerprintService.connectFingerprint()){
            jewFingerprintService.terminateFingerprint();
            return Response.status(Status.ERROR).entity("connecting device error").build();
        }

        JewFingerBean jewFingerBean = jewFingerprintService.getFingerPrint();
        jewFingerprintService.terminateFingerprint();
        if(jewFingerBean == null){
            return Response.status(Status.ERROR).entity("time out").build();
        }

        return Response.ok(jewFingerBean).build();
    }
}
