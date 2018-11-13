package svs.client_service.JewClientService.service;

import com.fazecast.jSerialComm.SerialPort;

public interface WeightScalingService {

    public String getWeightFromUsb(String commPort);
    public SerialPort[] getCommPortList();
}
