Feature: Busqueda de productos en Liverpool

  @Scenario1
  Scenario: Buscar un producto desde la home
    Given El usuario abre la pagina "urlLiverpool2" en el navegador
    #Then El usuario espera 10 segundos
    When navego al menu de categorias
    When selecciono la categoria Mujer
    Then finaliza el test "Scenario1" y se cierra navegador

    #When selecciono la categoria Chamarras

  @Scenario2
  Scenario: Ingresar a la pagina de liverpool
    Given El usuario abre la pagina "urlLiverpool2" en el navegador
    When navego al menu de categorias
    Then finaliza el test "Scenario2" y se cierra navegador

  @PasoGlobalLogin
  Scenario: Orange Login
    Given El usuario abre la pagina "urlDavid" en el navegador
    #Then El usuario espera 10 segundos
    When El usuario ingresa el valor "Admin" en el campo "Username" de la pagina
    When El usuario ingresa el valor "admin123" en el campo "Password" de la pagina
    Then finaliza el test "PasoGlobalLogin" y se cierra navegador