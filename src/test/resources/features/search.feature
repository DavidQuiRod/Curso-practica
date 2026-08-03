Feature: Busqueda de productos en Liverpool

  Scenario: Buscar un producto desde la home
    #Given que abro la pagina de Liverpool
    Given El usuario abre la pagina "urlLiverpool2" en el navegador
    When navego al menu de categorias
    When selecciono la categoria Mujer
    Then Finaliza el test y se cierra navegador

    #When selecciono la categoria Chamarras

  Scenario: Primer script con funcionalidades
    Given que abro la pagina de Liverpool
    When Ingreso texto al text input
    When Ingreso texto en el campo password
