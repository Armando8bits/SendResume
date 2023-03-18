package Funciones;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

/**
 * @author ROQUEARMANDORAMIREZP
 */
public class Correo {

    Session session;
    Operaciones operacion = new Operaciones();
    final String username = operacion.GetUsuar();
    final String password = operacion.GetContra();

    /* quaplqgxlirudmdo */
    public Correo() {//constructor
        //System.out.println("usu:>"+username+"<");
        //System.out.println("usu:>"+password+"<");
        /*se configura desde 
        https://myaccount.google.com/security
        en la opcion: contraseña de la aplicación */
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public String EnviaCorreos(String[] Datos) {
        String StrDireccion = Datos[1];
        String StrError = "";
        StrDireccion = StrDireccion.replaceAll("/", "\\");

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(Datos[0]));
            message.setSubject(Datos[2]);
            //message.setText(MatrixFinal[i][3]);

            // Crear la parte adjunta
            MimeBodyPart attachmentPart = new MimeBodyPart();

            // Obtener el archivo que deseas adjuntar
            //DataSource source = new FileDataSource("C:\\ruta\\al\\archivo.pdf");
            DataSource source = new FileDataSource(StrDireccion);

            // Configurar la parte adjunta
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(operacion.GetNombreArchivo(StrDireccion)); //obtiene el nombre del archivo

            // Crear la parte del contenido del mensaje
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            // Configurar el contenido del mensaje
            messageBodyPart.setText(Datos[3]);

            // Crear el multipart y agregar la parte adjunta
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart); //el orden de estos dos si importa
            multipart.addBodyPart(attachmentPart);

            // Configurar el mensaje para que utilice el multipart como su contenido
            message.setContent(multipart);

            Transport.send(message);

            //System.out.println("\nEl correo electrónico ha sido enviado con éxito.");
        } catch (MessagingException e) {
            StrError = String.valueOf(e);
            //throw new RuntimeException(e);
        }
        return StrError;
    }
}
