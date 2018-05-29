# Chat Java RMI

Projeto realizado para demonstrar funcionamento do RMI (Remote Method Invocation)

- Chat em grupo;
- Chat privado;
- Envio/Abertura de arquivos;

#### Instruções para compilação e funcionamento do projeto
1. Clonar arquivo ou realizar download do .zip;
2. Abrir diretório rmi-project/src;
3. Verificar ip da máquina através do cmd, utilizando o código: `ipconfig`;
4. Alterar o ip na classe `StartClient.java`;
5. Através do cmd ou git bash, compilar classes java utilizando o comando: `javac *.java`;
6. Inicializar servidor: `java StartServer`;
7. Inicializar cliente: `java StartClient`.

#### Instruções de uso
```
||---------------------------------------||
||/menu         -> Mostra menu           ||
||/chatPublico  -> Inicia o chat publico ||
||/chatPrivado  -> Inicia o chat privado ||
||/enviaArquivo -> Enviar arquivo        ||
||/abreArquivo  -> Abre arquivo          ||
||/sair         -> Sai do programa       ||
||---------------------------------------||
```
