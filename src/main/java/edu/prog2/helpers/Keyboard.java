package edu.prog2.helpers; // paquete donde se encuentra la clase

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Keyboard {

  public static final char ENTER = 13; // definición de una constante
  public static Scanner sc = new Scanner(System.in).useDelimiter("[\n]+|[\r\n]+");

  /**
   * Retorna un string
   * 
   * @param mensaje string que va a aprecer en pantalla para que el usuario
   *                ingrese el string
   * @return el mensaje que se pasa por parametros
   */
  public static String readString(String mensaje) {
    System.out.print(mensaje);
    return sc.nextLine();
  }

  public static String readString(int from, int to, String message) {
    String value;
    int tmp = Math.min(from, to);
    if (tmp == to) {
      to = from;
      from = tmp;
    }

    int length;

    do {
      value = readString(message);
      length = value.length();
      if (length < from || length > to) {
        System.out.print("Longitud no permitida. ");
      }
    } while (length < from || length > to);

    return value;
  }

  /**
   * Retorna un entero que se procesa, si el valor que se ingresa en el scanner no
   * es un entero entonces atrapa el error y deja volver a ingresar un valor
   * 
   * @param mensaje string que va a aprecer en pantalla para que el usuario
   *                ingrese el entero
   * @return el entero que entra por el scanner
   */
  public static int readInt(String mensaje) {
    boolean ok;
    int value = Integer.MIN_VALUE;
    System.out.print(mensaje);

    do {
      try { // intente ejecutar las instrucciones del bloque
        ok = true;
        value = sc.nextInt();
      } catch (InputMismatchException e) { // capture posibles excepciones
        ok = false;
        System.out.print(">> Valor erróneo. " + mensaje);
      } finally { // suceda lo que suceda, ejecute las instrucciones del bloque
        sc.nextLine();
      }
    } while (!ok);

    return value;
  }

  public static int readInt(int from, int to, String mensaje) {
    int value;
    int tmp = Math.min(from, to);
    if (tmp == to) {
      to = from;
      from = tmp;
    }

    do {
      value = readInt(mensaje);
      if (value < from || value > to) {
        System.out.print("Rango inválido. ");
      }
    } while (value < from || value > to);

    return value;
  }

  /**
   * Retorna un Long, si el valor que se ingresa en el scanner no
   * es un long entonces atrapa el error y deja volver a ingresar un valor
   * 
   * @param mensaje string que va a aprecer en pantalla para que el usuario
   *                ingrese el long
   * @return el long que entra por el scanner
   */
  public static long readLong(String mensaje) {
    boolean ok;
    long value = Long.MIN_VALUE;
    System.out.print(mensaje);

    do {
      try { // intente ejecutar las instrucciones del bloque
        ok = true;
        value = sc.nextLong();
      } catch (InputMismatchException e) { // capture posibles excepciones
        ok = false;
        System.out.print(">> Valor erróneo. " + mensaje);
      } finally { // suceda lo que suceda, ejecute las instrucciones del bloque
        sc.nextLine();
      }
    } while (!ok);

    return value;
  }

  public static long readLong(long from, long to, String mensaje) {
    long value;
    long tmp = Math.min(from, to);
    if (tmp == to) {
      to = from;
      from = tmp;
    }

    do {
      value = readLong(mensaje);
      if (value < from || value > to) {
        System.out.print("Rango inválido. ");
      }
    } while (value < from || value > to);

    return value;
  }

  /**
   * Retorna un double, si el valor que se ingresa en el scanner no
   * es un double entonces atrapa el error y deja volver a ingresar un valor
   * 
   * @param mensaje string que va a aprecer en pantalla para que el usuario
   *                ingrese el double
   * @return el double que entra por scanner
   */
  public static double readDouble(String mensaje) {
    boolean ok;
    double value = Double.NaN;
    System.out.print(mensaje);

    do {
      try { // intente ejecutar las instrucciones del bloque
        ok = true;
        value = sc.nextDouble();
      } catch (InputMismatchException e) { // capture posibles excepciones
        ok = false;
        System.out.print(">> Valor erróneo. " + mensaje);
      } finally { // suceda lo que suceda, ejecute las instrucciones del bloque
        sc.nextLine();
      }
    } while (!ok);

    return value;
  }

  public static double readDouble(double from, double to, String mensaje) {
    double value;
    double tmp = Math.min(from, to);
    if (tmp == to) {
      to = from;
      from = tmp;
    }

    do {
      value = readDouble(mensaje);
      if (value < from || value > to) {
        System.out.print("Rango inválido. ");
      }
    } while (value < from || value > to);

    return value;
  }

  /**
   * Retorna un valor booleano, si el valor que ingresa por scanner coincide con
   * (s, si, yes, y, true, t) entonces retorna true, si coincide con (no, n,
   * false, f, not) entonces retorna un false
   * 
   * @param message string que va a aprecer en pantalla para que el usuario
   *                ingrese el booleano
   * @return un boolean dependiendo de lo que ingrese el usuario
   */
  public static boolean readBoolean(String message) {
    boolean ok;
    boolean value = false;
    System.out.print(message);

    do {
      try {
        ok = true;
        String str = ' ' + sc.next().toLowerCase().trim() + ' ';
        if (" si s true t yes y ".contains(str)) {
          value = true;
        } else if (" no n false f not ".contains(str)) {
          value = false;
        } else {
          throw new InputMismatchException();
        }
      } catch (InputMismatchException e) {
        ok = false;
        System.out.print(">> Se esperaba [si|s|true|t|yes|y|no|not|n|false|f]"
            + message);
      } finally {
        sc.nextLine();
      }
    } while (!ok);

    return value;
  }

  /**
   * Retorna la fecha que se ingresa por scanner
   * 
   * @param message string que va a aprecer en pantalla para que el usuario
   *                ingrese la fecha
   * @return Fecha que se ingresa por scanner
   */
  public static LocalDate readDate(String message) {
    boolean ok;
    LocalDate date = LocalDate.now();
    System.out.print(message);

    do {
      try {
        ok = true;
        String strDate = sc.next().trim();
        if (!strDate.toLowerCase().equals("hoy")) {
          date = LocalDate.parse(strDate);
        }
      } catch (DateTimeException dtps) {
        ok = false;
        System.out.print(">> Fecha errónea. " + message);
      } finally {
        sc.nextLine();
      }
    } while (!ok);

    return date;
  }

  /**
   * Retorna la fecha y hora que se ingresa por scanner
   * 
   * @param message string que va a aprecer en pantalla para que el usuario
   *                ingrese la fecha y hora
   * @return fecha y hora que se ingresa por scanner
   */
  public static LocalDateTime readDateTime(String message) {
    boolean ok;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a");
    LocalDateTime dateTime = LocalDateTime.now();
    System.out.print(message);

    do {
      try {
        ok = true;
        String strDate = sc.next().trim();
        if (!strDate.toLowerCase().equals("ahora")) {
          dateTime = LocalDateTime.parse(strDate, formatter);
        }
      } catch (DateTimeParseException dtps) {
        ok = false;
        System.out.print(">> Fecha y hora erróneas. " + message);
      } finally {
        sc.nextLine();
      }
    } while (!ok);

    return dateTime;
  }

  public static <T extends Enum<T>> T readEnum(Class<T> c) {
    Object[] allItems = (EnumSet.allOf(c)).toArray();
    String message = String.format("%nOpciones de %s:", c.getSimpleName());

    int i;
    for (i = 0; i < allItems.length; i++) {
      String item = allItems[i].toString().replaceAll("_", " ");
      message += String.format("%n%3d-%s", i + 1, item);
    }

    message = String.format(
        "%s%nElija un tipo entre 1 y %d: ", message, allItems.length);

    do {
      i = readInt(message);
    } while (i < 1 || i > allItems.length);

    return Enum.valueOf(c, allItems[i - 1].toString());
  }

  /**
   * Esta función se encarga de leer un duración que se pide por consola al
   * usuario
   * 
   * @param message que se va a mostrar en consola
   * @return la duracion
   */
  public static Duration readDuration(String message) {
    boolean ok;
    Duration duration = Duration.ofMinutes(0);
    System.out.print(message);
    do {
      try {
        ok = true;
        String introDuration = sc.next().trim();
        String[] misTiempos = introDuration.split(":");
        int hours = Integer.parseInt(misTiempos[0]) * 60;
        int minutes = Integer.parseInt(misTiempos[1]);
        duration = Duration.ofMinutes(hours + minutes);

      } catch (DateTimeParseException dtpe) {
        ok = false;
        System.out.print("Duración erronea " + message);
      } finally {
        sc.nextLine();
      }

    } while (!ok);
    return duration;
  }
}
