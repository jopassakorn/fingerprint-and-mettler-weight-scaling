package svs.client_service.JewClientService.service;

import svs.client_service.JewClientService.bean.JewFingerBean;
import svs.client_service.JewClientService.bean.JewUserBean;

import java.util.List;

public interface JewFingerprintService {

    public boolean isFingerprintDriverInstalled();
    public boolean isOneFingerprintConnect();
    public boolean connectFingerprint();
    //return null if initial fail or time out
    public JewFingerBean getFingerPrint() throws InterruptedException;
    public Long getMatchFingerprintUserId(JewFingerBean fingerBean, List<JewUserBean> userBeanList);
    public void terminateFingerprint();
    public boolean isFingerprintSensorConnected();
}
