<h1>CommentApp</h1>

Projeto de comentários com gerenciamento de usuários.

<h3>Iniciando</h3>

Essas instruções o auxiliarão em como ter uma cópia deste projeto em sua máquina local para fins de teste e execução. A seguir, todas as instruções sobre como fazer o deploy deste projeto.

<h3>Pré-requisitos</h3>

<pre>
•	Eclipse Kepler ou superior<br>
•	PostgreSQL 9.3 ou superior<br>
•	Apache Tomcat 8 ou superior<br>
•	Node JS 8.11.2<br>
•	Git 2.17
</pre>

<h3>Instalando</h3>

Baixe o Eclipse Kepler ou superior no link a seguir:

https://www.eclipse.org/downloads/

O Eclipse não requer instalação, basta executar o arquivo eclipse.exe.
Ao abrir o Eclipse, clique com o botão direito do mouse em qualquer espaço em branco na área do Project Explorer e selecione a opção import>import.
 
Use a pesquisa para encontrar a opção “Projects from Git”. Selecione a opção, clique em next. Selecione a opção Clone URI e insira a seguinte URI:

https://github.com/renatodanielss/CommentApp.git

Feito isso, pressione next, next novamente, selecione a pasta a qual quer salvar o projeto, pressione next de novo, aguarde o Eclipse carregar o projeto, pressione next e clique em finish. Com isso, o projeto já está no nosso workspace do Eclipse.

Agora, configuraremos o Apache Tomcat. Faça o download no seguinte link:

http://ftp.unicamp.br/pub/apache/tomcat/tomcat-8/v8.0.52/bin/apache-tomcat-8.0.52-windows-x64.zip

Descompacte numa pasta de sua preferência.

De volta ao Eclipse, clique no menu Window>Preferences. Expanda a opção Server e clique em Runtime Environment. Clique no botão add. Selecione o Apache Tomcat 8.0, clique em next, clique em browse para selecionar a pasta do Tomcat. Vá até a pasta onde você descompactou o Tomcat, selecione ela, pressione ok, depois pressione finish e depois pressione ok.

Clique na aba Server, clique no link de azul “No servers are available. Click this link to create a new server...”.

Selecione a opção do Tomcat 8.0, clique em next, selecione o projeto CommentApp, clique em add e depois finish. O servidor vai aparecer.

Vamos instalar o PostgreSQL. Baixe-o no seguinte link:

https://www.enterprisedb.com/downloads/postgres-postgresql-downloads

Selecione o PostgreSQL 9.3 ou superior, selecione a versão do seu sistema operacional e faça o download.

Execute o arquivo, clique next três vezes, defina uma senha para o super usuário do PostgreSQL, pressione next até começar a instalação. Ao finalizar a instalação, desmarque a opção do Stack Builder e clique em finish.

Abra o pgAdmin III. Dê dois cliques no servidor que já vem instalado no PostgreSQL.
 
Insira a senha definida na instalação.
Expanda a opção Databases, selecione o banco de dados postgres e clique no botão “SQL” para abrirmos o editor de queries do PostgreSQL.
 
Insira o seguinte comando:

<pre>create database db_onepageenterprises;</pre>

Clique na combo em que está descrita a conexão e selecione a opção “\<new connection\>”.
 
Na opção Database, selecione o nosso recém criado banco de dados db_onepageenterprises como abaixo e clique em ok e depois insira sua senha.
 
Clique em file>open e abra o arquivo queries.sql que se encontra na pasta raiz do nosso projeto Java. Pressione F5 para executar a query. Com isso, criamos toda a estrutura do nosso banco de dados.

Instalaremos o NodeJS. Baixe o programa no link a seguir:

https://nodejs.org/dist/v8.11.2/node-v8.11.2-x64.msi

Execute o programa. Basta seguir o processo de instalação pressionando next e aceitando os termos do programa, pois essa instalação não exige nenhuma configuração. Feito é só aguardar o fim da instalação.

Agora, usaremos o Git para podermos fazer o checkout do nosso projeto do Nodejs. Baixe o Git Portable no link abaixo:

https://github.com/git-for-windows/git/releases/download/v2.17.0.windows.1/PortableGit-2.17.0-64-bit.7z.exe

Execute o arquivo do git e selecione uma pasta para fazer a descompactação da pasta do programa.

Abra o programa, execute o arquivo git-cmd.exe.

Execute o seguinte comando para fazer checkout do nosso projeto Node JS:

<pre>git clone https://github.com/renatodanielss/CommentService.git</pre>

Ao concluir o checkout, já poderemos começar a rodar os testes.

<h3>Rodando os testes</h3>

Abra a pasta do nosso serviço do Node Js chamado CommentService, o qual acabamos de fazer checkout, segure ctrl e pressione com o botão direito do mouse sobre qualquer espaço em branco da pasta e selecione a opção “Abrir janela de comando aqui”.

Execute o seguinte comando:

<pre>npm start</pre>

Com isso, nosso serviço de comentários já está disponível e rodando.

Agora, abra o Eclipse novamente e vá até a aba Server. Clique no servidor do Tomcat com o botão direito do Mouse e selecione start.

Agora, abra um navegador da web qualquer e insira a seguinte url:

http://localhost:8080

Clique no botão "Área de Comentários", insira o usuário e senha ou cadastre um novo usuário.

<h3>Autor</h3>

•	Renato Daniel Santana Santos
