package utils;

import org.apache.commons.io.FileUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.Comparator;
import java.util.Properties;

public class utilities {
    Properties props = new Properties(); //properties para leer un documento
    WebDriver driver; //Inicializar driver
    String directorio = System.getProperty("user.dir");
    private static int screenshotCounter = 1;
    //Variables globales para tomar screenshot y crear documento
    String rutaDeEvidencias = directorio + "\\src\\test\\java\\config\\configuration_evidencias.properties"; //Ruta donde se modifica el nombre de reporte de pruebas y la carpeta donde se va a crear

    private static final String URL = "https://www.liverpool.com.mx/"; //URL de liverpool

    public utilities(WebDriver driver) {
        this.driver = driver;
    }

    public void takeScrenShot(String nombreArchivo) {
        String renombradoDeimagen = nombreArchivo.replace(" ", "_"); //Variable para renombrar imagenes
        try {
            props.load(Files.newInputStream(new File(rutaDeEvidencias).toPath())); //Vamos a leer el archivo que declaramos en la ruta de evidencias
            String carpetaDeImagenes = String.valueOf(props.get("carpetaDestino")); //Obtenemos el valor de la variable carpetaDestino y lo almacenamos
            String rutaFinDeImagenes = directorio + "\\evidencias\\" + carpetaDeImagenes;//Obtenemos el valor de la variable carpetaDestino y lo almacenamos
            String indice = String.format("%03d", screenshotCounter++); // Formatea el contador para mantener el orden alfabético (001, 002, etc.)
            //Crear carpeta destino
            File carpetaDondeSeGuardaEvidencias = new File(rutaFinDeImagenes);
            //Si la carpeta no existe creala
            if (!carpetaDondeSeGuardaEvidencias.exists()) {
                //hay que crearla
                boolean crearCarpeta = carpetaDondeSeGuardaEvidencias.mkdir();
                System.out.println("Se creo la carpeta destino " + carpetaDeImagenes + " En la ruta: " + rutaFinDeImagenes);
            }
            // Si ya existe la carpeta hay que ignorar el codigo
            File captura = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String capturaImg = rutaFinDeImagenes + "\\" + indice+ renombradoDeimagen + ".png";
            File destino = new File(capturaImg);
            //Files.setAttribute(destino,"basic:lastModifiedTime",FileTime.from(Instant.now()));
            //Si existe la imagen hay que borrarla
            if (destino.exists()) {  //Si esxiste la imagen
                boolean borrarImagenAnterior = Files.deleteIfExists(Path.of(capturaImg)); //La vamos a eliminar
                if (borrarImagenAnterior) { //Se manda a llamar el booleano
                    System.out.println("Se elimino una imagen anterior con el nombre: " + capturaImg + " con exito"); //Mandamos el mensaje de que se elimino la captura
                }
            }
            //En caso de que no exista la imagen se va a copiar a la ruta
            FileUtils.copyFile(captura, new File(capturaImg));
            //Files.copy(captura.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);  // Esta sentencia funciona para copiar las iamgenes pero no las actualiza en tiempo se deja comentada para futura revision
            System.out.println("Se creo una nueva imagen con el nombre de " + capturaImg + " y se guardo en la ruta " + destino.getAbsolutePath());

        } catch (IOException e) {
            System.out.println("No pudo tomar captura de pantalla");
            System.out.println("Error al guardar la captura: " + e.getMessage());
            finPrueba();
        }
    }

