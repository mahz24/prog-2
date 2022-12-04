package edu.prog2;

/**
 * AppPruebas
 */
public class AppPruebas {

  public static void main(String[] args) {
    // System.out.println(factorial(5));
    // System.out.println(sumar(5));
    // System.out.println(fibonacci(20));
    System.out.println(sumaDigitos(1899));
    System.out.println(mcd(6, 124));
    metodoA('N');
    System.out.println(impar(16));
  }

  public static long factorial(int n) {
    if (n == 1) {
      return 1;
    } else {
      return n * factorial(n - 1);
    }
  }

  public static long sumar(int n) {
    if (n == 1) {
      return 1;
    } else {
      return n + sumar(n - 1);
    }
  }

  public static long fibonacci(int n) {
    if (n == 0 || n == 1) {
      return n;
    } else {
      return fibonacci(n - 2) + fibonacci(n - 1);
    }
  }

  public static int sumaDigitos(int n) {
    if (n <= 9) {
      return n;
    } else {
      return sumaDigitos(n / 10) + (n % 9);
    }
  }

  public static int mcd(int m, int n) {
    if (n <= m && m % n == 0) {
      return n;
    } else if (m < n) {
      return mcd(n, m);
    } else {
      return mcd(n, m % n);
    }
  }

  public static void metodoA(char c) {
    if (c > 'A') {
      metodoB(c);
    }
    System.out.println(c);
  }

  private static void metodoB(char c) {
    metodoA(--c);
  }

  public static boolean impar(int n) {
    if (n == 0) {
      return true;
    } else {
      return par(n - 1);
    }
  }

  public static boolean par(int n) {
    if (n == 0) {
      return true;
    } else {
      return impar(n - 1);
    }
  }

}