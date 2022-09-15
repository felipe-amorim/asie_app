## NOTAS

Não existe ordem de execução entre o front e o back, porém, o login não será efetuado sem o back-end

Qualquer ação efetuada já autenticado, porém, com o back-end encerrado, resultará no redirecionamento para a tela de login por falta de autenticação

Os usuários mockados são:

user: admin
pass: 123456

user: dev01
pass: 123456

user: user01
pass: 123456

*O usuário dev01 não tem acesso ao request -GET /products, portanto a sua lista de produtos é sempre vazia


## FRONT

Como pré-requisito, o npm precisa estar instalado

Para baixar as libraries, rodar o comando 
```bash
npm install
```

Para rodar a aplicação, rodar o comando
```bash
npm start
```

O projeto funciona baseado no encapsulamento de componentes do react, formando uma SPA, na seguinte ordem:


```
-> App
     -> Login
     -> Header
       -> Main
         -> Planning (lista)
         -> ProductDetails (edit)
         -> Home
         -> CreateNewProduct (create)
-> ToastContainer
```


Sendo o main, o principal foco da transição de conteúdo, fazendo de fato a função de SPA, mudando o corpo da aplicação e mantendo os scripts e css na página

O container App pode também mudar o conteúdo para Header ou Login


A aplicação executa as ações CRUD através de requests do tipo REST e gerencia os níveis de acesso através da presença de authorities dentro do bearer token, podendo interferir na ação executada pelo usuário como unauthorized, ou até mesmo a não visualização de conteúdo, sem a authority correta

A validação de authorities é feita usando os reactStates, componentDidMount e componentWillUnmount, realizando o refresh token a cada clique ou tecla pressionada pelo usuário, que ao apresentar falha na autenticação, o redireciona para a tela de login

## BACK

Como pré-requisito, o projeto precisa ser aberto utilizando o Intellij, Eclipse ou outras ferramentas podem ter erros de import, porém, é possível corrigi-los e rodar o projeto

Após abrir o projeto, o controlador de dependências Maven deverá pedir permissão para baixar as bibliotecas, após finalizado, a aplicação estará pronta para ser executada

Para rodar a aplicação, executar a classe ```StartAsieBackApplication```

A aplicação serve para o gerenciamento do token, acessos, e mock do banco de dados utilizando ```JpaRepository```

Os pacotes dividem as suas funcionalidades em
```
-> auth: Controlador de autenticação do endpoint /auth
-> config: Configuração de authorities e geração de tokens
-> error: Configuração de mensagens de erro genéricas para todos os endpoints
-> product: Controlador do enpoint /product (CRUD)
-> token: Controlador do enpoint /token
```
A aplicação funciona utilizando o framework Spring, principalmente pelas funcionalidades: validação de body/parametros/authenticação/headers das chamdas REST

O token gerado é o bearer token OAuth2.0, porém, mockado em sua assinatura

O mock do banco de dados está sendo feito na classe ```StartAsieBackApplication```, portanto, ao encerrar a execução, o conteúdo do banco é apagado e ao inicia-la, o conteúdo é escrito de acordo com o mock