    public void hightLight(WebElement ele) {
        String script = "arguments[0].style.border";
        String border = "3px solid blue";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script + " = '" + border + "'", ele);
    }

    public void unHightLight(WebElement ele) {
        String script = "arguments[0].style.border";
        String border = "";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(script + "='" + border + "'", ele);
    }

    public void elemtIsVisible(WebElement ele) {
        //Falta completar bien este paso
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOf(ele));
        } catch (TimeoutException | StaleElementReferenceException e) {
            System.out.println("El elemento " + ele + " no esta visible");
            finPrueba();
            throw e;
        }

    }

    public void crearReporteWord(String etiquetaIdentificadoraDeCaso) throws Exception {
        try {
            props.load(Files.newInputStream(new File(rutaDeEvidencias).toPath()));  //vamos a leer el archivo que mandamos
            String nombreDeCarpeta = String.valueOf(props.get("carpetaDestino"));
            String nombreDeReporte = String.valueOf(props.get("nombreDeReporteDestino"));
            //Copiar imagenes a nueva carpeta
            copiarImagenesYRenombrarlas(etiquetaIdentificadoraDeCaso);
            String rutaDondeExtraeremosLasImagenes = directorio + "\\evidencias\\" + nombreDeCarpeta + etiquetaIdentificadoraDeCaso;
            String archivoSalidaDeReporte = rutaDondeExtraeremosLasImagenes + "\\" + nombreDeReporte + etiquetaIdentificadoraDeCaso + ".docx";
            System.out.println("Valor de la variable rutaDondeExtraeremosLasImagenes: " + rutaDondeExtraeremosLasImagenes);
            System.out.println("Valor de la variable rutaDondeExtraeremosLasImagenes: " + archivoSalidaDeReporte);
            Path rutaDeArchivoEnCasoDeExistir = Paths.get(archivoSalidaDeReporte);
            //Si existe el documento word se procedera a borrar la informacion de ese documento
            if (Files.exists(rutaDeArchivoEnCasoDeExistir)) {
                eliminarInformacionDeDocumentoWord(rutaDeArchivoEnCasoDeExistir);
            }
            //LLenar de informacion el archivo word
            vaciarInformacionEnDocumentoWord(rutaDondeExtraeremosLasImagenes, rutaDeArchivoEnCasoDeExistir);
        } catch (IOException e) {
            System.err.println("Ocurrió un error al querer crear el reporte " + e.getMessage());
        }
    }

    private static boolean esImagen(String nombre) {
        String n = nombre.toLowerCase();
        return n.endsWith(".png") || n.endsWith(".jpg") || n.endsWith(".jpeg");
    }

    public void finPrueba() {
        driver.quit();
    }

    public void eliminarInformacionDeDocumentoWord(Path rutaDeArchivoALimpiar) {
        try (FileInputStream fis = new FileInputStream(rutaDeArchivoALimpiar.toFile());
             XWPFDocument documento = new XWPFDocument(fis)) {
            // 1. Eliminar todo el contenido actual del documento en bucle inverso
            int totalElementos = documento.getBodyElements().size();
            for (int i = totalElementos - 1; i >= 0; i--) {
                documento.removeBodyElement(i);
            }
            // 2. Guardar los cambios en el mismo archivo
            try (FileOutputStream fos = new FileOutputStream(rutaDeArchivoALimpiar.toFile())) {
                documento.write(fos);
            }
            System.out.println("El archivo se vació y esta listo para utilizarse con éxito.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al manipular el archivo: " + e.getMessage());
        }
    }

    public void vaciarInformacionEnDocumentoWord(String rutaDeDondeExtrarLasImagenes, Path rutaDeArchivoAllenar) {
        /**Extraccion de los nombres de la imagenes**/
        try (XWPFDocument doc = new XWPFDocument()) {
            File folderDeDondeSeExtraranLasImagenes = new File(rutaDeDondeExtrarLasImagenes);
            File[] listOfFiles = folderDeDondeSeExtraranLasImagenes.listFiles();
            System.out.println("Valor total de lista: " + listOfFiles.length);
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile() && esImagen(file.getName())) {
                        //Agregar titulo con el nombre de la imagen
                        XWPFParagraph p = doc.createParagraph();
                        XWPFRun run = p.createRun();
                        String nombreDeImagen = file.getName();  //nombre imagen -> nombre_imagen.png
                        String nombreDePasoParaReporte = nombreDeImagen.replace("_", " "); //nombre_imagen -> nombre imagen.png
                        run.setText(nombreDePasoParaReporte.substring(3, nombreDePasoParaReporte.length() - 4));//nombre imagen
                        run.addBreak();

                        //Insertat la imagen en el documento word
                        try (InputStream is = new FileInputStream(file)) {
                            run.addPicture(
                                    is,
                                    XWPFDocument.PICTURE_TYPE_PNG,
                                    file.getName(),
                                    Units.toEMU(300),
                                    Units.toEMU(200)
                            );
                        }
                        doc.createParagraph();
                    }
                }
            }
            //Guardar el archivo Word generado
            try (FileOutputStream fos = new FileOutputStream(rutaDeArchivoAllenar.toFile())) {
                doc.write(fos);
            }
            System.out.println("Reporte creado con éxito");
        } catch (Exception e) {
            System.out.println("No se logro crear el reporte de pruebas");
            finPrueba();
            e.printStackTrace();
        }
    }

    public void copiarImagenesYRenombrarlas(String etiquetaConLaQueSeRenombrara) throws Exception {
        try {
            props.load(Files.newInputStream(new File(rutaDeEvidencias).toPath()));
            String nombreDeCarpeta = String.valueOf(props.get("carpetaDestino"));
            String rutaDeDondeVoyAcompiarLasImagenes = directorio + "\\evidencias\\" + nombreDeCarpeta;
            String rutaDondeVoyADepositarLasImagenesRenombradas = directorio + "\\evidencias\\" + nombreDeCarpeta + etiquetaConLaQueSeRenombrara;
            File folderDeDondeSeExtraranLasImagenes = new File(rutaDeDondeVoyAcompiarLasImagenes); //Creamos una lista de imagenes
            File[] listOfFiles = folderDeDondeSeExtraranLasImagenes.listFiles(); //Declaramos un array para recorrer la carpeta donde obtendremos nuetras imagenes
            if (listOfFiles != null) { // si la lista de archivos es diferente de nulo prosegimos
                for (File file : listOfFiles) { //Generamos un ciclo for para itirar entre el total de imagenes que hay
                    if (file.isFile() && esImagen(file.getName())) { // validamos si la varible file es un archivo y ademas si es imagen
                        Path origen = Paths.get(file.toURI()); //Declaramos la varible origen de tipo path para obtener la ruta donde vamos a copiar la imagen
                        String nombreDeLaImagen = file.getName(); //Obtenemos el titulo de la imagen
                        System.out.println("Nombre de imagen " + nombreDeLaImagen);
                        try {
                            //Se declara una varibale de tipo file para poder crear una carpeta nueva y poder almacenar las imagenes renombradas
                            File carpetaDondeSeGuardanLasImagenesRenombradas = new File(rutaDondeVoyADepositarLasImagenesRenombradas);
                            //Verificamos si la carpeta no existe
                            if (!carpetaDondeSeGuardanLasImagenesRenombradas.exists()) {
                                //hay que crearla
                                boolean crearCarpeta = carpetaDondeSeGuardanLasImagenesRenombradas.mkdir();
                                System.out.println("Se creo la nueva carpeta destino " + carpetaDondeSeGuardanLasImagenesRenombradas);
                            }
                            System.out.println("Se obtiene el nombre de la imagen  " + nombreDeLaImagen); //Impresion para ver el nombre de la imagen
                            String rutaDestinoConcatenada = rutaDondeVoyADepositarLasImagenesRenombradas + "\\" + nombreDeLaImagen; //Se crea la variable para concatenar toda la ruta destino
                            Path destino = Paths.get(rutaDestinoConcatenada); //Se declara la variable destino para que obtenta la ruta del directorio
                            System.out.println("El valor de la variable destino " + destino); //Se imprime
                            //Si existe la imagen hay que borrarla
                            if (Files.deleteIfExists(destino)) {
                                System.out.println("Se elimino una imagen anterior con el nombre: " + destino.toUri() + " con exito"); //Mandamos el mensaje de que se elimino la captura
                            }// en caso de que no se prosigue
                            FileUtils.copyFile(origen.toFile(), new File(destino.toUri()));
                            System.out.println("Se cambio la imagen a la nueva ruta " + destino);
                        } catch (IOException e) {
                            System.out.println("Error al copiar: " + e.getMessage());
                        }
                    }
                }
            }
            /*
        Se crea una copia de las imagenes y se guardan en una nueva carpeta que incluya la etiqueta con la le incluyas en tu sentencia gherkin
        Esto para poder tener una carpeta donde se almacene las imagenes y el reporte final en cada ejecucion de sin que se agregen imagenes de otro
        escenario deprueba
        */
            //Eliminamos la carpeta origen de donde extraimos las imagenes
            eliminarCarpetaOrigen(rutaDeDondeVoyAcompiarLasImagenes);
        } catch (Exception e) {

        }
    }

    public void eliminarCarpetaOrigen(String rutaDeCarpetaDestino) {
        Path rutaDeCarpetaAeliminar = Paths.get(rutaDeCarpetaDestino);
        try {
            if (Files.exists(rutaDeCarpetaAeliminar)) { //Si existe la carpeta  procedemos a elimiar los archivos
                // recorremos todos los archivos y subdirectorios.
                Files.walk(rutaDeCarpetaAeliminar)
                        .sorted(Comparator.reverseOrder())
                        .forEach(p -> {
                            try {
                                Files.delete(p);
                            } catch (IOException e) {
                                System.err.println("No se pudo borrar: " + p);
                            }
                        });
                System.out.println("Directorio eliminado con éxito.");
            } else {
                System.out.println("El directorio ingresado " + rutaDeCarpetaDestino + " no existe");
            }
        } catch (IOException e) {
            System.err.println("Ocurrió un error: " + e.getMessage());
        }

    }

}
