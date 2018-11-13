package svs.client_service.JewClientService.bean;

import lombok.Data;

@Data
public class JewFingerBean {

    private byte[] fingerprintTemplate;
    private byte[] fingerprintImage;
    private Long matchFingerprintUserId;
}
