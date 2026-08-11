Feature: Practica

  #@Scenario1
  #Scenario: Buscar un valor de una java class
    #Given El usuario abre la pagina "urlLiverpool2" en el navegador
   # Given El usuario da clic "txtUsername" en la pagina "orangeLoginPageObjects"
  @dashboard1
  Scenario: Dar clic en Admin
    Given El usuario abre la pagina "urlDavid" en el navegador
    When El usuario ingresa el valor "Admin" en el campo "Username" de la pagina
    When El usuario ingresa el valor "admin123" en el campo "Password" de la pagina
    Then El usuario da clic en el boton login
    Then El usuario da clic en "admin" en el navbar de la pagina Dashboard
    Then El usuario espera 10 segundos
    Then finaliza el test "dashboard1" y se cierra navegador

  @dashboard2
  Scenario: Dar clic en boton que no existe
    Given El usuario abre la pagina "urlDavid" en el navegador
    When El usuario ingresa el valor "Admin" en el campo "Username" de la pagina
    When El usuario ingresa el valor "admin123" en el campo "Password" de la pagina
    Then El usuario da clic en el boton login
    Then El usuario da clic en "btnq123q" en el navbar de la pagina Dashboard
    Then El usuario espera 10 segundos
    Then finaliza el test "dashboard2" y se cierra navegador