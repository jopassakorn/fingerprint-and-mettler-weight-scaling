package svs.client_service.JewClientService.bean;

import lombok.Data;

@Data
public class JewUserBean {

    private Long userId;
    private byte[] fingerTemplate;
}
