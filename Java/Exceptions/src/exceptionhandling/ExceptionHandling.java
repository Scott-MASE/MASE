package exceptionhandling;

import java.io.IOException;

public class ExceptionHandling {

    public static void main(String args[]) throws Exception {
        a();
        b();
        c();
        e();
        try {
            f();
        } catch (Exception e) {
        }

        A a = new B();
        try {
            a.process();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void a() {
        try {

        } catch (Exception e) {
            System.out.println("Exception caught");
        } finally {
            System.out.println("Finally a");
        }

    }

    public static void b() {
        try {

        } catch (Exception e) {
            return;
        } finally {
            System.out.println("finally b");
        }

    }

    public static void c() {
        try {
            throw new Exception();
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            System.out.println("Finally c");
        }

    }
    //  public static void d() {
    // 	try {

    //     } catch (IOException, NoSuchMethodException, ClassNotFoundException) {
    //     } finally {
    // }
    // }
    public static void e() {
        try {
            throw new IOException();
        } catch (IOException e) {
            System.out.println("Caught IO Exc");
        } finally {
            System.out.println("finally e");
        }
    }

    public static void f() {
        try {
            throw new Exception();
        } catch (Exception e) {
            System.out.println(e.toString());
        } finally {
            System.out.println("Finally f");
        }
    }

    public static void checked1() {

    }

    public static void checked2() {

    }

    public static void checked3() {

    }

    public static void checked4() {

    }

    public static void unchecked1() {

    }

    public static void unchecked2() {

    }

    public static void unchecked3() {

    }

    public static void unchecked4() {

    }

    public static void x() {

    }

    public static void y() {

    }

    public static void z() {

    }

}

class A {

    void process() throws IOException {
        throw new IOException();
    }

}

class B extends A {

    // compiler error - "process() in B cannot override process() in A
    //                   overridden method does not throw NoSuchMethodException"
    // To fix:
    //   1. Change A::process() to throw NoSuchMethodException as well as IOException
    //   2. Change A::process() to throw Exception only (this catches all subclasses)
    //   3. Make B::process() throw IOException only
    //   4. Make B::process() throw a subset of IOException e.g. EOFException, no exceptions at all
    @Override
    //  void process() throws IOException, NoSuchMethodException{
    //  void process() throws EOFException{
    void process() {
        System.out.println("B");
    }
}
