# Product Service

## Guide

### Criando o Projeto

1. Crie um diretorio microservices;
2. Dentro do diretório `microservices`, crie um projeto Spring Boot com dependencia `Spring Web`, `Spring Actuator` e `Lombok`;
3. Ative o `Annotation Processors` nos pluggins do IntelliJ.

### Camada de Domínio

1. Crie o pacote `domain` dentro da raiz do projeto;
2. Crie a classe `Product` com o código abaixo;

```java
package seupacotedeaplicacao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    private Integer id;
    private String name;
    private Integer weight;
    private String serviceAddress;
}

```

#### ServiceUtil

Iremos criar um utiliario para exibir as informaçoes dos hosts envolvidos as requisicoes entre microservices, este utilitario posteriormente sera refatorado para utilizacao nos demais microservices.

Para criar o utilitário, siga as instruçoes abaixo.

1. Para a propriedade `servideAddress` da classe de dominio `Product` será preenchida por este utilitario. Crie o pacote `utils`;
2. Crie a classe `ServiceUtil.java` no pacote `utils`;

```java
package seupacotdeaplicacao.utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

@Component
@Slf4j
public class ServiceUtil {
    private final String port;
    private final String serviceAddress;

    /**
     * Construtor
     * @param port - Porta do serviço extraida a partir do arquivo application.yml
     */
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
```

### Camada de Aplicacao

A camda de aplicacao será composta por 2 classes inicialmente, um service  e um resource.

1. Crie a classe `ProductResource` no pacote `application`, o applicatio deve estar na raiz do projeto. 
2. Inclua o codigo abaixo na classe:

```java
package seupacotedeaplicacao.application;

import dev.sdras.microservices.core.product.domain.Product;
import dev.sdras.microservices.core.product.utils.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductResource {

    private final ServiceUtil serviceUtil;

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Integer id) {
        return Product.builder()
                .id(id)
                .name("iPad Pro M4")
                .weight(500)
                .serviceAddress(serviceUtil.getServiceAddress())
                .build();
    }
}
```

3. Renomeie o aqruivo `application.properties` para `application.yml`.
4. Inclua o nome do serviço e a porta do servidor no arquivo `application.yml`.

```yaml
server:
  port: 9000

spring:
  application:
    name: product-service
```

## Testando a aplicação

1. Execute a aplicação;
2. No navegador, entre com a url `localhost:9000`, o resultado deve ser um white label.
3. No navegador, entre com a url `localhost:9000/product/1`, o resultado deve ser um json conforme o exemplo abaixo.

```json
{"id":1,"name":"iPad Pro M4","weight":500,"serviceAddress":"SeuHost/127.0.0.1:9000"}
```

# Concluímos o MVP de nosso primeiro MicroService

## Service Discovery

Acesse as instruções para criar o [Service Discovery](../docs/service-discovery.md).
