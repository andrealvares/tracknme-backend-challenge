![TrackNMe](https://www.tracknme.com.br/app/images/logo-tracknme.png)


# Desafio Backend

O candidato deve dar **fork** neste repositório e após o termino do desenvolvimento, realizar um **pull request** e avisar por **email** para análise do time.

O desafio consiste em criar um micro-serviço com as seguintes características:

# Task 1 - Criar um endpoint que implemente as operações CRUD [Obrigatório]

Criar um endpoint REST que implemente as operações CRUD da entidade "Location".

O endpoint deve expor as seguintes operações no seguinte formato REST:

1) GET /locations
(Recupera as entidades persistidas.)

2) GET /locations/{ID}
(Recupera apenas uma entidade persistida pelo ID.)

3) POST /locations
(Cria ou atualiza uma entidade recebida em formato JSON no corpo da requisição. Retorna a entidade criada.)

4) PATCH /locations/{ID}
(Atualiza apenas os atributos de uma entidade informados no corpo da requisição. Retorna a entidade atualizada.)

5) DELETE /locations/{ID}
(Remove e retorna uma entidade por ID.)

PS.: A entidade "Location" deve ser recebida e recuperada através do endpoint em formato JSON.

Exemplo:
``` json
{
    "id": 123456,
    "trackerId": 654321,
    "dateTime": "2017-10-12T21:34:15",
    "latitude": -23.9626767,
    "longitude": -46.3884785
}
```


# Task 2 - Regras de negócio [Recomendado]

É importante que o micro-serviço seja bastante flexivel para adicionar novas regras de negócio.

Sugerimos que sejam implementadas as seguintes regras:
1) Não permitir que uma nova posição (Location) seja criada, se a distancia entre essa posição e a última for menor que 10 metros.
2) Após a persistencia de uma nova posição (Location), replicar assincronamente os valores dos atributos latitude e longitude para uma entidade Tracker sugerida abaixo:

``` json
{
    "id": 654321,
    "name": "Meu rastreador",
    "latitude": -23.9626767,
    "longitude": -46.3884785
}
```


# Task 3 - Arquitetura interna, testes autimatizados e clean Code [Recomendado]

Sugerimos que o projeto seja separado em camadas. O acoplamento entre as camadas deve ser baixo. Uma sugestão é utilizar injeção de dependencias para manter o acoplamento baixo.

É recomendado que o desenvolvedor utilize de ferramentas de automação de testes para garantir a qualidade do micro-serviço.

Será considerado um diferencial se o desenvolvedor aplicar as boas práticas de clean code.


# Task 4 - Deploy em ambiente cloud [Opcional]

Sugerimos que o desenvolvedor faça o deploy da aplicação em uma máquina hospedada em um ambiente cloud. Sugerimos o Google Cloud. O endereço para acessar o serviço publicado deve ser informado para que possamos fazer testes.



---
#### LICENSE
```
MIT License

Copyright (c) 2017 TrackNMe

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
