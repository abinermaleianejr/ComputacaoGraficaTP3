# ComputacaoGraficaTP3

Aplicacao Java/JOGL que apresenta um relogio analogico 2D e uma cena animada
com dois cubos 3D, textura de rede, iluminacao e diferentes vistas de camera.

## Executar

Instale um JDK 17 ou superior e o Gradle. Na raiz do projeto:

```shell
gradle run
```

O Gradle descarrega automaticamente JOGL e GlueGen do Maven Central. A
textura de rede e criada pela aplicacao, pelo que nao e necessario um ficheiro
de imagem externo.

## Controlos

| Tecla | Acao |
|---|---|
| Espaco | Fechar a aplicacao |
| H / h | Aumentar / diminuir o ponteiro das horas |
| M / m | Aumentar / diminuir o ponteiro dos minutos |
| S / s | Aumentar / diminuir o ponteiro dos segundos |
| I | Alternar o sentido dos ponteiros |
| T | Alternar o tema claro/escuro |
| 1-6 | Alternar a visibilidade das faces |
| Ctrl+1 / Ctrl+2 / Ctrl+3 | Selecionar camera isometrica / primeira pessoa / ortografica |
| Setas | Mover a luz no plano XY |
| Page Up / Page Down | Aproximar / afastar a luz |
| Arrastar com o rato | Rodar a camera |
| Roda do rato | Aproximar / afastar a camera |
