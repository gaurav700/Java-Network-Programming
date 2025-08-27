package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args){
        try (Socket socket = new Socket("localhost", 5000)){
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            Scanner scanner = new Scanner(System.in);

            String request;
            String response;
            do{
                System.out.println("Enter string to be echoed (sent to server): ");
                request = scanner.nextLine();

                output.println(request);

                if(!request.equals("exit")){
                    response = input.readLine();
                    System.out.println(response);
                }
            }while(!request.equals("exit"));
        }catch (IOException ex){
            System.out.println("Client Error: "+ex.getMessage());
        }finally {
            System.out.println("Client Disconnected");
        }
    }
}
