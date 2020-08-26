package policy;

import java.util.*;
 
class DiffieHellman
{
 public static void main(String args[])
 {
 Scanner sc=new Scanner(System.in);
 System.out.println("Enter modulo(p)");
 int p=sc.nextInt();
 System.out.println("Enter le nombre g  "+p);
 int g=sc.nextInt();
 System.out.println("Entrer cle secrete pour la personnne 1");
 int a=sc.nextInt();
 System.out.println("Entrer cle secrete pour la personnne 2");
 int b=sc.nextInt();
 
 int A = (int)Math.pow(g,a)%p;
 int B = (int)Math.pow(g,b)%p;
 
 int S_A = (int)Math.pow(B,a)%p;
 int S_B =(int)Math.pow(A,b)%p; 
 
 if(S_A==S_B)
 {
 System.out.println("La personne 1 et la personne 2 peuvent communiquer entre eux!!!");
 System.out.println("La clef prive est = " +S_A); 
 }
 
 else
 {
 System.out.println("La personne 1 et la personne 2 ne peuvent pas communiquer entre eux!!!");
 }
 } 
}