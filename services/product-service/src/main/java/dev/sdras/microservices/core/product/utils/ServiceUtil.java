package dev.sdras.microservices.core.product.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Slf4j
@Component
public class ServiceUtil {
    private final String port;
    private String serviceAddress;

    /**
     * Construtor
     * @param port - Porta do serviço extraida a partir do arquivo application.yml
     */
    @Autowired
    public ServiceUtil(@Value("${server.port}") String port) {
        this.port = port;
    }

    public String getServiceAddress() {
        if (serviceAddress == null) {
            String address = String.format("%s/%s:%s", findHostName(), findIpAddress(), port);
            log.debug("Endereço do serviço: {}", address);
            serviceAddress = address;
        }
        return serviceAddress;
    }

    /**
     * Determina o nome do host da máquina
     * @return - String - Nome do host
     */
    private String findHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (java.net.UnknownHostException e) {
            log.error("Falha ao determinar o nome do Host");
            return "Nome de Host desconhecido";
        }
    }

    /**
     * Determina o IP do host da máquina
     * @return String - IP do host
     */
    private String findIpAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (java.net.UnknownHostException e) {
            log.error("Falha ao determinar o IP do Host");
            return "IP desconhecido";
        }
    }
}
