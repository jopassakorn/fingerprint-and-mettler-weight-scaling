package svs.client_service.JewClientService.service.impl;

import com.fazecast.jSerialComm.SerialPort;
import org.springframework.stereotype.Service;
import svs.client_service.JewClientService.service.WeightScalingService;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeightScalingServiceImpl implements WeightScalingService {

    public String getWeightFromUsb(String commPort) {
        String weightValue = null;
        SerialPort serialPort = SerialPort.getCommPort(commPort);
        if (serialPort.openPort()) {
            serialPort.setComPortParameters(9600, 8, 1, 0);
            serialPort.setComPortTimeouts(com.fazecast.jSerialComm.SerialPort.TIMEOUT_READ_BLOCKING,250,250);
            if (serialPort.bytesAvailable() != -1) {
                byte[] byteArray = new byte[10];
                byte[] byteArray2 = new byte[10];
                int readByteResult = serialPort.readBytes(byteArray, 10);
                int readByteResult2 = serialPort.readBytes(byteArray2, 10);
                serialPort.closePort();
                if (readByteResult != -1 || readByteResult2 != -1) {
                    if (byteArray[0] == 32 || byteArray[0] == 83) {
                        weightValue = convertByteToString(byteArray, byteArray2);
                    }
                }
            }
        }

        return weightValue;
    }

    public SerialPort[] getCommPortList(){
        return SerialPort.getCommPorts();
    }

    private String convertByteToString(byte[] byteValue1, byte[] byteValue2) {
        StringBuffer weightBuffer = new StringBuffer();

        for (int i = 0; i < byteValue1.length; i++) {
            System.out.println(byteValue1[i]);
            if (byteValue1[i] > 44 && byteValue1[i] < 58) {
                weightBuffer.append((char) byteValue1[i]);
            }
        }

        for (int i = 0; i < byteValue2.length; i++) {
            System.out.println(byteValue2[i]);
            if (byteValue2[i] == 103) {
                break;
            }
            if (byteValue2[i] > 44 && byteValue2[i] < 58) {
                weightBuffer.append((char) byteValue2[i]);
            }
        }

        return weightBuffer.toString();
    }

}
