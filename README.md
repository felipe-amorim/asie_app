Como pré-requisito, o npm precisa estar instalado

Para baixar as libraries, rodar o comando npm install

Para rodar o projeto, rodar o comando npm start

Não existe ordem de execução entre o front e o back, porém, o login não será efetuado sem o back-end
Qualquer ação efetuada já autenticado, porém, com o back-end encerrado, resultará no redirecionamento para a tela de login por falta de autenticação

O projeto funciona baseado no encapsulamento de componentes do react, formando uma SPA, na seguinte ordem:

-> App
     -> Login
     -> Header
       -> Main
         -> Planning (lista)
         -> ProductDetails (edit)
         -> Home
         -> CreateNewProduct (create)
-> ToastContainer

Sendo o main, o principal foco da transição de conteúdo, fazendo de fato a função de SPA, mudando o corpo da aplicação e mantendo os scripts e css na página

O container App pode também mudar o conteúdo para Header ou Login


A aplicação executa as ações CRUD através de requests do tipo REST e gerencia os níveis de acesso através da presença de authorities dentro do bearer token, podendo interferir na ação executada pelo usuário como unauthorized, ou até mesmo a não visualização de conteúdo, sem a authority correta

A validação de authorities é feita usando os reactStates, componentDidMount e componentWillUnmount, realizando o refresh token a cada clique ou tecla pressionada pelo usuário, que ao apresentar falha na autenticação, o redireciona para a tela de login
