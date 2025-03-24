# Service Discovery

O Service Discovery é um padrão de design utilizado em arquiteturas de microsserviços para permitir que os serviços se encontrem dinamicamente sem depender de configurações fixas de endereço IP ou host. Ele facilita a escalabilidade, a resiliência e a flexibilidade da comunicação entre serviços.

## Como funciona?

Em um sistema distribuído, os microsserviços podem escalar dinamicamente e mudar de localização (endereço IP/porta).

O Service Discovery resolve isso permitindo que os serviços se registrem em um Registry (Registro de Serviços), onde outros serviços podem consultá-los para encontrar a instância correta com base no nome do serviço.

```mermaid
flowchart TD
cli(Client)
cli --> i1 
cli --> i2
cli --> i3
subgraph Microservice Product
    i1(Instance 01)
    i2(Instance 02)
    i3(Instance 03)
end
```

### Existem dois tipos principais de Service Discovery:

#### Client-side (Descoberta no Cliente)

O cliente consulta o Service Registry diretamente para obter o endereço do serviço antes de enviar a requisição.

#### Server-side (Descoberta no Servidor)

O cliente chama um Load Balancer que consulta o Service Registry e redireciona a requisição para uma instância do serviço.

## Spring Boot e Eureka (Netflix Eureka)

O **Eureka Server** é um serviço de descoberta (Service Discovery) da Netflix OSS, amplamente utilizado em arquiteturas de microsserviços com Spring Cloud. 
Ele permite que serviços se registrem dinamicamente e descubram uns aos outros sem necessidade de configuração manual.

### Como Funciona?
#### Registro
Os microsserviços clientes (Eureka Clients) se registram no Eureka Server.

#### Descoberta
Quando um serviço precisa chamar outro, ele consulta o Eureka Server para obter a localização do serviço desejado.

#### Heartbeats
Os clientes enviam sinais periódicos (heartbeats) para indicar que estão ativos.

#### Remoção Automática
Se um cliente parar de responder, o Eureka Server o remove da lista.



