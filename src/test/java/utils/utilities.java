package utils;

import org.apache.commons.io.FileUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;

public class utilities {
    Properties props = new Properties(); //properties para leer un documento
    WebDriver driver; //Inicializar driver
    String directorio = System.getProperty("user.dir");

    //Variables globales para tomar screenshot y crear documento
    String rutaDeEvidencias = directorio + "\\src\\test\\java\\config\\configuration_evidencias.properties"; //Ruta donde se modifica el nombre de reporte de pruebas y la carpeta donde se va a crear

    private static final String URL = "https://www.liverpool.com.mx/"; //URL de liverpool

    public utilities(WebDriver driver) {
        this.driver = driver;
    }

    public void takeScrenShot(String nombreArchivo) {
        String renombradoDeimagen = nombreArchivo.replace(" ", "_"); //Variable para renombrar imagenes
        try {
            props.load(Files.newInputStream(new File(rutaDeEvidencias).toPath()));
            String carpetaDeImagenes = String.valueOf(props.get("carpetaDestino"));
            String rutaFinDeImagenes = directorio + "\\evidencias\\" + carpetaDeImagenes;
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
            String capturaImg = rutaFinDeImagenes + "\\" + renombradoDeimagen + ".png";
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

    public void crearReporteWord() throws Exception {
        props.load(Files.newInputStream(new File(rutaDeEvidencias).toPath()));
        String nombreDeCarpeta = String.valueOf(props.get("carpetaDestino"));
        String nombreDeReporte = String.valueOf(props.get("nombreDeReporteDestino"));
        String rutaDondeExtraeremosLasImagenes = directorio + "\\evidencias\\" + nombreDeCarpeta;
        String archivoSalidaDeReporte = rutaDondeExtraeremosLasImagenes + "\\"+nombreDeReporte+".docx";
        Path rutaDeArchivoEnCasoDeExistir = Paths.get(archivoSalidaDeReporte);
        //Si existe el documento word se procedera a borrar la informacion de ese documento
        if (Files.exists(rutaDeArchivoEnCasoDeExistir)) {
            eliminarInformacionDeDocumentoWord(rutaDeArchivoEnCasoDeExistir);
        }
        //LLenar de informacion el archivo word
        vaciarInformacionEnDocumentoWord(rutaDondeExtraeremosLasImagenes,rutaDeArchivoEnCasoDeExistir);
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

    public void vaciarInformacionEnDocumentoWord(String rutaDeDondeExtrarLasImagenes, Path rutaDeArchivoAllenar){
        /**Extraccion de los nombres de la imagenes**/
        try (XWPFDocument doc = new XWPFDocument()) {
            File folderDeDondeSeExtraranLasImagenes = new File(rutaDeDondeExtrarLasImagenes);
            File[] listOfFiles = folderDeDondeSeExtraranLasImagenes.listFiles();
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (file.isFile() && esImagen(file.getName())) {
                        //Agregar titulo con el nombre de la imagen
                        XWPFParagraph p = doc.createParagraph();
                        XWPFRun run = p.createRun();
                        String paso=file.getName();
                        String nombreDePasoParaReporte= paso.replace("_"," ");
                        run.setText(nombreDePasoParaReporte.substring(0,nombreDePasoParaReporte.length()-4));
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

}
