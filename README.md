# Gerenciamento de clientes

## Tecnologias Utilizadas

- **Java 8**
- **Tomcat 9** (Servidor Web)
- **JSF**
- **Primefaces**
- **Lombok**
- **Eclipse IDE** (Ambiente de Desenvolvimento)

## Pré-requisitos

Antes de rodar o projeto, verifique se você tem os seguintes pré-requisitos instalados:

1. **Java 8** 
2. **Apache Tomcat 9**
3. **Maven**

## Certifique-se também
1. Lombok configurado em sua IDE
2. Backend rodando na porta 8081

### 1. Rodar projeto
Se você ainda não tem o repositório, faça o clone usando o Git:

```bash
https://github.com/GuiAmerico/cadastro-cliente-web.git
2. Configurar o Ambiente no Eclipse
Abra o Eclipse IDE.

Importe o projeto:

Vá em File > Import > Existing Projects into Workspace.

Selecione a pasta do projeto clonado.

Certifique-se de que o JDK 8 está configurado no Eclipse.

Vá em Window > Preferences > Java > Installed JREs.

Verifique se o JDK 8 está selecionado.

3. Configurar o Tomcat no Eclipse
Vá para Window > Preferences > Server > Runtime Environments.

Clique em Add Server e selecione Tomcat 9.

Escolha o diretório onde o Tomcat 9 está instalado.

Clique em Finish para adicionar o Tomcat ao Eclipse.

4. Configurar o Projeto para Usar o Tomcat
Clique com o botão direito no projeto > Properties > Project Facets.

Verifique se o Dynamic Web Module e Java estão habilitados.

Vá em Servers no Eclipse, clique com o botão direito e adicione seu projeto ao Tomcat 9.

5. Executar o Projeto
No Servers view (visualização de servidores), clique com o botão direito no Tomcat 9 e escolha Start.

O Tomcat irá iniciar e seu projeto será executado.

6. Acessando a Aplicação
Abra o navegador e acesse o seguinte endereço:

http://localhost:8080/cadastrocliente
```
### Login
```
{
    "email": "admin@email.com",
    "senha": "Admin@123"
}
```